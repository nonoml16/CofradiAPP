part of 'user_bloc.dart';

@immutable
sealed class UserState {}

final class UserInitial extends UserState {}

final class UserFetchSuccess extends UserState {
  final UserResponse userResponse;
  UserFetchSuccess(this.userResponse);
}

final class UserFetchError extends UserState {
  final String errorMessage;
  UserFetchError(this.errorMessage);
}
