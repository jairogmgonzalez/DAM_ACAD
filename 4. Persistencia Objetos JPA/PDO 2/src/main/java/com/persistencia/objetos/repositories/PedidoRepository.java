package com.persistencia.objetos.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.persistencia.objetos.entities.Cliente;
import com.persistencia.objetos.entities.Pedido;

import jakarta.transaction.Transactional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    // Obtiene todos los pedidos por estado
    List<Pedido> findByEstado(String estado);

    // Busca los pedidos por cliente y estado
    List<Pedido> findByClienteAndEstado(Cliente cliente, String estado);

    // Busca los pedidos cuyo precio total sea superiore a un precio dado
    List<Pedido> findByTotalGreaterThan(Double total);

    // Obtiene todos los pedidos realizados entre dos fechas
    @Query("SELECT p FROM Pedido p WHERE p.fecha BETWEEN :fechaInicio AND :fechaFin")
    List<Pedido> findByFechaBetween(
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin);

    // Actualiza el estado de todos los pedidos que tengan una fecha anterior a la dada y tengan un estado dado
    @Modifying
    @Transactional
    @Query("UPDATE Pedido p SET p.estado = :nuevoEstado WHERE p.fecha < :fecha AND p.estado = :estadoActual")
    int actualizarEstadoPedidosAntiguos(
            @Param("fecha") LocalDateTime fecha,
            @Param("estadoActual") String estadoActual,
            @Param("nuevoEstado") String nuevoEstado
    );
}
