import 'dart:convert';

import 'package:flutter_cofradiapp/card/models/card_list_response.dart';
import 'package:flutter_cofradiapp/card/repositories/card_repository.dart';
import 'package:http/http.dart';
import 'package:shared_preferences/shared_preferences.dart';

class CardRepositoryImpl extends CardRepository {
  final Client _httpClient = Client();

  @override
  Future<List<CardL>> fetchAllCards(int pagination) async {
    final SharedPreferences _prefs = await SharedPreferences.getInstance();

    final String? token = _prefs.getString('token');
    final response = await _httpClient.get(
        Uri.parse('http://10.0.2.2:8080/cards/user?page=$pagination'),
        headers: {
          'Content-Type': 'application/json',
          'Accept': 'application/json',
          'Authorization': 'Bearer $token',
        });
    if (response.statusCode == 200) {
      return CardListP.fromJson(json.decode(response.body)).result!;
    } else {
      throw Exception('Failed to load cards');
    }
  }
}
