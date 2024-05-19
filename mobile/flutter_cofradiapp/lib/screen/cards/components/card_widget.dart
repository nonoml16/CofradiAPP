import 'package:flutter/material.dart';
import 'package:flutter_cofradiapp/card/models/card_list_response.dart';
import 'package:shared_preferences/shared_preferences.dart';

class CardWidget extends StatefulWidget {
  final CardL cardL;
  const CardWidget({super.key, required this.cardL});

  @override
  State<CardWidget> createState() => _CardWidgetState();
}

class _CardWidgetState extends State<CardWidget> {
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
    return Container(
      width: 160.0, // Ancho en píxeles
      height: 220.0, // Alto en píxeles
      color: const Color(0xFF0E4600), // Color de fondo verde
      child: Stack(
        children: <Widget>[
          Center(
            child: Container(
              width: 150.0, // Ancho de la caja en píxeles
              height: 210.0, // Alto de la caja en píxeles
              color: Colors.white, // Color de fondo de la caja
              child: Stack(children: <Widget>[
                Center(
                  child: Padding(
                    padding: const EdgeInsets.only(bottom: 7),
                    child: Container(
                      width: 140.0, // Ancho de la imagen en píxeles
                      height: 193.0, // Alto de la imagen en píxeles
                      color: Colors.white, // Color de fondo de la imagen
                      child: Image.network(
                        'http://10.0.2.2:8080/download/${widget.cardL.imagen}',
                        headers: {
                          'Content-Type': 'application/json',
                          'Accept': 'application/json',
                          'Authorization': 'Bearer $token',
                        },
                        fit: BoxFit.cover,
                      ),
                    ),
                  ),
                ),
                Positioned(
                  right: 7,
                  top: 7,
                  child: Padding(
                    padding: EdgeInsets.all(0), // Padding de 2px
                    child: Text(
                      widget.cardL.id!.toString(),
                      style: const TextStyle(
                        fontFamily: 'Quando',
                        fontSize: 5.0,
                        backgroundColor: Colors.white,
                      ),
                    ),
                  ),
                ),
                Positioned(
                  bottom: 21,
                  child: Center(
                    // Center horizontally
                    child: Container(
                      width: 160.0, // Adjust as needed
                      color: Colors.white.withOpacity(0.5),
                      child: Text(
                        widget.cardL.titulo!,
                        style: const TextStyle(
                          fontFamily: 'Quando',
                          fontSize: 7.0,
                        ),
                        textAlign: TextAlign
                            .center, // Center text within the container
                      ),
                    ),
                  ),
                ),
                Positioned(
                  bottom: 2,
                  child: Center(
                    // Center horizontally
                    child: SizedBox(
                      width: 160,
                      child: Text(
                        'Hermandad de ${widget.cardL.nombreHermandad}',
                        style: const TextStyle(
                          fontFamily: 'Quando',
                          fontSize: 6.0,
                        ),
                        textAlign: TextAlign
                            .center, // Center text within the container
                      ),
                    ),
                  ),
                ),
              ]),
            ),
          ),
        ],
      ),
    );
  }
}
