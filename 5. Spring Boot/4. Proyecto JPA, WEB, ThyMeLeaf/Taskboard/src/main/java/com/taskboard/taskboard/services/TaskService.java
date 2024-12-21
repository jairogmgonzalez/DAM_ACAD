package com.taskboard.taskboard.services;

import java.time.LocalDateTime;
import java.util.List;

import com.taskboard.taskboard.entities.Category;
import com.taskboard.taskboard.entities.User;
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

    @Autowired
    private UserService userService;
    @Autowired
    private BoardService boardService;

    // Busca una tarea por su id
    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada con ID: " + id));
    }

    // Busca las tareas de una categoría por su id
    public List<Task> getTasksByCategoryId(Long categoryId) {
        categoryService.getCategoryById(categoryId);

        return taskRepository.findByCategoryId(categoryId);
    }

    // Busca las tareas de una categoría por nombre
    public List<Task> getCategoryTasksByName(Long categoryId, String name) {
        categoryService.getCategoryById(categoryId);
        validateName(name);

        return taskRepository.findCategoryTasksByName(categoryId, name);
    }

    // Busca las tareas de una categoría por estado
    public List<Task> getCategoryTasksByStatus(Long categoryId, TaskStatus status) {
        categoryService.getCategoryById(categoryId);
        validateStatus(status);

        return taskRepository.findCategoryTasksByStatus(categoryId, status);
    }

    // Busca las tareas de una categoría por prioridad
    public List<Task> getCategoryTasksByPriority(Long categoryId, TaskPriority priority) {
        categoryService.getCategoryById(categoryId);
        validatePriority(priority);

        return taskRepository.findCategoryTasksByPriority(categoryId, priority);
    }

    // Busca las tareas de una categoría próximas a vencer
    public List<Task> getUpcomingTasks(Long categoryId, LocalDateTime start, LocalDateTime end) {
        categoryService.getCategoryById(categoryId);

        return taskRepository.findUpcomingTasks(categoryId, start, end);
    }

    // Busca las tareas de una categoría vencidas
    public List<Task> getOverdueTasks(Long categoryId, LocalDateTime now) {
        categoryService.getCategoryById(categoryId);

        return taskRepository.findOverdueTasks(categoryId, now);
    }

    // Busca todas las tareas de una categoría ordenadas por fecha de creación
    public List<Task> getLatestTasks(Long categoryId) {
        categoryService.getCategoryById(categoryId);

        return taskRepository.findLatestTasks(categoryId);
    }

    // Busca todas las tareas de una categoría ordenadas por fecha de creación
    public List<Task> getTasksOrderByDueDateDesc(Long categoryId) {
        categoryService.getCategoryById(categoryId);

        return taskRepository.findTasksOrderByDueDateDesc(categoryId);
    }

    // Obtiene el número de tareas por estado en una categoría
    public Long getTasksCountByCategoryAndStatus(Long categoryId, TaskStatus status) {
        categoryService.getCategoryById(categoryId);
        validateStatus(status);

        return taskRepository.countByCategoryIdAndStatus(categoryId, status);
    }

    public boolean isTaskOverdue(Task task) {
        return task.getDueDate() != null &&
                task.getDueDate().isBefore(LocalDateTime.now()) &&
                task.getStatus() != TaskStatus.COMPLETED;
    }

    public boolean isTaskCompleted(Task task) {
        return TaskStatus.COMPLETED.equals(task.getStatus());
    }

    // Obtiene el número de tareas totales de un usuario
    public Long getTasksCountByUserId(Long userId) {
        userService.getUserById(userId);

        return taskRepository.countTasksByUserId(userId);
    }

    // Obtiene el número de tareas totales de un tablero
    public Long getTasksCountByBoardId(Long boardId) {
        boardService.getBoardById(boardId);

        return taskRepository.countTasksByBoardId(boardId);
    }

    // Crea un nuevo registro de tarea
    @Transactional
    public Task createTask(Task task) {
        if (task.getId() != null) {
            throw new IllegalArgumentException("Una nueva tarea no debe tener ID");
        }

        validateTask(task);
        categoryService.getCategoryById(task.getCategory().getId());

        if (task.getStatus() == null) {
            task.setStatus(TaskStatus.PENDING);
        }
        if (task.getPriority() == null) {
            task.setPriority(TaskPriority.MEDIUM);
        }

        return taskRepository.save(task);
    }

    @Transactional
    public Task updateTask(Task task) {
        if (task.getId() == null) {
            throw new IllegalArgumentException("La tarea debe tener un ID para ser actualizada");
        }

        // Busca la tarea existente en la base de datos
        Task existingTask = taskRepository.findById(task.getId())
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la tarea con ID: " + task.getId()));

        // Actualiza los campos solo si no son nulos
        if (task.getName() != null) {
            existingTask.setName(task.getName());
        }
        if (task.getDescription() != null) {
            existingTask.setDescription(task.getDescription());
        }
        if (task.getStatus() != null) {
            existingTask.setStatus(task.getStatus());
        }
        if (task.getPriority() != null) {
            existingTask.setPriority(task.getPriority());
        }
        if (task.getDueDate() != null) {
            existingTask.setDueDate(task.getDueDate());
        }

        // Validación adicional (si es necesario)
        validateTask(existingTask);

        // Guarda la tarea actualizada
        return taskRepository.save(existingTask);
    }

    // Actualiza el nombre de una tarea
    @Transactional
    public void updateTaskName(Long taskId, Long categoryId, String newName) {
        Task existingTask = getTaskById(taskId);
        categoryService.getCategoryById(categoryId);

        validateName(newName);

        int updatedRows = taskRepository.updateTaskName(taskId, categoryId, newName);
        if (updatedRows == 0) {
            throw new RuntimeException("No se pudo actualizar el nombre");
        }
    }

    // Actualiza la descripción de una tarea
    @Transactional
    public void updateTaskDescription(Long taskId, Long categoryId, String newDescription) {
        Task existingTask = getTaskById(taskId);
        categoryService.getCategoryById(categoryId);

        validateDescription(newDescription);

        int updatedRows = taskRepository.updateTaskDescription(taskId, categoryId, newDescription);
        if (updatedRows == 0) {
            throw new RuntimeException("No se pudo actualizar la descripción");
        }
    }

    // Actualiza el status de una tarea
    @Transactional
    public void updateTaskStatus(Long taskId, Long categoryId, TaskStatus newStatus) {
        Task existingTask = getTaskById(taskId);
        categoryService.getCategoryById(categoryId);

        validateStatus(newStatus);

        int updatedRows = taskRepository.updateTaskStatus(taskId, categoryId, newStatus);
        if (updatedRows == 0) {
            throw new RuntimeException("No se pudo actualizar el status");
        }
    }

    // Actualiza la prioridad de una tarea
    @Transactional
    public void updateTaskPriority(Long taskId, Long categoryId, TaskPriority newPriority) {
        Task existingTask = getTaskById(taskId);
        categoryService.getCategoryById(categoryId);

        validatePriority(newPriority);

        int updatedRows = taskRepository.updateTaskPriority(taskId, categoryId, newPriority);
        if (updatedRows == 0) {
            throw new RuntimeException("No se pudo actualizar la prioridad");
        }
    }

    // Actualiza la fecha de vencimiento de una tarea
    @Transactional
    public void updateTaskDueDate(Long taskId, Long categoryId, LocalDateTime dueDate) {
        Task existingTask = getTaskById(taskId);
        categoryService.getCategoryById(categoryId);

        validateDueDate(dueDate);

        int updatedRows = taskRepository.updateTaskDueDate(taskId, categoryId, dueDate);
        if (updatedRows == 0) {
            throw new RuntimeException("No se pudo actualizar la fecha de vencimiento");
        }
    }

    // Elimina un registro de tarea
    @Transactional
    public void deleteTask(Long taskId, Long categoryId) {
        Task task = getTaskById(taskId);
        categoryService.getCategoryById(categoryId);

        if (!task.getCategory().getId().equals(categoryId)) {
            throw new RuntimeException("La tarea no pertenece a esta categoría");
        }

        taskRepository.deleteById(taskId);
    }

    // Método privado para validaciones comunes
    private void validateTask(Task task) {
        validateName(task.getName());
        if (task.getDescription() != null) {
            validateDescription(task.getDescription());
        }
        if (task.getCategory() == null) {
            throw new IllegalArgumentException("La tarea debe pertenecer a una categoría");
        }
        if (task.getDueDate() != null) {
            validateDueDate(task.getDueDate());
        }
        if (task.getStatus() != null) {
            validateStatus(task.getStatus());
        }
        if (task.getPriority() != null) {
            validatePriority(task.getPriority());
        }
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
    }

    private void validateDescription(String description) {
        if (description != null && description.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción no puede estar vacía si se proporciona");
        }
    }

    private void validateStatus(TaskStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("El status no puede ser nulo");
        }
    }

    private void validatePriority(TaskPriority priority) {
        if (priority == null) {
            throw new IllegalArgumentException("La prioridad no puede ser nulo");
        }
    }

    private void validateDueDate(LocalDateTime dueDate) {
        if (dueDate != null && dueDate.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("La fecha de vencimiento no puede ser en el pasado");
        }
    }
}
