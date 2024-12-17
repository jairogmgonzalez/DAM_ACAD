package com.taskboard.taskboard.entities;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 50, nullable = false)
    @NotBlank
    private String name;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Task> tasks = new HashSet<Task>();

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Constructor por defecto
    public Category() {
    }

    // Constructor por parámetros completo
    public Category(String name, Board board) {
        this.name = name;
        this.board = board;
    }

    // Métodos adicionales
    public void addTask(Task task) {
        if (!tasks.contains(task)) {
            tasks.add(task);
            task.setCategory(this);
        }
    }

    public void removeTask(Task task) {
        if (tasks.contains(task)) {
            tasks.remove(task);
            task.setCategory(null);
        }
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;

        if (board != null && !board.getCategories().contains(this)) {
            board.addCategory(this);
        }
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks.clear();

        if (tasks != null) {
            for (Task t : tasks) {
                addTask(t);
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
        return "Category {" +
                "id=" + id +
                ", name='" + name + "'" +
                ", boardId=" + (board != null ? board.getId() : null) +
                "}";
    }

    // Método equals
    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (other == null || getClass() != other.getClass())
            return false;

        Category that = (Category) other;
        return Objects.equals(id, that.id);
    }

    // Método hashCode
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
