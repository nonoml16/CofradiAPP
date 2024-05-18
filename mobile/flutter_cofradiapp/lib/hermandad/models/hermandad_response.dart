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
  List<String>? nombreBanda;
  String? sede;
  List<Cards>? cards;
  List<String>? imagenes;

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
      this.nombreBanda,
      this.sede,
      this.cards,
      this.imagenes});

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
    nombreBanda = json['nombreBanda'].cast<String>();
    sede = json['sede'];
    if (json['cards'] != null) {
      cards = <Cards>[];
      json['cards'].forEach((v) {
        cards!.add(new Cards.fromJson(v));
      });
    }
    imagenes = json['imagenes'].cast<String>();
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
    data['nombreBanda'] = this.nombreBanda;
    data['sede'] = this.sede;
    if (this.cards != null) {
      data['cards'] = this.cards!.map((v) => v.toJson()).toList();
    }
    data['imagenes'] = this.imagenes;
    return data;
  }
}

class Cards {
  int? id;
  String? titulo;
  String? urlImage;
  String? nombreHermandad;

  Cards({this.id, this.titulo, this.urlImage, this.nombreHermandad});

  Cards.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    titulo = json['titulo'];
    urlImage = json['urlImage'];
    nombreHermandad = json['nombreHermandad'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['id'] = this.id;
    data['titulo'] = this.titulo;
    data['urlImage'] = this.urlImage;
    data['nombreHermandad'] = this.nombreHermandad;
    return data;
  }
}
