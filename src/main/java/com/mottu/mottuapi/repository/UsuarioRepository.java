package com.mottu.mottuapi.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.mottu.mottuapi.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
}
