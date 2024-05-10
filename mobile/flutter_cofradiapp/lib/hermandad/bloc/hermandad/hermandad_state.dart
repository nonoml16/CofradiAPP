part of 'hermandad_bloc.dart';

@immutable
sealed class HermandadState {}

final class HermandadInitial extends HermandadState {}

final class HermandadFetchSuccess extends HermandadState {
  final List<HermandadList> hermandadList;
  HermandadFetchSuccess(this.hermandadList);
}

final class HermandadFetchError extends HermandadState {
  final String errorMessage;
  HermandadFetchError(this.errorMessage);
}
