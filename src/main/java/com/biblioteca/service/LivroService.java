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
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;


import java.util.List;

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

    public LivroDTO criar(LivroCreateDTO dto) {
        if (livroRepository.existsByIsbn(dto.isbn())){
            throw new IllegalArgumentException("Livro com esse ISBN já existe");
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

    public List<LivroDTO> listarTodos(Long categoriaId, Integer ano, Long autorId) {
        List<Livro> livros = livroRepository.findAll().stream()
                .filter(livro -> categoriaId == null || livro.getId().equals(categoriaId))
                .filter(livro -> ano == null || livro.getAnoPublicacao().equals(ano))
                .filter(livro -> autorId == null || livro.getAutor().getId().equals(autorId))
                .toList();

        return livros.stream().map(livro ->
                new LivroDTO(
                        livro.getId(),
                        livro.getTitulo(),
                        livro.getIsbn(),
                        livro.getAnoPublicacao(),
                        livro.getPreco(),
                        livro.getAutor().getNome(),
                        livro.getCategoria().getNome()
                )).toList();
    }

    public LivroDTO buscarPorId(Long id) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado"));


        return new LivroDTO(
                livro.getId(),
                livro.getTitulo(),
                livro.getIsbn(),
                livro.getAnoPublicacao(),
                livro.getPreco(),
                livro.getAutor().getNome(),
                livro.getCategoria().getNome()
        );
    }

    public List<LivroDTO> buscarPorTitulo(String titulo) {
        List<Livro> livros = livroRepository.findByTituloContainingIgnoreCase(titulo);

        return livros.stream()
                .map(livro -> new LivroDTO(
                        livro.getId(),
                        livro.getTitulo(),
                        livro.getIsbn(),
                        livro.getAnoPublicacao(),
                        livro.getPreco(),
                        livro.getAutor().getNome(),
                        livro.getCategoria().getNome()))
                .toList();
    }


    public LivroDTO atualizar(Long id, @Valid LivroCreateDTO dto) {
        Livro livro = livroRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Livro não encontrado"));
        if (!livro.getIsbn().equals(dto.isbn()) &&  livroRepository.existsByIsbn(dto.isbn())){
            throw new IllegalArgumentException("Já existe outro livro com esse ISBN");
        }

        Autor autor = autorRepository.findById(dto.autorId())
                .orElseThrow(() -> new EntityNotFoundException("Autor não encontrado"));

        Categoria categoria = categoriaRepository.findById(dto.categoriaId())
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));


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
                categoria.getNome()
        );
    }
}
