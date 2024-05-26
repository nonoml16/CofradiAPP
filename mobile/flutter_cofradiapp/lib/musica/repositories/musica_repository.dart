import 'package:flutter_cofradiapp/musica/models/musica_details_response.dart';

abstract class MusicaRepository {
  Future<Musica> fetchMusicaDetails(String id);
}
