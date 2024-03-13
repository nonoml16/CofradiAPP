package com.nonomartinez.sfc.cofradiasapi.user.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserRequest {

    private String username;
    private String password;
    private String verifyPassword;
    private String avatar;
    private String nombre;
    private String apellidos;
    private String email;
}
