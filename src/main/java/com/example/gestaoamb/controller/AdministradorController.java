package com.example.gestaoamb.controller;

import com.example.gestaoamb.model.Pessoa;
import com.example.gestaoamb.model.Administrador;
import com.example.gestaoamb.repository.AdministradorRepository;
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
@RequestMapping("/administrador")
public class AdministradorController {

    @Autowired
    private AdministradorRepository administradorRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping
    public String listagem(Model model){
        model.addAttribute("administradores", administradorRepository.findAll());
        return "administrador/listagem";
    }


    @GetMapping("/form-inserir")
    public String formInserir(Model model){

        Pessoa administrador = new Administrador();

        model.addAttribute("administrador", administrador);
        return "administrador/form-inserir";
    }

    @PostMapping ("/salvar")
    public String salvar(
            @Valid Administrador administrador,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            @RequestParam("foto") MultipartFile multipartFile
    )throws IOException
    {
        if (result.hasErrors()){
            return  "administrador/form-inserir";
        }

        // Verifica se o id do aluno está preenchido
        if (administrador.getId() != null){
            if (result.hasErrors()){
                return "administrador/form-alterar";
            }
        }


        if(!administrador.getUser().getPassword().isEmpty()){
            administrador.getUser().setFirstName(administrador.getNome());
            administrador.getUser().setEmail(administrador.getEmail());
            administrador.getUser().setUsername(administrador.getEmail());
            administrador.getUser().setPassword(bCryptPasswordEncoder.encode(administrador.getUser().getPassword()));
            administrador.addRole(roleRepository);
        }

        administradorRepository.save(administrador);


        if (!multipartFile.isEmpty()){

            String extensao = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
            String fileName = administrador.getId() + "." + extensao;
            administrador.getUser().setImage(fileName);
            administradorRepository.save(administrador);

            String uploadPasta =  "src/main/resources/static/assets/img/fotos-usuarios";

            FileUploadUtil.saveFile(uploadPasta, fileName, multipartFile);
        }

        redirectAttributes.addFlashAttribute("mensagem", "Administrador inserido com sucesso!");
        return "redirect:/administrador";
    }

    @GetMapping ("/excluir/{id}" )
    public String excluir (@PathVariable ("id") Long id, RedirectAttributes redirectAttributes){
        Administrador administrador = administradorRepository.findById(id).orElseThrow();

        administradorRepository.delete(administrador);

        redirectAttributes.addFlashAttribute("mensagem", "Administrador excluído com sucesso!");
        return "redirect:/administrador";
    }

   @GetMapping ("/form-alterar/{id}")
    public String formAlterar (@PathVariable ("id") Long id, Model model){
        Administrador administrador = administradorRepository.findById(id).orElseThrow();
        model.addAttribute("administrador", administrador);
        return "administrador/form-alterar";
   }
}
