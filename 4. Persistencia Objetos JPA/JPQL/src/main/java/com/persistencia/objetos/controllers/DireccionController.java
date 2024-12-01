package com.persistencia.objetos.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.persistencia.objetos.entities.Direccion;
import com.persistencia.objetos.repositories.direccion.DireccionRepository;

@RestController
@RequestMapping("/api/direcciones")
public class DireccionController {

    private final DireccionRepository direccionRepository;

    public DireccionController(DireccionRepository direccionRepository) {
        this.direccionRepository = direccionRepository;
    }

    @GetMapping("/buscarPorCiudadOCodigoPostal")
    public List<Direccion> buscarPorCiudadOCodigoPostal(@RequestParam(required = false) String ciudad,
            @RequestParam(required = false) Integer codigoPostal) {
        return direccionRepository.buscarDireccionesPorCiudadOCodigoPostal(ciudad, codigoPostal);
    }

    @GetMapping("/ordenar")
    public List<Direccion> buscarDireccionesOrdenadas(@RequestParam String campoOrden,
            @RequestParam(defaultValue = "true") boolean ascendente) {
        return direccionRepository.buscarDireccionesOrdenadasPor(campoOrden, ascendente);
    }

    @GetMapping("/contarPorCiudad")
    public List<Object[]> contarPorCiudad() {
        return direccionRepository.contarDireccionesPorCiudad();
    }

}
