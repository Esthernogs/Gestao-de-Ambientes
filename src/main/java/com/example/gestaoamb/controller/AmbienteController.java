package com.example.gestaoamb.controller;

import com.example.gestaoamb.model.AgendarAmbiente;
import com.example.gestaoamb.model.Ambiente;
import com.example.gestaoamb.repository.AgendarAmbienteRepository;
import com.example.gestaoamb.repository.AmbienteRepository;
import com.example.gestaoamb.util.FileUploadUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/ambientes")
public class AmbienteController {

    @Autowired
    private AmbienteRepository ambienteRepository;

    @Autowired
    private AgendarAmbienteRepository agendarAmbienteRepository;

    @GetMapping("/listagem-cards")
    public String listagemcards(Model model) {
        model.addAttribute("ambientes", ambienteRepository.findAll());
        return "ambientes/listagem-cards";
    }

    @GetMapping
    public String listagem(Model model) {
        model.addAttribute("ambientes", ambienteRepository.findAll());
        return "ambientes/listagem";
    }

    @GetMapping("/form-inserir")
    public String formInserir(Model model) {

        model.addAttribute("ambiente", new Ambiente());
        return "ambientes/form-inserir";
    }


    @PostMapping("/salvar")
    public String salvar(
            @Valid Ambiente ambiente,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            @RequestParam("foto") MultipartFile multipartFile
    ) throws IOException {
        if (result.hasErrors()) {
            return "ambientes/form-inserir";
        }

        String extensao = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());

        ambienteRepository.save(ambiente);
        String fileName = ambiente.getId() + "." + extensao;
        ambiente.setImage(fileName);
        ambienteRepository.save(ambiente);

        String uploadPasta = "src/main/resources/static/assets/img/fotos-ambientes";

        FileUploadUtil.saveFile(uploadPasta, fileName, multipartFile);
        redirectAttributes.addFlashAttribute("mensagem", "Ambiente inserido com sucesso!");
        return "redirect:/ambientes";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        Ambiente ambiente = ambienteRepository.findById(id).orElseThrow();

        ambienteRepository.delete(ambiente);

        redirectAttributes.addFlashAttribute("mensagem", "Ambiente excluído com sucesso!");
        return "redirect:/ambientes";


    }

    @GetMapping("/form-alterar/{id}")
    public String formAlterar(@PathVariable("id") Long id, Model model) {
        Ambiente ambiente = ambienteRepository.findById(id).orElseThrow();
        model.addAttribute("ambiente", ambiente);
        return "ambientes/form-alterar";
    }

    @GetMapping("/form-agendar/{id}")
    public String formAgendar(@PathVariable("id") Long id, Model model) {
        Ambiente ambiente = ambienteRepository.findById(id).orElseThrow();
        AgendarAmbiente agendaAmbiente = new AgendarAmbiente();
        agendaAmbiente.setAmbiente(ambiente);
        model.addAttribute("agendarAmbiente", agendaAmbiente);
        return "agendar/agendamento";
    }


    @GetMapping("/agendamentos-ambientes/{id}")
    public String formAgendamentos(@PathVariable("id") Long id, Model model) {

        List<AgendarAmbiente> agendamentos = agendarAmbienteRepository.findByAmbienteId(id);
        model.addAttribute("agendamentos", agendamentos);
        return "agendar/agendamentos-ambientes";
    }
}
