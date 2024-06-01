import 'package:flutter/material.dart';
import 'package:flutter_cofradiapp/paso/models/paso_response.dart';
import 'package:flutter_cofradiapp/screen/paso/paso_details.dart';

class NombrePasoCardWidget extends StatefulWidget {
  final Paso paso;
  const NombrePasoCardWidget({super.key, required this.paso});

  @override
  State<NombrePasoCardWidget> createState() => _NombrePasoCardWidgetState();
}

class _NombrePasoCardWidgetState extends State<NombrePasoCardWidget> {
  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: () {
        Navigator.push(
            context,
            MaterialPageRoute(
              builder: (context) => PasoDetailsScreen(id: widget.paso.id!),
            ));
      },
      child: Center(
        child: Card(
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: <Widget>[
              ListTile(
                title: Text(
                  widget.paso.imagen!,
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
