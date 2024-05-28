import 'package:flutter_cofradiapp/card/models/card_list_response.dart';
import 'package:flutter_cofradiapp/hermandad/models/hermandad_list_response.dart';
import 'package:flutter_cofradiapp/musica/models/musica_details_response.dart';
import 'package:flutter_cofradiapp/user/models/user_profile_response.dart';

class HomeEntity {
  List<String>? carruselFotos;
  HermandadList? hermandadDelDia;
  List<CardL>? cardsDestacadas;
  List<HermandadesFavoritas>? hermandadesDestacadas;
  List<Musica>? bandasDestacadas;

  HomeEntity(
      {this.carruselFotos,
      this.hermandadDelDia,
      this.cardsDestacadas,
      this.hermandadesDestacadas,
      this.bandasDestacadas});

  HomeEntity.fromJson(Map<String, dynamic> json) {
    carruselFotos = json['carruselFotos'].cast<String>();
    hermandadDelDia = json['hermandadDelDia'] != null
        ? new HermandadList.fromJson(json['hermandadDelDia'])
        : null;
    if (json['cardsDestacadas'] != null) {
      cardsDestacadas = <CardL>[];
      json['cardsDestacadas'].forEach((v) {
        cardsDestacadas!.add(new CardL.fromJson(v));
      });
    }
    if (json['hermandadesDestacadas'] != null) {
      hermandadesDestacadas = <HermandadesFavoritas>[];
      json['hermandadesDestacadas'].forEach((v) {
        hermandadesDestacadas!.add(new HermandadesFavoritas.fromJson(v));
      });
    }
    if (json['bandasDestacadas'] != null) {
      bandasDestacadas = <Musica>[];
      json['bandasDestacadas'].forEach((v) {
        bandasDestacadas!.add(new Musica.fromJson(v));
      });
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['carruselFotos'] = this.carruselFotos;
    if (this.hermandadDelDia != null) {
      data['hermandadDelDia'] = this.hermandadDelDia!.toJson();
    }
    if (this.cardsDestacadas != null) {
      data['cardsDestacadas'] =
          this.cardsDestacadas!.map((v) => v.toJson()).toList();
    }
    if (this.hermandadesDestacadas != null) {
      data['hermandadesDestacadas'] =
          this.hermandadesDestacadas!.map((v) => v.toJson()).toList();
    }
    if (this.bandasDestacadas != null) {
      data['bandasDestacadas'] =
          this.bandasDestacadas!.map((v) => v.toJson()).toList();
    }
    return data;
  }
}
