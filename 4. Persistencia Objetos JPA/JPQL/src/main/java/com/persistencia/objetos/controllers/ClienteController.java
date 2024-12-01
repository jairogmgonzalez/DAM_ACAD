package com.persistencia.objetos.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.persistencia.objetos.entities.Cliente;
import com.persistencia.objetos.repositories.cliente.ClienteRepository;

@RestController
@RequestMapping(value = "/api/clientes")
public class ClienteController {

    private final ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }
    
    @GetMapping("/buscarPorNombreOEmail")
    public List<Cliente> buscarClientesPorNombreOEmail(@RequestParam String texto) {
        return clienteRepository.buscarClientesPorNombreOEmail(texto);
    }

    @GetMapping("/buscarPorNombreOrdenadosPorEmail")
    public List<Cliente> buscarClientesPorNombreOrdenadosPorEmail(@RequestParam String nombre,
                                                                  @RequestParam boolean ascendente) {
        return clienteRepository.buscarClientesPorNombreOrdenadosPorEmail(nombre, ascendente);
    }

    @GetMapping("/contarPorCiudad")
    public List<Object[]> contarClientesPorCiudad() {
        return clienteRepository.contarClientesPorCiudad();
    }
}