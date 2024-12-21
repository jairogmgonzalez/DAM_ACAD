package com.taskboard.taskboard.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taskboard.taskboard.entities.User;
import com.taskboard.taskboard.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Busca un usuario por su id
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }

    // Busca un usuario por su nombre
    public User getUserByUsername(String username) {
        validateUsername(username);

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + username));
    }

    // Busca un usuario por su email
    public User getUserByEmail(String email) {
        validateEmail(email);

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con email: " + email));
    }

    // Valida si existe un usuario por un nombre de usuario
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    // Valida si existe un usuario por un email
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    // Crea un usuario y lo guarda en la base de datos
    @Transactional
    public User createUser(User user) {
        if (user.getId() != null) {
            throw new IllegalArgumentException("Un nuevo usuario no debe tener ID");
        }

        validateUserFields(user);
        if (existsByUsername(user.getUsername()) || existsByEmail(user.getEmail())) {
            throw new RuntimeException("Ya existe un usuario con ese nombre o email");
        }

        return userRepository.save(user);
    }

    // Actualiza un usuario existente en la base de datos
    @Transactional
    public User updateUser(User user) {
        validateUserFields(user);

        User existingUser = getUserById(user.getId());

        if (!user.getUsername().equals(existingUser.getUsername())) {
            if (existsByUsername(user.getUsername())) {
                throw new RuntimeException("Ya existe un usuario con ese nombre");
            }
        }

        if (!user.getEmail().equals(existingUser.getEmail())) {
            if (existsByEmail(user.getEmail())) {
                throw new RuntimeException("Ya existe un usuario con ese email");
            }
        }

        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());

        return userRepository.save(existingUser);
    }

    // Actualiza el nombre de un usuario
    @Transactional
    public void updateUsername(Long id, String newUsername) {
        validateUsername(newUsername);

        User existingUser = getUserById(id);

        if (!newUsername.equals(existingUser.getUsername())) {
            if (existsByUsername(newUsername)) {
                throw new RuntimeException("Ya existe un usuario con ese nombre");
            }
        }

        int updatedRows = userRepository.updateUsername(id, newUsername);

        if (updatedRows == 0) {
            throw new RuntimeException("No se pudo actualizar el nombre");
        }
    }

    // Actualiza el email de un usuario
    @Transactional
    public void updateEmail(Long id, String newEmail) {
        validateEmail(newEmail);

        User existingUser = getUserById(id);

        if (!newEmail.equals(existingUser.getEmail())) {
            if (existsByEmail(newEmail)) {
                throw new RuntimeException("Ya existe un usuario con ese email");
            }
        }

        int updatedRows = userRepository.updateEmail(id, newEmail);

        if (updatedRows == 0) {
            throw new RuntimeException("No se pudo actualizar el email");
        }
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = getUserById(id);
        if (!user.getBoards().isEmpty()) {
            throw new RuntimeException("No se puede eliminar un usuario que tiene tableros asociados");
        }
        userRepository.deleteById(id);
    }

    // Métodos privados de validación
    private void validateUserFields(User user) {
        validateUsername(user.getUsername());
        validateEmail(user.getEmail());
    }

    private void validateUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de usuario no puede estar vacío");
        }
        if (username.length() < 3) {
            throw new IllegalArgumentException("El nombre de usuario debe tener al menos 3 caracteres");
        }
    }

    private void validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email no puede estar vacío");
        }
        if (!email.contains("@") || !email.contains(".")) {
            throw new IllegalArgumentException("Formato de email inválido");
        }
    }


}

