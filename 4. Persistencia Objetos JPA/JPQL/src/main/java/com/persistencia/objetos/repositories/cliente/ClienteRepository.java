package com.persistencia.objetos.repositories.cliente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.persistencia.objetos.entities.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Long>, ClienteCustomRepository {
    
}
