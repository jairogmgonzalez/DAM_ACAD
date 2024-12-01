package com.persistencia.objetos.repositories.direccion;

import java.util.List;

import com.persistencia.objetos.entities.Direccion;

public interface DireccionCustomRepository {
    
    // Busca direcciones cuyo ciudad contenga un texto dado o tengan un código postal igual que al proporcionado
    List<Direccion> buscarDireccionesPorCiudadOCodigoPostal(String ciudad, Integer codigoPostal);

    // Busca todas las direcciones y las ordenada por un campo
    List<Direccion> buscarDireccionesOrdenadasPor(String campoOrden, boolean ascendente);

    // Cuenta las direcciones por ciduad y las devuelve agrupadas
    List<Object[]> contarDireccionesPorCiudad();

}
