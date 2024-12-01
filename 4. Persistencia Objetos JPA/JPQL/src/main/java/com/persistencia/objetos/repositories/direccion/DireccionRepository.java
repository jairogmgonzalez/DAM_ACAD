package com.persistencia.objetos.repositories.direccion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.persistencia.objetos.entities.Direccion;

@Repository
public interface DireccionRepository extends JpaRepository<Direccion, Long>, DireccionCustomRepository {

}

