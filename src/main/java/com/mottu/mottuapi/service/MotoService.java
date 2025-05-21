package com.mottu.mottuapi.service;

import com.mottu.mottuapi.entity.Moto;
import com.mottu.mottuapi.repository.MotoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.mottu.mottuapi.exception.EntidadeNaoEncontradaException;


import java.util.List;

@Service
public class MotoService {

    private final MotoRepository motoRepository;

    public MotoService(MotoRepository motoRepository) {
        this.motoRepository = motoRepository;
    }

    public List<Moto> listarTodos() {
        return motoRepository.findAll();
    }

    public Moto buscarPorId(Long id) {
        return motoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Moto com ID " + id + " não encontrada"));
    }

    @Transactional
    public Moto salvar(Moto moto) {
        return motoRepository.save(moto);
    }

    public void deletar(Long id) {
        motoRepository.deleteById(id);
    }
}

