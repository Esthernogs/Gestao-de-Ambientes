package com.example.gestaoamb.controller;

import com.example.gestaoamb.model.Pessoa;
import com.example.gestaoamb.model.Professor;
import com.example.gestaoamb.repository.ProfessorRepository;
import com.example.gestaoamb.repository.RoleRepository;
import com.example.gestaoamb.util.FileUploadUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/professor")
public class ProfessorController {

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RoleRepository roleRepository;

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

    @PostMapping ("/salvar")
    public String salvar(
            @Valid Professor professor,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            @RequestParam("foto") MultipartFile multipartFile
    )throws IOException
    {
        if (result.hasErrors()){
            return  "professor/form-inserir";
        }

        // Verifica se o id do aluno está preenchido
        if (professor.getId() != null){
            if (result.hasErrors()){
                return "professor/form-alterar";
            }
        }


        if(!professor.getUser().getPassword().isEmpty()){
            professor.getUser().setFirstName(professor.getNome());
            professor.getUser().setEmail(professor.getEmail());
            professor.getUser().setUsername(professor.getEmail());
            professor.getUser().setPassword(bCryptPasswordEncoder.encode(professor.getUser().getPassword()));
            professor.addRole(roleRepository);
        }

        professorRepository.save(professor);


        if (!multipartFile.isEmpty()){

            String extensao = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
            String fileName = professor.getId() + "." + extensao;
            professor.getUser().setImage(fileName);
            professorRepository.save(professor);

            String uploadPasta =  "src/main/resources/static/assets/img/fotos-usuarios";

            FileUploadUtil.saveFile(uploadPasta, fileName, multipartFile);
        }

        redirectAttributes.addFlashAttribute("mensagem", "Professor inserido com sucesso!");
        return "redirect:/professor";
    }

    @GetMapping ("/excluir/{id}" )
    public String excluir (@PathVariable ("id") Long id, RedirectAttributes redirectAttributes){
        Professor professor = professorRepository.findById(id).orElseThrow();

        professorRepository.delete(professor);

        redirectAttributes.addFlashAttribute("mensagem", "Professor excluído com sucesso!");
        return "redirect:/professor";


    }

   @GetMapping ("/form-alterar/{id}")
    public String formAlterar (@PathVariable ("id") Long id, Model model){
        Professor professor = professorRepository.findById(id).orElseThrow();
        model.addAttribute("professor", professor);
        return "professor/form-alterar";
   }
}
