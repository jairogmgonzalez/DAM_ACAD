package com.taskboard.taskboard.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.taskboard.taskboard.entities.Category;
import com.taskboard.taskboard.entities.Task;
import com.taskboard.taskboard.repositories.CategoryRepository;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BoardService boardService;

    // Obtiene una categoría por su id
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + id));
    }

    // Obtiene todas las categorías
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // Obtiene una categoría por su nombre
    public Category findByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }

        return categoryRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con nombre: " + name));
    }

    // Obtiene todas las categoría que pertenecen a un tablero
    public List<Category> findByBoardId(Long boardId) {
        boardService.getBoardById(boardId);

        return categoryRepository.findByBoardId(boardId);
    }

    // Obtiene todas las categorías que contengan tareas asociadas
    public List<Category> getCategoriesWithTasks() {
        return categoryRepository.findCategoriesWithTasks();
    }

    // Obtiene todas las categorías que no contengan tareas asociadas
    public List<Category> getCategoriesWithoutTasks() {
        return categoryRepository.findCategoriesWithoutTasks();
    }

    // Obtiene cuantas categorías tiene un tablero
    public Long getCountCategoriesByBoardId(Long boardId) {
        boardService.getBoardById(boardId);

        return categoryRepository.countCategoriesByBoardId(boardId);
    }

    // Obtiene todas las categorías creadas después de una fecha
    public List<Category> getCategoriesCreatedAfter(LocalDateTime fecha) {
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha no puede ser nula");
        }

        return categoryRepository.findCategoriesCreatedAfter(fecha);
    }

    // Guarda una nueva categoría en la base de datos
    @Transactional
    public Category createCategory(Category category) {
        if (category.getId() != null) {
            throw new IllegalArgumentException("Una nueva categoría no debe tener ID");
        }

        validateCategory(category);

        boardService.getBoardById(category.getBoard().getId());

        return categoryRepository.save(category);
    }

    @Transactional
    public Category updateCategory(Category category) {
        Category existingCategory = getCategoryById(category.getId());

        validateCategory(category);

        boardService.getBoardById(category.getBoard().getId());

        existingCategory.setName(category.getName());
        existingCategory.setBoard(category.getBoard());

        return categoryRepository.save(existingCategory);
    }

    @Transactional
    public void updateCategoryName(Long id, String newName) {
        if (newName == null || newName.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }

        int updatedRows = categoryRepository.updateCategoryName(id, newName);

        if (updatedRows == 0) {
            throw new RuntimeException("No se pudo actualizar el nombre de la categoría, puede que no exista.");
        }
    }

    @Transactional
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("No se pudo eliminar la categoría, no existe con ID: " + id);
        }

        categoryRepository.deleteById(id);
    }

    // Métodos adicionales para gestión de tareas
    @Transactional
    public Category addTaskToCategory(Long categoryId, Task task) {
        Category category = getCategoryById(categoryId);
        category.addTask(task);
        return categoryRepository.save(category);
    }

    @Transactional
    public Category removeTaskFromCategory(Long categoryId, Task task) {
        Category category = getCategoryById(categoryId);
        category.removeTask(task);
        return categoryRepository.save(category);
    }

    public void validateCategory(Category category) {
        if (category.getName() == null || category.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }

        if (category.getBoard() == null) {
            throw new IllegalArgumentException("Debe pertenecer a un tablero");
        }
    }

}
