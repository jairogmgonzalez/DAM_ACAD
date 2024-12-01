package com.persistencia.objetos.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.persistencia.objetos.entities.Cliente;
import com.persistencia.objetos.repositories.ClienteRepository;

@RestController
@RequestMapping(value = "/api/clientes")
public class ClienteController {

    private final ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    // Busca un cliente por un nombre
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerCliente(@PathVariable Long id) {
        return clienteRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Obtiene todos los clientes
    @GetMapping
    public ResponseEntity<List<Cliente>> obtenerTodosLosClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        return ResponseEntity.ok(clientes);
    }

    // Actualiza el email de un cliente
    @PatchMapping("/{id}/email")
    public ResponseEntity<Cliente> actualizarEmailCliente(
            @PathVariable Long id,
            @RequestBody String nuevoEmail) {

        int filasActualizadas = clienteRepository.actualizarEmailCliente(id, nuevoEmail);

        if (filasActualizadas > 0) {
            return clienteRepository.findById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }

        return ResponseEntity.notFound().build();
    }

    // Obtiene una lista de los clientes que han gastado más de cierta cantidad
    @GetMapping("/gasto-superior")
    public ResponseEntity<List<Cliente>> obtenerClientesConGastoSuperiorA(
            @RequestParam Double cantidad) {
        List<Cliente> clientes = clienteRepository.findByGastoTotalSuperiorA(cantidad);
        return ResponseEntity.ok(clientes);
    }

    // Registra un nuevo cliente
    @PostMapping("/registrar-cliente")
    public ResponseEntity<Cliente> registrarCliente(@RequestBody Cliente cliente) {
        Cliente nuevoCliente = clienteRepository.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCliente);
    }

    // Borra un nuevo cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        if (!clienteRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        clienteRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
