part of 'card_bloc.dart';

@immutable
sealed class CardEvent {}

class CardFetchList extends CardEvent {
  final String modo;
  final int pagination;
  CardFetchList(this.modo, this.pagination);
}
