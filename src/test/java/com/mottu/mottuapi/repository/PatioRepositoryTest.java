package com.mottu.mottuapi.repository;

import com.mottu.mottuapi.entity.Patio;
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
class PatioRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PatioRepository patioRepository;

    private Patio patio;

    @BeforeEach
    void setUp() {
        patio = new Patio();
        patio.setNome("Pátio Central");
        patio.setEndereco("Rua Teste, 123");
        patio.setLocalizacao("São Paulo");
    }

    @Test
    void testCreate() {
        // Act
        Patio saved = patioRepository.save(patio);

        // Assert
        assertNotNull(saved.getId());
        assertEquals("Pátio Central", saved.getNome());
        assertEquals("Rua Teste, 123", saved.getEndereco());
        assertEquals("São Paulo", saved.getLocalizacao());
    }

    @Test
    void testRead() {
        // Arrange
        Patio saved = entityManager.persistAndFlush(patio);

        // Act
        Optional<Patio> found = patioRepository.findById(saved.getId());

        // Assert
        assertTrue(found.isPresent());
        assertEquals(saved.getId(), found.get().getId());
        assertEquals("Pátio Central", found.get().getNome());
        assertEquals("Rua Teste, 123", found.get().getEndereco());
        assertEquals("São Paulo", found.get().getLocalizacao());
    }

    @Test
    void testReadAll() {
        // Arrange
        entityManager.persistAndFlush(patio);

        Patio patio2 = new Patio();
        patio2.setNome("Pátio Norte");
        patio2.setEndereco("Rua Nova, 456");
        patio2.setLocalizacao("Rio de Janeiro");
        entityManager.persistAndFlush(patio2);

        Patio patio3 = new Patio();
        patio3.setNome("Pátio Sul");
        patio3.setEndereco("Avenida Sul, 789");
        patio3.setLocalizacao("Brasília");
        entityManager.persistAndFlush(patio3);

        // Act
        List<Patio> all = patioRepository.findAll();

        // Assert
        assertTrue(all.size() >= 3);
    }

    @Test
    void testUpdate() {
        // Arrange
        Patio saved = entityManager.persistAndFlush(patio);
        Long id = saved.getId();

        // Act
        saved.setNome("Pátio Central Atualizado");
        saved.setEndereco("Rua Atualizada, 999");
        saved.setLocalizacao("São Paulo - Zona Norte");
        Patio updated = patioRepository.save(saved);

        // Assert
        assertEquals(id, updated.getId());
        assertEquals("Pátio Central Atualizado", updated.getNome());
        assertEquals("Rua Atualizada, 999", updated.getEndereco());
        assertEquals("São Paulo - Zona Norte", updated.getLocalizacao());
    }

    @Test
    void testDelete() {
        // Arrange
        Patio saved = entityManager.persistAndFlush(patio);
        Long id = saved.getId();

        // Act
        patioRepository.deleteById(id);
        entityManager.flush();

        // Assert
        Optional<Patio> deleted = patioRepository.findById(id);
        assertFalse(deleted.isPresent());
    }

    @Test
    void testFindByIdNotFound() {
        // Act
        Optional<Patio> found = patioRepository.findById(999L);

        // Assert
        assertFalse(found.isPresent());
    }
}
