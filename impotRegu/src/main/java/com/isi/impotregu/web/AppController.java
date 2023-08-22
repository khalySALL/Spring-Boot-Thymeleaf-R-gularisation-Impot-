package com.isi.impotregu.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {
    @GetMapping("/home")
    public String viewHomePage(){
        return "home";
    }
}
