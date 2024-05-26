part of 'musica_bloc.dart';

@immutable
sealed class MusicaState {}

final class MusicaInitial extends MusicaState {}

final class MusicaFetchSuccess extends MusicaState {
  final Musica musica;
  MusicaFetchSuccess(this.musica);
}

final class MusicaFetchError extends MusicaState {
  final String errorMessage;
  MusicaFetchError(this.errorMessage);
}
