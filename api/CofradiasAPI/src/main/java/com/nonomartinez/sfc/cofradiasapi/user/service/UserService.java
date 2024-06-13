package com.nonomartinez.sfc.cofradiasapi.user.service;

import com.nonomartinez.sfc.cofradiasapi.MyPage;
import com.nonomartinez.sfc.cofradiasapi.card.service.CardService;
import com.nonomartinez.sfc.cofradiasapi.exception.NotFoundException;
import com.nonomartinez.sfc.cofradiasapi.hermandad.dto.PostHermandadDTO;
import com.nonomartinez.sfc.cofradiasapi.hermandad.model.Hermandad;
import com.nonomartinez.sfc.cofradiasapi.hermandad.repository.HermandadRepository;
import com.nonomartinez.sfc.cofradiasapi.hermandad.service.HermandadService;
import com.nonomartinez.sfc.cofradiasapi.musica.service.MusicaService;
import com.nonomartinez.sfc.cofradiasapi.user.dto.*;
import com.nonomartinez.sfc.cofradiasapi.user.model.User;
import com.nonomartinez.sfc.cofradiasapi.user.model.UserRole;
import com.nonomartinez.sfc.cofradiasapi.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final MusicaService musicaService;
    private final CardService cardService;
    private final HermandadService hermandadService;
    private final HermandadRepository hermandadRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public User createUser(CreateUserRequest createUserRequest, EnumSet<UserRole> roles) {

        if (userRepository.existsByUsernameIgnoreCase(createUserRequest.getUsername()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre de usuario ya existe");

        User user =  User.builder()
                .username(createUserRequest.getUsername())
                .password(passwordEncoder.encode(createUserRequest.getPassword()))
                .avatar(createUserRequest.getAvatar())
                .nombre(createUserRequest.getNombre())
                .apellidos(createUserRequest.getApellidos())
                .email(createUserRequest.getEmail())
                .roles(roles)
                .build();

        return userRepository.save(user);
    }

    public User createUserWithUserRole(CreateUserRequest createUserRequest) {
        return createUser(createUserRequest, EnumSet.of(UserRole.USER));
    }

    public User createUserWithAdminRole(CreateUserRequest createUserRequest) {
        return createUser(createUserRequest, EnumSet.of(UserRole.ADMIN));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(UUID id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findFirstByUsername(username);
    }

    @Transactional
    public void actualizarUsername(UUID id, String nuevoUsername) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            user.setUsername(nuevoUsername);
        }
    }

    public GetPerfilDTO edit(PutUserDTO putUserDTO, UUID id) {

        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isEmpty())
            throw new NotFoundException("No existe el usuario");

        User editado = userOptional.get();

        editado.setNombre(putUserDTO.nombre());
        editado.setApellidos(putUserDTO.apellidos());
        editado.setAvatar(putUserDTO.avatar());
        editado.setEmail(putUserDTO.email());
        editado.setUsername(putUserDTO.username());

        userRepository.save(editado);
        return GetPerfilDTO.of(editado);

    }

    public Optional<User> editPassword(UUID userId, String newPassword) {

        return userRepository.findById(userId)
                .map(u -> {
                    u.setPassword(passwordEncoder.encode(newPassword));
                    return userRepository.save(u);
                });

    }

    public boolean delete(UUID id){
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isEmpty())
            throw new NotFoundException("No existe el usuario");

        User aBorrar = userOptional.get();

        userRepository.delete(aBorrar);

        return true;
    }


    public boolean passwordMatch(User user, String clearPassword) {
        return passwordEncoder.matches(clearPassword, user.getPassword());
    }

    public GetPerfilDTO getPerfil(User user){
        Optional<User> userOptional = userRepository.findById(user.getId());
        if(userOptional.isEmpty())
            throw new NotFoundException("User");

        return GetPerfilDTO.of(userOptional.get());
    }

    public GetHomeDTO getHome(){
        List<String> fotos = new ArrayList<>();
        fotos.add("amor1.png");
        fotos.add("amor2.png");
        fotos.add("amor3.png");
        fotos.add("amor4.png");
        return GetHomeDTO.of(
                fotos,
                //hermandadService.getFiveRandomFotos(),
                hermandadService.getHermandadDia(),
                cardService.getFiveRandomCards(),
                hermandadService.getFiveRandomHermandades(),
                musicaService.getFiveRandomBandas()
        );
    }

    public MyPage<GetUserListDTO> getAllUsersPaginado(Pageable pageable){
        Page<User> userPage = userRepository.findAll(pageable);

        return MyPage.of(userPage.map(GetUserListDTO::of));
    }

    public List<GetUserWebListDTO> getAllUsers(){

        return userRepository.findAll().stream().map(GetUserWebListDTO::of).toList();
    }

    public PostUserDTO addUser(PostUserDTO nuevo){
        Optional<Hermandad> hermandadOptional = hermandadRepository.findById(nuevo.hermandadId());

        Hermandad hermandad = hermandadOptional.get();

        User user = User.builder()
                .username(nuevo.username())
                .password(passwordEncoder.encode(nuevo.password()))
                .email(nuevo.email())
                .nombre(nuevo.nombre())
                .apellidos(nuevo.apellidos())
                .avatar(nuevo.avatar())
                .hermandad(hermandad)
                .build();

        userRepository.save(user);

        return nuevo;

    }
}
