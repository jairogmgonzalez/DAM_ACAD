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
    TaskService taskService;

    @PostMapping("/add")
    public Task addTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }
}