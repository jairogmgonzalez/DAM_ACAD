package com.taskboard.taskboard.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.taskboard.taskboard.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.taskboard.taskboard.entities.Task;
import com.taskboard.taskboard.entities.Task.TaskPriority;
import com.taskboard.taskboard.entities.Task.TaskStatus;

import jakarta.transaction.Transactional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

        // Obtiene todas las tareas de una categoría
        List<Task> findByCategoryId(Long categoryId);

        // Busca tareas de una categoría por nombre
        @Query("SELECT t FROM Task t WHERE t.category.id = :categoryId AND LOWER(t.name) LIKE LOWER(CONCAT('%', :name, '%'))")
        List<Task> findCategoryTasksByName(@Param("categoryId") Long categoryId, @Param("name") String name);

        // Busca tareas de una categoría por estado
        @Query("SELECT t FROM Task t WHERE t.category.id = :categoryId AND t.status = :status")
        List<Task> findCategoryTasksByStatus(@Param("categoryId") Long categoryId, @Param("status") TaskStatus status);

        // Busca tareas de una categoría por prioridad
        @Query("SELECT t FROM Task t WHERE t.category.id = :categoryId AND t.priority = :priority")
        List<Task> findCategoryTasksByPriority(@Param("categoryId") Long categoryId, @Param("priority") TaskPriority priority);

        // Busca tareas de una categoría próximas a vencer
        @Query("SELECT t FROM Task t WHERE t.category.id = :categoryId AND t.dueDate BETWEEN :start AND :end")
        List<Task> findUpcomingTasks(@Param("categoryId") Long categoryId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

        // Busca tareas de una categoría atrasadas
        @Query("SELECT t FROM Task t WHERE t.category.id = :categoryId AND t.dueDate < :now AND t.status != 'COMPLETED'")
        List<Task> findOverdueTasks(@Param("categoryId") Long categoryId, @Param("now") LocalDateTime now);

        // Busca todas las tareas de una categoría ordenadas por fecha de creación
        @Query("SELECT t FROM Task t WHERE t.category = :categoryId ORDER BY t.createdAt DESC")
        List<Task> findLatestTasks(@Param("categoryId") Long categoryId);

        // Obtiene todas las tareas de una categoría ordenadas por fecha de vencimiento
        @Query("SELECT t FROM Task t WHERE t.category.id = :categoryId ORDER BY t.dueDate DESC")
        List<Task> findTasksOrderByDueDateDesc(@Param("categoryId") Long categoryId);

        // Obtiene el número de tareas por estado en una categoría
        @Query("SELECT COUNT(t) FROM Task t WHERE t.category.id = :categoryId AND t.status = :status")
        long countByCategoryIdAndStatus(@Param("categoryId") Long categoryId, @Param("status") TaskStatus status);

        @Query(value = "SELECT COUNT(t.id) FROM tasks t " +
                "JOIN categories c ON t.category_id = c.id " +
                "JOIN boards b ON c.board_id = b.id " +
                "WHERE b.user_id = :userId", nativeQuery = true)
        Long countTasksByUserId(@Param("userId") Long userId);

        @Query(value = "SELECT COUNT(t.id) FROM tasks t " +
                "JOIN categories c ON t.category_id = c.id " +
                "WHERE c.board_id = :boardId", nativeQuery = true)
        Long countTasksByBoardId(@Param("boardId") Long boardId);

        // Actualiza el nombre de una tarea
        @Modifying
        @Transactional
        @Query("UPDATE Task t SET t.name = :newName WHERE t.id = :taskId AND t.category.id = :categoryId")
        int updateTaskName(@Param("taskId") Long taskId, @Param("categoryId") Long categoryId, @Param("newName") String newName);

        // Actualiza la descripción de una tarea
        @Modifying
        @Transactional
        @Query("UPDATE Task t SET t.description = :newDescription WHERE t.id = :taskId AND t.category.id = :categoryId")
        int updateTaskDescription(@Param("taskId") Long taskId, @Param("categoryId") Long categoryId, @Param("newDescription") String newDescription);

        // Actualiza el estado de una tarea
        @Modifying
        @Transactional
        @Query("UPDATE Task t SET t.status = :status WHERE t.id = :taskId AND t.category.id = :categoryId")
        int updateTaskStatus(@Param("taskId") Long taskId, @Param("categoryId") Long categoryId, @Param("status") TaskStatus status);

        // Actualiza la prioridad de una tarea
        @Modifying
        @Transactional
        @Query("UPDATE Task t SET t.priority = :priority WHERE t.id = :taskId AND t.category.id = :categoryId")
        int updateTaskPriority(@Param("taskId") Long taskId, @Param("categoryId") Long categoryId, @Param("priority") TaskPriority priority);

        // Actualiza la fecha de vencimiento
        @Modifying
        @Transactional
        @Query("UPDATE Task t SET t.dueDate = :dueDate WHERE t.id = :taskId AND t.category.id = :categoryId")
        int updateTaskDueDate(@Param("taskId") Long taskId, @Param("categoryId") Long categoryId, @Param("dueDate") LocalDateTime dueDate);

}
