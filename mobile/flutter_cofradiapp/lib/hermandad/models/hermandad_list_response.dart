class HermandadList {
  String? id;
  String? nombre;
  String? escudo;

  HermandadList({this.id, this.nombre, this.escudo});

  HermandadList.fromJson(Map<String, dynamic> json) {
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
