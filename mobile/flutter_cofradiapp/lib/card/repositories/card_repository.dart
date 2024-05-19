import 'package:flutter_cofradiapp/card/models/card_list_response.dart';

abstract class CardRepository {
  Future<List<CardL>> fetchAllCards(int pagination);
}
