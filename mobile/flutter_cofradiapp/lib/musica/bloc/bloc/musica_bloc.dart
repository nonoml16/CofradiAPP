import 'package:bloc/bloc.dart';
import 'package:flutter_cofradiapp/musica/models/musica_details_response.dart';
import 'package:flutter_cofradiapp/musica/repositories/musica_repository.dart';
import 'package:meta/meta.dart';

part 'musica_event.dart';
part 'musica_state.dart';

class MusicaBloc extends Bloc<MusicaEvent, MusicaState> {
  final MusicaRepository musicaRepository;
  MusicaBloc(this.musicaRepository) : super(MusicaInitial()) {
    on<MusicaFetchDetail>(_onMusicaFetch);
  }

  void _onMusicaFetch(
      MusicaFetchDetail event, Emitter<MusicaState> emit) async {
    try {
      final musica = await musicaRepository.fetchMusicaDetails(event.musicaId);
      emit(MusicaFetchSuccess(musica));
      return;
    } on Exception catch (e) {
      emit(MusicaFetchError(e.toString()));
    }
  }
}
