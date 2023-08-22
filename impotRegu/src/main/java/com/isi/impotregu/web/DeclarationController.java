package com.isi.impotregu.web;

import com.isi.impotregu.entities.Declarant;
import com.isi.impotregu.entities.Declaration;
import com.isi.impotregu.repository.DeclarantRepository;
import com.isi.impotregu.repository.DeclarationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class DeclarationController {
    @Autowired
    private DeclarationRepository declarationRepository;
    @Autowired
    private DeclarantRepository declarantRepository;

    @GetMapping("/declarations/new")
    public String showNewDeclarationForm(Model model){
        List<Declarant> listDeclarants = declarantRepository.findAll();
        model.addAttribute("declaration", new Declaration());
        model.addAttribute("listDeclarants", listDeclarants);
        return "declaration_form";
    }
    @PostMapping("/declarations/save")
    public String saveDeclaration(Declaration declaration){
        declarationRepository.save(declaration);

        return "redirect:/declarations";
    }

    @GetMapping("/declarations")
    public String listDeclarations(Model model){
        List<Declaration> listDeclarations = declarationRepository.findAll();
        model.addAttribute("listDeclarations", listDeclarations);
        return "declaration";
    }
}
