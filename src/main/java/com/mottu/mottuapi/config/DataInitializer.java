package com.mottu.mottuapi.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mottu.mottuapi.entity.Usuario;
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
            Usuario admin = new Usuario(
                null,
                "admin", 
                passwordEncoder.encode("admin123"), 
                "ROLE_ADMIN",
                "Administrador",
                "admin@mottu.com",
                "11999999999"
            );

            Usuario user = new Usuario(
                null,
                "user", 
                passwordEncoder.encode("user123"), 
                "ROLE_USER",
                "Usuário",
                "user@mottu.com",
                "11988888888"
            );

            usuarioRepository.save(admin);
            usuarioRepository.save(user);

            System.out.println("Usuários iniciais criados: admin/admin123 e user/user123");
        }
    }
}
