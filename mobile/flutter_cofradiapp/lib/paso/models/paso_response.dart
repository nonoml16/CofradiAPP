import 'package:flutter_cofradiapp/musica/models/musica_details_response.dart';

class Paso {
  String? id;
  String? imagen;
  String? capataz;
  int? numCostaleros;
  List<Musica>? acompannamiento;
  String? hermandad;
  List<String>? imagenes;

  Paso(
      {this.id,
      this.imagen,
      this.capataz,
      this.numCostaleros,
      this.acompannamiento,
      this.hermandad,
      this.imagenes});

  Paso.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    imagen = json['imagen'];
    capataz = json['capataz'];
    numCostaleros = json['numCostaleros'];
    if (json['acompannamiento'] != null) {
      acompannamiento = <Musica>[];
      json['acompannamiento'].forEach((v) {
        acompannamiento!.add(new Musica.fromJson(v));
      });
    }
    hermandad = json['hermandad'];
    imagenes = json['imagenes'].cast<String>();
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['id'] = this.id;
    data['imagen'] = this.imagen;
    data['capataz'] = this.capataz;
    data['numCostaleros'] = this.numCostaleros;
    if (this.acompannamiento != null) {
      data['acompannamiento'] =
          this.acompannamiento!.map((v) => v.toJson()).toList();
    }
    data['hermandad'] = this.hermandad;
    data['imagenes'] = this.imagenes;
    return data;
  }
}

class Acompannamiento {
  String? id;
  String? nombre;

  Acompannamiento({this.id, this.nombre});

  Acompannamiento.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    nombre = json['nombre'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['id'] = this.id;
    data['nombre'] = this.nombre;
    return data;
  }
}
