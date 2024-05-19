import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_cofradiapp/screen/cards/components/card_widget.dart';
import 'package:flutter_cofradiapp/screen/cards/components/card_widget_user.dart';
import 'package:flutter_cofradiapp/screen/hermandades/components/hermandad_card_sm_widget.dart';
import 'package:flutter_cofradiapp/user/bloc/bloc/user_bloc.dart';
import 'package:flutter_cofradiapp/user/repositories/user_repository.dart';
import 'package:flutter_cofradiapp/user/repositories/user_repository_impl.dart';
import 'package:shared_preferences/shared_preferences.dart';

class UserProfileScreen extends StatefulWidget {
  const UserProfileScreen({super.key});

  @override
  State<UserProfileScreen> createState() => _UserProfileScreenState();
}

class _UserProfileScreenState extends State<UserProfileScreen> {
  late UserRepository userRepository;
  String? token;

  @override
  void initState() {
    userRepository = UserRepositoryImpl();
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
      create: (context) => UserBloc(userRepository)..add(UserFetchDetails()),
      child: _userDetails(),
    );
  }

  _userDetails() {
    return BlocBuilder<UserBloc, UserState>(
        builder: (BuildContext context, UserState state) {
      if (state is UserFetchSuccess) {
        return Scaffold(
          appBar: AppBar(
            title: const Text(
              'Mi perfil',
              style: TextStyle(
                fontFamily: 'WorkSans',
                fontWeight: FontWeight.w600,
                fontSize: 26.0,
              ),
            ),
          ),
          body: Padding(
            padding: const EdgeInsets.only(top: 26),
            child: SingleChildScrollView(
              child: Padding(
                padding: const EdgeInsets.all(27.0),
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
                                        'http://10.0.2.2:8080/download/${state.userResponse.imagenPerfil}',
                                        headers: {
                                          'Content-Type': 'application/json',
                                          'Accept': 'application/json',
                                          'Authorization': 'Bearer $token',
                                        }),
                                  ))),
                          Padding(
                            padding: const EdgeInsets.only(top: 11),
                            child: Text(state.userResponse.nombreApellidos!,
                                style: const TextStyle(
                                  fontFamily: 'WorkSans',
                                  fontWeight: FontWeight.w600,
                                  fontSize: 24.0,
                                )),
                          ),
                          Padding(
                            padding: const EdgeInsets.only(top: 5),
                            child: Text(state.userResponse.nombreHermandad!,
                                style: const TextStyle(
                                  fontFamily: 'WorkSans',
                                  fontWeight: FontWeight.w400,
                                  fontSize: 14.0,
                                )),
                          )
                        ],
                      ),
                    ),
                    const Align(
                      alignment: Alignment.centerLeft,
                      child: Padding(
                        padding: EdgeInsets.only(top: 34),
                        child: Text('Hermandades favoritas:',
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
                            state.userResponse.hermandadesFavoritas!.length,
                        itemBuilder: (context, index) {
                          return Padding(
                            padding: const EdgeInsets.only(bottom: 20.0),
                            child: HermandadCardSmWidget(
                                hermandadesFavoritas: state
                                    .userResponse.hermandadesFavoritas![index]),
                          );
                        },
                      ),
                    ),
                    const Align(
                      alignment: Alignment.centerLeft,
                      child: Text('Cards:',
                          style: TextStyle(
                            fontFamily: 'WorkSans',
                            fontWeight: FontWeight.w600,
                            fontSize: 20.0,
                          )),
                    ),
                    SizedBox(
                      height: 230,
                      child: ListView.builder(
                        scrollDirection: Axis.horizontal,
                        itemCount: state.userResponse.cards!.length,
                        itemBuilder: (context, index) {
                          return Padding(
                            padding: const EdgeInsets.all(5),
                            child: SizedBox(
                              height: 220,
                              width: 160,
                              child: CardUserWidget(
                                  cardL: state.userResponse.cards![index]),
                            ),
                          );
                        },
                      ),
                    ),
                  ],
                ),
              ),
            ),
          ),
        );
      } else if (state is UserFetchError) {
        return Text(state.errorMessage);
      }
      return const Center(child: CircularProgressIndicator());
    });
  }
}
