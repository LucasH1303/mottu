package com.mottu.mottuapi.repository;

import com.mottu.mottuapi.entity.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("Testes de CRUD - UsuarioRepository")
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
    @DisplayName("Deve criar um novo usuário")
    void testCreateUsuario() {
        // When
        Usuario savedUsuario = usuarioRepository.save(usuario);

        // Then
        assertThat(savedUsuario).isNotNull();
        assertThat(savedUsuario.getId()).isNotNull();
        assertThat(savedUsuario.getUsername()).isEqualTo("testuser");
        assertThat(savedUsuario.getEmail()).isEqualTo("teste@example.com");
    }

    @Test
    @DisplayName("Deve buscar um usuário por ID")
    void testReadUsuarioById() {
        // Given
        Usuario savedUsuario = entityManager.persistAndFlush(usuario);

        // When
        Optional<Usuario> foundUsuario = usuarioRepository.findById(savedUsuario.getId());

        // Then
        assertThat(foundUsuario).isPresent();
        assertThat(foundUsuario.get().getUsername()).isEqualTo("testuser");
        assertThat(foundUsuario.get().getNome()).isEqualTo("Usuário Teste");
    }

    @Test
    @DisplayName("Deve buscar um usuário por username")
    void testReadUsuarioByUsername() {
        // Given
        entityManager.persistAndFlush(usuario);

        // When
        Optional<Usuario> foundUsuario = usuarioRepository.findByUsername("testuser");

        // Then
        assertThat(foundUsuario).isPresent();
        assertThat(foundUsuario.get().getUsername()).isEqualTo("testuser");
        assertThat(foundUsuario.get().getEmail()).isEqualTo("teste@example.com");
    }

    @Test
    @DisplayName("Deve retornar vazio quando buscar por username inexistente")
    void testReadUsuarioByUsernameNotFound() {
        // When
        Optional<Usuario> foundUsuario = usuarioRepository.findByUsername("inexistente");

        // Then
        assertThat(foundUsuario).isEmpty();
    }

    @Test
    @DisplayName("Deve listar todos os usuários")
    void testReadAllUsuarios() {
        // Given
        entityManager.persistAndFlush(usuario);

        Usuario usuario2 = new Usuario();
        usuario2.setUsername("testuser2");
        usuario2.setPassword("$2a$10$encryptedPassword2");
        usuario2.setRole("ROLE_ADMIN");
        usuario2.setNome("Usuário Teste 2");
        usuario2.setEmail("teste2@example.com");
        usuario2.setTelefone("11888888888");
        entityManager.persistAndFlush(usuario2);

        // When
        List<Usuario> usuarios = usuarioRepository.findAll();

        // Then
        assertThat(usuarios).hasSize(2);
        assertThat(usuarios).extracting(Usuario::getUsername)
                .containsExactlyInAnyOrder("testuser", "testuser2");
    }

    @Test
    @DisplayName("Deve atualizar um usuário existente")
    void testUpdateUsuario() {
        // Given
        Usuario savedUsuario = entityManager.persistAndFlush(usuario);
        Long id = savedUsuario.getId();

        // When
        savedUsuario.setNome("Nome Atualizado");
        savedUsuario.setEmail("atualizado@example.com");
        savedUsuario.setTelefone("11777777777");
        Usuario updatedUsuario = usuarioRepository.save(savedUsuario);

        // Then
        assertThat(updatedUsuario.getId()).isEqualTo(id);
        assertThat(updatedUsuario.getNome()).isEqualTo("Nome Atualizado");
        assertThat(updatedUsuario.getEmail()).isEqualTo("atualizado@example.com");
        assertThat(updatedUsuario.getTelefone()).isEqualTo("11777777777");
    }

    @Test
    @DisplayName("Deve deletar um usuário por ID")
    void testDeleteUsuario() {
        // Given
        Usuario savedUsuario = entityManager.persistAndFlush(usuario);
        Long id = savedUsuario.getId();

        // When
        usuarioRepository.deleteById(id);
        entityManager.flush();

        // Then
        Optional<Usuario> deletedUsuario = usuarioRepository.findById(id);
        assertThat(deletedUsuario).isEmpty();
    }

    @Test
    @DisplayName("Deve deletar um usuário por entidade")
    void testDeleteUsuarioByEntity() {
        // Given
        Usuario savedUsuario = entityManager.persistAndFlush(usuario);
        Long id = savedUsuario.getId();

        // When
        usuarioRepository.delete(savedUsuario);
        entityManager.flush();

        // Then
        Optional<Usuario> deletedUsuario = usuarioRepository.findById(id);
        assertThat(deletedUsuario).isEmpty();
    }

    @Test
    @DisplayName("Deve verificar se um usuário existe por ID")
    void testExistsUsuarioById() {
        // Given
        Usuario savedUsuario = entityManager.persistAndFlush(usuario);
        Long id = savedUsuario.getId();

        // When
        boolean exists = usuarioRepository.existsById(id);

        // Then
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Deve retornar false quando verificar ID inexistente")
    void testNotExistsUsuarioById() {
        // When
        boolean exists = usuarioRepository.existsById(999L);

        // Then
        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("Deve contar o total de usuários")
    void testCountUsuarios() {
        // Given
        entityManager.persistAndFlush(usuario);

        Usuario usuario2 = new Usuario();
        usuario2.setUsername("testuser2");
        usuario2.setPassword("$2a$10$encryptedPassword2");
        usuario2.setRole("ROLE_USER");
        usuario2.setNome("Usuário Teste 2");
        usuario2.setEmail("teste2@example.com");
        usuario2.setTelefone("11888888888");
        entityManager.persistAndFlush(usuario2);

        // When
        long count = usuarioRepository.count();

        // Then
        assertThat(count).isEqualTo(2);
    }
}

