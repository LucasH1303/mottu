package com.mottu.mottuapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorControllerCustom {

    @GetMapping("/acesso-negado")
    public String acessoNegado() {
        return "acesso-negado"; 
    }
}
