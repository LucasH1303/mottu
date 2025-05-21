package com.mottu.mottuapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mottu.mottuapi.dto.MotoDTO;
import com.mottu.mottuapi.entity.Moto;
import com.mottu.mottuapi.service.MotoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/motos")
public class MotoController {

    private final MotoService motoService;

    public MotoController(MotoService motoService) {
        this.motoService = motoService;
    }

    @GetMapping
    public List<Moto> listar() {
        return motoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Moto buscar(@PathVariable Long id) {
        return motoService.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<Moto> criar(@RequestBody @Valid MotoDTO motoDTO) {
        Moto novaMoto = new Moto();
        novaMoto.setPlaca(motoDTO.getPlaca());
        novaMoto.setModelo(motoDTO.getModelo());
        novaMoto.setFabricante(motoDTO.getFabricante());
        return ResponseEntity.ok(motoService.salvar(novaMoto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Moto> atualizar(@PathVariable Long id, @RequestBody @Valid MotoDTO motoDTO) {
        Moto motoAtualizada = new Moto();
        motoAtualizada.setId(id);
        motoAtualizada.setPlaca(motoDTO.getPlaca());
        motoAtualizada.setModelo(motoDTO.getModelo());
        motoAtualizada.setFabricante(motoDTO.getFabricante());
        return ResponseEntity.ok(motoService.salvar(motoAtualizada));
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        motoService.deletar(id);
    }
}
