package com.persistencia.objetos.entities;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class PedidoProductoPK implements Serializable{
    
    @Column(name = "pedido_id")
    private Long pedidoId;

    @Column(name = "producto_id")
    private Long productoId;

    // Constructor por defecto
    public PedidoProductoPK() {}

    // Constructor por parámetros completo
    public PedidoProductoPK(Long pedidoId, Long productoId){
        this.pedidoId = pedidoId;
        this.productoId = productoId;
    }

    // Getters y setters
    public Long getPedidoId(){
        return this.pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }

    public Long getProductoId(){
        return this.productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    // Método equals
    @Override
    public boolean equals(Object other){
        if (this == other) return true;
        if (other == null || getClass()!= other.getClass()) return false;

        PedidoProductoPK that = (PedidoProductoPK) other;
        return pedidoId.equals(that.pedidoId) && productoId.equals(that.productoId);
    }

    // Método hashCode
    @Override
    public int hashCode(){
        return Objects.hash(pedidoId, productoId);
    }
    
}
