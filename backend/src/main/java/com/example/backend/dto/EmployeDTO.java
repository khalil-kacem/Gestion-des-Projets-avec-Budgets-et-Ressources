package com.example.backend.dto;

import com.example.backend.enums.RoleEmploye;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EmployeDTO {

    private Long id;

    private String nom;

    private String email;

    private RoleEmploye role;

    private String equipe;
}