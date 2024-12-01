package com.persistencia.objetos.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.persistencia.objetos.repositories.PedidoProductoRepository;

@RestController
@RequestMapping("/pedido-producto")
public class PedidoProductoController {

    private final PedidoProductoRepository pedidoProductoRepository;

    public PedidoProductoController(PedidoProductoRepository pedidoProductoRepository) {
        this.pedidoProductoRepository = pedidoProductoRepository;
    }

    // Actualiza la cantidad de un producto en un pedido
    @PutMapping("/actualizar-cantidad")
    public ResponseEntity<String> actualizarCantidadProducto(
            @RequestParam Long pedidoId,
            @RequestParam Long productoId,
            @RequestParam Integer nuevaCantidad) {

        int filasActualizadas = pedidoProductoRepository.actualizarCantidadProducto(pedidoId, productoId, nuevaCantidad);
        
        if (filasActualizadas > 0) {
            return ResponseEntity.ok("Cantidad actualizada correctamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el registro para actualizar.");
        }
    }
}

