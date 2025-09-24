package com.mottu.mottuapi.controller;

import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"/", "/home"})
    public String home(Model model, Principal principal) {
        model.addAttribute("username", principal != null ? principal.getName() : "Visitante");
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
