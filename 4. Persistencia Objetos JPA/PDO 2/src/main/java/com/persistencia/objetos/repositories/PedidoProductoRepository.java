package com.persistencia.objetos.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.persistencia.objetos.entities.Cliente;
import com.persistencia.objetos.entities.Pedido;
import com.persistencia.objetos.entities.PedidoProducto;
import com.persistencia.objetos.entities.PedidoProductoPK;
import com.persistencia.objetos.entities.Producto;

import jakarta.transaction.Transactional;

@Repository
public interface PedidoProductoRepository extends JpaRepository<PedidoProducto, PedidoProductoPK> {

    // Busca todos los registros de un pedido dado
    List<PedidoProducto> findByPedido(Pedido pedido);

    // Busca todos los registros de un producto dado
    List<PedidoProducto> findByProducto(Producto producto);

    // Busca todos los registros donde la cantidad de productos vendidos sea mayor a una cantidad dada
    List<PedidoProducto> findByCantidadGreaterThan(Integer cantidad);

    // Calcula el total gastado por un cliente en un producto dado
    @Query("SELECT SUM(pp.cantidad * pp.producto.precio) "
            + "FROM PedidoProducto pp "
            + "WHERE pp.producto = :producto "
            + "AND pp.pedido.cliente = :cliente")
    Double calcularTotalGastadoPorClienteEnProducto(
            @Param("producto") Producto producto,
            @Param("cliente") Cliente cliente
    );

    // Actualiza la cantidad que de un producto en un pedido dado
    @Modifying
    @Transactional
    @Query("UPDATE PedidoProducto pp SET pp.cantidad = :nuevaCantidad "
            + "WHERE pp.pedido.id = :pedidoId AND pp.producto.id = :productoId")
    int actualizarCantidadProducto(
            @Param("pedidoId") Long pedidoId,
            @Param("productoId") Long productoId,
            @Param("nuevaCantidad") Integer nuevaCantidad
    );

}
