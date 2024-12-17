package com.taskboard.taskboard.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

        // Búsqueda por campos específicos
        Optional<Task> findByName(String name);

        List<Task> findByCategoryId(Long categoryId);

        // Búsquedas por estado y prioridad
        List<Task> findByStatus(TaskStatus status);

        List<Task> findByPriority(TaskPriority priority);

        // Busca las tareas más recientes ordenadas por fecha
        @Query("SELECT t FROM Task t ORDER BY t.createdAt DESC")
        List<Task> findLatestTasks();

        // Cuenta tareas por estado en una categoría específica
        @Query(value = "SELECT COUNT(*) FROM tasks " +
                        "WHERE category_id = :categoryId AND status = :status", nativeQuery = true)
        long countTasksByCategoryAndStatus(@Param("categoryId") Long categoryId,
                        @Param("status") String status);

        // Busca tareas de alta prioridad pendientes
        @Query("SELECT t FROM Task t " +
                        "WHERE t.status = 'PENDING' AND t.priority = 'HIGH' " +
                        "ORDER BY t.createdAt DESC")
        List<Task> findHighPriorityPendingTasks();

        // Busca tareas creadas en un rango de fechas
        @Query("SELECT t FROM Task t " +
                        "WHERE t.createdAt BETWEEN :startDate AND :endDate")
        List<Task> findTasksCreatedBetweenDates(@Param("startDate") LocalDateTime startDate,
                        @Param("endDate") LocalDateTime endDate);

        // Cuenta las tareas asociadas a una categoría
        @Query(value = "SELECT COUNT(*) FROM tasks t WHERE t.category_id = :categoryId", nativeQuery = true)
        Long countTasksByCategoryId(@Param("categoryId") Long categoryId);

        // Actualiza el estado de una tarea
        @Modifying
        @Transactional
        @Query("UPDATE Task t SET t.status = :newStatus WHERE t.id = :taskId")
        int updateTaskStatus(@Param("taskId") Long taskId,
                        @Param("newStatus") TaskStatus newStatus);
}