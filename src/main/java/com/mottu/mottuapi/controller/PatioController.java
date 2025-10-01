//package com.mottu.mottuapi.controller;
//
//import com.mottu.mottuapi.dto.PatioDTO;
//import com.mottu.mottuapi.service.PatioService;
//import jakarta.validation.Valid;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import java.util.List;
//
//@RestController
//@RequestMapping("/patio") 
//public class PatioController {
//
//    private final PatioService patioService;
//
//    public PatioController(PatioService patioService) {
//        this.patioService = patioService;
//    }
//
//    // Listar todos (comentado para evitar conflito)
//    // @GetMapping("/listar")
//    // public List<PatioDTO> listarTodos() {
//    //     return patioService.listarTodos();
//    // }
//
//    // Criar novo (comentado para evitar conflito)
//    // @PostMapping("/novo")
//    // public ResponseEntity<PatioDTO> criar(@Valid @RequestBody PatioDTO patioDTO) {
//    //     PatioDTO criado = patioService.criar(patioDTO);
//    //     return ResponseEntity.ok(criado);
//    // }
//
//    // Atualizar existente (comentado para evitar conflito)
//    // @PutMapping("/{id}")
//    // public ResponseEntity<PatioDTO> atualizar(@PathVariable Long id, @Valid @RequestBody PatioDTO patioDTO) {
//    //     PatioDTO atualizado = patioService.atualizar(id, patioDTO);
//    //     return ResponseEntity.ok(atualizado);
//    // }
//}
