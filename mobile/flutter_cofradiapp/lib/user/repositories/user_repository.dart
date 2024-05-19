import 'package:flutter_cofradiapp/user/models/user_profile_response.dart';

abstract class UserRepository {
  Future<UserResponse> fetchUserProfile();
}
