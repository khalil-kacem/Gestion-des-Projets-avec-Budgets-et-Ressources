package com.example.backend.repository;

import com.example.backend.entity.Tache;
import com.example.backend.enums.EtatTache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TacheRepository extends JpaRepository<Tache, Long> {

    List<Tache> findByProjetId(Long projetId);

    List<Tache> findByResponsableId(Long responsableId);

    List<Tache> findByEtat(EtatTache etat);

    List<Tache> findByProjetIdAndEtat(Long projetId, EtatTache etat);
}