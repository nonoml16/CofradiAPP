import 'package:flutter_cofradiapp/card/models/card_list_response.dart';
import 'package:flutter_cofradiapp/musica/models/musica_details_response.dart';

class Hermandad {
  String? id;
  String? nombre;
  String? nombreCompleto;
  String? escudo;
  int? annoFundacion;
  String? deInteres;
  int? numNazarenos;
  int? numHermanos;
  int? tiempoPaso;
  List<Musica>? banda;
  String? sede;
  List<CardL>? cards;
  List<String>? imagenes;
  List<Pasos>? pasos;

  Hermandad(
      {this.id,
      this.nombre,
      this.nombreCompleto,
      this.escudo,
      this.annoFundacion,
      this.deInteres,
      this.numNazarenos,
      this.numHermanos,
      this.tiempoPaso,
      this.banda,
      this.sede,
      this.cards,
      this.imagenes,
      this.pasos});

  Hermandad.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    nombre = json['nombre'];
    nombreCompleto = json['nombreCompleto'];
    escudo = json['escudo'];
    annoFundacion = json['annoFundacion'];
    deInteres = json['deInteres'];
    numNazarenos = json['numNazarenos'];
    numHermanos = json['numHermanos'];
    tiempoPaso = json['tiempoPaso'];
    if (json['banda'] != null) {
      banda = <Musica>[];
      json['banda'].forEach((v) {
        banda!.add(new Musica.fromJson(v));
      });
    }
    sede = json['sede'];
    if (json['cards'] != null) {
      cards = <CardL>[];
      json['cards'].forEach((v) {
        cards!.add(new CardL.fromJson(v));
      });
    }
    imagenes = json['imagenes'].cast<String>();
    if (json['pasos'] != null) {
      pasos = <Pasos>[];
      json['pasos'].forEach((v) {
        pasos!.add(new Pasos.fromJson(v));
      });
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['id'] = this.id;
    data['nombre'] = this.nombre;
    data['nombreCompleto'] = this.nombreCompleto;
    data['escudo'] = this.escudo;
    data['annoFundacion'] = this.annoFundacion;
    data['deInteres'] = this.deInteres;
    data['numNazarenos'] = this.numNazarenos;
    data['numHermanos'] = this.numHermanos;
    data['tiempoPaso'] = this.tiempoPaso;
    if (this.banda != null) {
      data['banda'] = this.banda!.map((v) => v.toJson()).toList();
    }
    data['sede'] = this.sede;
    if (this.cards != null) {
      data['cards'] = this.cards!.map((v) => v.toJson()).toList();
    }
    data['imagenes'] = this.imagenes;
    if (this.pasos != null) {
      data['pasos'] = this.pasos!.map((v) => v.toJson()).toList();
    }
    return data;
  }
}

class Pasos {
  String? id;
  String? imagen;

  Pasos({this.id, this.imagen});

  Pasos.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    imagen = json['imagen'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['id'] = this.id;
    data['imagen'] = this.imagen;
    return data;
  }
}
