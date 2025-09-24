package com.mottu.mottuapi.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.mottu.mottuapi.entity.usuario;

public interface UsuarioRepository extends JpaRepository<usuario, Long> {
    Optional<usuario> findByUsername(String username);
}
