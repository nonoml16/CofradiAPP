import 'dart:convert';

import 'package:flutter_cofradiapp/user/models/user_profile_response.dart';
import 'package:flutter_cofradiapp/user/repositories/user_repository.dart';
import 'package:http/http.dart';
import 'package:shared_preferences/shared_preferences.dart';

class UserRepositoryImpl extends UserRepository {
  final Client _httpClient = Client();

  @override
  Future<UserResponse> fetchUserProfile() async {
    final SharedPreferences _prefs = await SharedPreferences.getInstance();

    final String? token = _prefs.getString('token');
    final response =
        await _httpClient.get(Uri.parse('http://10.0.2.2:8080/me'), headers: {
      'Content-Type': 'application/json',
      'Accept': 'application/json',
      'Authorization': 'Bearer $token',
    });
    if (response.statusCode == 200) {
      return UserResponse.fromJson(json.decode(response.body));
    } else {
      throw Exception('Failed to load user');
    }
  }
}
