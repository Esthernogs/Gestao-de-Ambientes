package com.example.gestaoamb.controller;

import com.example.gestaoamb.model.AgendarAmbiente;
import com.example.gestaoamb.model.Professor;
import com.example.gestaoamb.repository.AgendarAmbienteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Controller
@RequestMapping ("/agendar-ambiente")
public class AgendarAmbienteController {

    @Autowired
    private AgendarAmbienteRepository agendarAmbienteRepository;
    @PostMapping("/salvar")
    public String salvar(@Valid AgendarAmbiente agendarAmbiente, BindingResult result, RedirectAttributes redirectAttributes){
        if (result.hasErrors()){
            return  "agendar/agendamento";
        }

        agendarAmbienteRepository.save(agendarAmbiente);
        redirectAttributes.addFlashAttribute("mensagem", "Agendamento realizado com sucesso!");
        return "redirect:/ambientes/listagem-cards";
    }

    @GetMapping("/excluir/{id}/{idAmbiente}" )
    public String excluir (@PathVariable("id") Long id, @PathVariable("idAmbiente") Long idAmbiente, RedirectAttributes redirectAttributes){
        AgendarAmbiente agendarAmbiente = agendarAmbienteRepository.findById(id).orElseThrow();

        String urlRedirecionamento = "/ambientes/agendamentos-ambientes/"+idAmbiente;

        agendarAmbienteRepository.delete(agendarAmbiente);

        redirectAttributes.addFlashAttribute("mensagem", "Agendamento excluído com sucesso!");
        return "redirect:"+urlRedirecionamento;


    }
}
