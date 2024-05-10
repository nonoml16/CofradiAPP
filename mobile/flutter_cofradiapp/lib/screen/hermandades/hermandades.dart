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

const List<String> list = <String>[
  'DOMINGO_DE_RAMOS',
  'LUNES_SANTO',
  'MARTES_SANTO',
  'MIERCOLES_SANTO'
];

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
    String dropdownValue = list.first;
    return BlocBuilder<HermandadBloc, HermandadState>(
      builder: (BuildContext context, HermandadState state) {
        if (state is HermandadFetchSuccess) {
          return Scaffold(
            appBar: AppBar(
              title: const Text('Lista de Hermandades'),
            ),
            body: Column(
              children: [
                Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: DropdownButton<String>(
                    value: dropdownValue,
                    icon: const Icon(Icons.arrow_downward),
                    elevation: 16,
                    style: const TextStyle(color: Colors.deepPurple),
                    onChanged: (String? value) {
                      // This is called when the user selects an item.
                      setState(() {
                        dropdownValue = value!;
                      });
                    },
                    items: list.map<DropdownMenuItem<String>>((String value) {
                      return DropdownMenuItem<String>(
                        value: value,
                        child: Text(value),
                      );
                    }).toList(),
                    hint: const Text('Selecciona una hermandad'),
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
