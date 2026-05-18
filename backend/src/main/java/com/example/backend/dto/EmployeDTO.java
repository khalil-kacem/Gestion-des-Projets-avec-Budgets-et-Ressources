package com.example.backend.dto;

import com.example.backend.enums.RoleEmploye;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EmployeDTO {

    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit être valide (ex: nom@exemple.com)")
    private String email;

    @NotNull(message = "Le rôle est obligatoire")
    private RoleEmploye role;

    private String equipe;
}