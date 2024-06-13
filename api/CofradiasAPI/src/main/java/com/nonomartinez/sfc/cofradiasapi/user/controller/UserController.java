package com.nonomartinez.sfc.cofradiasapi.user.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.nonomartinez.sfc.cofradiasapi.MyPage;
import com.nonomartinez.sfc.cofradiasapi.paso.dto.GetPasoDTO;
import com.nonomartinez.sfc.cofradiasapi.paso.dto.PostPasoDTO;
import com.nonomartinez.sfc.cofradiasapi.paso.dto.PutPasoDTO;
import com.nonomartinez.sfc.cofradiasapi.security.jwt.access.JwtProvider;
import com.nonomartinez.sfc.cofradiasapi.user.dto.*;
import com.nonomartinez.sfc.cofradiasapi.user.model.User;
import com.nonomartinez.sfc.cofradiasapi.user.service.UserService;
import com.nonomartinez.sfc.cofradiasapi.user.views.HomeViews;
import com.nonomartinez.sfc.cofradiasapi.user.views.UserViews;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Obtiene los datos del usuario autenticado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado el usuario",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetPerfilDTO.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                 "imagenPerfil": null,
                                                 "nombreApellidos": "Paco Lopez Perez",
                                                 "nombreHermandad": "El Amor",
                                                 "hermandadesFavoritas": [
                                                     {
                                                         "id": "40418d3f-6e05-4008-81d4-2facfa257509",
                                                         "nombre": "Las Penas",
                                                         "escudo": null
                                                     },
                                                     {
                                                         "id": "2a822aaf-05e5-42d3-8600-069ec12adac9",
                                                         "nombre": "El Amor",
                                                         "escudo": "amor.png"
                                                     }
                                                 ],
                                                 "cards": [
                                                     {
                                                         "id": 1,
                                                         "titulo": "Escudo de la Hdad. de El Amor",
                                                         "imagen": null,
                                                         "nombreHermandad": "El Amor"
                                                     },
                                                     {
                                                         "id": 2,
                                                         "titulo": "Cruz de GuÃ­a",
                                                         "imagen": null,
                                                         "nombreHermandad": "El Amor"
                                                     },
                                                 ]
                                             }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado datos",
                    content = @Content),
    })
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    @JsonView(UserViews.UserDetails.class)
    public GetPerfilDTO getLoggedUser(@AuthenticationPrincipal User user) {
        return userService.getPerfil(user);
    }

    @GetMapping("/user/web/{id}")
    @JsonView(UserViews.UserWebDetails.class)
    public GetPerfilWebDTO getWebUser(@PathVariable UUID id) {
        return userService.getPerfilWeb(id);
    }

    @Operation(summary = "Obtiene los datos del usuario autenticado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado el usuario",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetPerfilDTO.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "username": "paco",
                                                "imagenPerfil": null,
                                                "nombreHermandad": "El Amor"
                                            }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado datos",
                    content = @Content),
    })
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me-lite")
    @JsonView(UserViews.UserBasic.class)
    public GetPerfilDTO getLoggedUserBasic(@AuthenticationPrincipal User user) {
        return userService.getPerfil(user);
    }

    @Operation(summary = "Obtiene todos los datos de la home")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la home",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetHomeDTO.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "carruselFotos": [
                                                    "amor1.png",
                                                    "amor2.png",
                                                    "amor3.png",
                                                    "amor4.png"
                                                ],
                                                "hermandadDelDia": {
                                                    "id": "e4371fd8-aace-4c0f-ab91-3e8dce4037a7",
                                                    "nombre": "Las Aguas",
                                                    "escudo": null
                                                },
                                                "cardsDestacadas": [
                                                    {
                                                        "id": 7,
                                                        "titulo": "Ntra. Sra. del Socorro",
                                                        "imagen": null,
                                                        "nombreHermandad": "Casita"
                                                    },
                                                    {
                                                        "id": 24,
                                                        "titulo": "Ntro. Padre Jesus de la Humildad",
                                                        "imagen": null,
                                                        "nombreHermandad": "El Olivo"
                                                    },
                                                ],
                                                "hermandadesDestacadas": [
                                                    {
                                                        "id": "80712f97-4d00-4719-98a5-2aa6ed062b4d",
                                                        "nombre": "La misiÃ³n",
                                                        "escudo": "la_misiÃ³n.png"
                                                    },
                                                    {
                                                        "id": "d31014f4-7d71-4af4-84c8-0c9f592d1aba",
                                                        "nombre": "Torreblanca",
                                                        "escudo": "torreblanca.png"
                                                    },
                                                ],
                                                "bandasDestacadas": [
                                                    {
                                                        "id": "3f2b4699-eaf7-43c3-99c2-a9b1aa22e20c",
                                                        "nombre": "AM RedenciÃ³n"
                                                    },
                                                    {
                                                        "id": "1fab52e7-24e4-49d3-9b05-ef50412cdf86",
                                                        "nombre": "Centuria Juvenil"
                                                    },
                                                ]
                                            }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado datos",
                    content = @Content),
    })
    @GetMapping("/home")
    @JsonView(HomeViews.HomePageView.class)
    public GetHomeDTO getHomePage(){
        return userService.getHome();
    }

    @Operation(summary = "Obtiene todos los datos de la home")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la home",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetHomeWebDTO.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "imagenes": [
                                                    "amor1.png",
                                                    "amor2.png",
                                                    "amor3.png",
                                                    "amor4.png"
                                                ],
                                                "numHdades": 33,
                                                "numBandas": 16,
                                                "numCards": 27,
                                                "numUsers": 1,
                                                "hermandadDia": {
                                                    "id": "40418d3f-6e05-4008-81d4-2facfa257509",
                                                    "nombre": "Las Penas",
                                                    "escudo": null
                                                },
                                                "cardsDestacadas": [
                                                    {
                                                        "id": 4,
                                                        "titulo": "Paso del Stmo. Cristo del Amor",
                                                        "imagen": null,
                                                        "nombreHermandad": "El Amor"
                                                    },
                                                    {
                                                        "id": 12,
                                                        "titulo": "Escudo de la Hdad. de Jesus Despojado",
                                                        "imagen": null,
                                                        "nombreHermandad": "Jesus Despojado"
                                                    },
                                                ],
                                                "hermandadesDestacadas": [
                                                    {
                                                        "id": "e0c2ea91-6e22-4f9a-b0e4-4ad7a68d0c0a",
                                                        "nombre": "Las Maravillas",
                                                        "escudo": "las_maravillas.png"
                                                    },
                                                    {
                                                        "id": "111c0c6e-9bd7-44d9-9f4e-43cd7c2eee06",
                                                        "nombre": "La Milagrosa",
                                                        "escudo": "la_milagrosa.png"
                                                    },
                                                ],
                                                "musicasDestacadas": [
                                                    {
                                                        "id": "c890a848-9521-4d9d-ad82-3a81e4698184",
                                                        "nombre": "Banda de Nuestra SeÃ±ora de los Ã�ngeles"
                                                    },
                                                    {
                                                        "id": "1d6e6b5c-59b4-4252-b4e9-e80da5e535c7",
                                                        "nombre": "AM MarÃ­a SantÃ­sima del RocÃ­o"
                                                    },
                                                ]
                                            }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado datos",
                    content = @Content),
    })
    @GetMapping("/web/home")
    @JsonView(HomeViews.HomePageView.class)
    public GetHomeWebDTO getHomePageWeb(){
        return userService.getHomeWeb();
    }

    @Operation(summary = "Obtiene todos los usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado usuarios",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetUserListDTO.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "result": [
                                                    {
                                                        "imagenPerfil": null,
                                                        "apellidosNombre": "Lopez Perez, Paco",
                                                        "username": "paco",
                                                        "nombreHdad": "El Amor"
                                                    }
                                                ],
                                                "size": 20,
                                                "totalElements": 1,
                                                "pageNumber": 0,
                                                "first": true,
                                                "last": true
                                            }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ningun usuario",
                    content = @Content),
    })
    @GetMapping("/users/")
    public ResponseEntity<MyPage<GetUserListDTO>> getAllHermandades(Pageable pageable){
        return ResponseEntity.status(200).body(userService.getAllUsersPaginado(pageable));
    }

    @Operation(summary = "Obtiene todos los usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado usuarios",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetUserWebListDTO.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {
                                                    "id": "45968cb7-1d51-4751-8d40-e0ffd1e07c16",
                                                    "fotoPerfil": null,
                                                    "apellidosNombre": "Lopez Perez, Paco",
                                                    "username": "paco",
                                                    "nombreHermandad": "El Amor",
                                                    "numCards": 27
                                                }
                                            ]
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ningun usuario",
                    content = @Content),
    })
    @GetMapping("/users/web/")
    public ResponseEntity<List<GetUserWebListDTO>> getAllUsers(){
        return ResponseEntity.status(200).body(userService.getAllUsers());
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Edita el usuario", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PutUserDTO.class)),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                    "nombre": "Alfonso",
                                                    "apellidos": "Benito Alvarez",
                                                    "avatar": "amor.png",
                                                    "email": "holahola@pepedomingocastano.cope",
                                                    "username": "paco_gonzalez",
                                                    "idHermandad": "80712f97-4d00-4719-98a5-2aa6ed062b4d"
                                                }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado el usuario",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PutUserDTO.class)),
                            examples = {@ExampleObject(
                                    """
                                        {
                                            "error": "The User or the list of it could not be found" 
                                        }       
                                    """
                            )
                            }))
    })
    @Operation(summary = "Edita un usuario", description = "Devuelve el usuario editado")
    @PutMapping("/user/{id}")
    public ResponseEntity<GetPerfilDTO> editUser(@PathVariable UUID id, @RequestBody PutUserDTO putUserDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.edit(putUserDTO, id));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Edita el usuario autentificado", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PutUserDTO.class)),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                    "nombre": "Alfonso",
                                                    "apellidos": "Benito Alvarez",
                                                    "avatar": "amor.png",
                                                    "email": "holahola@pepedomingocastano.cope",
                                                    "username": "paco_gonzalez",
                                                    "idHermandad": "80712f97-4d00-4719-98a5-2aa6ed062b4d"
                                                }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado el usuario",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PutPasoDTO.class)),
                            examples = {@ExampleObject(
                                    """
                                        {
                                            "error": "The User or the list of it could not be found" 
                                        }       
                                    """
                            )
                            }))
    })
    @Operation(summary = "Edita un usuario", description = "Devuelve el usuario editado")
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/me/edit")
    public ResponseEntity<GetPerfilDTO> editLoginUser(@AuthenticationPrincipal User user, @RequestBody PutUserDTO putUserDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.edit(putUserDTO, user.getId()));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "202",
                    description = "El usuario se ha borrado correctamente",
                    content = @Content
            ),
            @ApiResponse(responseCode = "404",
                    description = "No encontrado el usuario a borrar",
                    content = @Content)
    })
    @Operation(summary = "Borrar Usuario", description = "Devuelve 202 Accepted si todo va bien ")
    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID id){
        boolean borrado = userService.delete(id);
        if(borrado)
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "El usuario se ha creado de forma correcta", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PostUserDTO.class)),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                    "username": "manololama1",
                                                    "password": "doritos",
                                                    "email": "pepe@domingo.hola",
                                                    "nombre": "Pepe",
                                                    "apellidos": "Domingo Castaño",
                                                    "avatar": "pepe.png",
                                                    "hermandadId": "f1f2dc19-69c9-4aab-981c-0967657d4f2b"
                                                }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400",
                    description = "No se han aportado los datos correctamente",
                    content = @Content)
    })
    @Operation(summary = "Crear un nuevo usuario")
    @PostMapping("/user/nuevo")
    public ResponseEntity<PostUserDTO> newUser(@RequestBody PostUserDTO postUserDTO){
        return ResponseEntity.status(201).body(userService.addUser(postUserDTO));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "202",
                    description = "La hermandad se ha borrado correctamente",
                    content = @Content
            ),
            @ApiResponse(responseCode = "404",
                    description = "No encontrado la hermandad a borrar",
                    content = @Content)
    })
    @Operation(summary = "Borrar Hermandad de lista de Hermandades Favoritas de Usuario", description = "Devuelve 202 Accepted si todo va bien ")
    @DeleteMapping("/user/{idUser}/hermandad/{idHermandad}")
    public ResponseEntity<?> deleteHermandadFav(@PathVariable UUID idUser, @PathVariable UUID idHermandad){

        boolean borrado = userService.deleteHermandadFav(idUser, idHermandad);
        if(borrado)
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "La hermandad se ha añadido correctamente",
                    content = @Content
            ),
            @ApiResponse(responseCode = "404",
                    description = "No encontrado la hermandad a añadir",
                    content = @Content)
    })
    @Operation(summary = "Añadir Hermandad a User", description = "Devuelve 200 OK si todo va bien ")
    @PostMapping("/user/{idUser}/hermandad/{idHermandad}")
    public ResponseEntity<GetPerfilWebDTO> addHermandadFav(@PathVariable UUID idUser, @PathVariable UUID idHermandad){

        return ResponseEntity.status(201).body(userService.addHermandadFav(idUser, idHermandad));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "202",
                    description = "La card se ha borrado correctamente",
                    content = @Content
            ),
            @ApiResponse(responseCode = "404",
                    description = "No encontrado la card a borrar",
                    content = @Content)
    })
    @Operation(summary = "Borrar Card de lista de Cards de Usuario", description = "Devuelve 202 Accepted si todo va bien ")
    @DeleteMapping("/user/{idUser}/card/{idCard}")
    public ResponseEntity<?> deleteHermandadFav(@PathVariable UUID idUser, @PathVariable Long idCard){

        boolean borrado = userService.deleteCard(idUser, idCard);
        if(borrado)
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "La card se ha añadido correctamente",
                    content = @Content
            ),
            @ApiResponse(responseCode = "404",
                    description = "No encontrado la card a añadir",
                    content = @Content)
    })
    @Operation(summary = "Añadir Card a User", description = "Devuelve 200 OK si todo va bien ")
    @PostMapping("/user/{idUser}/card/{idCard}")
    public ResponseEntity<GetPerfilWebDTO> addCard(@PathVariable UUID idUser, @PathVariable Long idCard){

        return ResponseEntity.status(201).body(userService.addCard(idUser, idCard));
    }
}
