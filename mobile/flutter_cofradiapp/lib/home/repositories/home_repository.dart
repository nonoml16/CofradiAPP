import 'package:flutter_cofradiapp/home/model/home_response.dart';

abstract class HomeRepository {
  Future<HomeEntity> fetchHomePage();
}
