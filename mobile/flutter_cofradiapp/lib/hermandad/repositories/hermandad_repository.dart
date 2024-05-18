import 'package:flutter_cofradiapp/hermandad/models/hermandad_list_response.dart';
import 'package:flutter_cofradiapp/hermandad/models/hermandad_response.dart';

abstract class HermandadRepository {
  Future<List<HermandadList>> fetchHermandadesDia(String dia);
  Future<Hermandad> fetchHermandadDetails(String id);
}
