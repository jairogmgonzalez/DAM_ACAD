package com.taskboard.taskboard.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taskboard.taskboard.entities.Board;
import com.taskboard.taskboard.entities.Category;
import com.taskboard.taskboard.repositories.BoardRepository;

import jakarta.transaction.Transactional;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserService userService;

    // Busca un tablero por su id
    public Board getBoardById(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(("Tablero no encontrado con ID: " + id)));
    }

    // Obtiene todos los tableros
    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }

    // Busca un tablero por su nombre
    public Board getBoardByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }

        return boardRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Tablero no encontrado con nombre: " + name));
    }

    // Busca un tablero por el id de usuario
    public List<Board> getBoardsByUserId(Long userId) {
        userService.getUserById(userId);

        return boardRepository.findByUserId(userId);
    }

    // Busca tableros creados después de una fecha determinada
    public List<Board> getBoardsCreatedAfter(LocalDateTime date) {
        if (date == null) {
            throw new IllegalArgumentException("La fecha no puede ser nula");
        }
        return boardRepository.findBoardsCreatedAfter(date);

    }

    // Cuenta los tableros que tienen descripcion
    public Long countBoardsWithDescription() {
        return boardRepository.countBoardsWithDescription();
    }

    // Busca tableros que tengan al menos x categorías
    public List<Board> getBoardsWithMinCategories(Long minCategories) {
        if (minCategories < 0) {
            throw new IllegalArgumentException("El número mínimo de categorías no puede ser negativo");
        }
        return boardRepository.findBoardsWithMinCategories(minCategories);
    }

    // Busca tableros con más de x tareas
    public List<Board> getBoardsWithMoreThanXTasks(Long minTasks) {
        if (minTasks < 0) {
            throw new IllegalArgumentException("El número mínimo de tareas no puede ser negativo");
        }
        return boardRepository.findBoardsWithMoreThanXTasks(minTasks);
    }

    // Busca tableros que no tienen categorías asociadas
    public List<Board> getBoardsWithoutCategories() {
        return boardRepository.findBoardsWithoutCategories();
    }

    // Cuenta cuántos tableros asociados tiene un usuario
    public Long countBoardsByUserId(Long userId) {
        userService.getUserById(userId);

        return boardRepository.countBoardsByUserId(userId);
    }

    // Crea un nuevo tablero y lo guarda en la base de datos
    @Transactional
    public Board createBoard(Board board) {
        if (board.getId() != null) {
            throw new IllegalArgumentException("Un nuevo tablero no debe tener ID");
        }

        // Valida los campos obligatorios
        validateBoard(board);

        // Verifica que el usuario existe
        userService.getUserById(board.getUser().getId());

        return boardRepository.save(board);
    }

    // Actualiza un tablero existente
    @Transactional
    public Board updateBoard(Board board) {
        Board existingBoard = getBoardById(board.getId());

        validateBoard(board);

        // Verifica que el usuario existe
        userService.getUserById(board.getUser().getId());

        // Actualiza solo los campos no nulos
        if (board.getName() != null) {
            existingBoard.setName(board.getName());
        }
        if (board.getDescription() != null) {
            existingBoard.setDescription(board.getDescription());
        }
        if (board.getUser() != null) {
            existingBoard.setUser(board.getUser());
        }

        return boardRepository.save(existingBoard);
    }

    // Actualiza solo el nombre de un tablero
    @Transactional
    public void updateBoardName(Long id, String newName) {
        if (newName == null || newName.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre proporcionado no es válido");
        }

        getBoardById(id); // Verifica que el tablero existe

        int updatedRows = boardRepository.updateBoardName(id, newName);
        if (updatedRows == 0) {
            throw new RuntimeException("No se pudo actualizar el nombre del tablero");
        }
    }

    // Elimina un tablero
    @Transactional
    public void deleteBoard(Long id) {
        getBoardById(id); // Verifica que el tablero existe
        boardRepository.deleteById(id);
    }

    // Métodos adicionales para gestión de categorías
    @Transactional
    public Board addCategoryToBoard(Long boardId, Category category) {
        Board board = getBoardById(boardId);
        board.addCategory(category);
        return boardRepository.save(board);
    }

    @Transactional
    public Board removeCategoryFromBoard(Long boardId, Category category) {
        Board board = getBoardById(boardId);
        board.removeCategory(category);
        return boardRepository.save(board);
    }

    // Método privado para validaciones comunes
    private void validateBoard(Board board) {
        if (board.getName() == null || board.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (board.getUser() == null) {
            throw new IllegalArgumentException("Debe pertenecer a un usuario");
        }
    }

}
