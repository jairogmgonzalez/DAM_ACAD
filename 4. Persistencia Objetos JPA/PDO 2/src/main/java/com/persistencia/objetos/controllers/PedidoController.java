package com.persistencia.objetos.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.persistencia.objetos.entities.Pedido;
import com.persistencia.objetos.repositories.PedidoRepository;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoRepository pedidoRepository;

    public PedidoController(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    // Crea un nuevo pedido
    @PostMapping
    public ResponseEntity<Pedido> crearPedido(@RequestBody Pedido pedido) {
        Pedido nuevoPedido = pedidoRepository.save(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPedido);

    }

    // Obtiene pedidos entre fechas
    @GetMapping("/rango-fechas")
    public ResponseEntity<List<Pedido>> obtenerPedidosEntreFechas(
            @RequestParam LocalDateTime inicio,
            @RequestParam LocalDateTime fin) {
        return ResponseEntity.ok(pedidoRepository.findByFechaBetween(inicio, fin));
    }

    // Actualiza el estado de un pedido
    @PatchMapping("/actualizar-pedidos-antiguos/")
    public ResponseEntity<Integer> actualizarEstado(
            @RequestParam LocalDateTime fecha,
            @RequestParam String estadoActual,
            @RequestParam String estadoNuevo) {

        int filasActualizadas = pedidoRepository.actualizarEstadoPedidosAntiguos(fecha, estadoActual, estadoNuevo);

        return ResponseEntity.ok(filasActualizadas);
    }
}
