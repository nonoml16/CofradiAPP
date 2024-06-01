import 'package:carousel_slider/carousel_slider.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_cofradiapp/paso/bloc/paso/paso_bloc.dart';
import 'package:flutter_cofradiapp/paso/repositories/paso_repository.dart';
import 'package:flutter_cofradiapp/paso/repositories/paso_repository_impl.dart';
import 'package:flutter_cofradiapp/screen/musica/components/nombre_musica_card_widget.dart';
import 'package:shared_preferences/shared_preferences.dart';

class PasoDetailsScreen extends StatefulWidget {
  final String id;
  const PasoDetailsScreen({super.key, required this.id});

  @override
  State<PasoDetailsScreen> createState() => _PasoDetailsScreenState();
}

class _PasoDetailsScreenState extends State<PasoDetailsScreen> {
  late PasoRepository pasoRepository;
  String? token;

  @override
  void initState() {
    pasoRepository = PasoRepositoryImpl();
    super.initState();
    _loadToken();
  }

  _loadToken() async {
    final prefs = await SharedPreferences.getInstance();
    setState(() {
      token = prefs.getString('token');
    });
  }

  @override
  Widget build(BuildContext context) {
    return BlocProvider(
      create: (context) =>
          PasoBloc(pasoRepository)..add(PasoFetchDetails(widget.id)),
      child: _pasoDetails(),
    );
  }

  _pasoDetails() {
    return BlocBuilder<PasoBloc, PasoState>(
        builder: (BuildContext context, PasoState state) {
      if (state is PasoFetchSuccess) {
        return Scaffold(
          appBar: AppBar(
            title: Text(
              "Hermandad de ${state.paso.hermandad!}",
              style: const TextStyle(
                fontFamily: 'WorkSans',
                fontWeight: FontWeight.w600,
                fontSize: 26.0,
              ),
            ),
          ),
          body: Padding(
            padding: const EdgeInsets.fromLTRB(18, 10, 18, 10),
            child: SingleChildScrollView(
              child: Column(
                children: [
                  CarouselSlider(
                    options: CarouselOptions(
                      height: 270.0,
                      aspectRatio: 343 / 270,
                      viewportFraction: 1.0,
                      enlargeCenterPage: false,
                    ),
                    items: state.paso.imagenes!.map((url) {
                      return Builder(
                        builder: (BuildContext context) {
                          return Container(
                            width: MediaQuery.of(context).size.width,
                            margin: const EdgeInsets.symmetric(horizontal: 5.0),
                            decoration: BoxDecoration(
                              borderRadius: BorderRadius.circular(16),
                              image: DecorationImage(
                                image: NetworkImage(
                                    'http://10.0.2.2:8080/download/$url',
                                    headers: {
                                      'Content-Type': 'application/json',
                                      'Accept': 'application/json',
                                      'Authorization': 'Bearer $token',
                                    }),
                                fit: BoxFit.cover,
                              ),
                            ),
                          );
                        },
                      );
                    }).toList(),
                  ),
                  Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      const Padding(
                        padding: EdgeInsets.only(top: 28),
                        child: Text(
                          'Imagen:',
                          style: TextStyle(
                            fontFamily: 'WorkSans',
                            fontWeight: FontWeight.w600,
                            fontSize: 20.0,
                          ),
                        ),
                      ),
                      Padding(
                        padding: const EdgeInsets.only(top: 9),
                        child: Text(
                          state.paso.imagen!,
                          style: const TextStyle(
                            fontFamily: 'WorkSans',
                            fontWeight: FontWeight.w400,
                            fontSize: 12.0,
                          ),
                        ),
                      ),
                      const Padding(
                        padding: EdgeInsets.only(top: 9),
                        child: Text(
                          'Capataz:',
                          style: TextStyle(
                            fontFamily: 'WorkSans',
                            fontWeight: FontWeight.w600,
                            fontSize: 20.0,
                          ),
                        ),
                      ),
                      Padding(
                        padding: const EdgeInsets.only(top: 9),
                        child: Text(
                          state.paso.capataz!,
                          style: const TextStyle(
                            fontFamily: 'WorkSans',
                            fontWeight: FontWeight.w400,
                            fontSize: 12.0,
                          ),
                        ),
                      ),
                      const Padding(
                        padding: EdgeInsets.only(top: 9),
                        child: Text(
                          'Número de costaleros:',
                          style: TextStyle(
                            fontFamily: 'WorkSans',
                            fontWeight: FontWeight.w600,
                            fontSize: 20.0,
                          ),
                        ),
                      ),
                      Padding(
                        padding: const EdgeInsets.only(top: 9),
                        child: Text(
                          "${state.paso.numCostaleros.toString()} costaleros",
                          style: const TextStyle(
                            fontFamily: 'WorkSans',
                            fontWeight: FontWeight.w400,
                            fontSize: 12.0,
                          ),
                        ),
                      ),
                      const Padding(
                        padding: EdgeInsets.only(top: 9),
                        child: Text(
                          'Acompañamiento musical:',
                          style: TextStyle(
                            fontFamily: 'WorkSans',
                            fontWeight: FontWeight.w600,
                            fontSize: 20.0,
                          ),
                        ),
                      ),
                      state.paso.acompannamiento!.isEmpty
                          ? Container(
                              width: double.infinity,
                              padding:
                                  const EdgeInsets.symmetric(vertical: 10.0),
                              child: const Text(
                                'Vacío',
                                style: TextStyle(
                                  fontFamily: 'WorkSans',
                                  fontWeight: FontWeight.w400,
                                  fontSize: 12.0,
                                ),
                              ),
                            )
                          : ListView.builder(
                              shrinkWrap: true,
                              physics: const NeverScrollableScrollPhysics(),
                              itemCount: state.paso.acompannamiento!.length,
                              itemBuilder: (context, index) {
                                return NombreMusicaCardWidget(
                                    musica: state.paso.acompannamiento![index]);
                              },
                            ),
                    ],
                  )
                ],
              ),
            ),
          ),
        );
      } else if (state is PasoFetchError) {
        return Text(state.errorMessage);
      }
      return const Center(child: CircularProgressIndicator());
    });
  }
}
