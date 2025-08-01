package com.biblioteca.controller;


import com.biblioteca.dto.LivroCreateDTO;
import com.biblioteca.dto.LivroDTO;
import com.biblioteca.dto.LivroImportRequestDTO;
import com.biblioteca.service.LivroService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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

    @GetMapping("/search")
    public List<LivroDTO> buscarPorTitulo(@RequestParam String titulo) {
        return livroService.buscarPorTitulo(titulo);
    }
    @PutMapping("/{id}")
    public LivroDTO atualizar(@PathVariable Long id, @RequestBody @Valid LivroCreateDTO dto) {
        return livroService.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id){
        livroService.deletar(id);
    }

    @PostMapping("/importar")
    public LivroDTO importarLivro(@RequestBody LivroImportRequestDTO dto) throws IOException {
        return livroService.importarLivro(dto);
    }




}
