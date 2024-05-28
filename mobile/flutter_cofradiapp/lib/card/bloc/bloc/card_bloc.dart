import 'package:bloc/bloc.dart';
import 'package:flutter_cofradiapp/card/models/card_list_response.dart';
import 'package:flutter_cofradiapp/card/repositories/card_repository.dart';
import 'package:meta/meta.dart';

part 'card_event.dart';
part 'card_state.dart';

class CardBloc extends Bloc<CardEvent, CardState> {
  final CardRepository cardRepository;
  CardBloc(this.cardRepository) : super(CardInitial()) {
    on<CardFetchList>(_onCardFetchList);
  }

  void _onCardFetchList(CardFetchList event, Emitter<CardState> emit) async {
    try {
      final cardList =
          await cardRepository.fetchCards(event.modo, event.pagination);
      emit(CardFetchSuccess(cardList));
      return;
    } on Exception catch (e) {
      emit(CardFetchError(e.toString()));
    }
  }
}
