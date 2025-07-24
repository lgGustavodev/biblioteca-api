package com.biblioteca.service;

import com.biblioteca.dto.AutorCreateDTO;
import com.biblioteca.dto.AutorDTO;
import com.biblioteca.dto.LivroDTO;
import com.biblioteca.model.Autor;
import com.biblioteca.repository.AutorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AutorService {

    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public Page<AutorDTO> listarTodos(Pageable pageable){
        return autorRepository.findAll(pageable).map(this::toDTO);
    }

    public AutorDTO buscarPorId(Long id) {
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Autor não encontrado"));
        return toDTO(autor);
    }

    public AutorDTO criar(AutorCreateDTO dto) {
        Autor autor = new Autor();
        autor.setNome(dto.nome());
        autor.setEmail(dto.email());
        autor.setDataNascimento(dto.dataNascimento());

        autorRepository.save(autor);
        return toDTO(autor);
    }

    public AutorDTO atualizar(Long id, AutorCreateDTO dto) {
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Autor não encontrado"));

        autor.setNome(dto.nome());
        autor.setEmail(dto.email());
        autor.setDataNascimento(dto.dataNascimento());

        autorRepository.save(autor);
        return toDTO(autor);
    }

    public void deletar(Long id) {
        if (!autorRepository.existsById(id)) {
            throw new EntityNotFoundException("Autor não encontrado");
        }
        autorRepository.deleteById(id);
    }
    private AutorDTO toDTO(Autor autor) {
        return new AutorDTO(autor.getId(), autor.getNome(), autor.getEmail(), autor.getDataNascimento());
    }

    public List<LivroDTO> listarLivrosPorAutor(Long autorId) {
        Autor autor = autorRepository.findById(autorId)
                .orElseThrow(() -> new EntityNotFoundException("Autor não encontrado"));

        return autor.getLivros().stream()
                .map(livro -> new LivroDTO(
                        livro.getId(),
                        livro.getTitulo(),
                        livro.getIsbn(),
                        livro.getAnoPublicacao(),
                        livro.getPreco(),
                        autor.getNome(),
                        livro.getCategoria().getNome()
                ))
                .toList();
    }
}
