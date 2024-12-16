package com.ud5_springboot.tarea3.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ud5_springboot.tarea3.dto.Producto;

@Controller
public class WebController {

    @GetMapping("/contacto")
    public String contacto(Model model) {
        model.addAttribute("mensaje", "hola desde spring mvc");
        return "contacto";
    }

    @GetMapping("/productos")
    public String productos(Model model) {
        List<Producto> productos = new ArrayList<>();
        productos.add(new Producto(1L, "Silla", 24));
        productos.add(new Producto(2L, "Mesa", 18));
        productos.add(new Producto(3L, "Lámpara", 12));
        model.addAttribute("productos", productos);

        Producto productoEspecial = new Producto(4L, "Sofá", 5);
        model.addAttribute("productoEspecial", productoEspecial);

        return "productos";
    }
}
