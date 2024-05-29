import 'package:carousel_slider/carousel_options.dart';
import 'package:carousel_slider/carousel_slider.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_cofradiapp/hermandad/bloc/hermandad/hermandad_bloc.dart';
import 'package:flutter_cofradiapp/hermandad/repositories/hermandad_repository.dart';
import 'package:flutter_cofradiapp/hermandad/repositories/hermandad_repository_impl.dart';
import 'package:flutter_cofradiapp/screen/cards/components/card_widget.dart';
import 'package:flutter_cofradiapp/screen/musica/musica_details.dart';
import 'package:shared_preferences/shared_preferences.dart';

class HermandadDetailsScreen extends StatefulWidget {
  final String id;
  const HermandadDetailsScreen({super.key, required this.id});

  @override
  State<HermandadDetailsScreen> createState() => _HermandadDetailsScreenState();
}

class _HermandadDetailsScreenState extends State<HermandadDetailsScreen> {
  late HermandadRepository hermandadRepository;
  String? token;

  @override
  void initState() {
    hermandadRepository = HermandadRepositoryImpl();
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
      create: (context) => HermandadDetailsBloc(hermandadRepository)
        ..add(HermandadViewDetail(widget.id)),
      child: _hermandadDetails(),
    );
  }

  _hermandadDetails() {
    return BlocBuilder<HermandadDetailsBloc, HermandadState>(
        builder: (BuildContext context, HermandadState state) {
      if (state is HermandadDetailsFetchSuccess) {
        return Scaffold(
          appBar: AppBar(
            title: Text(
              'Hermandad de ${state.hermandad.nombre!}',
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
                    items: state.hermandad.imagenes!.map((url) {
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
                          'Nombre completo:',
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
                          state.hermandad.nombreCompleto!,
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
                          'Año de fundación:',
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
                          state.hermandad.annoFundacion.toString(),
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
                          'De interés:',
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
                          state.hermandad.deInteres!,
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
                          'Pasos:',
                          style: TextStyle(
                            fontFamily: 'WorkSans',
                            fontWeight: FontWeight.w600,
                            fontSize: 20.0,
                          ),
                        ),
                      ),
                      ListView.builder(
                        shrinkWrap: true,
                        physics: const NeverScrollableScrollPhysics(),
                        itemCount: state.hermandad.pasos!.length,
                        itemBuilder: (context, index) {
                          return GestureDetector(
                            onTap: () {
                              Navigator.push(
                                  context,
                                  MaterialPageRoute(
                                    builder: (context) => MusicaDetailsScreen(
                                        id: state.hermandad.pasos![index].id!),
                                  ));
                            },
                            child: Center(
                              child: Card(
                                child: Column(
                                  mainAxisSize: MainAxisSize.min,
                                  children: <Widget>[
                                    ListTile(
                                      title: Text(
                                        state.hermandad.pasos![index].imagen!,
                                        style: const TextStyle(
                                          fontFamily: 'WorkSans',
                                          fontWeight: FontWeight.w500,
                                          fontSize: 16.0,
                                        ),
                                      ),
                                    ),
                                  ],
                                ),
                              ),
                            ),
                          );
                        },
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
                      ListView.builder(
                        shrinkWrap: true,
                        physics: const NeverScrollableScrollPhysics(),
                        itemCount: state.hermandad.banda!.length,
                        itemBuilder: (context, index) {
                          return GestureDetector(
                            onTap: () {
                              Navigator.push(
                                  context,
                                  MaterialPageRoute(
                                    builder: (context) => MusicaDetailsScreen(
                                        id: state.hermandad.banda![index].id!),
                                  ));
                            },
                            child: Center(
                              child: Card(
                                child: Column(
                                  mainAxisSize: MainAxisSize.min,
                                  children: <Widget>[
                                    ListTile(
                                      title: Text(
                                        state.hermandad.banda![index].nombre!,
                                        style: const TextStyle(
                                          fontFamily: 'WorkSans',
                                          fontWeight: FontWeight.w500,
                                          fontSize: 16.0,
                                        ),
                                      ),
                                    ),
                                  ],
                                ),
                              ),
                            ),
                          );
                        },
                      ),
                      const Padding(
                        padding: EdgeInsets.only(top: 9),
                        child: Text(
                          'Sede:',
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
                          state.hermandad.sede!,
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
                          'Cards:',
                          style: TextStyle(
                            fontFamily: 'WorkSans',
                            fontWeight: FontWeight.w600,
                            fontSize: 20.0,
                          ),
                        ),
                      ),
                      SizedBox(
                        height: 230,
                        child: ListView.builder(
                          scrollDirection: Axis.horizontal,
                          itemCount: state.hermandad.cards!.length,
                          itemBuilder: (context, index) {
                            return Padding(
                              padding: const EdgeInsets.all(5),
                              child: SizedBox(
                                height: 220,
                                width: 160,
                                child: CardWidget(
                                    cardL: state.hermandad.cards![index]),
                              ),
                            );
                          },
                        ),
                      ),
                    ],
                  )
                ],
              ),
            ),
          ),
          bottomNavigationBar: BottomAppBar(
            color: const Color(0xFFFBE7F5),
            child: Padding(
              padding: const EdgeInsets.all(0),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceAround,
                children: <Widget>[
                  Column(
                    children: [
                      Text(
                        state.hermandad.numNazarenos.toString(),
                        style: const TextStyle(
                          fontFamily: 'WorkSans',
                          fontWeight: FontWeight.w600,
                          fontSize: 25.0,
                        ),
                      ),
                      const Text(
                        'N. Nazarenos:',
                        style: TextStyle(
                          fontFamily: 'WorkSans',
                          fontWeight: FontWeight.w600,
                          fontSize: 14.0,
                        ),
                      ),
                    ],
                  ),
                  Column(
                    children: [
                      Text(
                        state.hermandad.numHermanos.toString(),
                        style: const TextStyle(
                          fontFamily: 'WorkSans',
                          fontWeight: FontWeight.w600,
                          fontSize: 25.0,
                        ),
                      ),
                      const Text(
                        'N. Hermanos:',
                        style: TextStyle(
                          fontFamily: 'WorkSans',
                          fontWeight: FontWeight.w600,
                          fontSize: 14.0,
                        ),
                      ),
                    ],
                  ),
                  Column(
                    children: [
                      Text(
                        '${state.hermandad.tiempoPaso} min',
                        style: const TextStyle(
                          fontFamily: 'WorkSans',
                          fontWeight: FontWeight.w600,
                          fontSize: 25.0,
                        ),
                      ),
                      const Text(
                        'Tiempo de paso:',
                        style: TextStyle(
                          fontFamily: 'WorkSans',
                          fontWeight: FontWeight.w600,
                          fontSize: 14.0,
                        ),
                      ),
                    ],
                  ),
                ],
              ),
            ),
          ),
        );
      } else if (state is HermandadDetailsFetchError) {
        return Text(state.errorMessage);
      }
      return const Center(child: CircularProgressIndicator());
    });
  }
}
