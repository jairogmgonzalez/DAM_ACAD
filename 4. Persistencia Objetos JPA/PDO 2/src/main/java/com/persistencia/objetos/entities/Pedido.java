package com.persistencia.objetos.entities;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fecha;

    @Column(name = "estado", nullable = false)
    private String estado;

    @Column(name = "total")
    private Double total;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "pedido", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    private Set<PedidoProducto> productosPedido = new HashSet<>();

    // Constructor por defecto
    public Pedido() {
    }

    // Constructor por parámetros para los campos no null
    public Pedido(LocalDateTime fecha, String estado, Cliente cliente) {
        this.fecha = fecha;
        this.estado = estado;
        setCliente(cliente);
    }

    // Métodos adicionales
    public void addProductoPedido(PedidoProducto productoPedido) {
        productosPedido.add(productoPedido);

        if (productoPedido != null && productoPedido.getPedido() != this) {
            productoPedido.setPedido(this);
        }

        calcularTotal();
    }

    public void removeProductoPedido(PedidoProducto productoPedido) {
        productosPedido.remove(productoPedido);

        if (productoPedido.getPedido() == this) {
            productoPedido.setPedido(null);
        }

        calcularTotal();
    }

    public void calcularTotal() {
        this.total = productosPedido.stream()
                .mapToDouble(pp -> pp.getCantidad() * pp.getProducto().getPrecio())
                .sum();
    }

    // Getters y setters
    public Long getId() {
        return this.id;
    }

    public LocalDateTime getFecha() {
        return this.fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Double getTotal() {
        return this.total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;

        if (cliente != null && !cliente.getPedidos().contains(this)) {
            cliente.addPedido(this);
        }
    }

    public Set<PedidoProducto> getProductosPedido() {
        return this.productosPedido;
    }

    public void setProductosPedido(Set<PedidoProducto> productosPedido) {
        this.productosPedido = productosPedido;

        if (productosPedido != null) {
            for (PedidoProducto pp : productosPedido) {
                if (pp.getPedido() != this) {
                    pp.setPedido(this);
                }
            }
        }

        calcularTotal();
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

        Pedido that = (Pedido) other;
        return id.equals(that.id);
    }

    // Método hashCode
    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
