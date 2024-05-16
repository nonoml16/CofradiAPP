import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_cofradiapp/auth/bloc/login/login_bloc.dart';
import 'package:flutter_cofradiapp/auth/repositories/auth_repository.dart';
import 'package:flutter_cofradiapp/auth/repositories/auth_repository_impl.dart';
import 'package:flutter_cofradiapp/screen/home_screen.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:hexcolor/hexcolor.dart';

class LoginWidget extends StatefulWidget {
  const LoginWidget({super.key});

  @override
  State<LoginWidget> createState() => _LoginWidgetState();
}

class _LoginWidgetState extends State<LoginWidget>
    with SingleTickerProviderStateMixin {
  final _formLogin = GlobalKey<FormState>();
  final userTextController = TextEditingController();
  final passTextController = TextEditingController();

  late TabController _tabController;
  late AuthRepository authRepository;
  late LoginBloc _loginBloc;
  late bool _passwordVisible;
  late bool _isLoginForm = true;

  void signUserIn() async {
    if (_formLogin.currentState!.validate()) {
      _loginBloc.add(
        DoLoginEvent(
          userTextController.text,
          passTextController.text,
        ),
      );
    }
  }

  void showErrorMessage(String message) {
    // Tampilkan dialog dengan pesan error
    showDialog(
        context: context,
        builder: (context) {
          return AlertDialog(
            title: Text(message),
          );
        });
  }

  String _errorMessage = "";

  @override
  void initState() {
    _tabController = TabController(length: 2, vsync: this);
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
        buildWhen: (context, state) =>
            state is LoginInitial ||
            state is DoLoginSuccess ||
            state is DoLoginError,
        builder: (context, state) {
          if (state is DoLoginSuccess) {
            return const HomeScreen();
          } else if (state is DoLoginError) {
            return const Text('Login error');
          }
          return Center(child: _buildLoginRegister());
        },
        listener: (BuildContext context, LoginState state) {},
      ),
    );
  }

  Widget _buildLoginRegister() {
    return SafeArea(
      child: Scaffold(
        resizeToAvoidBottomInset: false,
        body: Column(
          mainAxisAlignment: MainAxisAlignment.end,
          children: [
            Container(
              height: _isLoginForm ? 522 : 738,
              width: double.infinity,
              decoration: BoxDecoration(
                color: HexColor("#FBE7F5"),
                borderRadius: const BorderRadius.only(
                  topLeft: Radius.circular(40),
                  topRight: Radius.circular(40),
                ),
              ),
              child: Column(
                children: [
                  Padding(
                    padding: const EdgeInsets.fromLTRB(0, 20, 0, 0),
                    child: TabBar(
                      controller: _tabController,
                      tabs: [
                        Tab(
                          child: Text(
                            'Iniciar Sesión',
                            style: GoogleFonts.quando(
                              fontSize: 20,
                            ),
                          ),
                        ),
                        Tab(
                          child: Text(
                            'Registrarse',
                            style: GoogleFonts.quando(
                              fontSize: 20,
                            ),
                          ),
                        ),
                      ],
                      onTap: (index) {
                        setState(() {
                          _isLoginForm = index == 0;
                        });
                      },
                    ),
                  ),
                  _isLoginForm ? _buildLoginForm() : _buildRegisterForm(),
                ],
              ),
            ),
            const SizedBox(height: 10),
          ],
        ),
      ),
    );
  }

  _buildLoginForm() {
    return Form(
      key: _formLogin,
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Padding(
            padding: const EdgeInsets.fromLTRB(0, 75, 0, 20),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Positioned(
                  top:
                      20, // Ajusta la posición del texto encima del TextFormField
                  left:
                      24, // Ajusta la posición del texto encima del TextFormField
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
                      border: Border.all(
                          color: const Color(0xFF451955), width: 1.0),
                      borderRadius:
                          const BorderRadius.all(Radius.circular(0.0)),
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
                  padding: const EdgeInsets.fromLTRB(8, 0, 0, 0),
                  child: Text(
                    _errorMessage,
                    style: GoogleFonts.poppins(
                      fontSize: 12,
                      color: Colors.red,
                    ),
                  ),
                ),
                Padding(
                  padding: const EdgeInsets.only(top: 20),
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
                  left: 24,
                  child: Container(
                    width: 302,
                    height: 45,
                    decoration: BoxDecoration(
                      border: Border.all(
                          color: const Color(0xFF451955), width: 1.0),
                      borderRadius:
                          const BorderRadius.all(Radius.circular(0.0)),
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
                  padding: const EdgeInsets.fromLTRB(14, 58, 0, 0),
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
                          borderRadius:
                              BorderRadius.circular(0), // Bordes rectos
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
                ),
                const SizedBox(
                  height: 12,
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }

  _buildRegisterForm() {
    return const Text('data');
  }

  _buildLoginForm2() {
    return SafeArea(
        child: Scaffold(
      resizeToAvoidBottomInset: false,
      body: ListView(
          padding: const EdgeInsets.fromLTRB(0, 400, 0, 0),
          shrinkWrap: true,
          reverse: true,
          children: [
            Column(
              mainAxisAlignment: MainAxisAlignment.end,
              children: [
                Stack(
                  children: [
                    Container(
                      height: 522,
                      width: double.infinity,
                      decoration: BoxDecoration(
                        color: HexColor("#FBE7F5"),
                        borderRadius: const BorderRadius.only(
                          topLeft: Radius.circular(40),
                          topRight: Radius.circular(40),
                        ),
                      ),
                      child: DefaultTabController(
                        initialIndex: 1,
                        length: 2,
                        child: Scaffold(
                          appBar: AppBar(
                            bottom: const TabBar(
                              tabs: <Widget>[
                                Tab(
                                  icon: Icon(Icons.cloud_outlined),
                                ),
                                Tab(
                                  icon: Icon(Icons.beach_access_sharp),
                                ),
                              ],
                            ),
                          ),
                          body: const TabBarView(
                            children: <Widget>[
                              Center(
                                child: Text("It's cloudy here"),
                              ),
                              Center(
                                child: Text("It's rainy here"),
                              ),
                            ],
                          ),
                        ),
                      ),
                    )
                  ],
                )
              ],
            )
          ]),
    ));
  }
}
