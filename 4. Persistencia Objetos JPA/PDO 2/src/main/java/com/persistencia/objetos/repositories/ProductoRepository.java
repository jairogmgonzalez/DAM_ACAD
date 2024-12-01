package com.persistencia.objetos.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.persistencia.objetos.entities.Producto;

import jakarta.transaction.Transactional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Busca los productos por nombre
    public List<Producto> findByNombreContaining(String nombre);

    // Busca los productos por rango de precio
    public List<Producto> findByPrecioBetween(Double precioMin, Double precioMax);

    // Obtiene los productos ordenados por precio
    public List<Producto> findAllByOrderByPrecioDesc();

    // Obtiene los productos más vendidos
    @Query(value = "SELECT p.nombre, SUM(pp.cantidad) as total FROM productos p "
            + "JOIN pedidos_productos pp ON p.id = pp.producto_id "
            + "GROUP BY p.nombre "
            + "ORDER BY total DESC",
            nativeQuery = true)
    List<Object[]> findProductosMasVendidos();

    // Elimina los productos cuyo precio es 0 y no tinen pedidos asociados
    @Modifying
    @Transactional
    @Query("DELETE FROM Producto p WHERE p.precio = 0 AND NOT EXISTS (SELECT pp FROM PedidoProducto pp WHERE pp.producto = p)")
    int eliminarProductosSinPrecioYSinPedidos();

}
