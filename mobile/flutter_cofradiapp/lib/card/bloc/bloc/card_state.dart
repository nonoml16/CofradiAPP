part of 'card_bloc.dart';

@immutable
sealed class CardState {}

final class CardInitial extends CardState {}

final class CardFetchSuccess extends CardState {
  final List<CardL> cardList;
  CardFetchSuccess(this.cardList);
}

final class CardFetchError extends CardState {
  final String errorMessage;
  CardFetchError(this.errorMessage);
}
