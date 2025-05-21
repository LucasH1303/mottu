package com.mottu.mottuapi.service;

import com.mottu.mottuapi.entity.Patio;
import com.mottu.mottuapi.exception.EntidadeNaoEncontradaException;
import com.mottu.mottuapi.repository.PatioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatioService {

    private final PatioRepository patioRepository;

    public PatioService(PatioRepository patioRepository) {
        this.patioRepository = patioRepository;
    }

    public List<Patio> listarTodos() {
        return patioRepository.findAll();
    }

    public Patio buscarPorId(Long id) {
        return patioRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Pátio com ID " + id + " não encontrado"));
    }

    public Patio salvar(Patio patio) {
        return patioRepository.save(patio);
    }

    public void deletar(Long id) {
        patioRepository.deleteById(id);
    }
}