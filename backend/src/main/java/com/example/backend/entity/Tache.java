package com.example.backend.entity;

import com.example.backend.enums.EtatTache;
import com.example.backend.enums.PrioriteTache;
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
public class Tache {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    private EtatTache etat;

    @Enumerated(EnumType.STRING)
    private PrioriteTache priorite;

    private LocalDate deadline;

    @ManyToOne
    @JoinColumn(name = "projet_id")
    private Projet projet;

    @ManyToOne
    @JoinColumn(name = "responsable_id")
    private Employe responsable;

    @ManyToMany
    @JoinTable(
        name = "tache_ressource",
        joinColumns = @JoinColumn(name = "tache_id"),
        inverseJoinColumns = @JoinColumn(name = "ressource_id")
    )
    private List<Ressource> ressources = new ArrayList<>();
}