import 'package:bloc/bloc.dart';
import 'package:flutter_cofradiapp/user/models/user_profile_response.dart';
import 'package:flutter_cofradiapp/user/repositories/user_repository.dart';
import 'package:meta/meta.dart';

part 'user_event.dart';
part 'user_state.dart';

class UserBloc extends Bloc<UserEvent, UserState> {
  final UserRepository userRepository;
  UserBloc(this.userRepository) : super(UserInitial()) {
    on<UserFetchDetails>(_onUserFetchDetails);
  }

  void _onUserFetchDetails(
      UserFetchDetails event, Emitter<UserState> emit) async {
    try {
      final userR = await userRepository.fetchUserProfile();
      emit(UserFetchSuccess(userR));
      return;
    } on Exception catch (e) {
      emit(UserFetchError(e.toString()));
    }
  }
}
