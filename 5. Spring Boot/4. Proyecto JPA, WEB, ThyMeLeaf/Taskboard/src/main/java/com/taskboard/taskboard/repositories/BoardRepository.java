package com.taskboard.taskboard.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.taskboard.taskboard.entities.Board;

import jakarta.transaction.Transactional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

        // Obtiene todos los tableros de un usuario
        List<Board> findByUserId(Long userId);

        // Busca tableros de un usuario por nombre
        @Query("SELECT b FROM Board b WHERE b.user.id = :userId AND LOWER(b.name) LIKE LOWER(CONCAT('%', :name, '%'))")
        List<Board> findUserBoardsByName(@Param("userId") Long userId, @Param("name") String name);

        // Obtiene el número de tableros de un usuario
        @Query("SELECT COUNT(b) FROM Board b WHERE b.user.id = :userId")
        Long countBoardsByUserId(@Param("userId") Long userId);

        // Busca tableros de un usuario que tienen categorías
        @Query("SELECT b FROM Board b WHERE b.user.id = :userId AND b.categories IS NOT EMPTY")
        List<Board> findUserBoardsWithCategories(@Param("userId") Long userId);

        // Busca tableros de un usuario sin categorías
        @Query("SELECT b FROM Board b WHERE b.user.id = :userId AND b.categories IS EMPTY")
        List<Board> findUserBoardsWithoutCategories(@Param("userId") Long userId);

        // Busca tableros de un usuario creados antes de una fecha
        @Query("SELECT b FROM Board b WHERE b.user.id = :userId AND b.createdAt < :date")
        List<Board> findUserBoardsCreatedBefore(@Param("userId") Long userId, @Param("date") LocalDateTime date);

        // Busca tableros de un usuario creados después de una fecha
        @Query("SELECT b FROM Board b WHERE b.user.id = :userId AND b.createdAt > :date")
        List<Board> findUserBoardsCreatedAfter(@Param("userId") Long userId, @Param("date") LocalDateTime date);

        // Actualiza el nombre de un tablero
        @Modifying
        @Transactional
        @Query("UPDATE Board b SET b.name = :newName WHERE b.id = :boardId AND b.user.id = :userId")
        int updateBoardName(@Param("boardId") Long boardId, @Param("userId") Long userId, @Param("newName") String newName);

        // Actualiza la descripción de de un tablero
        @Modifying
        @Transactional
        @Query("UPDATE Board b SET b.description = :newDescription WHERE b.id = :boardId AND b.user.id = :userId")
        int updateBoardDescription(@Param("boardId") Long boardId, @Param("userId") Long userId, @Param("newDescription") String newDescription);

}

