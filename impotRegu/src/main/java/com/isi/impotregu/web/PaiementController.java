package com.isi.impotregu.web;

import com.isi.impotregu.entities.Declarant;
import com.isi.impotregu.entities.Declaration;
import com.isi.impotregu.entities.Paiement;
import com.isi.impotregu.repository.DeclarantRepository;
import com.isi.impotregu.repository.DeclarationRepository;
import com.isi.impotregu.repository.PaiementRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class PaiementController {
    @Autowired
    private PaiementRepository paiementRepository;
    @Autowired
    private DeclarationRepository declarationRepository;

    @GetMapping(path="/paiements")
    public String index(Model model){
        List<Paiement> listPaiements = paiementRepository.findAll();
        model.addAttribute("listPaiements", listPaiements);
        return "paiement";
    }

    @GetMapping("/paiements/new")
    public String showNewDeclarationForm(Model model){
        List<Declaration> listDeclarations = declarationRepository.findAll();
        model.addAttribute("paiement", new Paiement());
        model.addAttribute("listDeclarations", listDeclarations);
        return "paiement_form";
    }
    @PostMapping("/paiements/save")
    public String savePaiement(Paiement paiement){
        paiementRepository.save(paiement);

        return "redirect:/paiements";
    }

}

