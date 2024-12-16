package com.ud5_springboot.tarea2;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/pruebas")
    public String pruebas(Model model) {
        // LE PASAMOS UN MODELO (DATOS) AL TEMPLATE
        model.addAttribute("title", "Título desde el controlador");
        model.addAttribute("nombre", "Pepe");
        model.addAttribute("apellido", "Gómez");

        // RETORNA EL NOMBRE DEL TEMPLATE HTML
        return "pruebas";
    }
}