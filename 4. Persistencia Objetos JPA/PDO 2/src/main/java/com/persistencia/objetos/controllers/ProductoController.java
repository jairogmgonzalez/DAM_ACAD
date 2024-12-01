package com.persistencia.objetos.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.persistencia.objetos.entities.Producto;
import com.persistencia.objetos.repositories.ProductoRepository;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    private final ProductoRepository productoRepository;

    public ProductoController(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    // Obtiene los productos más vendidos
    @GetMapping("/mas-vendidos")
    public ResponseEntity<List<Object[]>> obtenerProductosMasVendidos() {
        return ResponseEntity.ok(productoRepository.findProductosMasVendidos());
    }

    // Busca productos por rango de precio
    @GetMapping("/rango-precio")
    public ResponseEntity<List<Producto>> buscarPorRangoPrecio(
            @RequestParam Double precioMin,
            @RequestParam Double precioMax) {
        return ResponseEntity.ok(productoRepository.findByPrecioBetween(precioMin, precioMax));
    }

    // Actualiza el precio de un producto
    @PatchMapping("/{id}/precio")
    public ResponseEntity<Producto> actualizarPrecio(
            @PathVariable Long id,
            @RequestBody Double nuevoPrecio) {
        if (nuevoPrecio < 0) {
            return ResponseEntity.badRequest().build();
        }
        return productoRepository.findById(id)
                .map(producto -> {
                    producto.setPrecio(nuevoPrecio);
                    return ResponseEntity.ok(productoRepository.save(producto));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
