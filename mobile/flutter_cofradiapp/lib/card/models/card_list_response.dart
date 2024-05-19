class CardListP {
  List<CardL>? result;
  int? size;
  int? totalElements;
  int? pageNumber;
  bool? first;
  bool? last;

  CardListP(
      {this.result,
      this.size,
      this.totalElements,
      this.pageNumber,
      this.first,
      this.last});

  CardListP.fromJson(Map<String, dynamic> json) {
    if (json['result'] != null) {
      result = <CardL>[];
      json['result'].forEach((v) {
        result!.add(new CardL.fromJson(v));
      });
    }
    size = json['size'];
    totalElements = json['totalElements'];
    pageNumber = json['pageNumber'];
    first = json['first'];
    last = json['last'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    if (this.result != null) {
      data['result'] = this.result!.map((v) => v.toJson()).toList();
    }
    data['size'] = this.size;
    data['totalElements'] = this.totalElements;
    data['pageNumber'] = this.pageNumber;
    data['first'] = this.first;
    data['last'] = this.last;
    return data;
  }
}

class CardL {
  int? id;
  String? titulo;
  String? imagen;
  String? nombreHermandad;

  CardL({this.id, this.titulo, this.imagen, this.nombreHermandad});

  CardL.fromJson(Map<String, dynamic> json) {
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
