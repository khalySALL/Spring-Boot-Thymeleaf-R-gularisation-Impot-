package com.isi.impotregu.web;

import com.isi.impotregu.entities.Declarant;
import com.isi.impotregu.repository.DeclarantRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.model.IModel;

import java.util.List;

@Controller
@AllArgsConstructor
public class DeclarantController {
    private DeclarantRepository declarantRepository;
    @GetMapping(path="/index")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "0") int p,
                        @RequestParam(name = "size", defaultValue = "4") int s,
                        @RequestParam(name = "keyword", defaultValue = "") String kw
                        ){
        Page<Declarant> pageDeclarants=declarantRepository.findByAdresseContains(kw,PageRequest.of(p,s));

        model.addAttribute("listDeclarants",pageDeclarants.getContent());
        model.addAttribute("pages",new int[pageDeclarants.getTotalPages()]);
        model.addAttribute("currentPage",p);
        model.addAttribute("keyword",kw);
        return "declarant";
    }
   /* @GetMapping("/delete")
    public String delete(
            @RequestParam(name = "id", defaultValue = "id")Long id,
            @RequestParam(name = "keyword", defaultValue = "")String keyword,
            @RequestParam(name = "page", defaultValue = "0")int page){
        declarantRepository.deleteById(id);
        return "redirect:/index?page="+page+"&keyword="+keyword;
    }*/

    @GetMapping("/")
    public String home(){
        return "redirect:/index";
    }

    @GetMapping ("/formDeclarants")
    public String formDeclarant(Model model){
        model.addAttribute("declarant", new Declarant());
        return "fromDeclarants";
    }

    @PostMapping("/saveDeclarant")
    public String saveDeclarant(@Valid  Declarant declarant, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "fromDeclarants";
        }
        declarantRepository.save(declarant);
        return "redirect:/index";

    }
}

