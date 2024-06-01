import 'package:flutter_cofradiapp/paso/models/paso_response.dart';

abstract class PasoRepository {
  Future<Paso> fetchPasoDetails(String id);
}
