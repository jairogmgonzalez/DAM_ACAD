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

    // Busca categorías que tienen tareas creadas
    @Query("SELECT c FROM Category c WHERE c.tasks IS NOT EMPTY")
    List<Category> findCategoriesWithTasks();

    // Cuenta las tareas asociadas a una categoría
    @Query(value = "SELECT COUNT(*) FROM tasks WHERE category_id = :categoryId", nativeQuery = true)
    long countTasksByCategoryId(@Param("categoryId") Long categoryId);

    // Busca categorías por nombre o que contengan ciertas palabras
    @Query("SELECT c FROM Category c WHERE c.name LIKE :term")
    List<Category> findByNameContaining(@Param("term") String searchTerm);

    // Cuenta las categorías que no tienen tareas asociadas
    @Query(value = "SELECT COUNT(*) FROM categories c " +
            "LEFT JOIN tasks t ON c.id = t.category_id " +
            "WHERE t.id IS NULL", nativeQuery = true)
    Long countCategoriesWithoutTasks();

    // Busca las categorías creadas antes de una fecha específica
    @Query("SELECT c FROM Category c WHERE c.createdAt < :date")
    List<Category> findCategoriesCreatedBefore(@Param("date") LocalDateTime date);

    // Busca categorías con tareas de alta prioridad
    @Query("SELECT DISTINCT c FROM Category c " +
            "JOIN c.tasks t " +
            "WHERE t.priority = 'HIGH'")
    List<Category> findCategoriesWithHighPriorityTasks();

    // Actualiza el nombre de una categoría
    @Modifying
    @Transactional
    @Query("UPDATE Category c SET c.name = :newName WHERE c.id = :categoryId")
    int updateCategoryName(@Param("categoryId") Long categoryId, @Param("newName") String newName);

}
