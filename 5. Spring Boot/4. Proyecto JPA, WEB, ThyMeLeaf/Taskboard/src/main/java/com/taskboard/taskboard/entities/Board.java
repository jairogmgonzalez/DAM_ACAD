package com.taskboard.taskboard.entities;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "boards")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 50, nullable = false)
    @NotBlank
    private String name;

    @Column(name = "description", length = 255)
    private String description;

    @JsonIgnoreProperties("boards")
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @JsonIgnoreProperties("board")
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Category> categories = new HashSet<Category>();

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Constructor por defecto
    public Board() {
    }

    // Constructor por parámetros para los campos obligatorios
    public Board(String name, User user) {
        this.name = name;
        this.user = user;
    }

    // Constructor por parámetros completo
    public Board(String name, String description, User user) {
        this.name = name;
        this.description = description;
        this.user = user;
    }

    // Métodos adicioanles
    public void addCategory(Category category) {
        if (!categories.contains(category)) {
            categories.add(category);
            category.setBoard(this);
        }
    }

    public void removeCategory(Category category) {
        if (categories.contains(category)) {
            categories.remove(category);
            category.setBoard(null);
        }
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;

        if (user != null && !user.getBoards().contains(this)) {
            user.addBoard(this);
        }
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories.clear();

        if (categories != null) {
            for (Category category : categories) {
                addCategory(category);
            }
        }
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @PrePersist
    private void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    private void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Método toString
    @Override
    public String toString() {
        return "Board{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", userId=" + (user != null ? user.getId() : null) +
                '}';
    }

    // Método equals
    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (other == null || getClass() != other.getClass())
            return false;

        Board that = (Board) other;
        return Objects.equals(id, that.id);
    }

    // Método hashCode
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
