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

    // Busca los tableros de un usuario por su nombre
    public List<Board> getBoardsByUserId(Long userId) {
        userService.getUserById(userId);

        return boardRepository.findByUserId(userId);
    }

    // Busca los tableros de un usuario por su nombre
    public List<Board> getUserBoardsByName(Long userId, String name) {
        userService.getUserById(userId);
        validateName(name);

        return boardRepository.findUserBoardsByName(userId, name);
    }

    // Obtiene el número de tableros de un usuario
    public Long getBoardsCountByUserId(Long userId) {
        userService.getUserById(userId);

        return boardRepository.countBoardsByUserId(userId);
    }

    // Busca los tableros de un usuario que tienen categorías
    public List<Board> getUserBoardsWithCategories(Long userId) {
        userService.getUserById(userId);

        return boardRepository.findUserBoardsWithCategories(userId);
    }

    // Busca los tableros de un usuario que no tienen categorías
    public List<Board> getUserBoardsWithoutCategories(Long userId) {
        userService.getUserById(userId);

        return boardRepository.findUserBoardsWithoutCategories(userId);
    }

    // Busca los tableros de un usuario creados antes de una fecha
    public List<Board> getUsersBoardsCreatedBefore(Long userId, LocalDateTime date) {
        userService.getUserById(userId);

        return boardRepository.findUserBoardsCreatedBefore(userId, date);
    }

    // Busca los tableros de un usuario creados después de una fecha
    public List<Board> getUsersBoardsCreatedAfter(Long userId, LocalDateTime date) {
        userService.getUserById(userId);

        return boardRepository.findUserBoardsCreatedAfter(userId, date);
    }

    // Crea un nuevo registro de tablero
    @Transactional
    public Board createBoard(Board board) {
        if (board.getId() != null) {
            throw new IllegalArgumentException("Un nuevo tablero no debe tener ID");
        }

        validateBoardFields(board);
        userService.getUserById(board.getUser().getId());

        return boardRepository.save(board);
    }

    // Actualiza el nombre de un tablero
    @Transactional
    public void updateBoardName(Long boardId, Long userId, String newName) {
        Board existingBoard = getBoardById(boardId);
        userService.getUserById(userId);

        validateName(newName);

        existingBoard.setName(newName);

        int updatedRows = boardRepository.updateBoardName(boardId, userId, newName);
        if (updatedRows == 0) {
            throw new RuntimeException("No se pudo actualizar el nombre");
        }
    }

    // Actualiza la descripción de un tablero
    @Transactional
    public void updateBoardDescription(Long boardId, Long userId, String newDescription) {
        Board existingBoard = getBoardById(boardId);
        userService.getUserById(userId);

        validateDescription(newDescription);

        existingBoard.setDescription(newDescription);

        int updatedRows = boardRepository.updateBoardDescription(boardId, userId, newDescription);
        if (updatedRows == 0) {
            throw new RuntimeException("No se pudo actualizar la descripción");
        }
    }

    // Elimina un regisro de un tablero
    @Transactional
    public void deleteBoard(Long boardId, Long userId) {
        Board board = getBoardById(boardId);

        if (!board.getUser().getId().equals(userId)) {
            throw new RuntimeException("No tienes permiso para eliminar este tablero");
        }

        boardRepository.deleteById(boardId);
    }


    // Métodos privados de validación
    private void validateBoardFields(Board board) {
        validateName(board.getName());
        validateDescription(board.getDescription());
        if (board.getUser() == null) {
            throw new IllegalArgumentException("El tablero debe pertenecer a un usuario");
        }
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
    }

    private void validateDescription(String description) {
        if (description == null && description.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción no puede estar vacía");
        }
    }

}
