import 'package:bloc/bloc.dart';
import 'package:flutter_cofradiapp/paso/models/paso_response.dart';
import 'package:flutter_cofradiapp/paso/repositories/paso_repository.dart';
import 'package:meta/meta.dart';

part 'paso_event.dart';
part 'paso_state.dart';

class PasoBloc extends Bloc<PasoEvent, PasoState> {
  final PasoRepository pasoRepository;
  PasoBloc(this.pasoRepository) : super(PasoInitial()) {
    on<PasoFetchDetails>(_onPasoFetch);
  }

  void _onPasoFetch(PasoFetchDetails event, Emitter<PasoState> emit) async {
    try {
      final pasoDetails = await pasoRepository.fetchPasoDetails(event.pasoId);
      emit(PasoFetchSuccess(pasoDetails));
      return;
    } on Exception catch (e) {
      emit(PasoFetchError(e.toString()));
    }
  }
}
