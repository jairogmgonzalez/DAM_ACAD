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
import jakarta.persistence.Table;

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", length = 255, nullable = false, unique = true)
    private String nombre;

    @Column(name = "precio", nullable = false)
    private Double precio;

    @Column(name = "descripcion", length = 255)
    private String descripcion;

    @OneToMany(mappedBy = "producto",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<PedidoProducto> pedidosProducto = new HashSet<>();

    // Constructor por defecto
    public Producto() {}

    // Constructor por parámetros para los campos no null
    public Producto(String nombre, Double precio){
        this.nombre = nombre;
        this.precio = precio;
    }

    // Constructor por parámetros completo
    public Producto(String nombre, Double precio, String descripcion){
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
    }

    // Métodos adicionales
    public void addPedidoProducto(PedidoProducto pedidoProducto){
        pedidosProducto.add(pedidoProducto);

        if (pedidoProducto != null && pedidoProducto.getProducto() != this){
            pedidoProducto.setProducto(this);
        }
    }

    public void removePedidoProducto(PedidoProducto pedidoProducto){
        pedidosProducto.remove(pedidoProducto);

        if (pedidoProducto != null && pedidoProducto.getProducto() == this){
            pedidoProducto.setProducto(null);
        }
    }

    // Getters y setters
    public Long getId(){
        return this.id;
    }

    public String getNombre(){
        return this.nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public Double getPrecio(){
        return this.precio;
    }

    public void setPrecio(Double precio){
        this.precio = precio;
    }

    public String getDescripcion(){
        return this.descripcion;
    }

    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }

    public Set<PedidoProducto> getPedidosProducto(){
        return this.pedidosProducto;
    }

    public void setPedidosProducto(Set<PedidoProducto> pedidosProducto){
        this.pedidosProducto = pedidosProducto;

        if (pedidosProducto != null) {
            for (PedidoProducto pp : pedidosProducto) {
                if (pp.getProducto() != this){
                    pp.setProducto(this);
                }
            }
        }
    }

    // Método equals
    @Override
    public boolean equals(Object other){
        if (this == other) return true;
        if (other == null || getClass()!= other.getClass()) return false;

        Producto that = (Producto) other;
        return id.equals(that.id);
    }

    // Método hashCode
    @Override
    public int hashCode(){
        return id.hashCode();
    }

}
