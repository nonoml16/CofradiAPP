import 'package:flutter_cofradiapp/user/models/user_profile_response.dart';

class Musica {
  String? id;
  String? nombre;
  int? anno;
  String? localidad;
  List<HermandadesFavoritas>? hermandades;

  Musica({this.id, this.nombre, this.anno, this.localidad, this.hermandades});

  Musica.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    nombre = json['nombre'];
    anno = json['anno'];
    localidad = json['localidad'];
    if (json['hermandades'] != null) {
      hermandades = <HermandadesFavoritas>[];
      json['hermandades'].forEach((v) {
        hermandades!.add(new HermandadesFavoritas.fromJson(v));
      });
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['id'] = this.id;
    data['nombre'] = this.nombre;
    data['anno'] = this.anno;
    data['localidad'] = this.localidad;
    if (this.hermandades != null) {
      data['hermandades'] = this.hermandades!.map((v) => v.toJson()).toList();
    }
    return data;
  }
}
