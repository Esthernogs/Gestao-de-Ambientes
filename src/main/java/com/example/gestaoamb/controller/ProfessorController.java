package com.example.gestaoamb.controller;

import com.example.gestaoamb.model.Pessoa;
import com.example.gestaoamb.model.Professor;
import com.example.gestaoamb.repository.ProfessorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

        Pessoa professor = new Professor();

        model.addAttribute("professor", professor);
        return "professor/form-inserir";
    }

    @PostMapping ("/inserir")
    public String inserir(@Valid Professor professor, BindingResult result, RedirectAttributes redirectAttributes){
        if (result.hasErrors()){
            return  "professor/form-inserir";
        }

        professorRepository.save(professor);
        redirectAttributes.addFlashAttribute("mensagem", "Professor inserido com sucesso!");
        return "redirect:/professor";
    }

    @GetMapping ("/excluir/{id}" )
    public String excluir (@PathVariable ("id") Long id, RedirectAttributes redirectAttributes){
        Professor professor = professorRepository.findById(id).orElseThrow();

        redirectAttributes.addFlashAttribute("mensagem", "Professor excluído com sucesso!");
        return "redirect:/professor";


    }

   @GetMapping ("/form-alterar/{id}")
    public String formAlterar (@PathVariable ("id") Long id, Model model){
        Professor professor = professorRepository.findById(id).orElseThrow();
        model.addAttribute("professor", professor);
        return "professor/form-alterar";
   }

    @PostMapping ("/alterar")
    public String alterar(Professor professor, BindingResult result, RedirectAttributes redirectAttributes){
        if (result.hasErrors()){
            return  "professor/form-alterar";
        }

        professorRepository.save(professor);
        redirectAttributes.addFlashAttribute("mensagem", "Professor alterado com sucesso!");
        return "redirect:/professor";
    }
}
