package com.taskboard.taskboard.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.taskboard.taskboard.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Búsqueda por campos específicos
    Optional<User> findByName(String name);

    Optional<User> findByEmail(String email);

    // Búsqueda combinada
    Optional<User> findByNameAndEmail(String name, String email);

    // Búsqueda de usuarios con tableros
    @Query("SELECT u FROM User u WHERE u.boards IS NOT EMPTY")
    List<User> findUsersWithBoards();

    // Cuenta tableros por usuario
    @Query(value = "SELECT COUNT(*) FROM boards WHERE user_id = :userId", nativeQuery = true)
    long countBoardsByUserId(@Param("userId") Long userId);

    // Busca usuarios por nombre o email
    @Query("SELECT u FROM User u WHERE u.name LIKE :name OR u.email LIKE :email")
    List<User> findByNameOrEmail(@Param("name") String name, @Param("email") String email);

    // Busca usuarios sin tableros
    @Query("SELECT u FROM User u WHERE u.boards IS EMPTY")
    List<User> findUsersWithoutBoards();

    // Busca los nombres de usuarios con más de x tableros
    @Query(value = "SELECT u.name FROM users u " +
            "JOIN boards b ON u.id = b.user_id " +
            "GROUP BY u.id, u.name " +
            "HAVING COUNT(b.board_id) > :minBoards", nativeQuery = true)
    List<String> findUsersWithMoreThanXBoards(@Param("minBoards") Long minBoards);

    // Busca los nombres de usuarios que empiezan con una letra
    @Query("SELECT u FROM User u WHERE u.name LIKE :prefix%")
    List<User> findUsersByNameStartingWith(@Param("prefix") String prefix);

}
