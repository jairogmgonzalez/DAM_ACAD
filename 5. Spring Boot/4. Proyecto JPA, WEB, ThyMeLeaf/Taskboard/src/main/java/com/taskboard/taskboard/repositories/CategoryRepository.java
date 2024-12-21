package com.taskboard.taskboard.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.taskboard.taskboard.entities.Board;
import com.taskboard.taskboard.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.taskboard.taskboard.entities.Category;

import jakarta.transaction.Transactional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

        // Obtiene todas las categorías de un tablero
        List<Category> findByBoardId(Long boardId);

        // Busca categorías de un tablero por nombre
        @Query("SELECT c FROM Category c WHERE c.board.id = :boardId AND LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))")
        List<Category> findBoardCategoriesByName(@Param("boardId") Long boardId, @Param("name") String name);

        // Obtiene el número de categorías de un tablero
        @Query("SELECT COUNT(c) FROM Category c WHERE c.board.id = :boardId")
        Long countCategoriesByBoardId(@Param("boardId") Long boardId);

        // Busca categorías de un tablero que tienen tareas
        @Query("SELECT c FROM Category c WHERE c.board.id = :boardId and c.tasks IS NOT EMPTY")
        List<Category> findBoardCategoriesWithTasks(@Param("boardId") Long boardId);

        // Busca categorías de un tablero que no tienen tareas
        @Query("SELECT c FROM Category c WHERE c.board.id = :boardId and c.tasks IS EMPTY")
        List<Category> findBoardCategoriesWithoutTasks(@Param("boardId") Long boardId);

        // Obtiene el número de tareas que tiene una categoría
        @Query("SELECT COUNT(t) FROM Category c JOIN c.tasks t WHERE c.id = :categoryId")
        Long countTasksInCategory(@Param("categoryId") Long categoryId);

        // Obtiene las categorías de un tablero ordenadas por número de tareas
        @Query("SELECT c FROM Category c LEFT JOIN c.tasks t WHERE c.board.id = :boardId GROUP BY c ORDER BY COUNT(t) DESC")
        List<Category> findBoardCategoriesOrderByTaskCount(@Param("boardId") Long boardId);

        @Query(value = "SELECT COUNT(c.id) FROM categories c " +
                "JOIN boards b ON c.board_id = b.id " +
                "WHERE b.user_id = :userId", nativeQuery = true)
        Long countCategoriesByUserId(@Param("userId") Long userId);

        // Actualiza el nombre de una categoría
        @Modifying
        @Transactional
        @Query("UPDATE Category c SET c.name = :newName WHERE c.id = :categoryId AND c.board.id = :boardId")
        int updateCategoryName(@Param("categoryId") Long categoryId, @Param("boardId") Long boardId, @Param("newName") String newName);

        // Actualiza la descripción de una categoría
        @Modifying
        @Transactional
        @Query("UPDATE Category c SET c.description = :newDescription WHERE c.id = :categoryId AND c.board.id = :boardId")
        int updateCategoryDescription(@Param("categoryId") Long categoryId, @Param("boardId") Long boardId, @Param("newDescription") String newDescription);

}