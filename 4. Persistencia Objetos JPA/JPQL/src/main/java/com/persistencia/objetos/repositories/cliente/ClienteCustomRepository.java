package com.persistencia.objetos.repositories.cliente;

import java.util.List;

import com.persistencia.objetos.entities.Cliente;

public interface ClienteCustomRepository {

    // Busca clientes cuyo nombre o email contengan un texto dado.
    List<Cliente> buscarClientesPorNombreOEmail(String texto);

    // Busca clientes por nombre y los ordena por email.
    List<Cliente> buscarClientesPorNombreOrdenadosPorEmail(String nombre, boolean ascendente);

    // Cuenta cuántos clientes hay por ciudad.
    List<Object[]> contarClientesPorCiudad();
    
}
