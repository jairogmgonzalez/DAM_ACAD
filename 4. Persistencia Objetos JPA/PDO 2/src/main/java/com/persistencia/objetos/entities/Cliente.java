package com.persistencia.objetos.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", length = 255, nullable = false)
    private String nombre;

    @Column(name = "email", length = 255, nullable = false, unique = true)
    private String email;

    @OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL)
    private Direccion direccion;

    @OneToMany(mappedBy = "cliente",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Pedido> pedidos = new HashSet<>();

    // Constructor por defecto
    public Cliente() {
    }

    // Constructor por parámetros para los campos no null
    public Cliente(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }

    // Constructor por parámetros completo
    public Cliente(String nombre, String email, Direccion direccion) {
        this.nombre = nombre;
        this.email = email;
        setDireccion(direccion);
    }

    // Métodos adicionales
    public void addPedido(Pedido pedido) {
        pedidos.add(pedido);

        if (pedido != null && pedido.getCliente() != this) {
            pedido.setCliente(this);
        }
    }

    public void removePedido(Pedido pedido) {
        pedidos.remove(pedido);

        if (pedido.getCliente() == this) {
            pedido.setCliente(null);
        }
    }

    // Getters y setters
    public Long getId() {
        return this.id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Direccion getDireccion() {
        return this.direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;

        if (direccion != null && direccion.getCliente() != this) {
            direccion.setCliente(this);
        }
    }

    public Set<Pedido> getPedidos() {
        return this.pedidos;
    }

    public void setPedidos(Set<Pedido> pedidos) {
        this.pedidos = pedidos;

        if (pedidos != null) {
            for (Pedido p : pedidos) {
                if (p.getCliente() != this) {
                    p.setCliente(this);
                }
            }
        }
    }

    // Método equals
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        Cliente that = (Cliente) other;
        return id.equals(that.id);
    }

    // Método hashCode
    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
