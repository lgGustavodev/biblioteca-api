package com.biblioteca.repository;

import com.biblioteca.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    List<Livro> findByTituloContainingIgnoreCase(String titulo);
    List<Livro> findByCategoriaId(Long categoria);
    List<Livro> findByAutorId(Long autorId);
    List<Livro> findByAnoPublicacao(Integer ano);
    boolean existsByIsbn(String isbn);
}
