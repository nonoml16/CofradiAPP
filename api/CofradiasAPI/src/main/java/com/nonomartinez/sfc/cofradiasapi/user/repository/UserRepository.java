package com.nonomartinez.sfc.cofradiasapi.user.repository;

import com.nonomartinez.sfc.cofradiasapi.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByUsernameIgnoreCase(String username);

    Optional<User> findFirstByUsername(String username);
}
