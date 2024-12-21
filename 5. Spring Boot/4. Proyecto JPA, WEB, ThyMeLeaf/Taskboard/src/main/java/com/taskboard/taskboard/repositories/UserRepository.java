package com.taskboard.taskboard.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.taskboard.taskboard.entities.User;

import jakarta.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Búsquedas por campos específicos
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    // Verificar existencias (registro)
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    // Actualiza el nombre de un usuario
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.username = :newUsername WHERE u.id = :userId")
    int updateUsername(@Param("userId") Long userId, @Param("newUsername") String newUsername);

    // Actualiza el email de un usuario
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.email = :newEmail WHERE u.id = :userId")
    int updateEmail(@Param("userId") Long userId, @Param("newEmail") String newEmail);

}