class HermandadList {
  String? nombre;
  String? escudo;

  HermandadList({this.nombre, this.escudo});

  HermandadList.fromJson(Map<String, dynamic> json) {
    nombre = json['nombre'];
    escudo = json['escudo'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['nombre'] = this.nombre;
    data['escudo'] = this.escudo;
    return data;
  }
}
