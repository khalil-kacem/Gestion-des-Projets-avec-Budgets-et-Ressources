package com.example.backend.entity;
import com.example.backend.enums.StatutProjet; 
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Projet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    private LocalDate dateDebut;

    private LocalDate dateFin;

    private Double budget;
    private StatutProjet statut;  

}