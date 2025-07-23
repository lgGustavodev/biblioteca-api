package com.biblioteca.service;

import com.biblioteca.dto.LivroCreateDTO;
import com.biblioteca.dto.LivroDTO;
import com.biblioteca.model.Autor;
import com.biblioteca.model.Categoria;
import com.biblioteca.model.Livro;
import com.biblioteca.repository.AutorRepository;
import com.biblioteca.repository.CategoriaRepository;
import com.biblioteca.repository.LivroRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LivroService {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final CategoriaRepository categoriaRepository;


    public LivroService(LivroRepository livroRepository, AutorRepository autorRepository, CategoriaRepository categoriaRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public LivroDTO criar(LivroCreateDTO dto) throws IllegalAccessException {
        if (livroRepository.existsByIsbn(dto.isbn())){
            throw new IllegalAccessException("Livro com esse ISBN já existe");
        }
        Autor autor = autorRepository.findById(dto.autorId())
                .orElseThrow(() ->new EntityNotFoundException("Autor não encontrado"));

        Categoria categoria = categoriaRepository.findById(dto.categoriaId())
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

        Livro livro = new Livro();
        livro.setTitulo(dto.titulo());
        livro.setIsbn(dto.isbn());
        livro.setAnoPublicacao(dto.anoPublicacao());
        livro.setPreco(dto.preco());
        livro.setAutor(autor);
        livro.setCategoria(categoria);

        livroRepository.save(livro);

        return new LivroDTO(
                livro.getId(),
                livro.getTitulo(),
                livro.getIsbn(),
                livro.getAnoPublicacao(),
                livro.getPreco(),
                autor.getNome(),
                categoria.getNome());
    }

}
