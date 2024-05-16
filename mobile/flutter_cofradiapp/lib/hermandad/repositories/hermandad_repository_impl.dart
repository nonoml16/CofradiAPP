import 'dart:convert';

import 'package:flutter_cofradiapp/hermandad/models/hermandad_list_response.dart';
import 'package:flutter_cofradiapp/hermandad/repositories/hermandad_repository.dart';
import 'package:http/http.dart';
import 'package:shared_preferences/shared_preferences.dart';

class HermandadRepositoryImpl extends HermandadRepository {
  final Client _httpClient = Client();

  @override
  Future<List<HermandadList>> fetchHermandadesDia(String dia) async {
    final SharedPreferences _prefs = await SharedPreferences.getInstance();

    final String? token = _prefs.getString('token');
    final response = await _httpClient
        .get(Uri.parse('http://10.0.2.2:8080/hermandades/$dia'), headers: {
      'Content-Type': 'application/json',
      'Accept': 'application/json',
      'Authorization': 'Bearer $token',
    });
    if (response.statusCode == 200) {
      List<dynamic> jsonResponse = json.decode(response.body);
      return jsonResponse.map((map) => HermandadList.fromJson(map)).toList();
    } else {
      throw Exception('Failed to load hermandades');
    }
  }
}
