package com.taskboard.taskboard.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taskboard.taskboard.entities.Task;
import com.taskboard.taskboard.entities.Task.TaskPriority;
import com.taskboard.taskboard.entities.Task.TaskStatus;
import com.taskboard.taskboard.repositories.TaskRepository;

import jakarta.transaction.Transactional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private CategoryService categoryService;

    // Busca una tarea por su id
    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada con ID: " + id));
    }

    // Obtiene todas las tareas
    public Iterable<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // Obtiene una tarea por su nombre
    public Task getTaskByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }

        return taskRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada con nombre: " + name));
    }

    // Obtiene todas las categorías que pertenezcan a una categoría por su id
    public List<Task> getTasksByCategoryId(Long categoryId) {
        categoryService.getCategoryById(categoryId);

        return taskRepository.findByCategoryId(categoryId);
    }

    // Obtiene todas las categorías por estado
    public List<Task> getTasksByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status);
    }

    // Obtiene todas las categorías por prioridad
    public List<Task> getTasksByPriority(TaskPriority priority) {
        return taskRepository.findByPriority(priority);
    }

    // Obtiene las tareas más reciendas ordenadas por fecha
    public List<Task> getLatestTasks() {
        return taskRepository.findLatestTasks();
    }

    // Cuenta cuántas tareas por estado hay en una categoría específica
    public Long getCountTasksByCategoryAndStatus(Long categoryId, String status) {
        categoryService.getCategoryById(categoryId);

        if (status == null) {
            throw new IllegalArgumentException("El estado no puede ser nulo");
        }

        return taskRepository.countTasksByCategoryAndStatus(categoryId, status);
    }

    // Obtiene todas las tareas de alta prioridad pendientes
    public List<Task> getHighPriorityPendingTasks() {
        return taskRepository.findHighPriorityPendingTasks();
    }

    // Obtiene todas las tareas creadas en un rango de fechas
    public List<Task> getTasksCreatedBetween(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("La fecha no puede ser nula");
        }

        return taskRepository.findTasksCreatedBetweenDates(startDate, endDate);
    }

    // Cuenta las tareas asociadas a una categoría
    public Long getCountTasksByCategoryId(Long categoryId) {
        categoryService.getCategoryById(categoryId);

        return taskRepository.countTasksByCategoryId(categoryId);
    }

    // Crea una nueva tarea en la base de datos
    @Transactional
    public Task createTask(Task task) {
        if (task.getId() != null) {
            throw new IllegalArgumentException("Una nueva tarea no debe tener ID");
        }

        validateTask(task);

        categoryService.getCategoryById(task.getCategory().getId());

        return taskRepository.save(task);
    }

    // Actualiza una tarea existente en la base de datos
    @Transactional
    public Task updateTask(Task task) {
        Task existingTask = getTaskById(task.getId());

        validateTask(task);
        categoryService.getCategoryById(task.getCategory().getId());

        existingTask.setName(task.getName());
        existingTask.setCategory(task.getCategory());

        if (task.getDescription() != null) {
            existingTask.setDescription(task.getDescription());
        }

        if (task.getStatus() != null) {
            existingTask.setStatus(task.getStatus());
        }

        if (task.getPriority() != null) {
            existingTask.setPriority(task.getPriority());
        }

        return taskRepository.save(existingTask);
    }

    @Transactional
    public void updateTaskStatus(Long id, TaskStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("El estado no puede ser nulo");
        }

        int updatedRows = taskRepository.updateTaskStatus(id, status);

        if (updatedRows == 0) {
            throw new RuntimeException("No se pudo actualizar el nombre de la tarea, puede que no exista.");
        }
    }

    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new RuntimeException("No se pudo eliminar la tarea, no existe con ID: " + id);
        }

        taskRepository.deleteById(id);
    }

    // Método privado para validaciones comunes
    private void validateTask(Task task) {
        if (task.getName() == null || task.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (task.getCategory() == null) {
            throw new IllegalArgumentException("Debe pertenecer a una categoría");
        }
    }
}
