import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_cofradiapp/auth/bloc/login/login_bloc.dart';
import 'package:flutter_cofradiapp/auth/repositories/auth_repository.dart';
import 'package:flutter_cofradiapp/auth/repositories/auth_repository_impl.dart';
import 'package:flutter_cofradiapp/screen/home_screen.dart';
import 'package:google_fonts/google_fonts.dart';

class LoginWidget extends StatefulWidget {
  const LoginWidget({super.key});

  @override
  State<LoginWidget> createState() => _LoginWidgetState();
}

class _LoginWidgetState extends State<LoginWidget> {
  final _formLogin = GlobalKey<FormState>();
  final userTextController = TextEditingController();
  final passTextController = TextEditingController();

  late AuthRepository authRepository;
  late LoginBloc _loginBloc;
  late bool _passwordVisible;

  @override
  void initState() {
    authRepository = AuthRepositoryImpl();
    _loginBloc = LoginBloc(authRepository);
    _passwordVisible = false;

    super.initState();
  }

  @override
  void dispose() {
    userTextController.dispose();
    passTextController.dispose();
    _loginBloc.close();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return BlocProvider.value(
        value: _loginBloc,
        child: BlocConsumer<LoginBloc, LoginState>(
          buildWhen: (context, state) {
            return state is LoginInitial ||
                state is DoLoginSuccess ||
                state is DoLoginError;
          },
          builder: (context, state) {
            if (state is DoLoginSuccess) {
              return const HomeScreen();
            } else if (state is DoLoginError) {
              return const Text('Login error');
            }
            return Center(child: _buildLoginForm());
          },
          listener: (BuildContext context, LoginState state) {},
        ));
  }

  _buildLoginForm() {
    return Container(
      child: Form(
        key: _formLogin,
        child: Column(children: [
          Positioned(
            top: 20, // Ajusta la posición del texto encima del TextFormField
            left: 24, // Ajusta la posición del texto encima del TextFormField
            child: Text(
              'Usuario: ',
              style: GoogleFonts.quando(
                fontSize: 23,
                color: const Color(0xFF451955),
              ),
            ),
          ),
          Positioned(
            top: 50, // Ajusta la posición del TextFormField
            left: 24, // Ajusta la posición del TextFormField
            child: Container(
              width: 302,
              height: 45,
              decoration: BoxDecoration(
                border: Border.all(color: const Color(0xFF451955), width: 1.0),
                borderRadius: const BorderRadius.all(Radius.circular(0.0)),
              ),
              child: TextFormField(
                controller: userTextController,
                validator: (value) {
                  if (value == null || value.isEmpty) {
                    return 'Please enter some text';
                  }
                  return null;
                },
                decoration: const InputDecoration(
                  border: InputBorder.none,
                  contentPadding: EdgeInsets.symmetric(horizontal: 10.0),
                ),
              ),
            ),
          ),
          Padding(
            padding: const EdgeInsets.only(top: 40),
            child: Positioned(
              top: 20,
              left: 24,
              child: Text(
                'Contraseña: ',
                textAlign: TextAlign.left,
                style: GoogleFonts.quando(
                  fontSize: 23,
                  color: const Color(0xFF451955),
                ),
              ),
            ),
          ),
          Positioned(
            top: 50, // Ajusta la posición del TextFormField
            left: 24, // Ajusta la posición del TextFormField
            child: Container(
              width: 302,
              height: 45,
              decoration: BoxDecoration(
                border: Border.all(color: const Color(0xFF451955), width: 1.0),
                borderRadius: const BorderRadius.all(Radius.circular(0.0)),
              ),
              child: TextFormField(
                controller: passTextController,
                validator: (value) {
                  if (value == null || value.isEmpty) {
                    return 'Please enter some text';
                  }
                  return null;
                },
                decoration: const InputDecoration(
                  border: InputBorder.none,
                  contentPadding: EdgeInsets.symmetric(horizontal: 10.0),
                ),
              ),
            ),
          ),
          Padding(
            padding: const EdgeInsets.only(top: 58),
            child: SizedBox(
              width: 274,
              height: 62,
              child: ElevatedButton(
                onPressed: () {
                  if (_formLogin.currentState!.validate()) {
                    _loginBloc.add(
                      DoLoginEvent(
                        userTextController.text,
                        passTextController.text,
                      ),
                    );
                  }
                },
                style: ElevatedButton.styleFrom(
                  backgroundColor:
                      const Color(0xFF451955), // Background color #451955
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(0), // Bordes rectos
                  ),
                ),
                child: Center(
                  child: Text(
                    'Iniciar Sesión',
                    style: GoogleFonts.robotoSlab(
                      fontWeight: FontWeight.bold,
                      fontSize: 27,
                      color: Colors.white, // Text color blanco
                    ),
                  ),
                ),
              ),
            ),
          )
        ]),
      ),
    );
  }
}
