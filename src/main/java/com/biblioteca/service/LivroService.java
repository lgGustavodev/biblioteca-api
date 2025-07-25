package com.biblioteca.service;

import com.biblioteca.dto.LivroCreateDTO;
import com.biblioteca.dto.LivroDTO;
import com.biblioteca.dto.LivroImportRequestDTO;
import com.biblioteca.model.Autor;
import com.biblioteca.model.Categoria;
import com.biblioteca.model.Livro;
import com.biblioteca.repository.AutorRepository;
import com.biblioteca.repository.CategoriaRepository;
import com.biblioteca.repository.LivroRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Service
public class LivroService {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final CategoriaRepository categoriaRepository;
    private final LivroScrapingService scrapingService;



    public LivroService(LivroRepository livroRepository, AutorRepository autorRepository, CategoriaRepository categoriaRepository, LivroScrapingService scrapingService) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
        this.categoriaRepository = categoriaRepository;
        this.scrapingService = scrapingService;
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

    public void deletar(Long id) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Livro não encontrado"));

        livroRepository.delete(livro);
    }

    public LivroDTO importarLivro(LivroImportRequestDTO dto) throws IOException {
        LivroDTO dados = scrapingService.extrairTituloAmazon(dto.url());

        System.out.println(" Título: " + dados.titulo());
        System.out.println(" ISBN: " + dados.isbn());
        System.out.println(" Preço: " + dados.preco());
        System.out.println(" Ano: " + dados.anoPublicacao());

        if (livroRepository.existsByIsbn(dados.isbn())) {
            throw new IllegalArgumentException("Livro já existe");
        }

        Autor autor = autorRepository.findById(dto.autorId())
                .orElseThrow(() -> new EntityNotFoundException("Autor não encontrado"));

        Categoria categoria = categoriaRepository.findById(dto.categoriaId())
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

        if (dados.titulo() == null || dados.titulo().isBlank()) {
            throw new IllegalArgumentException("Título extraído é inválido");
        }
        if (dados.isbn() == null || !dados.isbn().trim().matches("\\d{10}|\\d{13}")) {
            throw new IllegalArgumentException("ISBN extraído é inválido: " + dados.isbn());
        }
        if (dados.preco() == null || dados.preco().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Preço inválido: " + dados.preco());
        }
        if (dados.anoPublicacao() == null || dados.anoPublicacao() < 1000 || dados.anoPublicacao() > 2100) {
            throw new IllegalArgumentException("Ano de publicação inválido: " + dados.anoPublicacao());
        }

        Livro livro = new Livro();
        livro.setTitulo(dados.titulo());
        livro.setIsbn(dados.isbn());
        livro.setAnoPublicacao(dados.anoPublicacao());
        livro.setPreco(dados.preco());
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
