package com.persistencia.objetos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.persistencia.objetos.entities.Cliente;

@Repository
public interface ClientesRepository extends JpaRepository<Cliente, Long> {

}
