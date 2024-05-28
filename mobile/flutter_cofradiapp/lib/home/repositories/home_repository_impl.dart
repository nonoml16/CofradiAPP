import 'dart:convert';

import 'package:flutter_cofradiapp/home/model/home_response.dart';
import 'package:flutter_cofradiapp/home/repositories/home_repository.dart';
import 'package:http/http.dart';
import 'package:shared_preferences/shared_preferences.dart';

class HomeRepositoryImpl extends HomeRepository {
  final Client _httpClient = Client();
  @override
  Future<HomeEntity> fetchHomePage() async {
    final SharedPreferences _prefs = await SharedPreferences.getInstance();

    final String? token = _prefs.getString('token');
    final response =
        await _httpClient.get(Uri.parse('http://10.0.2.2:8080/home'), headers: {
      'Content-Type': 'application/json',
      'Accept': 'application/json',
      'Authorization': 'Bearer $token',
    });
    if (response.statusCode == 200) {
      return HomeEntity.fromJson(json.decode(response.body));
    } else {
      throw Exception('Failed to load home');
    }
  }
}
