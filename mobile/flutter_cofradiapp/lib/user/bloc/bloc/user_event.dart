part of 'user_bloc.dart';

@immutable
sealed class UserEvent {}

class UserFetchDetails extends UserEvent {
  UserFetchDetails();
}
