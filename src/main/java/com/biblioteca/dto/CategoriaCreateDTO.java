package com.biblioteca.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoriaCreateDTO(@NotBlank String nome, String descricao) {
}
