import 'package:flutter/material.dart';
import 'package:flutter_cofradiapp/screen/hermandades/hermandad_details.dart';
import 'package:flutter_cofradiapp/user/models/user_profile_response.dart';
import 'package:shared_preferences/shared_preferences.dart';

class HermandadCardSmWidget extends StatefulWidget {
  final HermandadesFavoritas hermandadesFavoritas;
  const HermandadCardSmWidget({super.key, required this.hermandadesFavoritas});

  @override
  State<HermandadCardSmWidget> createState() => _HermandadCardSmWidgetState();
}

class _HermandadCardSmWidgetState extends State<HermandadCardSmWidget> {
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
                HermandadDetailsScreen(id: widget.hermandadesFavoritas.id!),
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
                          'http://10.0.2.2:8080/download/${widget.hermandadesFavoritas.escudo}',
                          headers: {
                            'Content-Type': 'application/json',
                            'Accept': 'application/json',
                            'Authorization': 'Bearer $token',
                          }),
                    ),
                  ),
                ),
                Text(widget.hermandadesFavoritas.nombre!,
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
