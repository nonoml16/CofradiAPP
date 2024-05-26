import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_cofradiapp/musica/bloc/bloc/musica_bloc.dart';
import 'package:flutter_cofradiapp/musica/repositories/musica_repository.dart';
import 'package:flutter_cofradiapp/musica/repositories/musica_repository_impl.dart';
import 'package:flutter_cofradiapp/screen/hermandades/components/hermandad_card_sm_widget.dart';
import 'package:shared_preferences/shared_preferences.dart';

class MusicaDetailsScreen extends StatefulWidget {
  final String id;
  const MusicaDetailsScreen({super.key, required this.id});

  @override
  State<MusicaDetailsScreen> createState() => _MusicaDetailsScreenState();
}

class _MusicaDetailsScreenState extends State<MusicaDetailsScreen> {
  late MusicaRepository musicaRepository;
  String? token;

  @override
  void initState() {
    musicaRepository = MusicaRepositoryImpl();
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
          MusicaBloc(musicaRepository)..add(MusicaFetchDetail(widget.id)),
      child: _musicaDetails(),
    );
  }

  _musicaDetails() {
    return BlocBuilder<MusicaBloc, MusicaState>(
      builder: (BuildContext context, MusicaState state) {
        if (state is MusicaFetchSuccess) {
          return Scaffold(
            appBar: AppBar(
              title: Text(
                state.musica.nombre!,
                style: const TextStyle(
                  fontFamily: 'WorkSans',
                  fontWeight: FontWeight.w600,
                  fontSize: 26.0,
                ),
              ),
            ),
            body: Padding(
              padding: EdgeInsets.only(top: 26),
              child: Padding(
                padding: EdgeInsets.all(27),
                child: Column(
                  children: [
                    Center(
                      child: Column(
                        children: [
                          SizedBox(
                              height: 150,
                              width: 150,
                              child: ClipRRect(
                                  borderRadius: const BorderRadius.all(
                                      Radius.circular(
                                          100)), // Para hacer un borde circular
                                  child: Image(
                                    image: NetworkImage(
                                        'http://10.0.2.2:8080/download/null',
                                        headers: {
                                          'Content-Type': 'application/json',
                                          'Accept': 'application/json',
                                          'Authorization': 'Bearer $token',
                                        }),
                                  ))),
                          Padding(
                            padding: const EdgeInsets.only(top: 11),
                            child: Text(state.musica.nombre!,
                                style: const TextStyle(
                                  fontFamily: 'WorkSans',
                                  fontWeight: FontWeight.w600,
                                  fontSize: 24.0,
                                )),
                          ),
                        ],
                      ),
                    ),
                    const Align(
                      alignment: Alignment.centerLeft,
                      child: Padding(
                        padding: EdgeInsets.only(top: 34),
                        child: Text('Año de fundacion:',
                            style: TextStyle(
                              fontFamily: 'WorkSans',
                              fontWeight: FontWeight.w600,
                              fontSize: 20.0,
                            )),
                      ),
                    ),
                    Align(
                      alignment: Alignment.centerLeft,
                      child: Padding(
                        padding: const EdgeInsets.only(top: 9),
                        child: Text(
                          state.musica.anno.toString()!,
                          style: const TextStyle(
                            fontFamily: 'WorkSans',
                            fontWeight: FontWeight.w400,
                            fontSize: 12.0,
                          ),
                        ),
                      ),
                    ),
                    const Align(
                      alignment: Alignment.centerLeft,
                      child: Padding(
                        padding: EdgeInsets.only(top: 34),
                        child: Text('Localidad:',
                            style: TextStyle(
                              fontFamily: 'WorkSans',
                              fontWeight: FontWeight.w600,
                              fontSize: 20.0,
                            )),
                      ),
                    ),
                    Align(
                      alignment: Alignment.centerLeft,
                      child: Padding(
                        padding: const EdgeInsets.only(top: 9),
                        child: Text(
                          state.musica.localidad!,
                          style: const TextStyle(
                            fontFamily: 'WorkSans',
                            fontWeight: FontWeight.w400,
                            fontSize: 12.0,
                          ),
                        ),
                      ),
                    ),
                    const Align(
                      alignment: Alignment.centerLeft,
                      child: Padding(
                        padding: EdgeInsets.only(top: 34),
                        child: Text('Hermandades en las que toca:',
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
                        itemCount: state.musica.hermandades!.length,
                        itemBuilder: (context, index) {
                          return Padding(
                            padding: const EdgeInsets.only(bottom: 20.0),
                            child: HermandadCardSmWidget(
                                hermandadesFavoritas:
                                    state.musica.hermandades![index]),
                          );
                        },
                      ),
                    ),
                  ],
                ),
              ),
            ),
          );
        } else if (state is MusicaFetchError) {
          return Text(state.errorMessage);
        }
        return const Center(child: CircularProgressIndicator());
      },
    );
  }
}
