part of 'musica_bloc.dart';

@immutable
sealed class MusicaEvent {}

class MusicaFetchDetail extends MusicaEvent {
  final String musicaId;
  MusicaFetchDetail(this.musicaId);
}
