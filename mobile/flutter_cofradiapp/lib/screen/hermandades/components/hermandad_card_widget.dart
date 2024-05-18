import 'package:flutter/material.dart';
import 'package:flutter_cofradiapp/hermandad/models/hermandad_list_response.dart';
import 'package:flutter_cofradiapp/screen/hermandades/hermandad_details.dart';
import 'package:shared_preferences/shared_preferences.dart';

class HermandadCardWidget extends StatefulWidget {
  final HermandadList hermandadList;

  const HermandadCardWidget({Key? key, required this.hermandadList})
      : super(key: key);

  @override
  State<HermandadCardWidget> createState() => _HermandadCardWidgetState();
}

class _HermandadCardWidgetState extends State<HermandadCardWidget> {
  String? token;

  @override
  void initState() {
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
    return GestureDetector(
      onTap: () {
        Navigator.push(
          context,
          MaterialPageRoute(
            builder: (context) =>
                HermandadDetailsScreen(id: widget.hermandadList.id!),
          ),
        );
      },
      child: Center(
        child: SizedBox(
          width: 342,
          height: 70,
          child: Card(
            child: Center(
              child: Stack(
                children: <Widget>[
                  Positioned(
                    left: 27,
                    top: 9, // Centra verticalmente la imagen
                    child: SizedBox(
                      width: 45,
                      height: 45,
                      child: ClipRRect(
                        borderRadius: const BorderRadius.all(Radius.circular(
                            22)), // Para hacer un borde circular
                        child: Image(
                          image: NetworkImage(
                              'http://10.0.2.2:8080/download/${widget.hermandadList.escudo}',
                              headers: {
                                'Content-Type': 'application/json',
                                'Accept': 'application/json',
                                'Authorization': 'Bearer $token',
                              }),
                        ),
                      ),
                    ),
                  ),
                  Positioned(
                    left: 90, // Espacio entre la imagen y el título
                    top: 17, // Centra verticalmente el título
                    child: Text(
                      widget.hermandadList.nombre!,
                      style: const TextStyle(fontSize: 20),
                    ),
                  ),
                  const Positioned(
                    right: 18, // Margen derecho para el icono
                    top: 19, // Centra verticalmente el icono
                    child: Icon(
                      Icons.more_vert,
                      size: 24,
                      color: Colors.black,
                    ),
                  ),
                ],
              ),
            ),
          ),
        ),
      ),
    );
  }
}
