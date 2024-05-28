import 'package:flutter_cofradiapp/card/models/card_list_response.dart';

abstract class CardRepository {
  Future<List<CardL>> fetchCards(String modo, int pagination);
}
