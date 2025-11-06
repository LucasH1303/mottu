package com.mottu.mottuapi.repository;

import com.mottu.mottuapi.entity.Moto;
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
@DisplayName("Testes de CRUD - MotoRepository")
class MotoRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MotoRepository motoRepository;

    private Moto moto;
    private Patio patio;

    @BeforeEach
    void setUp() {
        // Criar pátio primeiro
        patio = new Patio();
        patio.setNome("Pátio Central");
        patio.setEndereco("Rua Teste, 123");
        patio.setLocalizacao("São Paulo");
        patio = entityManager.persistAndFlush(patio);

        // Criar moto
        moto = new Moto();
        moto.setPlaca("ABC1234");
        moto.setModelo("CB 600F");
        moto.setFabricante("Honda");
        moto.setPatio(patio);
    }

    @Test
    @DisplayName("Deve criar uma nova moto")
    void testCreateMoto() {
        // When
        Moto savedMoto = motoRepository.save(moto);

        // Then
        assertThat(savedMoto).isNotNull();
        assertThat(savedMoto.getId()).isNotNull();
        assertThat(savedMoto.getPlaca()).isEqualTo("ABC1234");
        assertThat(savedMoto.getModelo()).isEqualTo("CB 600F");
        assertThat(savedMoto.getFabricante()).isEqualTo("Honda");
        assertThat(savedMoto.getPatio()).isNotNull();
        assertThat(savedMoto.getPatio().getId()).isEqualTo(patio.getId());
    }

    @Test
    @DisplayName("Deve buscar uma moto por ID")
    void testReadMotoById() {
        // Given
        Moto savedMoto = entityManager.persistAndFlush(moto);

        // When
        Optional<Moto> foundMoto = motoRepository.findById(savedMoto.getId());

        // Then
        assertThat(foundMoto).isPresent();
        assertThat(foundMoto.get().getPlaca()).isEqualTo("ABC1234");
        assertThat(foundMoto.get().getModelo()).isEqualTo("CB 600F");
        assertThat(foundMoto.get().getFabricante()).isEqualTo("Honda");
    }

    @Test
    @DisplayName("Deve listar todas as motos")
    void testReadAllMotos() {
        // Given
        entityManager.persistAndFlush(moto);

        Moto moto2 = new Moto();
        moto2.setPlaca("XYZ9876");
        moto2.setModelo("MT-07");
        moto2.setFabricante("Yamaha");
        moto2.setPatio(patio);
        entityManager.persistAndFlush(moto2);

        // When
        List<Moto> motos = motoRepository.findAll();

        // Then
        assertThat(motos).hasSize(2);
        assertThat(motos).extracting(Moto::getPlaca)
                .containsExactlyInAnyOrder("ABC1234", "XYZ9876");
    }

    @Test
    @DisplayName("Deve atualizar uma moto existente")
    void testUpdateMoto() {
        // Given
        Moto savedMoto = entityManager.persistAndFlush(moto);
        Long id = savedMoto.getId();

        // Criar novo pátio para atualização
        Patio novoPatio = new Patio();
        novoPatio.setNome("Pátio Norte");
        novoPatio.setEndereco("Rua Nova, 456");
        novoPatio.setLocalizacao("Rio de Janeiro");
        novoPatio = entityManager.persistAndFlush(novoPatio);

        // When
        savedMoto.setPlaca("DEF5678");
        savedMoto.setModelo("CB 650F");
        savedMoto.setFabricante("Honda");
        savedMoto.setPatio(novoPatio);
        Moto updatedMoto = motoRepository.save(savedMoto);

        // Then
        assertThat(updatedMoto.getId()).isEqualTo(id);
        assertThat(updatedMoto.getPlaca()).isEqualTo("DEF5678");
        assertThat(updatedMoto.getModelo()).isEqualTo("CB 650F");
        assertThat(updatedMoto.getPatio().getId()).isEqualTo(novoPatio.getId());
    }

    @Test
    @DisplayName("Deve deletar uma moto por ID")
    void testDeleteMotoById() {
        // Given
        Moto savedMoto = entityManager.persistAndFlush(moto);
        Long id = savedMoto.getId();

        // When
        motoRepository.deleteById(id);
        entityManager.flush();

        // Then
        Optional<Moto> deletedMoto = motoRepository.findById(id);
        assertThat(deletedMoto).isEmpty();
    }

    @Test
    @DisplayName("Deve deletar uma moto por entidade")
    void testDeleteMotoByEntity() {
        // Given
        Moto savedMoto = entityManager.persistAndFlush(moto);
        Long id = savedMoto.getId();

        // When
        motoRepository.delete(savedMoto);
        entityManager.flush();

        // Then
        Optional<Moto> deletedMoto = motoRepository.findById(id);
        assertThat(deletedMoto).isEmpty();
    }

    @Test
    @DisplayName("Deve verificar se uma moto existe por ID")
    void testExistsMotoById() {
        // Given
        Moto savedMoto = entityManager.persistAndFlush(moto);
        Long id = savedMoto.getId();

        // When
        boolean exists = motoRepository.existsById(id);

        // Then
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Deve retornar false quando verificar ID inexistente")
    void testNotExistsMotoById() {
        // When
        boolean exists = motoRepository.existsById(999L);

        // Then
        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("Deve contar o total de motos")
    void testCountMotos() {
        // Given
        entityManager.persistAndFlush(moto);

        Moto moto2 = new Moto();
        moto2.setPlaca("XYZ9876");
        moto2.setModelo("MT-07");
        moto2.setFabricante("Yamaha");
        moto2.setPatio(patio);
        entityManager.persistAndFlush(moto2);

        // When
        long count = motoRepository.count();

        // Then
        assertThat(count).isEqualTo(2);
    }

    @Test
    @DisplayName("Deve buscar motos relacionadas a um pátio")
    void testReadMotosByPatio() {
        // Given
        entityManager.persistAndFlush(moto);

        Patio outroPatio = new Patio();
        outroPatio.setNome("Pátio Sul");
        outroPatio.setEndereco("Rua Outra, 789");
        outroPatio.setLocalizacao("Brasília");
        final Patio outroPatioSalvo = entityManager.persistAndFlush(outroPatio);

        Moto moto2 = new Moto();
        moto2.setPlaca("XYZ9876");
        moto2.setModelo("MT-07");
        moto2.setFabricante("Yamaha");
        moto2.setPatio(outroPatioSalvo);
        entityManager.persistAndFlush(moto2);

        // When
        List<Moto> todasMotos = motoRepository.findAll();

        // Then
        assertThat(todasMotos).hasSize(2);
        assertThat(todasMotos).anyMatch(m -> m.getPatio().getId().equals(patio.getId()));
        assertThat(todasMotos).anyMatch(m -> m.getPatio().getId().equals(outroPatioSalvo.getId()));
    }
}

