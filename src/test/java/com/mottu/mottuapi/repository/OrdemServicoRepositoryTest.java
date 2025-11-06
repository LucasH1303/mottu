package com.mottu.mottuapi.repository;

import com.mottu.mottuapi.entity.Moto;
import com.mottu.mottuapi.entity.OrdemServico;
import com.mottu.mottuapi.entity.Patio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("Testes de CRUD - OrdemServicoRepository")
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
        // Criar pátio
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
        moto = entityManager.persistAndFlush(moto);

        // Criar ordem de serviço
        ordemServico = new OrdemServico();
        ordemServico.setTipoProblema("Manutenção Preventiva");
        ordemServico.setDescricaoLivre("Troca de óleo e filtros");
        ordemServico.setStatus("ABERTA");
        ordemServico.setDataAbertura(LocalDateTime.now());
        ordemServico.setMoto(moto);
    }

    @Test
    @DisplayName("Deve criar uma nova ordem de serviço")
    void testCreateOrdemServico() {
        // When
        OrdemServico savedOrdem = ordemServicoRepository.save(ordemServico);

        // Then
        assertThat(savedOrdem).isNotNull();
        assertThat(savedOrdem.getId()).isNotNull();
        assertThat(savedOrdem.getTipoProblema()).isEqualTo("Manutenção Preventiva");
        assertThat(savedOrdem.getDescricaoLivre()).isEqualTo("Troca de óleo e filtros");
        assertThat(savedOrdem.getStatus()).isEqualTo("ABERTA");
        assertThat(savedOrdem.getDataAbertura()).isNotNull();
        assertThat(savedOrdem.getMoto()).isNotNull();
        assertThat(savedOrdem.getMoto().getId()).isEqualTo(moto.getId());
    }

    @Test
    @DisplayName("Deve buscar uma ordem de serviço por ID")
    void testReadOrdemServicoById() {
        // Given
        OrdemServico savedOrdem = entityManager.persistAndFlush(ordemServico);

        // When
        Optional<OrdemServico> foundOrdem = ordemServicoRepository.findById(savedOrdem.getId());

        // Then
        assertThat(foundOrdem).isPresent();
        assertThat(foundOrdem.get().getTipoProblema()).isEqualTo("Manutenção Preventiva");
        assertThat(foundOrdem.get().getDescricaoLivre()).isEqualTo("Troca de óleo e filtros");
        assertThat(foundOrdem.get().getStatus()).isEqualTo("ABERTA");
        assertThat(foundOrdem.get().getMoto().getId()).isEqualTo(moto.getId());
    }

    @Test
    @DisplayName("Deve listar todas as ordens de serviço")
    void testReadAllOrdensServico() {
        // Given
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

        // When
        List<OrdemServico> ordens = ordemServicoRepository.findAll();

        // Then
        assertThat(ordens).hasSize(3);
        assertThat(ordens).extracting(OrdemServico::getTipoProblema)
                .containsExactlyInAnyOrder("Manutenção Preventiva", "Reparo", "Revisão");
    }

    @Test
    @DisplayName("Deve atualizar uma ordem de serviço existente")
    void testUpdateOrdemServico() {
        // Given
        OrdemServico savedOrdem = entityManager.persistAndFlush(ordemServico);
        Long id = savedOrdem.getId();

        // When
        savedOrdem.setStatus("EM_ANDAMENTO");
        savedOrdem.setDescricaoLivre("Descrição atualizada - em andamento");
        OrdemServico updatedOrdem = ordemServicoRepository.save(savedOrdem);

        // Then
        assertThat(updatedOrdem.getId()).isEqualTo(id);
        assertThat(updatedOrdem.getStatus()).isEqualTo("EM_ANDAMENTO");
        assertThat(updatedOrdem.getDescricaoLivre()).isEqualTo("Descrição atualizada - em andamento");
    }

    @Test
    @DisplayName("Deve atualizar status e data de conclusão de uma ordem")
    void testUpdateOrdemServicoComConclusao() {
        // Given
        OrdemServico savedOrdem = entityManager.persistAndFlush(ordemServico);
        LocalDateTime dataConclusao = LocalDateTime.now();

        // When
        savedOrdem.setStatus("CONCLUIDA");
        savedOrdem.setDataConclusao(dataConclusao);
        OrdemServico updatedOrdem = ordemServicoRepository.save(savedOrdem);

        // Then
        assertThat(updatedOrdem.getStatus()).isEqualTo("CONCLUIDA");
        assertThat(updatedOrdem.getDataConclusao()).isNotNull();
    }

    @Test
    @DisplayName("Deve deletar uma ordem de serviço por ID")
    void testDeleteOrdemServicoById() {
        // Given
        OrdemServico savedOrdem = entityManager.persistAndFlush(ordemServico);
        Long id = savedOrdem.getId();

        // When
        ordemServicoRepository.deleteById(id);
        entityManager.flush();

        // Then
        Optional<OrdemServico> deletedOrdem = ordemServicoRepository.findById(id);
        assertThat(deletedOrdem).isEmpty();
    }

    @Test
    @DisplayName("Deve deletar uma ordem de serviço por entidade")
    void testDeleteOrdemServicoByEntity() {
        // Given
        OrdemServico savedOrdem = entityManager.persistAndFlush(ordemServico);
        Long id = savedOrdem.getId();

        // When
        ordemServicoRepository.delete(savedOrdem);
        entityManager.flush();

        // Then
        Optional<OrdemServico> deletedOrdem = ordemServicoRepository.findById(id);
        assertThat(deletedOrdem).isEmpty();
    }

    @Test
    @DisplayName("Deve verificar se uma ordem de serviço existe por ID")
    void testExistsOrdemServicoById() {
        // Given
        OrdemServico savedOrdem = entityManager.persistAndFlush(ordemServico);
        Long id = savedOrdem.getId();

        // When
        boolean exists = ordemServicoRepository.existsById(id);

        // Then
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Deve retornar false quando verificar ID inexistente")
    void testNotExistsOrdemServicoById() {
        // When
        boolean exists = ordemServicoRepository.existsById(999L);

        // Then
        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("Deve contar o total de ordens de serviço")
    void testCountOrdensServico() {
        // Given
        entityManager.persistAndFlush(ordemServico);

        OrdemServico ordem2 = new OrdemServico();
        ordem2.setTipoProblema("Reparo");
        ordem2.setDescricaoLivre("Troca de pneus");
        ordem2.setStatus("ABERTA");
        ordem2.setDataAbertura(LocalDateTime.now());
        ordem2.setMoto(moto);
        entityManager.persistAndFlush(ordem2);

        // When
        long count = ordemServicoRepository.count();

        // Then
        assertThat(count).isEqualTo(2);
    }

    @Test
    @DisplayName("Deve criar ordem de serviço relacionada a uma moto")
    void testOrdemServicoRelacionadaComMoto() {
        // Given
        OrdemServico savedOrdem = entityManager.persistAndFlush(ordemServico);

        // When
        Optional<OrdemServico> foundOrdem = ordemServicoRepository.findById(savedOrdem.getId());

        // Then
        assertThat(foundOrdem).isPresent();
        assertThat(foundOrdem.get().getMoto()).isNotNull();
        assertThat(foundOrdem.get().getMoto().getPlaca()).isEqualTo("ABC1234");
        assertThat(foundOrdem.get().getMoto().getModelo()).isEqualTo("CB 600F");
    }

    @Test
    @DisplayName("Deve criar ordem de serviço sem descrição livre")
    void testCreateOrdemServicoSemDescricaoLivre() {
        // Given
        ordemServico.setDescricaoLivre(null);

        // When
        OrdemServico savedOrdem = ordemServicoRepository.save(ordemServico);

        // Then
        assertThat(savedOrdem).isNotNull();
        assertThat(savedOrdem.getId()).isNotNull();
        assertThat(savedOrdem.getDescricaoLivre()).isNull();
        assertThat(savedOrdem.getTipoProblema()).isEqualTo("Manutenção Preventiva");
    }
}

