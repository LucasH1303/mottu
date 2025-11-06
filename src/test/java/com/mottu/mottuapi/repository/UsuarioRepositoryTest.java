package com.mottu.mottuapi.repository;

import com.mottu.mottuapi.entity.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application-test.properties")
class UsuarioRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setUsername("testuser");
        usuario.setPassword("$2a$10$encryptedPassword");
        usuario.setRole("ROLE_USER");
        usuario.setNome("Usuário Teste");
        usuario.setEmail("teste@example.com");
        usuario.setTelefone("11999999999");
    }

    @Test
    void testCreate() {
        // Act
        Usuario saved = usuarioRepository.save(usuario);

        // Assert
        assertNotNull(saved.getId());
        assertEquals("testuser", saved.getUsername());
        assertEquals("teste@example.com", saved.getEmail());
        assertEquals("Usuário Teste", saved.getNome());
    }

    @Test
    void testRead() {
        // Arrange
        Usuario saved = entityManager.persistAndFlush(usuario);

        // Act
        Optional<Usuario> found = usuarioRepository.findById(saved.getId());

        // Assert
        assertTrue(found.isPresent());
        assertEquals(saved.getId(), found.get().getId());
        assertEquals("testuser", found.get().getUsername());
        assertEquals("Usuário Teste", found.get().getNome());
    }

    @Test
    void testReadByUsername() {
        // Arrange
        entityManager.persistAndFlush(usuario);

        // Act
        Optional<Usuario> found = usuarioRepository.findByUsername("testuser");

        // Assert
        assertTrue(found.isPresent());
        assertEquals("testuser", found.get().getUsername());
        assertEquals("teste@example.com", found.get().getEmail());
    }

    @Test
    void testReadByUsernameNotFound() {
        // Act
        Optional<Usuario> found = usuarioRepository.findByUsername("inexistente");

        // Assert
        assertFalse(found.isPresent());
    }

    @Test
    void testReadAll() {
        // Arrange
        entityManager.persistAndFlush(usuario);

        Usuario usuario2 = new Usuario();
        usuario2.setUsername("testuser2");
        usuario2.setPassword("$2a$10$encryptedPassword2");
        usuario2.setRole("ROLE_ADMIN");
        usuario2.setNome("Usuário Teste 2");
        usuario2.setEmail("teste2@example.com");
        usuario2.setTelefone("11888888888");
        entityManager.persistAndFlush(usuario2);

        // Act
        List<Usuario> all = usuarioRepository.findAll();

        // Assert
        assertTrue(all.size() >= 2);
    }

    @Test
    void testUpdate() {
        // Arrange
        Usuario saved = entityManager.persistAndFlush(usuario);
        Long id = saved.getId();

        // Act
        saved.setNome("Nome Atualizado");
        saved.setEmail("atualizado@example.com");
        saved.setTelefone("11777777777");
        Usuario updated = usuarioRepository.save(saved);

        // Assert
        assertEquals(id, updated.getId());
        assertEquals("Nome Atualizado", updated.getNome());
        assertEquals("atualizado@example.com", updated.getEmail());
        assertEquals("11777777777", updated.getTelefone());
    }

    @Test
    void testDelete() {
        // Arrange
        Usuario saved = entityManager.persistAndFlush(usuario);
        Long id = saved.getId();

        // Act
        usuarioRepository.deleteById(id);
        entityManager.flush();

        // Assert
        Optional<Usuario> deleted = usuarioRepository.findById(id);
        assertFalse(deleted.isPresent());
    }

    @Test
    void testDeleteByEntity() {
        // Arrange
        Usuario saved = entityManager.persistAndFlush(usuario);
        Long id = saved.getId();

        // Act
        usuarioRepository.delete(saved);
        entityManager.flush();

        // Assert
        Optional<Usuario> deleted = usuarioRepository.findById(id);
        assertFalse(deleted.isPresent());
    }

    @Test
    void testExistsById() {
        // Arrange
        Usuario saved = entityManager.persistAndFlush(usuario);
        Long id = saved.getId();

        // Act
        boolean exists = usuarioRepository.existsById(id);

        // Assert
        assertTrue(exists);
    }

    @Test
    void testExistsByIdNotFound() {
        // Act
        boolean exists = usuarioRepository.existsById(999L);

        // Assert
        assertFalse(exists);
    }

    @Test
    void testCount() {
        // Arrange
        entityManager.persistAndFlush(usuario);

        Usuario usuario2 = new Usuario();
        usuario2.setUsername("testuser2");
        usuario2.setPassword("$2a$10$encryptedPassword2");
        usuario2.setRole("ROLE_USER");
        usuario2.setNome("Usuário Teste 2");
        usuario2.setEmail("teste2@example.com");
        usuario2.setTelefone("11888888888");
        entityManager.persistAndFlush(usuario2);

        // Act
        long count = usuarioRepository.count();

        // Assert
        assertTrue(count >= 2);
    }
}
