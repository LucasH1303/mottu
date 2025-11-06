package com.mottu.mottuapi.repository;

import com.mottu.mottuapi.entity.Moto;
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
class MotoRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MotoRepository motoRepository;

    private Moto moto;
    private Patio patio;

    @BeforeEach
    void setUp() {
        patio = new Patio();
        patio.setNome("Pátio Central");
        patio.setEndereco("Rua Teste, 123");
        patio.setLocalizacao("São Paulo");
        patio = entityManager.persistAndFlush(patio);

        moto = new Moto();
        moto.setPlaca("ABC1234");
        moto.setModelo("CB 600F");
        moto.setFabricante("Honda");
        moto.setPatio(patio);
    }

    @Test
    void testCreate() {
        // Act
        Moto saved = motoRepository.save(moto);

        // Assert
        assertNotNull(saved.getId());
        assertEquals("ABC1234", saved.getPlaca());
        assertEquals("CB 600F", saved.getModelo());
        assertEquals("Honda", saved.getFabricante());
        assertNotNull(saved.getPatio());
        assertEquals(patio.getId(), saved.getPatio().getId());
    }

    @Test
    void testRead() {
        // Arrange
        Moto saved = entityManager.persistAndFlush(moto);

        // Act
        Optional<Moto> found = motoRepository.findById(saved.getId());

        // Assert
        assertTrue(found.isPresent());
        assertEquals(saved.getId(), found.get().getId());
        assertEquals("ABC1234", found.get().getPlaca());
        assertEquals("CB 600F", found.get().getModelo());
        assertEquals("Honda", found.get().getFabricante());
    }

    @Test
    void testReadAll() {
        // Arrange
        entityManager.persistAndFlush(moto);

        Moto moto2 = new Moto();
        moto2.setPlaca("XYZ9876");
        moto2.setModelo("MT-07");
        moto2.setFabricante("Yamaha");
        moto2.setPatio(patio);
        entityManager.persistAndFlush(moto2);

        // Act
        List<Moto> all = motoRepository.findAll();

        // Assert
        assertTrue(all.size() >= 2);
    }

    @Test
    void testUpdate() {
        // Arrange
        Moto saved = entityManager.persistAndFlush(moto);
        Long id = saved.getId();

        Patio novoPatio = new Patio();
        novoPatio.setNome("Pátio Norte");
        novoPatio.setEndereco("Rua Nova, 456");
        novoPatio.setLocalizacao("Rio de Janeiro");
        novoPatio = entityManager.persistAndFlush(novoPatio);

        // Act
        saved.setPlaca("DEF5678");
        saved.setModelo("CB 650F");
        saved.setFabricante("Honda");
        saved.setPatio(novoPatio);
        Moto updated = motoRepository.save(saved);

        // Assert
        assertEquals(id, updated.getId());
        assertEquals("DEF5678", updated.getPlaca());
        assertEquals("CB 650F", updated.getModelo());
        assertEquals(novoPatio.getId(), updated.getPatio().getId());
    }

    @Test
    void testUpdatePatio() {
        // Arrange
        Moto saved = entityManager.persistAndFlush(moto);

        Patio novoPatio = new Patio();
        novoPatio.setNome("Pátio Sul");
        novoPatio.setEndereco("Rua Outra, 789");
        novoPatio.setLocalizacao("Brasília");
        novoPatio = entityManager.persistAndFlush(novoPatio);

        // Act
        saved.setPatio(novoPatio);
        Moto updated = motoRepository.save(saved);

        // Assert
        assertNotNull(updated.getPatio());
        assertEquals(novoPatio.getId(), updated.getPatio().getId());
    }

    @Test
    void testDelete() {
        // Arrange
        Moto saved = entityManager.persistAndFlush(moto);
        Long id = saved.getId();

        // Act
        motoRepository.deleteById(id);
        entityManager.flush();

        // Assert
        Optional<Moto> deleted = motoRepository.findById(id);
        assertFalse(deleted.isPresent());
    }

    @Test
    void testFindByIdNotFound() {
        // Act
        Optional<Moto> found = motoRepository.findById(999L);

        // Assert
        assertFalse(found.isPresent());
    }
}
