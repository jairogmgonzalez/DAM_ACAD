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

        // Búsqueda por campos específicos
        Optional<Board> findByName(String name);

        List<Board> findByUserId(Long userId);

        // Busca tableros creados después de una fecha específica
        @Query("SELECT b FROM Board b WHERE b.createdAt > :date")
        List<Board> findBoardsCreatedAfter(@Param("date") LocalDateTime date);

        // Cuenta los tableros que tienen descripción
        @Query("SELECT COUNT(b) FROM Board b WHERE b.description IS NOT NULL")
        Long countBoardsWithDescription();

        // Busca tableros que tienen al menos x categorías
        @Query(value = "SELECT * FROM boards b " +
                        "JOIN categories c ON b.id = c.board_id " +
                        "GROUP BY b.id " +
                        "HAVING COUNT(c.id) >= :minCategories", nativeQuery = true)
        List<Board> findBoardsWithMinCategories(@Param("minCategories") Long minCategories);

        // Busca tableros con más de x tareas
        @Query(value = "SELECT b.* FROM boards b " +
                        "JOIN categories c ON b.id = c.board_id " +
                        "JOIN tasks t ON c.id = t.category_id " +
                        "GROUP BY b.id " +
                        "HAVING COUNT(t.id) > :minTasks", nativeQuery = true)
        List<Board> findBoardsWithMoreThanXTasks(@Param("minTasks") Long minTasks);

        // Busca tableros que no tienen categorías asociadas
        @Query("SELECT b FROM Board b WHERE b.categories IS EMPTY")
        List<Board> findBoardsWithoutCategories();

        // Busca tableros que tienen categorías
        @Query("SELECT b FROM Board b WHERE b.categories IS NOT EMPTY")
        List<Board> findBoardsWithCategories();

        // Cuenta cuántos tableros tiene un usuario específico
        @Query(value = "SELECT COUNT(*) FROM boards b WHERE b.user_id = :userId", nativeQuery = true)
        long countBoardsByUserId(@Param("userId") Long userId);

        // Actualiza el nombre de un tablero
        @Modifying
        @Transactional
        @Query("UPDATE Board b SET b.name = :newName WHERE b.id = :boardId")
        int updateBoardName(@Param("boardId") Long boardId, @Param("newName") String newName);

}
