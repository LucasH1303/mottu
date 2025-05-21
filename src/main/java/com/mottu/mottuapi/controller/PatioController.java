package com.mottu.mottuapi.controller;

import com.mottu.mottuapi.dto.PatioDTO;
import com.mottu.mottuapi.entity.Patio;
import com.mottu.mottuapi.service.PatioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patios")
public class PatioController {

    private final PatioService patioService;

    public PatioController(PatioService patioService) {
        this.patioService = patioService;
    }

    @GetMapping
    public List<Patio> listar() {
        return patioService.listarTodos();
    }

    @GetMapping("/{id}")
    public Patio buscar(@PathVariable Long id) {
        return patioService.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<Patio> criar(@RequestBody @Valid PatioDTO patioDTO) {
        Patio novoPatio = new Patio();
        novoPatio.setNome(patioDTO.getNome());
        novoPatio.setEndereco(patioDTO.getEndereco());
        return ResponseEntity.ok(patioService.salvar(novoPatio));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Patio> atualizar(@PathVariable Long id, @RequestBody @Valid PatioDTO patioDTO) {
        Patio patioAtualizado = new Patio();
        patioAtualizado.setId(id);
        patioAtualizado.setNome(patioDTO.getNome());
        patioAtualizado.setEndereco(patioDTO.getEndereco());
        return ResponseEntity.ok(patioService.salvar(patioAtualizado));
    }


    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        patioService.deletar(id);
    }
}
