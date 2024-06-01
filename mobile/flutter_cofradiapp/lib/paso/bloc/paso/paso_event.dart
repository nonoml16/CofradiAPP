part of 'paso_bloc.dart';

@immutable
sealed class PasoEvent {}

class PasoFetchDetails extends PasoEvent {
  final String pasoId;
  PasoFetchDetails(this.pasoId);
}
