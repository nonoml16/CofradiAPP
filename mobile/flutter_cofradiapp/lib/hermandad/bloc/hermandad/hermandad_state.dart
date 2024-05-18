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

final class HermandadDetailsFetchSuccess extends HermandadState {
  final Hermandad hermandad;
  HermandadDetailsFetchSuccess(this.hermandad);
}

final class HermandadDetailsFetchError extends HermandadState {
  final String errorMessage;
  HermandadDetailsFetchError(this.errorMessage);
}
