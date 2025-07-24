package com.biblioteca.controller;


import com.biblioteca.dto.LivroCreateDTO;
import com.biblioteca.dto.LivroDTO;
import com.biblioteca.service.LivroService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/livros")
public class LivroController {

    private final LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }


    @PostMapping
    public LivroDTO criar(@RequestBody @Valid LivroCreateDTO dto) {
        return livroService.criar(dto);
    }

    @GetMapping
    public List<LivroDTO> listarTodos(
            @RequestParam(required = false) Long categoriaId,
            @RequestParam(required = false) Integer ano,
            @RequestParam(required = false) Long autorId
    ){
        return livroService.listarTodos(categoriaId, ano, autorId);

    }

    @GetMapping("/{id}")
    public LivroDTO buscarPorId(@PathVariable Long id) {
        return livroService.buscarPorId(id);
    }
}
