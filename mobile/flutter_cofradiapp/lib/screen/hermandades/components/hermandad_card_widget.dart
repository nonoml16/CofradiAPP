import 'package:flutter/material.dart';

class HermandadCardWigdet extends StatelessWidget {
  const HermandadCardWigdet({super.key});

  @override
  Widget build(BuildContext context) {
    return const Center(
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
                      borderRadius: BorderRadius.all(
                          Radius.circular(22)), // Para hacer un borde circular
                      child: Image(
                        image: NetworkImage(
                            'https://upload.wikimedia.org/wikipedia/commons/f/f4/Escudo_Hermandad_del_Amor.jpg'),
                      ),
                    ),
                  ),
                ),
                Positioned(
                  left: 90, // Espacio entre la imagen y el título
                  top: 17, // Centra verticalmente el título
                  child: Text(
                    'El Amor',
                    style: TextStyle(fontSize: 20),
                  ),
                ),
                Positioned(
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
    );
  }
}
