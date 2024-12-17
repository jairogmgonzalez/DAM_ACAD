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

    // Busca tableros que contengan una palabra clave en el nombre
    @Query("SELECT b FROM Board b WHERE b.name LIKE %:keyword%")
    List<Board> searchByNameContaining(@Param("keyword") String keyword);

    // Cuenta los tableros que tienen descripcion
    @Query(value = "SELECT COUNT(*) FROM boards b WHERE b.description IS NOT NULL")
    Long countBoardsWithDescription();

    // Busca tableros creados después de una fecha determinada
    @Query("SELECT b FROM Board b WHERE b.createdAt > :date")
    List<Board> findBoardsCreatedAfter(@Param("date") LocalDateTime date);

    // Busca tableros que tienen categorías
    @Query("SELECT b FROM Board b WHERE b.categories IS NOT EMPTY")
    List<Board> findBoardsWithCategories();

    // Busca tableros sin categorías
    @Query("SELECT b FROM Board b WHERE b.categories IS EMPTY")
    List<Board> findBoardsWithoutCategories();

    // Busca los tableros con más de x tareas
    @Query(value = "SELECT b.* FROM boards b " +
            "JOIN categories c ON b.id = c.board_id " +
            "JOIN tasks t ON c.id = t.category_id " +
            "GROUP BY b.id " +
            "HAVING COUNT(t.id) > :minTasks", nativeQuery = true)
    List<Board> findBoardsWithMoreThanXTasks(@Param("minTasks") Long minTasks);

    // Actualiza el nombre de un tablero
    @Modifying
    @Transactional
    @Query("UPDATE Board b SET b.name = :newName WHERE b.id = :boardId")
    int updateBoardName(@Param("boardId") Long boardId, @Param("newName") String newName);

}
