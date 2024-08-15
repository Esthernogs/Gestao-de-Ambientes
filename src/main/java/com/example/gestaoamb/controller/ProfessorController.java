package com.example.gestaoamb.controller;

import com.example.gestaoamb.model.Professor;
import com.example.gestaoamb.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/professor")
public class ProfessorController {

    @Autowired
    private ProfessorRepository professorRepository;

    @GetMapping
    public String listagem(Model model){
        model.addAttribute("professores", professorRepository.findAll());
        return "professor/listagem";
    }


    @GetMapping("/form-inserir")
    public String formInserir(Model model){
        model.addAttribute("professor", new Professor());
        return "professor/form-inserir";
    }
}
