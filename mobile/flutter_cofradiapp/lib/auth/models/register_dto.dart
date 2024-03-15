class RegisterDTO {
  String? nombre;
  String? apellidos;
  String? email;
  String? username;
  String? password;
  String? verifyPassword;

  RegisterDTO(
      {this.nombre,
      this.apellidos,
      this.email,
      this.username,
      this.password,
      this.verifyPassword});

  RegisterDTO.fromJson(Map<String, dynamic> json) {
    nombre = json['nombre'];
    apellidos = json['apellidos'];
    email = json['email'];
    username = json['username'];
    password = json['password'];
    verifyPassword = json['verifyPassword'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['nombre'] = this.nombre;
    data['apellidos'] = this.apellidos;
    data['email'] = this.email;
    data['username'] = this.username;
    data['password'] = this.password;
    data['verifyPassword'] = this.verifyPassword;
    return data;
  }
}
