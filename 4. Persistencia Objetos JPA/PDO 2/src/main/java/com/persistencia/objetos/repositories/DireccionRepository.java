package com.persistencia.objetos.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.persistencia.objetos.entities.Direccion;

import jakarta.transaction.Transactional;

@Repository
public interface DireccionRepository extends JpaRepository<Direccion, Long> {

    // Obtiene todas las direcciones por ciudad
    List<Direccion> findByCiudad(String ciudad);

    // Obtiene todas las direcciones por código postal
    List<Direccion> findByCodigoPostal(Integer codigoPostal);

    // Cuenta cuantas direcciones hay por cada ciudad
    @Query("SELECT d.ciudad, COUNT(d) FROM Direccion d GROUP BY d.ciudad")
    List<Object[]> countByCiudad();

    // Elimina las direcciones de una ciudad que no tienen cliente asociado
    @Modifying
    @Transactional
    @Query("DELETE FROM Direccion d WHERE d.ciudad = :ciudad AND d.cliente IS NULL")
    int eliminarDireccionesSinClienteEnCiudad(@Param("ciudad") String ciudad);

}
