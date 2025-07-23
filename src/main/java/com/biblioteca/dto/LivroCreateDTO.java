package com.biblioteca.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record LivroCreateDTO(
        @NotBlank String titulo,

        @Pattern(regexp = "\\d{10}|\\d{13}", message = "ISBN deve ter 10 ou 13 dígitos")
        String isbn,

        @Min(1000) @Max(2100)
        Integer anoPublicacao,

        @Positive
        BigDecimal preco,

        @NotNull
        Long autorId,

        @NotNull
        Long categoriaId
) {
}
