package com.example.backend.dto;

import com.example.backend.enums.RoleEmploye;
import lombok.Data;

@Data
public class EmployeDTO {

    private Long id;
    private String nom;
    private String email;
    private RoleEmploye role;
    private String equipe;
    private Integer nombreTaches;
}
