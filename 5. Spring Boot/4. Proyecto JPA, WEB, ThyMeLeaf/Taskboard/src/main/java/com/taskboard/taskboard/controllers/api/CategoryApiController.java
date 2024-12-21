package com.taskboard.taskboard.controllers.api;

import com.taskboard.taskboard.entities.Board;
import com.taskboard.taskboard.entities.Category;
import com.taskboard.taskboard.entities.Task;
import com.taskboard.taskboard.services.BoardService;
import com.taskboard.taskboard.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CancellationException;

@RestController
@RequestMapping("/api/categories")
public class CategoryApiController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/add")
    public Category addCategory(@RequestBody Category category) {
        return categoryService.createCategory(category);
    }
}