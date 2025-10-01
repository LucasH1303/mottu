package com.mottu.mottuapi.service;

import com.mottu.mottuapi.entity.Patio;
import com.mottu.mottuapi.repository.PatioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatioService {

    private final PatioRepository patioRepository;

    public PatioService(PatioRepository patioRepository) {
        this.patioRepository = patioRepository;
    }

    // Listar todos os pátios
    public List<Patio> listarTodos() {
        return patioRepository.findAll();
    }

    // Buscar por ID
    public Patio buscarPorId(Long id) {
        return patioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patio não encontrado com id: " + id));
    }

    // Criar novo pátio
    public Patio criar(Patio patio) {
        return patioRepository.save(patio);
    }

    // Atualizar pátio existente
    public Patio atualizar(Long id, Patio patio) {
        Patio existente = buscarPorId(id);
        existente.setNome(patio.getNome());
        existente.setEndereco(patio.getEndereco());
        return patioRepository.save(existente);
    }

    // Deletar pátio
    public void deletar(Long id) {
        Patio existente = buscarPorId(id);
        patioRepository.delete(existente);
    }
}
