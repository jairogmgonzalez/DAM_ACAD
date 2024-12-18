package com.taskboard.taskboard.controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainWebController {

    @GetMapping
    public String homePage() {
        return "index";
    }

    @GetMapping("/menu")
    public String menuPage() {
        return "menu";
    }
}
