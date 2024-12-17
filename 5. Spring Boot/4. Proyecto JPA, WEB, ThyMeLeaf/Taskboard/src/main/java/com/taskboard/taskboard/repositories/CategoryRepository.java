package com.taskboard.taskboard.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.taskboard.taskboard.entities.Category;

import jakarta.transaction.Transactional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

        // Búsqueda por campos específicos
        Optional<Category> findByName(String name);

        List<Category> findByBoardId(Long boardId);

        // Busca categorías que tienen tareas asociadas
        @Query("SELECT c FROM Category c WHERE c.tasks IS NOT EMPTY")
        List<Category> findCategoriesWithTasks();

        // Busca categorías que no tienen tareas asociadas
        @Query("SELECT c FROM Category c WHERE c.tasks IS EMPTY")
        List<Category> findCategoriesWithoutTasks();

        // Cuenta las categorías que tiene un tablero
        @Query(value = "SELECT COUNT(*) FROM categories c WHERE c.board_id = :boardId", nativeQuery = true)
        Long countCategoriesByBoardId(@Param("boardId") Long boardId);

        // Busca categorías creadas después de una fecha
        @Query("SELECT c FROM Category c WHERE c.createdAt > :date")
        List<Category> findCategoriesCreatedAfter(@Param("date") LocalDateTime date);

        // Actualiza el nombre de una categoría
        @Modifying
        @Transactional
        @Query("UPDATE Category c SET c.name = :newName WHERE c.id = :id")
        int updateCategoryName(@Param("id") Long id, @Param("newName") String newName);

}
