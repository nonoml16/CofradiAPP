part of 'paso_bloc.dart';

@immutable
sealed class PasoState {}

final class PasoInitial extends PasoState {}

final class PasoFetchSuccess extends PasoState {
  final Paso paso;
  PasoFetchSuccess(this.paso);
}

final class PasoFetchError extends PasoState {
  final String errorMessage;
  PasoFetchError(this.errorMessage);
}
