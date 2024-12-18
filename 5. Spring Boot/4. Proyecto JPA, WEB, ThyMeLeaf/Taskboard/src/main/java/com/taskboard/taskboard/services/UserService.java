package com.taskboard.taskboard.services;

import java.util.List;

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

    // Obtiene todos los usuarios
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Busca un usuario por su nombre
    public User getUserByName(String name) {
        validateName(name);

        return userRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con nombre: " + name));
    }

    // Busca un usuario por su email
    public User getUserByEmail(String email) {
        validateEmail(email);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con email: " + email));
    }

    // Cuenta cuántos usuarios hay en el sistema
    public long getCountUsers() {
        return userRepository.countUsers();
    }

    // Busca usuarios cuyo nombre contenga una palabra clave
    public List<User> getUsersByNameContains(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException("La palabra clave proporcionada no es válida");
        }

        return userRepository.searchByNameContaining(keyword.toLowerCase());
    }

    // Busca usuarios que tienen tableros
    public List<User> getUsersWithBoards() {
        return userRepository.findUsersWithBoards();
    }

    // Busca usuarios que no tienen tableros
    public List<User> getUsersWithoutBoards() {
        return userRepository.findUsersWithoutBoards();
    }

    // Obtiene los nombres de usuarios con más de x tableros
    public List<String> getUsersNamesWithMoreThanXBoards(Long minBoards) {
        if (minBoards < 0) {
            throw new IllegalArgumentException("El número mínimo de tableros no puede ser negativo");
        }
        return userRepository.findUsersWithMoreThanXBoards(minBoards);
    }

    // Obtiene todos los usuarios cuyo email tiene un dominio específico
    public List<User> getUsersByEmailDomain(String domain) {
        if (domain == null || domain.trim().isEmpty() || !domain.startsWith("@")) {
            throw new IllegalArgumentException("El dominio proporcionado no es válido");
        }
        return userRepository.findUsersByEmailDomain(domain);
    }

    // Obtiene los usuarios con el nombre más largo
    public List<User> getUsersWithLongestName() {
        return userRepository.findUsersWithLongestName();
    }

    // Crea un usuario y lo guarda en la base de datos
    @Transactional
    public User createUser(User user) {
        if (user.getId() != null) {
            throw new IllegalArgumentException("Un nuevo usuario no debe tener ID");
        }

        validateUserFields(user);
        checkEmailUnique(user.getEmail());

        return userRepository.save(user);
    }

    // Actualiza un usuario existente en la base de datos
    @Transactional
    public User updateUser(User user) {
        User existingUser = getUserById(user.getId());

        validateUserFields(user);

        if (!user.getEmail().equals(existingUser.getEmail())) {
            checkEmailUnique(user.getEmail());
        }

        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());

        return userRepository.save(existingUser);
    }

    // Actualiza el nombre de un usuario
    @Transactional
    public void updateUserName(Long id, String newName) {
        validateName(newName);
        getUserById(id);

        int updatedRows = userRepository.updateUserName(id, newName);
        if (updatedRows == 0) {
            throw new RuntimeException("No se pudo actualizar el nombre del usuario");
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
        validateName(user.getName());
        validateEmail(user.getEmail());
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
    }

    private void validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email no puede estar vacío");
        }
    }

    private void checkEmailUnique(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Ya existe un usuario con ese email");
        }
    }

}
