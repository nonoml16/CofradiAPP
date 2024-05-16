part of 'hermandad_bloc.dart';

@immutable
sealed class HermandadEvent {}

class HermandadFetchList extends HermandadEvent {
  final String dia;
  HermandadFetchList(this.dia);
}

class HermandadViewDetail extends HermandadEvent {
  final int hermandadId;
  HermandadViewDetail(this.hermandadId);
}
