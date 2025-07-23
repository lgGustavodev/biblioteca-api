package com.biblioteca.dto;

import java.math.BigDecimal;

public record LivroDTO(Long id,
                       String titulo,
                       String isbn,
                       Integer anoPublicacao,
                       BigDecimal preco,
                       String autorNome,
                       String categoriaNome) {
}
