package com.mottu.mottuapi.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mottu.mottuapi.entity.usuario;
import com.mottu.mottuapi.repository.UsuarioRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (usuarioRepository.count() == 0) {
            usuario admin = new usuario(null, "admin", passwordEncoder.encode("admin123"), "ROLE_ADMIN");
            usuario user = new usuario(null, "user", passwordEncoder.encode("user123"), "ROLE_USER");
            usuarioRepository.save(admin);
            usuarioRepository.save(user);
            System.out.println("Criados usuários iniciais: admin/admin123 e user/user123");
        }
    }
}
