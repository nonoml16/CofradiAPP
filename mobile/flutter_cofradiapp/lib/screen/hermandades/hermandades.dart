import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_cofradiapp/hermandad/bloc/hermandad/hermandad_bloc.dart';
import 'package:flutter_cofradiapp/hermandad/repositories/hermandad_repository.dart';
import 'package:flutter_cofradiapp/hermandad/repositories/hermandad_repository_impl.dart';
import 'package:flutter_cofradiapp/screen/hermandades/components/hermandad_card_widget.dart';

class HermandadesScreen extends StatefulWidget {
  const HermandadesScreen({super.key});

  @override
  State<HermandadesScreen> createState() => _HermandadesScreenState();
}

const Map<String, String> list = {
  'VIERNES_DOLORES': 'Viernes de Dolores',
  'SABADO_PASION': 'Sábado de Pasión',
  'DOMINGO_DE_RAMOS': 'Domingo de Ramos',
  'LUNES_SANTO': 'Lunes Santo',
  'MARTES_SANTO': 'Martes Santo',
  'MIERCOLES_SANTO': 'Miércoles Santo',
  'JUEVES_SANTO': 'Jueves Santo',
  'VIERNES_SANTO_MADRUGA': 'Madrugá',
  'VIERNES_SANTO': 'Viernes Santo',
  'SABADO_SANTO': 'Sábado Santo',
  'DOMINGO_RESURRECCION': 'Domingo de Resurrección'
};

class _HermandadesScreenState extends State<HermandadesScreen> {
  late HermandadRepository hermandadRepository;

  late String dia = 'DOMINGO_DE_RAMOS';

  @override
  void initState() {
    hermandadRepository = HermandadRepositoryImpl();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return BlocProvider(
      create: (context) =>
          HermandadBloc(hermandadRepository)..add(HermandadFetchList(dia)),
      child: _hermandadesDiaList(),
    );
  }

  _hermandadesDiaList() {
    return BlocBuilder<HermandadBloc, HermandadState>(
      builder: (BuildContext context, HermandadState state) {
        if (state is HermandadFetchSuccess) {
          return Scaffold(
            appBar: AppBar(
              title: const Text(
                'Hermandades',
                style: TextStyle(
                  fontFamily: 'WorkSans',
                  fontWeight: FontWeight.w600,
                  fontSize: 28.0,
                ),
              ),
            ),
            body: Column(
              children: [
                Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: DropdownButtonHideUnderline(
                    // Esto oculta la línea inferior
                    child: DropdownButton<String>(
                      value: dia,
                      icon: const Icon(Icons.arrow_downward),
                      elevation: 16,
                      style: const TextStyle(
                        fontFamily: 'WorkSans',
                        fontWeight: FontWeight
                            .w400, // Esto establece la fuente a Work Sans Regular
                        fontSize:
                            18.0, // Esto establece el tamaño de la fuente a 18px
                        color: Colors
                            .black, // Esto establece el color del texto a negro
                      ),
                      onChanged: (String? value) {
                        setState(() {
                          dia = value!;
                          context
                              .read<HermandadBloc>()
                              .add(HermandadFetchList(dia));
                        });
                      },
                      items:
                          list.entries.map<DropdownMenuItem<String>>((entry) {
                        return DropdownMenuItem<String>(
                          value: entry.key,
                          child: Text(entry.value),
                        );
                      }).toList(),
                      hint: const Text('Selecciona una hermandad'),
                    ),
                  ),
                ),
                Expanded(
                  child: ListView.builder(
                    itemCount: state.hermandadList.length,
                    itemBuilder: (context, index) {
                      return Padding(
                        padding: const EdgeInsets.only(bottom: 20.0),
                        child: HermandadCardWidget(
                            hermandadList: state.hermandadList[index]),
                      );
                    },
                  ),
                ),
              ],
            ),
          );
        } else if (state is HermandadFetchError) {
          return Text(state.errorMessage);
        }
        return const Center(child: CircularProgressIndicator());
      },
    );
  }
}
