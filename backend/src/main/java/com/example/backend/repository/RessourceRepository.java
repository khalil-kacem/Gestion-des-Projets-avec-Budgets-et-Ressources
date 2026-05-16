package com.example.backend.repository;

import com.example.backend.entity.Ressource;
import com.example.backend.enums.DisponibiliteRessource;
import com.example.backend.enums.TypeRessource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RessourceRepository extends JpaRepository<Ressource, Long> {

    List<Ressource> findByType(TypeRessource type);

    List<Ressource> findByDisponibilite(DisponibiliteRessource disponibilite);

    List<Ressource> findByNomContainingIgnoreCase(String nom);
}