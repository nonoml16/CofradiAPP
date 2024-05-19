class UserResponse {
  String? imagenPerfil;
  String? nombreApellidos;
  String? nombreHermandad;
  List<HermandadesFavoritas>? hermandadesFavoritas;
  List<Cards>? cards;

  UserResponse(
      {this.imagenPerfil,
      this.nombreApellidos,
      this.nombreHermandad,
      this.hermandadesFavoritas,
      this.cards});

  UserResponse.fromJson(Map<String, dynamic> json) {
    imagenPerfil = json['imagenPerfil'];
    nombreApellidos = json['nombreApellidos'];
    nombreHermandad = json['nombreHermandad'];
    if (json['hermandadesFavoritas'] != null) {
      hermandadesFavoritas = <HermandadesFavoritas>[];
      json['hermandadesFavoritas'].forEach((v) {
        hermandadesFavoritas!.add(new HermandadesFavoritas.fromJson(v));
      });
    }
    if (json['cards'] != null) {
      cards = <Cards>[];
      json['cards'].forEach((v) {
        cards!.add(new Cards.fromJson(v));
      });
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['imagenPerfil'] = this.imagenPerfil;
    data['nombreApellidos'] = this.nombreApellidos;
    data['nombreHermandad'] = this.nombreHermandad;
    if (this.hermandadesFavoritas != null) {
      data['hermandadesFavoritas'] =
          this.hermandadesFavoritas!.map((v) => v.toJson()).toList();
    }
    if (this.cards != null) {
      data['cards'] = this.cards!.map((v) => v.toJson()).toList();
    }
    return data;
  }
}

class HermandadesFavoritas {
  String? id;
  String? nombre;
  String? escudo;

  HermandadesFavoritas({this.id, this.nombre, this.escudo});

  HermandadesFavoritas.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    nombre = json['nombre'];
    escudo = json['escudo'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['id'] = this.id;
    data['nombre'] = this.nombre;
    data['escudo'] = this.escudo;
    return data;
  }
}

class Cards {
  int? id;
  String? titulo;
  String? imagen;
  String? nombreHermandad;

  Cards({this.id, this.titulo, this.imagen, this.nombreHermandad});

  Cards.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    titulo = json['titulo'];
    imagen = json['imagen'];
    nombreHermandad = json['nombreHermandad'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['id'] = this.id;
    data['titulo'] = this.titulo;
    data['imagen'] = this.imagen;
    data['nombreHermandad'] = this.nombreHermandad;
    return data;
  }
}
