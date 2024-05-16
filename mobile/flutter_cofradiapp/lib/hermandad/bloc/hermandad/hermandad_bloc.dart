import 'package:bloc/bloc.dart';
import 'package:flutter_cofradiapp/hermandad/models/hermandad_list_response.dart';
import 'package:flutter_cofradiapp/hermandad/repositories/hermandad_repository.dart';
import 'package:meta/meta.dart';

part 'hermandad_event.dart';
part 'hermandad_state.dart';

class HermandadBloc extends Bloc<HermandadEvent, HermandadState> {
  final HermandadRepository hermandadRepository;
  HermandadBloc(this.hermandadRepository) : super(HermandadInitial()) {
    on<HermandadFetchList>(_onHermandadFetchList);
  }

  void _onHermandadFetchList(
      HermandadFetchList event, Emitter<HermandadState> emit) async {
    try {
      final hermandadList =
          await hermandadRepository.fetchHermandadesDia(event.dia);
      emit(HermandadFetchSuccess(hermandadList));
      return;
    } on Exception catch (e) {
      emit(HermandadFetchError(e.toString()));
    }
  }
}
