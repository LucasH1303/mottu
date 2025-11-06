package com.mottu.mottuapi.repository;

import com.mottu.mottuapi.entity.Patio;
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
@DisplayName("Testes de CRUD - PatioRepository")
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
    @DisplayName("Deve criar um novo pátio")
    void testCreatePatio() {
        // When
        Patio savedPatio = patioRepository.save(patio);

        // Then
        assertThat(savedPatio).isNotNull();
        assertThat(savedPatio.getId()).isNotNull();
        assertThat(savedPatio.getNome()).isEqualTo("Pátio Central");
        assertThat(savedPatio.getEndereco()).isEqualTo("Rua Teste, 123");
        assertThat(savedPatio.getLocalizacao()).isEqualTo("São Paulo");
    }

    @Test
    @DisplayName("Deve buscar um pátio por ID")
    void testReadPatioById() {
        // Given
        Patio savedPatio = entityManager.persistAndFlush(patio);

        // When
        Optional<Patio> foundPatio = patioRepository.findById(savedPatio.getId());

        // Then
        assertThat(foundPatio).isPresent();
        assertThat(foundPatio.get().getNome()).isEqualTo("Pátio Central");
        assertThat(foundPatio.get().getEndereco()).isEqualTo("Rua Teste, 123");
        assertThat(foundPatio.get().getLocalizacao()).isEqualTo("São Paulo");
    }

    @Test
    @DisplayName("Deve listar todos os pátios")
    void testReadAllPatios() {
        // Given
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

        // When
        List<Patio> patios = patioRepository.findAll();

        // Then
        assertThat(patios).hasSize(3);
        assertThat(patios).extracting(Patio::getNome)
                .containsExactlyInAnyOrder("Pátio Central", "Pátio Norte", "Pátio Sul");
    }

    @Test
    @DisplayName("Deve atualizar um pátio existente")
    void testUpdatePatio() {
        // Given
        Patio savedPatio = entityManager.persistAndFlush(patio);
        Long id = savedPatio.getId();

        // When
        savedPatio.setNome("Pátio Central Atualizado");
        savedPatio.setEndereco("Rua Atualizada, 999");
        savedPatio.setLocalizacao("São Paulo - Zona Norte");
        Patio updatedPatio = patioRepository.save(savedPatio);

        // Then
        assertThat(updatedPatio.getId()).isEqualTo(id);
        assertThat(updatedPatio.getNome()).isEqualTo("Pátio Central Atualizado");
        assertThat(updatedPatio.getEndereco()).isEqualTo("Rua Atualizada, 999");
        assertThat(updatedPatio.getLocalizacao()).isEqualTo("São Paulo - Zona Norte");
    }

    @Test
    @DisplayName("Deve deletar um pátio por ID")
    void testDeletePatioById() {
        // Given
        Patio savedPatio = entityManager.persistAndFlush(patio);
        Long id = savedPatio.getId();

        // When
        patioRepository.deleteById(id);
        entityManager.flush();

        // Then
        Optional<Patio> deletedPatio = patioRepository.findById(id);
        assertThat(deletedPatio).isEmpty();
    }

    @Test
    @DisplayName("Deve deletar um pátio por entidade")
    void testDeletePatioByEntity() {
        // Given
        Patio savedPatio = entityManager.persistAndFlush(patio);
        Long id = savedPatio.getId();

        // When
        patioRepository.delete(savedPatio);
        entityManager.flush();

        // Then
        Optional<Patio> deletedPatio = patioRepository.findById(id);
        assertThat(deletedPatio).isEmpty();
    }

    @Test
    @DisplayName("Deve verificar se um pátio existe por ID")
    void testExistsPatioById() {
        // Given
        Patio savedPatio = entityManager.persistAndFlush(patio);
        Long id = savedPatio.getId();

        // When
        boolean exists = patioRepository.existsById(id);

        // Then
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Deve retornar false quando verificar ID inexistente")
    void testNotExistsPatioById() {
        // When
        boolean exists = patioRepository.existsById(999L);

        // Then
        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("Deve contar o total de pátios")
    void testCountPatios() {
        // Given
        entityManager.persistAndFlush(patio);

        Patio patio2 = new Patio();
        patio2.setNome("Pátio Norte");
        patio2.setEndereco("Rua Nova, 456");
        patio2.setLocalizacao("Rio de Janeiro");
        entityManager.persistAndFlush(patio2);

        // When
        long count = patioRepository.count();

        // Then
        assertThat(count).isEqualTo(2);
    }

    @Test
    @DisplayName("Deve salvar um pátio com todos os campos nulos exceto nome")
    void testCreatePatioWithMinimalData() {
        // Given
        Patio minimalPatio = new Patio();
        minimalPatio.setNome("Pátio Mínimo");

        // When
        Patio savedPatio = patioRepository.save(minimalPatio);

        // Then
        assertThat(savedPatio).isNotNull();
        assertThat(savedPatio.getId()).isNotNull();
        assertThat(savedPatio.getNome()).isEqualTo("Pátio Mínimo");
    }
}

