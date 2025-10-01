package com.mottu.mottuapi.controller;

import com.mottu.mottuapi.entity.Patio;
import com.mottu.mottuapi.service.PatioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/patio") 
public class PatioViewController {

    private final PatioService patioService;

    public PatioViewController(PatioService patioService) {
        this.patioService = patioService;
    }

    
    @GetMapping("/listar")
    public String listarRedirect() {
        return "redirect:/patios"; 
    }

    
    @GetMapping("/novo")
    public String novoPatio(Model model) {
        model.addAttribute("patio", new Patio());
        return "patio-form"; 
    }

    
    @PostMapping("/salvar")
    public String salvarPatio(@ModelAttribute("patio") Patio patio) {
        patioService.criar(patio);
        return "redirect:/patios"; 
    }

    
    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        patioService.deletar(id);
        return "redirect:/patios"; 
    }

    
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Patio patio = patioService.buscarPorId(id);
        model.addAttribute("patio", patio);
        return "patio-form"; 
    }
}


@Controller
class PatioListController {

    private final PatioService patioService;

    public PatioListController(PatioService patioService) {
        this.patioService = patioService;
    }

    
    @GetMapping("/patios")
    public String listar(Model model) {
        model.addAttribute("patios", patioService.listarTodos());
        return "patios"; 
    }
}
