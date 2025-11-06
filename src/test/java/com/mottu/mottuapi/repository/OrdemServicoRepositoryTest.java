package com.mottu.mottuapi.repository;

import com.mottu.mottuapi.entity.Moto;
import com.mottu.mottuapi.entity.OrdemServico;
import com.mottu.mottuapi.entity.Patio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application-test.properties")
class OrdemServicoRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrdemServicoRepository ordemServicoRepository;

    private OrdemServico ordemServico;
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
        moto = entityManager.persistAndFlush(moto);

        ordemServico = new OrdemServico();
        ordemServico.setTipoProblema("Manutenção Preventiva");
        ordemServico.setDescricaoLivre("Troca de óleo e filtros");
        ordemServico.setStatus("ABERTA");
        ordemServico.setDataAbertura(LocalDateTime.now());
        ordemServico.setMoto(moto);
    }

    @Test
    void testCreate() {
        // Act
        OrdemServico saved = ordemServicoRepository.save(ordemServico);

        // Assert
        assertNotNull(saved.getId());
        assertEquals("Manutenção Preventiva", saved.getTipoProblema());
        assertEquals("Troca de óleo e filtros", saved.getDescricaoLivre());
        assertEquals("ABERTA", saved.getStatus());
        assertNotNull(saved.getDataAbertura());
        assertNotNull(saved.getMoto());
        assertEquals(moto.getId(), saved.getMoto().getId());
    }

    @Test
    void testRead() {
        // Arrange
        OrdemServico saved = entityManager.persistAndFlush(ordemServico);

        // Act
        Optional<OrdemServico> found = ordemServicoRepository.findById(saved.getId());

        // Assert
        assertTrue(found.isPresent());
        assertEquals(saved.getId(), found.get().getId());
        assertEquals("Manutenção Preventiva", found.get().getTipoProblema());
        assertEquals("Troca de óleo e filtros", found.get().getDescricaoLivre());
        assertEquals("ABERTA", found.get().getStatus());
        assertEquals(moto.getId(), found.get().getMoto().getId());
    }

    @Test
    void testReadAll() {
        // Arrange
        entityManager.persistAndFlush(ordemServico);

        OrdemServico ordem2 = new OrdemServico();
        ordem2.setTipoProblema("Reparo");
        ordem2.setDescricaoLivre("Troca de pneus");
        ordem2.setStatus("EM_ANDAMENTO");
        ordem2.setDataAbertura(LocalDateTime.now());
        ordem2.setMoto(moto);
        entityManager.persistAndFlush(ordem2);

        OrdemServico ordem3 = new OrdemServico();
        ordem3.setTipoProblema("Revisão");
        ordem3.setDescricaoLivre("Revisão completa");
        ordem3.setStatus("CONCLUIDA");
        ordem3.setDataAbertura(LocalDateTime.now().minusDays(5));
        ordem3.setDataConclusao(LocalDateTime.now());
        ordem3.setMoto(moto);
        entityManager.persistAndFlush(ordem3);

        // Act
        List<OrdemServico> all = ordemServicoRepository.findAll();

        // Assert
        assertTrue(all.size() >= 3);
    }

    @Test
    void testUpdate() {
        // Arrange
        OrdemServico saved = entityManager.persistAndFlush(ordemServico);
        Long id = saved.getId();

        // Act
        saved.setStatus("EM_ANDAMENTO");
        saved.setDescricaoLivre("Descrição atualizada - em andamento");
        OrdemServico updated = ordemServicoRepository.save(saved);

        // Assert
        assertEquals(id, updated.getId());
        assertEquals("EM_ANDAMENTO", updated.getStatus());
        assertEquals("Descrição atualizada - em andamento", updated.getDescricaoLivre());
    }

    @Test
    void testUpdateStatus() {
        // Arrange
        OrdemServico saved = entityManager.persistAndFlush(ordemServico);
        LocalDateTime dataConclusao = LocalDateTime.now();

        // Act
        saved.setStatus("CONCLUIDA");
        saved.setDataConclusao(dataConclusao);
        OrdemServico updated = ordemServicoRepository.save(saved);

        // Assert
        assertEquals("CONCLUIDA", updated.getStatus());
        assertNotNull(updated.getDataConclusao());
    }

    @Test
    void testDelete() {
        // Arrange
        OrdemServico saved = entityManager.persistAndFlush(ordemServico);
        Long id = saved.getId();

        // Act
        ordemServicoRepository.deleteById(id);
        entityManager.flush();

        // Assert
        Optional<OrdemServico> deleted = ordemServicoRepository.findById(id);
        assertFalse(deleted.isPresent());
    }

    @Test
    void testFindByIdNotFound() {
        // Act
        Optional<OrdemServico> found = ordemServicoRepository.findById(999L);

        // Assert
        assertFalse(found.isPresent());
    }
}
