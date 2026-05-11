package com.example.backend.entity;

import com.example.backend.enums.StatutProjet;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Projet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    private LocalDate dateDebut;

    private LocalDate dateFin;

    private Double budget;

    @Enumerated(EnumType.STRING)
    private StatutProjet statut;

    @OneToMany(mappedBy = "projet")
    private List<Tache> taches = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "projet_ressource",
        joinColumns = @JoinColumn(name = "projet_id"),
        inverseJoinColumns = @JoinColumn(name = "ressource_id")
    )
    private List<Ressource> ressources = new ArrayList<>();
}