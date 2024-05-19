part of 'card_bloc.dart';

@immutable
sealed class CardEvent {}

class CardFetchList extends CardEvent {
  final int pagination;
  CardFetchList(this.pagination);
}
