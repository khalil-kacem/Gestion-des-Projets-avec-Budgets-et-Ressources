package com.example.backend.repository;

import com.example.backend.entity.Projet;
import com.example.backend.enums.StatutProjet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjetRepository extends JpaRepository<Projet, Long> {

    List<Projet> findByStatut(StatutProjet statut);

    @Query("SELECT p FROM Projet p LEFT JOIN FETCH p.taches WHERE p.id = :id")
    Projet findByIdWithTaches(Long id);

    @Query("SELECT p FROM Projet p LEFT JOIN FETCH p.ressources WHERE p.id = :id")
    Projet findByIdWithRessources(Long id);
}