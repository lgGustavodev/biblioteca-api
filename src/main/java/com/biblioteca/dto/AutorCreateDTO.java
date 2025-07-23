package com.biblioteca.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record AutorCreateDTO(@NotBlank String nome, @NotBlank @Email String email, @Past LocalDate dataNascimento
) {
}
