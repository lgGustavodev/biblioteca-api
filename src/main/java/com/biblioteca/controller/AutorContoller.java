package com.biblioteca.controller;


import com.biblioteca.dto.AutorCreateDTO;
import com.biblioteca.dto.AutorDTO;
import com.biblioteca.service.AutorService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/autores")
public class AutorContoller {

    private final AutorService autorService;

    public AutorContoller(AutorService autorService) {
        this.autorService = autorService;
    }

    @GetMapping("/{id}")
    public AutorDTO buscarPorID(@PathVariable Long id){
        return autorService.buscarPorId(id);
    }

    @PostMapping
    public AutorDTO criar(@RequestBody @Valid AutorCreateDTO dto){
        return autorService.criar(dto);
    }

    @PutMapping("/{id}")
    public AutorDTO atualizar(@PathVariable Long id, @RequestBody @Valid AutorCreateDTO dto){
        return autorService.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        autorService.deletar(id);
    }
}
