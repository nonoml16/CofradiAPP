import 'dart:convert';

import 'package:flutter_cofradiapp/musica/models/musica_details_response.dart';
import 'package:flutter_cofradiapp/musica/repositories/musica_repository.dart';
import 'package:http/http.dart';
import 'package:shared_preferences/shared_preferences.dart';

class MusicaRepositoryImpl extends MusicaRepository {
  final Client _httpClient = Client();

  @override
  Future<Musica> fetchMusicaDetails(String id) async {
    final SharedPreferences _prefs = await SharedPreferences.getInstance();

    final String? token = _prefs.getString('token');
    final response = await _httpClient
        .get(Uri.parse('http://10.0.2.2:8080/musica/$id'), headers: {
      'Content-Type': 'application/json',
      'Accept': 'application/json',
      'Authorization': 'Bearer $token',
    });
    if (response.statusCode == 200) {
      return Musica.fromJson(json.decode(response.body));
    } else {
      throw Exception('Failed to load musica');
    }
  }
}
