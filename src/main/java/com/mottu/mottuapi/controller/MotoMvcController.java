package com.mottu.mottuapi.controller;

import com.mottu.mottuapi.entity.Moto;
import com.mottu.mottuapi.entity.Patio;
import com.mottu.mottuapi.service.MotoService;
import com.mottu.mottuapi.service.PatioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/motos")
public class MotoMvcController {

    private final MotoService motoService;
    private final PatioService patioService;

    public MotoMvcController(MotoService motoService, PatioService patioService) {
        this.motoService = motoService;
        this.patioService = patioService;
    }

    
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("motos", motoService.listarTodos());
        return "motos"; 
    }

    
    @GetMapping("/novo")
    public String novoForm(Model model) {
        model.addAttribute("moto", new Moto());
        model.addAttribute("patios", patioService.listarTodos());
        return "form_moto";
    }

    
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Moto moto) {
        motoService.salvar(moto);
        return "redirect:/motos";
    }

    
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Moto moto = motoService.buscarPorId(id);
        model.addAttribute("moto", moto);
        model.addAttribute("patios", patioService.listarTodos());
        return "form_moto";
    }

    
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        motoService.deletar(id);
        return "redirect:/motos";
    }
}
