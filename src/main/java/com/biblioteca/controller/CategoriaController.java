package com.biblioteca.controller;


import com.biblioteca.dto.CategoriaCreateDTO;
import com.biblioteca.dto.CategoriaDTO;
import com.biblioteca.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public List<CategoriaDTO> listarTodas() {
        return categoriaService.listarTodos();
    }

    @GetMapping("/{id}")
    public CategoriaDTO buscarPorId(@PathVariable Long id){
        return categoriaService.buscarPorId(id);
    }

    @PostMapping
    public CategoriaDTO criar(@RequestBody @Valid  CategoriaCreateDTO dto) {
        return categoriaService.criar(dto);
    }

    @PutMapping("/{id}")
    public CategoriaDTO atualizar(@PathVariable Long id, @RequestBody CategoriaCreateDTO dto){
        return categoriaService.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        categoriaService.deletar(id);
    }
}
