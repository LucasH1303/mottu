package com.mottu.mottuapi.controller;

import com.mottu.mottuapi.dto.PatioDTO;
import com.mottu.mottuapi.entity.Patio;
import com.mottu.mottuapi.service.PatioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/patios")
public class PatioRestController {

    private final PatioService patioService;

    public PatioRestController(PatioService patioService) {
        this.patioService = patioService;
    }

    @GetMapping("/listar")
    public List<Patio> listarTodos() {
        return patioService.listarTodos();
    }

    @GetMapping("/{id}")
    public Patio buscar(@PathVariable Long id) {
        return patioService.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<Patio> criar(@RequestBody @Valid PatioDTO patioDTO) {
        Patio novo = new Patio();
        novo.setNome(patioDTO.getNome());
        novo.setEndereco(patioDTO.getEndereco());
        Patio criado = patioService.criar(novo);
        return ResponseEntity.created(URI.create("/api/patios/" + criado.getId())).body(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patio> atualizar(@PathVariable Long id, @RequestBody @Valid PatioDTO patioDTO) {
        Patio atualizado = new Patio();
        atualizado.setNome(patioDTO.getNome());
        atualizado.setEndereco(patioDTO.getEndereco());
        Patio salvo = patioService.atualizar(id, atualizado);
        return ResponseEntity.ok(salvo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        patioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}


