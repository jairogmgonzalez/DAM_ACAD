package com.taskboard.taskboard.controllers.api;

import com.taskboard.taskboard.entities.Board;
import com.taskboard.taskboard.entities.Category;
import com.taskboard.taskboard.entities.Task;
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

    @PostMapping("/create")
    public Category createCategory(@RequestBody Category category) {
        return categoryService.createCategory(category);
    }

    @PutMapping("/update")
    public Category updateCategory(@RequestBody Category category) {
        return categoryService.updateCategory(category);
    }

    @PatchMapping("/update-name/{id}")
    public void updateCategoryName(@PathVariable("id") Long id, @RequestParam("newName") String newName) {
        categoryService.updateCategoryName(id, newName);
    }

    @PostMapping("/{id}/tasks/add")
    public Category addTask(@PathVariable("id") Long id, @RequestBody Task task) {
        return categoryService.addTask(id, task);
    }

    @DeleteMapping("/{id}/tasks/remove")
    public Category removeTask(@PathVariable("id") Long id, @RequestBody Task task) {
        return categoryService.removeTask(id, task);
    }

    @DeleteMapping("/delete/{id}")
    public void removeCategory(@PathVariable("id") Long id) {
        categoryService.deleteCategory(id);
    }

    @GetMapping("/{id}")
    public Category getCategory(@PathVariable("id") Long id) {
        return categoryService.getCategoryById(id);
    }

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/by-name")
    public List<Category> getCategoriesByName(@RequestParam("name") String name) {
        return categoryService.getCategoriesByName(name);
    }

    @GetMapping("/by-boardid/{boardId}")
    public List<Category> getCategoriesByBoardId(@PathVariable("boardId") Long boardId) {
        return categoryService.getCategoriesByBoardId(boardId);
    }

    @GetMapping("/with-tasks")
    public List<Category> getCategoriesWithTasks() {
        return categoryService.getCategoriesWithTasks();
    }

    @GetMapping("/without-tasks")
    public List<Category> getCategoriesWithoutTasks() {
        return categoryService.getCategoriesWithoutTasks();
    }

    @GetMapping("/count-by-board/{boardId}")
    public Long getCategoriesCountByBoardId(@PathVariable("boardId") Long boardId) {
        return categoryService.getCountCategoriesByBoardId(boardId);
    }

    @GetMapping("/created-after")
    public List<Category> getCreatedAfter(@RequestParam("date") LocalDateTime date) {
        return categoryService.getCategoriesCreatedAfter(date);
    }

}
