import 'package:carousel_slider/carousel_slider.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_cofradiapp/home/bloc/home/home_bloc.dart';
import 'package:flutter_cofradiapp/home/repositories/home_repository.dart';
import 'package:flutter_cofradiapp/home/repositories/home_repository_impl.dart';
import 'package:flutter_cofradiapp/screen/cards/components/card_widget.dart';
import 'package:flutter_cofradiapp/screen/hermandades/components/hermandad_card_sm_widget.dart';
import 'package:flutter_cofradiapp/screen/hermandades/components/hermandad_card_widget.dart';
import 'package:flutter_cofradiapp/screen/musica/components/musica_card_sm_widget.dart';
import 'package:shared_preferences/shared_preferences.dart';

class HomePageScreen extends StatefulWidget {
  const HomePageScreen({super.key});

  @override
  State<HomePageScreen> createState() => _HomePageScreenState();
}

class _HomePageScreenState extends State<HomePageScreen> {
  late HomeRepository homeRepository;
  String? token;

  @override
  void initState() {
    homeRepository = HomeRepositoryImpl();
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
      create: (context) => HomeBloc(homeRepository)..add(HomePageFetch()),
      child: _homePageScreen(),
    );
  }

  _homePageScreen() {
    return BlocBuilder<HomeBloc, HomeState>(
        builder: (BuildContext context, HomeState state) {
      if (state is HomeFetchSuccess) {
        return Scaffold(
          appBar: AppBar(
            title: const Text(
              'Inicio',
              style: TextStyle(
                fontFamily: 'WorkSans',
                fontWeight: FontWeight.w600,
                fontSize: 28.0,
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
                      height: 181.0,
                      aspectRatio: 343 / 181,
                      viewportFraction: 1.0,
                      enlargeCenterPage: false,
                    ),
                    items: state.homeEntity.carruselFotos!.map((url) {
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
                        padding: EdgeInsets.only(top: 9),
                        child: Text(
                          'Hermandad del dia:',
                          style: TextStyle(
                            fontFamily: 'WorkSans',
                            fontWeight: FontWeight.w600,
                            fontSize: 20.0,
                          ),
                        ),
                      ),
                      HermandadCardWidget(
                          hermandadList: state.homeEntity.hermandadDelDia!),
                      const Padding(
                        padding: EdgeInsets.only(top: 9),
                        child: Text(
                          'Cards destacadas:',
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
                          itemCount: state.homeEntity.cardsDestacadas!.length,
                          itemBuilder: (context, index) {
                            return Padding(
                              padding: const EdgeInsets.all(5),
                              child: SizedBox(
                                height: 220,
                                width: 160,
                                child: CardWidget(
                                    cardL: state
                                        .homeEntity.cardsDestacadas![index]),
                              ),
                            );
                          },
                        ),
                      ),
                      const Align(
                        alignment: Alignment.centerLeft,
                        child: Padding(
                          padding: EdgeInsets.only(top: 14),
                          child: Text('Hermandades destacadas:',
                              style: TextStyle(
                                fontFamily: 'WorkSans',
                                fontWeight: FontWeight.w600,
                                fontSize: 20.0,
                              )),
                        ),
                      ),
                      SizedBox(
                        height: 150,
                        child: ListView.builder(
                          scrollDirection: Axis.horizontal,
                          itemCount:
                              state.homeEntity.hermandadesDestacadas!.length,
                          itemBuilder: (context, index) {
                            return Padding(
                              padding: const EdgeInsets.only(bottom: 20.0),
                              child: HermandadCardSmWidget(
                                  hermandadesFavoritas: state.homeEntity
                                      .hermandadesDestacadas![index]),
                            );
                          },
                        ),
                      ),
                      const Align(
                        alignment: Alignment.centerLeft,
                        child: Padding(
                          padding: EdgeInsets.only(top: 0),
                          child: Text('Bandas destacadas:',
                              style: TextStyle(
                                fontFamily: 'WorkSans',
                                fontWeight: FontWeight.w600,
                                fontSize: 20.0,
                              )),
                        ),
                      ),
                      SizedBox(
                        height: 150,
                        child: ListView.builder(
                          scrollDirection: Axis.horizontal,
                          itemCount: state.homeEntity.bandasDestacadas!.length,
                          itemBuilder: (context, index) {
                            return Padding(
                              padding: const EdgeInsets.only(bottom: 20.0),
                              child: MusicaCardSmWidget(
                                  musica: state
                                      .homeEntity.bandasDestacadas![index]),
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
        );
      } else if (state is HomeFetchError) {
        return Text(state.errorMessage);
      }
      return const Center(child: CircularProgressIndicator());
    });
  }
}
