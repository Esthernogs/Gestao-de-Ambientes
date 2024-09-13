package com.example.gestaoamb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ambientes")
public class AmbienteController {

    @GetMapping
    public String listagem() {
        return "ambientes/telasamb";
    }
}
