package com.taskboard.taskboard.controllers.api;

import com.taskboard.taskboard.entities.Task;
import com.taskboard.taskboard.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskApiController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/create")
    public Task crateTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @PutMapping("/update")
    public Task updateTask(@RequestBody Task task) {
        return taskService.updateTask(task);
    }

    @PatchMapping("/update-status/{id}")
    public void updateTaskStatus(@PathVariable("id") Long id, @RequestParam("newStatus") Task.TaskStatus newStatus) {
        taskService.updateTaskStatus(id, newStatus);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTask(@PathVariable("id") Long id) {
        taskService.deleteTask(id);
    }

    @GetMapping("/{id}")
    public Task getTask(@PathVariable("id") Long id) {
        return taskService.getTaskById(id);
    }

    @GetMapping()
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/by-name")
    public List<Task> getTasksByName(@RequestParam("name") String name) {
        return taskService.getTaskByName(name);
    }

    @GetMapping("/by-categoryid/{categoryId}")
    public List<Task> getTasksByCategoryId(@PathVariable("categoryId") Long categoryId) {
        return taskService.getTasksByCategoryId(categoryId);
    }

    @GetMapping("/by-status")
    public List<Task> getTasksByStatus(@RequestParam("status") Task.TaskStatus status) {
        return taskService.getTasksByStatus(status);
    }

    @GetMapping("/by-priority")
    public List<Task> getTasksByPriority(@RequestParam("priority") Task.TaskPriority priority) {
        return taskService.getTasksByPriority(priority);
    }

    @GetMapping("/latest")
    public List<Task> getLatestTasks() {
        return taskService.getLatestTasks();
    }

    @GetMapping("/high-priority")
    public List<Task> getHighPriorityTasks() {
        return taskService.getHighPriorityTasks();
    }

    @GetMapping("/created-between-dates")
    public List<Task> getTasksCreatedBetweenDates(@RequestParam("from") LocalDateTime from, @RequestParam("to") LocalDateTime to) {
        return taskService.getTasksCreatedBetween(from, to);
    }

    @GetMapping("/count-by-categoryid-and-status/{categoryId}")
    public Long getTasksByCategoryIdAndStatus(@PathVariable("categoryId") Long categoryId, @RequestParam("status") Task.TaskStatus status) {
        return taskService.getCountTasksByCategoryAndStatus(categoryId, status);
    }

    @GetMapping("/count-by-categoryid/{categoryId}")
    public Long getCountTasksByCategoryId(@PathVariable("categoryId") Long categoryId) {
        return taskService.getCountTasksByCategoryId(categoryId);
    }

}
