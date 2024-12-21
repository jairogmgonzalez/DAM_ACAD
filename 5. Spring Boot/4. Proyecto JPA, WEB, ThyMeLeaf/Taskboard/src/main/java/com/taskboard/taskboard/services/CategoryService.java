package com.taskboard.taskboard.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.taskboard.taskboard.entities.Board;
import com.taskboard.taskboard.repositories.BoardRepository;
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

    @Autowired
    private UserService userService;

    // Busca una categoría por su id
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(("Categoría no encontrada con ID: " + id)));
    }

    // Busca las categorías de un tablero por su id
    public List<Category> getCategoriesByBoardId(Long boardId) {
        boardService.getBoardById(boardId);

        return categoryRepository.findByBoardId(boardId);
    }

    // Busca las categorías de un tablero por nombre
    public List<Category> getBoardCategoriesByName(Long boardId, String name) {
        boardService.getBoardById(boardId);
        validateName(name);

        return categoryRepository.findBoardCategoriesByName(boardId, name);
    }

    // Obtiene un mapa de las tareas de cada categoría de un tablero específico
    public Map<Category, List<Task>> getTasksByCategory(Long boardId) {
        List<Category> categories = getCategoriesByBoardId(boardId);

        Map<Category, List<Task>> categoryTaskMap = new HashMap<>();

        for (Category category : categories) {
            categoryTaskMap.put(category, new ArrayList<>(category.getTasks()));
        }

        return categoryTaskMap;
    }

    // Obtiene el número de categorías de un tablero
    public Long getCategoriesCountByBoardId(Long boardId) {
        boardService.getBoardById(boardId);

        return categoryRepository.countCategoriesByBoardId(boardId);
    }

    // Obtiene el número de tareas en una categoría
    public Long getTasksCountByCategoryId(Long categoryId) {
        getCategoryById(categoryId);

        return categoryRepository.countTasksInCategory(categoryId);
    }

    // Busca las categorías de un tablero que tienen tareas
    public List<Category> getBoardCategoriesWithTasks(Long boardId) {
        boardService.getBoardById(boardId);

        return categoryRepository.findBoardCategoriesWithTasks(boardId);
    }

    // Busca las categorías de un tablero que tienen tareas
    public List<Category> getBoardCategoriesWithoutTasks(Long boardId) {
        boardService.getBoardById(boardId);

        return categoryRepository.findBoardCategoriesWithoutTasks(boardId);
    }

    // Obtiene las categorías de un tablero ordenadas por número de tareas
    public List<Category> getBoardCategoriesOrderByTaskCount(Long boardId) {
        boardService.getBoardById(boardId);

        return categoryRepository.findBoardCategoriesOrderByTaskCount(boardId);
    }

    // Obtiene el número de categorías totales de un usuario
    public Long getCategoriesCountByUserId(Long userId) {
        userService.getUserById(userId);

        return categoryRepository.countCategoriesByUserId(userId);
    }

    // Crea un nuevo registro de categoría
    @Transactional
    public Category createCategory(Category category) {
        if (category.getId() != null) {
            throw new IllegalArgumentException("Una nueva categoría no debe tener ID");
        }

        validateCategory(category);
        boardService.getBoardById(category.getBoard().getId());

        return categoryRepository.save(category);
    }

    // Actualiza el nombre de una categoría
    @Transactional
    public void updateCategoryName(Long categoryId, Long boardId, String newName) {
        Category existingCategory = getCategoryById(categoryId);
        boardService.getBoardById(boardId);

        validateName(newName);

        existingCategory.setName(newName);

        int updatedRows = categoryRepository.updateCategoryName(categoryId, boardId, newName);
        if (updatedRows == 0) {
            throw new RuntimeException("No se pudo actualizar el nombre");
        }
    }

    // Actualiza la descripción de una categoría
    @Transactional
    public void updateCategoryDescription(Long categoryId, Long boardId, String newDescription) {
        Category existingCategory = getCategoryById(categoryId);
        boardService.getBoardById(boardId);

        validateDescription(newDescription);

        existingCategory.setDescription(newDescription);

        int updatedRows = categoryRepository.updateCategoryDescription(categoryId, boardId, newDescription);
        if (updatedRows == 0) {
            throw new RuntimeException("No se pudo actualizar la descripción");
        }
    }

    // Elimina un regisro de un tablero
    @Transactional
    public void deleteCategory(Long categoryId, Long boardId) {
        Category category = getCategoryById(categoryId);
        boardService.getBoardById(boardId);

        if (!category.getBoard().getId().equals(boardId)) {
            throw new RuntimeException("La categoría no pertenece a este tablero");
        }

        if (!category.getTasks().isEmpty()) {
            throw new RuntimeException("No se puede eliminar una categoría con tareas");
        }

        categoryRepository.deleteById(categoryId);
    }

    // Métodos adicionales para gestión de tareas
    @Transactional
    public Category addTask(Long categoryId, Task task) {
        Category category = getCategoryById(categoryId);
        category.addTask(task);
        return categoryRepository.save(category);
    }

    @Transactional
    public Category removeTask(Long categoryId, Task task) {
        Category category = getCategoryById(categoryId);
        category.removeTask(task);
        return categoryRepository.save(category);
    }


    // Métodos privados de validación
    private void validateCategory(Category category) {
        validateName(category.getName());
        validateBoard(category.getBoard());
        validateDescription(category.getDescription());
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
    }

    private void validateBoard(Board board) {
        if (board == null) {
            throw new IllegalArgumentException("La categoría debe pertenecer a un tablero");
        }
    }

    private void validateDescription(String description) {
        // Si la descripción no es nula pero está vacía o tiene solo espacios, lanza una excepción
        if (description == null && description.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción no puede estar vacía si se proporciona");
        }
    }


}
