package com.persistencia.objetos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.persistencia.objetos.entities.Cliente;
import com.persistencia.objetos.repositories.ClientesRepository;

import jakarta.transaction.Transactional;

@SpringBootApplication
public class PersistenciaDeObjetosApplication implements CommandLineRunner {

	@Autowired
	private ClientesRepository clienteRepository;

	public static void main(String[] args) {
		SpringApplication.run(PersistenciaDeObjetosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		funcionPruebas1();
	}

	@Transactional
	public void funcionPruebas1() {

		Cliente c1 = new Cliente("Pepe", "Perez");
		Cliente c2 = new Cliente("Juan", "Garcia");

		clienteRepository.save(c1);
		clienteRepository.save(c2);

		clienteRepository.saveAll(List.of(c1, c2));

		Optional<Cliente> optionalCliente = clienteRepository.findById(1L);

		if (optionalCliente.isPresent()) {
			System.out.println("Cliente encontrado: " + optionalCliente.get());
		} else {
			System.out.println("Cliente no encontrado");
		}

	}

}
