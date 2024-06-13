package com.nonomartinez.sfc.cofradiasapi.user.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.nonomartinez.sfc.cofradiasapi.MyPage;
import com.nonomartinez.sfc.cofradiasapi.security.jwt.access.JwtProvider;
import com.nonomartinez.sfc.cofradiasapi.user.dto.*;
import com.nonomartinez.sfc.cofradiasapi.user.model.User;
import com.nonomartinez.sfc.cofradiasapi.user.service.UserService;
import com.nonomartinez.sfc.cofradiasapi.user.views.HomeViews;
import com.nonomartinez.sfc.cofradiasapi.user.views.UserViews;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authManager;
    private final JwtProvider jwtProvider;

    @PostMapping("/auth/register")
    public ResponseEntity<UserResponse> createUserWithUserRole(@RequestBody CreateUserRequest createUserRequest) {
        User user = userService.createUserWithUserRole(createUserRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.fromUser(user));
    }

    @PostMapping("/auth/register/admin")
    public ResponseEntity<UserResponse> createUserWithAdminRole(@RequestBody CreateUserRequest createUserRequest) {
        User user = userService.createUserWithAdminRole(createUserRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.fromUser(user));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<JwtUserResponse> login(@RequestBody LoginRequest loginRequest) {

        Authentication authentication =
                authManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.getUsername(),
                                loginRequest.getPassword()
                        )
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        User user = (User) authentication.getPrincipal();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(JwtUserResponse.of(user, token));

    }

    @PutMapping("/user/changePassword")
    public ResponseEntity<UserResponse> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest,
                                                       @AuthenticationPrincipal User loggedUser) {

        try {
            if (userService.passwordMatch(loggedUser, changePasswordRequest.getOldPassword())) {
                Optional<User> modified = userService.editPassword(loggedUser.getId(), changePasswordRequest.getNewPassword());
                if (modified.isPresent())
                    return ResponseEntity.ok(UserResponse.fromUser(modified.get()));
            } else {
                throw new RuntimeException();
            }
        } catch (RuntimeException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password Data Error");
        }

        return null;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    @JsonView(UserViews.UserDetails.class)
    public GetPerfilDTO getLoggedUser(@AuthenticationPrincipal User user) {
        return userService.getPerfil(user);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me-lite")
    @JsonView(UserViews.UserBasic.class)
    public GetPerfilDTO getLoggedUserBasic(@AuthenticationPrincipal User user) {
        return userService.getPerfil(user);
    }

    @GetMapping("/home")
    @JsonView(HomeViews.HomePageView.class)
    public GetHomeDTO getHomePage(){
        return userService.getHome();
    }

    @GetMapping("/users/")
    public ResponseEntity<MyPage<GetUserListDTO>> getAllHermandades(Pageable pageable){
        return ResponseEntity.status(200).body(userService.getAllUsersPaginado(pageable));
    }

    @GetMapping("/users/web/")
    public ResponseEntity<List<GetUserWebListDTO>> getAllUsers(){
        return ResponseEntity.status(200).body(userService.getAllUsers());
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<GetPerfilDTO> editUser(@PathVariable UUID id, @RequestBody PutUserDTO putUserDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.edit(putUserDTO, id));
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/me/edit")
    public ResponseEntity<GetPerfilDTO> editLoginUser(@AuthenticationPrincipal User user, @RequestBody PutUserDTO putUserDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.edit(putUserDTO, user.getId()));
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID id){
        boolean borrado = userService.delete(id);
        if(borrado)
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/user/nuevo")
    public ResponseEntity<PostUserDTO> newUser(@RequestBody PostUserDTO postUserDTO){
        return ResponseEntity.status(201).body(userService.addUser(postUserDTO));
    }
}
