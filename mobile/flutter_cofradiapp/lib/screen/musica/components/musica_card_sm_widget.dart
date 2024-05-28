import 'package:flutter/material.dart';
import 'package:flutter_cofradiapp/musica/models/musica_details_response.dart';
import 'package:flutter_cofradiapp/screen/musica/musica_details.dart';
import 'package:shared_preferences/shared_preferences.dart';

class MusicaCardSmWidget extends StatefulWidget {
  final Musica musica;
  const MusicaCardSmWidget({super.key, required this.musica});

  @override
  State<MusicaCardSmWidget> createState() => _MusicaCardSmWidgetState();
}

class _MusicaCardSmWidgetState extends State<MusicaCardSmWidget> {
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
            builder: (context) => MusicaDetailsScreen(id: widget.musica.id!),
          ),
        );
      },
      child: SizedBox(
        height: 130,
        width: 94,
        child: Card(
          child: Padding(
            padding: const EdgeInsets.fromLTRB(15.0, 15, 15, 0),
            child: Column(
              mainAxisSize: MainAxisSize.min,
              children: <Widget>[
                SizedBox(
                  height: 63,
                  width: 63,
                  child: ClipRRect(
                    borderRadius: const BorderRadius.all(
                        Radius.circular(100)), // Para hacer un borde circular
                    child: Image(
                      image: NetworkImage(
                          'http://10.0.2.2:8080/download/null', //todo corregir
                          headers: {
                            'Content-Type': 'application/json',
                            'Accept': 'application/json',
                            'Authorization': 'Bearer $token',
                          }),
                    ),
                  ),
                ),
                Text(widget.musica.nombre!,
                    style: const TextStyle(
                      fontFamily: 'WorkSans',
                      fontWeight: FontWeight.w600,
                      fontSize: 12.0,
                    )),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
