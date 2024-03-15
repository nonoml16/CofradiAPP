class LoginResponse {
  String? username;
  String? token;

  LoginResponse({this.username, this.token});

  LoginResponse.fromJson(Map<String, dynamic> json) {
    username = json['username'];
    token = json['token'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['username'] = this.username;
    data['token'] = this.token;
    return data;
  }
}
