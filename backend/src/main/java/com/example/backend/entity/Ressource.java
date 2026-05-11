package com.example.backend.entity;

import com.example.backend.enums.DisponibiliteRessource;
import com.example.backend.enums.TypeRessource;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ressource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Enumerated(EnumType.STRING)
    private TypeRessource type;

    private Double cout;

    @Enumerated(EnumType.STRING)
    private DisponibiliteRessource disponibilite;

    @ManyToMany(mappedBy = "ressources")
    private List<Projet> projets = new ArrayList<>();

    @ManyToMany(mappedBy = "ressources")
    private List<Tache> taches = new ArrayList<>();
}