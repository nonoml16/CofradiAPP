import 'dart:convert';
import 'package:http/http.dart';

import 'package:flutter_cofradiapp/auth/models/login_dto.dart';
import 'package:flutter_cofradiapp/auth/models/login_response.dart';
import 'package:flutter_cofradiapp/auth/repositories/auth_repository.dart';

class AuthRepositoryImpl extends AuthRepository {
  final Client _httpClient = Client();

  @override
  Future<LoginResponse> login(LoginDTO loginDTO) async {
    final response =
        await _httpClient.post(Uri.parse("http://10.0.2.2:8080/auth/login"),
            headers: <String, String>{
              'Content-Type': 'application/json',
            },
            body: jsonEncode(loginDTO.toJson()));
    if (response.statusCode == 201) {
      return LoginResponse.fromJson(json.decode(response.body));
    } else {
      throw Exception('Failed to do login');
    }
  }
}
