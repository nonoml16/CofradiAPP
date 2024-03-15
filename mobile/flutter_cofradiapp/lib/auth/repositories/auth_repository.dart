import 'package:flutter_cofradiapp/auth/models/login_dto.dart';
import 'package:flutter_cofradiapp/auth/models/login_response.dart';

abstract class AuthRepository {
  Future<LoginResponse> login(LoginDTO loginDTO);
}
