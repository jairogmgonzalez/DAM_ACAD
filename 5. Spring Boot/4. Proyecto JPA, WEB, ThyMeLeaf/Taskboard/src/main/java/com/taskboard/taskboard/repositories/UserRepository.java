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

    // Búsqueda por campos específicos
    Optional<User> findByName(String name);

    Optional<User> findByEmail(String email);

    // Busca usuarios cuyo nombre contenga una palabra clave
    @Query("SELECT u FROM User u WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<User> searchByNameContaining(@Param("keyword") String keyword);

    // Busca usuarios que tienen tableros
    @Query("SELECT u FROM User u WHERE u.boards IS NOT EMPTY")
    List<User> findUsersWithBoards();

    // Busca usuarios que no tienen tableros
    @Query("SELECT u FROM User u WHERE u.boards IS EMPTY")
    List<User> findUsersWithoutBoards();

    // Cuenta cuántos usuarios hay en el sistema
    @Query("SELECT COUNT(u) FROM User u")
    long countUsers();

    // Busca los nombres de usuarios con más de X tableros
    @Query(value = "SELECT u.name FROM users u " +
            "JOIN boards b ON u.id = b.user_id " +
            "GROUP BY u.id, u.name " +
            "HAVING COUNT(b.id) > :minBoards", nativeQuery = true)
    List<String> findUsersWithMoreThanXBoards(@Param("minBoards") Long minBoards);

    // Busca usuarios cuyo email termine en un dominio específico
    @Query("SELECT u FROM User u WHERE u.email LIKE %:domain")
    List<User> findUsersByEmailDomain(@Param("domain") String domain);

    // Busca usuarios con el nomre más largo
    @Query(value = "SELECT * FROM users WHERE LENGTH(name) = (SELECT MAX(LENGTH(name)) FROM users)", nativeQuery = true)
    List<User> findUsersWithLongestName();

    // Actualiza el nombre de un usuario
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.name = :newName WHERE u.id = :id")
    int updateUserName(@Param("id") Long id, @Param("newName") String newName);

}
