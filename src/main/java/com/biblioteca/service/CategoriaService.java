package com.biblioteca.service;

import com.biblioteca.dto.CategoriaCreateDTO;
import com.biblioteca.dto.CategoriaDTO;
import com.biblioteca.model.Categoria;
import com.biblioteca.repository.CategoriaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<CategoriaDTO> listarTodos(){
        return categoriaRepository.findAll().stream().map(this::toDTO).toList();
    }

    public CategoriaDTO buscarPorId(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));
        return toDTO(categoria);
    }

    public CategoriaDTO criar(CategoriaCreateDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setNome(dto.nome());
        categoria.setDescricao(dto.descricao());

        categoriaRepository.save(categoria);
        return toDTO(categoria);
    }

    public CategoriaDTO atualizar(Long id, CategoriaCreateDTO dto) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

        categoria.setNome(dto.nome());
        categoria.setDescricao(dto.descricao());

        categoriaRepository.save(categoria);
        return toDTO(categoria);
    }

    public void deletar(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new EntityNotFoundException("Categoria não encontrada");
        }
        categoriaRepository.deleteById(id);
    }

    private CategoriaDTO toDTO(Categoria categoria) {
        return new CategoriaDTO(categoria.getId(), categoria.getNome(), categoria.getDescricao());
    }


}
