package com.persistencia.objetos.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "pedidos_productos")
public class PedidoProducto {
    
    @EmbeddedId
    private PedidoProductoPK id;

    @ManyToOne
    @MapsId("pedidoId")
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @ManyToOne
    @MapsId("productoId")
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    // Constructor por defecto
    public PedidoProducto() {}

    // Constructor por parámetros completo
    public PedidoProducto(Pedido pedido, Producto producto, Integer cantidad){
        this.id = new PedidoProductoPK(pedido.getId(), producto.getId());
        this.pedido = pedido;
        this.producto = producto;
        this.cantidad = cantidad;
    }

    // Getters y setters
    public PedidoProductoPK getId(){
        return this.id;
    }

    public Pedido getPedido(){
        return this.pedido;
    }

    public void setPedido(Pedido pedido){
        this.pedido = pedido;

        if (pedido != null){
            this.id.setPedidoId(pedido.getId());

            if (!this.pedido.getProductosPedido().contains(this)){
                this.pedido.getProductosPedido().add(this);
            }
        }
    }

    public Producto getProducto(){
        return this.producto;
    }

    public void setProducto(Producto producto){
        this.producto = producto;

        if (producto != null){
            this.id.setProductoId(producto.getId());

            if (!this.producto.getPedidosProducto().contains(this)){
                this.producto.getPedidosProducto().add(this);
            }
        }
    }

    public Integer getCantidad(){
        return this.cantidad;
    }

    public void setCantidad(Integer cantidad){
        this.cantidad = cantidad;
    }

    // Método equals
    @Override
    public boolean equals(Object other){
        if (this == other) return true;
        if (other == null || getClass()!= other.getClass()) return false;

        PedidoProducto that = (PedidoProducto) other;
        return this.id.equals(that.id);
    }

    // Método hashCode
    @Override
    public int hashCode(){
        return this.id.hashCode();
    }

}
