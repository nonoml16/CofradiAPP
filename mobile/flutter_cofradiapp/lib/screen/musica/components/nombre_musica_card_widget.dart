import 'package:flutter/material.dart';
import 'package:flutter_cofradiapp/musica/models/musica_details_response.dart';
import 'package:flutter_cofradiapp/screen/musica/musica_details.dart';

class NombreMusicaCardWidget extends StatefulWidget {
  final Musica musica;
  const NombreMusicaCardWidget({super.key, required this.musica});

  @override
  State<NombreMusicaCardWidget> createState() => _NombreMusicaCardWidgetState();
}

class _NombreMusicaCardWidgetState extends State<NombreMusicaCardWidget> {
  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: () {
        Navigator.push(
            context,
            MaterialPageRoute(
              builder: (context) => MusicaDetailsScreen(id: widget.musica.id!),
            ));
      },
      child: Center(
        child: Card(
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: <Widget>[
              ListTile(
                title: Text(
                  widget.musica.nombre!,
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
  }
}
