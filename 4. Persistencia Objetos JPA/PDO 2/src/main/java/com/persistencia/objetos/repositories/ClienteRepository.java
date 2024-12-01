package com.persistencia.objetos.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.persistencia.objetos.entities.Cliente;

import jakarta.transaction.Transactional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    // Busca un cliente por un nombre
    Optional<Cliente> findByNombre(String nombre);

    // Busca los clientes por un email
    List<Cliente> findByEmail(String email);

    // Busca los clientes cuya dirección se encuentre en Granada
    List<Cliente> findByDireccionCiudad(String ciudad);

    // Cuenta los clientes por ciudad
    Long countByDireccionCiudad(String ciudad);

    // Busca los clientes cuya cantidad de productos comprados sea superior a una cantidad dada
    @Query(value = "SELECT DISTINCT c.* FROM clientes c "
            + "JOIN pedidos p ON p.cliente_id = c.id "
            + "JOIN pedidos_productos pp ON pp.pedido_id = p.id "
            + "WHERE pp.cantidad > :cantidad",
            nativeQuery = true)
    List<Cliente> findByCantidadCompradaSuperiorA(@Param("cantidad") Integer cantidad);

    // Busca los clientes cuya cantidad de productos comprados sea inferior a una cantidad dada
    @Query("SELECT DISTINCT c FROM Cliente c "
            + "JOIN c.pedidos p "
            + "JOIN p.productosPedido pp "
            + "WHERE pp.cantidad < :cantidad")
    List<Cliente> findByCantidadCompradaInferiorA(@Param("cantidad") Integer cantidad);

    // Busca los clientes que han gastado más de cierta cantidad
    @Query("SELECT c FROM Cliente c "
            + "JOIN c.pedidos p "
            + "WHERE p.total > :total")
    List<Cliente> findByGastoTotalSuperiorA(@Param("total") Double total);

    // Actualiza el email de un cliente
    @Modifying
    @Transactional
    @Query("UPDATE Cliente c SET c.email = :nuevoEmail WHERE c.id = :clienteId")
    int actualizarEmailCliente(@Param("clienteId") Long clienteId,
            @Param("nuevoEmail") String nuevoEmail);
}
