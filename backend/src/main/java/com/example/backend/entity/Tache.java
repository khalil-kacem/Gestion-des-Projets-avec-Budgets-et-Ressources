package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tache {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;


    private LocalDate deadline;

    @ManyToOne
    @JoinColumn(name = "projet_id")
    private Projet projet;
    
    @ManyToOne
    private Employe responsable; 
    

}