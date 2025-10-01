package com.mottu.mottuapi.controller;

import com.mottu.mottuapi.entity.Usuario;
import com.mottu.mottuapi.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioController(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    
    @GetMapping("/cadastrar")
    public String mostrarFormCadastro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "form_usuario";
    }

    
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Usuario usuario, Model model) {
        // checagem mínima: senha presente
        if (usuario.getPassword() == null || usuario.getPassword().isBlank()) {
            model.addAttribute("usuario", usuario);
            model.addAttribute("error", "Senha é obrigatória");
            return "form_usuario";
        }

        // role padrão, caso não informado
        if (usuario.getRole() == null || usuario.getRole().isBlank()) {
            usuario.setRole("USER");
        }

        // criptografa e salva
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioRepository.save(usuario);

        return "redirect:/login";
    }
}
