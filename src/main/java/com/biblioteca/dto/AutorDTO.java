package com.biblioteca.dto;

import java.time.LocalDate;

public record AutorDTO(Long id, String nome, String email, LocalDate dataNascimento) {
}
