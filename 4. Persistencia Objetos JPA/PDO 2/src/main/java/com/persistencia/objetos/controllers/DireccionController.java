package com.persistencia.objetos.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.persistencia.objetos.entities.Direccion;
import com.persistencia.objetos.repositories.DireccionRepository;

@RestController
@RequestMapping("/api/direcciones")
public class DireccionController {

    private final DireccionRepository direccionRepository;

    public DireccionController(DireccionRepository direccionRepository) {
        this.direccionRepository = direccionRepository;
    }

    // Obtiene una dirección específica
    @GetMapping("/{id}")
    public ResponseEntity<Direccion> obtenerDireccion(@PathVariable Long id) {
        return direccionRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Busca direcciones por ciudad
    @GetMapping("/ciudad/{ciudad}")
    public ResponseEntity<List<Direccion>> buscarPorCiudad(@PathVariable String ciudad) {
        return ResponseEntity.ok(direccionRepository.findByCiudad(ciudad));
    }

    // Obtiene un conteo de direcciones por ciudad
    @GetMapping("/conteo-por-ciudad")
    public ResponseEntity<Map<String, Long>> obtenerConteoPorCiudad() {
        List<Object[]> resultados = direccionRepository.countByCiudad();
        Map<String, Long> conteoPorCiudad = new HashMap<>();

        for (Object[] fila : resultados) {
            String ciudad = (String) fila[0];
            Long conteo = (Long) fila[1];

            conteoPorCiudad.put(ciudad, conteo);
        }

        return ResponseEntity.ok(conteoPorCiudad);
    }
}
