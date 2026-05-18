package com.example.backend.service;

import com.example.backend.dto.TacheDTO;
import com.example.backend.entity.Employe;
import com.example.backend.entity.Projet;
import com.example.backend.entity.Ressource;
import com.example.backend.entity.Tache;
import com.example.backend.enums.DisponibiliteRessource;
import com.example.backend.mapper.TacheMapper;
import com.example.backend.repository.EmployeRepository;
import com.example.backend.repository.ProjetRepository;
import com.example.backend.repository.RessourceRepository;
import com.example.backend.repository.TacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TacheService {

    @Autowired
    private TacheRepository tacheRepository;

    @Autowired
    private ProjetRepository projetRepository;

    @Autowired
    private EmployeRepository employeRepository;

    @Autowired
    private RessourceRepository ressourceRepository;

    @Autowired
    private TacheMapper tacheMapper;

    public List<TacheDTO> getAllTaches() {
        return tacheMapper.toDtoList(tacheRepository.findAll());
    }

    public Optional<TacheDTO> getTacheById(Long id) {
        return tacheRepository.findById(id).map(tacheMapper::toDto);
    }

    public List<TacheDTO> getTachesByProjet(Long projetId) {
        return tacheMapper.toDtoList(tacheRepository.findByProjetId(projetId));
    }

    public Optional<TacheDTO> createTache(TacheDTO tacheDTO) {
        Optional<Projet> projetOpt = projetRepository.findById(tacheDTO.getProjetId());
        if (projetOpt.isEmpty()) {
            return Optional.empty();
        }

        Tache tache = new Tache();
        tache.setDescription(tacheDTO.getDescription());
        tache.setEtat(tacheDTO.getEtat());
        tache.setPriorite(tacheDTO.getPriorite());
        tache.setDeadline(tacheDTO.getDeadline());
        tache.setProjet(projetOpt.get());

        if (tacheDTO.getResponsableId() != null) {
            employeRepository.findById(tacheDTO.getResponsableId()).ifPresent(tache::setResponsable);
        }

        Tache saved = tacheRepository.save(tache);
        return Optional.of(tacheMapper.toDto(saved));
    }

    public Optional<TacheDTO> updateTache(Long id, TacheDTO tacheDTO) {
        return tacheRepository.findById(id).map(existing -> {
            existing.setDescription(tacheDTO.getDescription());
            existing.setEtat(tacheDTO.getEtat());
            existing.setPriorite(tacheDTO.getPriorite());
            existing.setDeadline(tacheDTO.getDeadline());

            if (tacheDTO.getResponsableId() != null) {
                employeRepository.findById(tacheDTO.getResponsableId()).ifPresent(existing::setResponsable);
            }

            return tacheMapper.toDto(tacheRepository.save(existing));
        });
    }

    public boolean deleteTache(Long id) {
        if (!tacheRepository.existsById(id)) {
            return false;
        }
        tacheRepository.deleteById(id);
        return true;
    }

    public Optional<TacheDTO> assignerRessources(Long tacheId, List<Long> ressourceIds) {
        return tacheRepository.findById(tacheId).map(tache -> {
            // Verifier que toutes les ressources sont DISPONIBLES
            List<Ressource> ressources = ressourceRepository.findAllById(ressourceIds);
            
            List<Ressource> nonDisponibles = ressources.stream()
                    .filter(r -> r.getDisponibilite() != DisponibiliteRessource.DISPONIBLE)
                    .collect(Collectors.toList());
            
            if (!nonDisponibles.isEmpty()) {
                String noms = nonDisponibles.stream()
                        .map(Ressource::getNom)
                        .collect(Collectors.joining(", "));
                throw new IllegalArgumentException(
                        "Ressources non disponibles : " + noms + 
                        ". Seules les ressources avec etat DISPONIBLE peuvent etre assignees."
                );
            }
            
            tache.setRessources(ressources);
            return tacheMapper.toDto(tacheRepository.save(tache));
        });
    }

    public Optional<TacheDTO> changerEtat(Long id, String nouvelEtat) {
        return tacheRepository.findById(id).map(tache -> {
            tache.setEtat(com.example.backend.enums.EtatTache.valueOf(nouvelEtat));
            return tacheMapper.toDto(tacheRepository.save(tache));
        });
    }

    public List<Map<String, Object>> getAllWithRessources() {
        List<Tache> taches = tacheRepository.findAll();
        List<Map<String, Object>> result = new ArrayList<>();

        for (Tache tache : taches) {
            for (Ressource ressource : tache.getRessources()) {
                Map<String, Object> map = new HashMap<>();
                map.put("tacheId", tache.getId());
                map.put("tacheDescription", tache.getDescription());
                map.put("tacheEtat", tache.getEtat().toString());
                map.put("ressourceId", ressource.getId());
                map.put("ressourceNom", ressource.getNom());
                map.put("ressourceType", ressource.getType().toString());
                map.put("ressourceCout", ressource.getCout());
                result.add(map);
            }
        }

        return result;
    }

    public boolean retirerRessource(Long tacheId, Long ressourceId) {
        Optional<Tache> tacheOpt = tacheRepository.findById(tacheId);
        if (tacheOpt.isEmpty()) {
            return false;
        }
        
        Tache tache = tacheOpt.get();
        boolean removed = tache.getRessources().removeIf(r -> r.getId().equals(ressourceId));
        
        if (removed) {
            tacheRepository.save(tache);
        }
        
        return removed;
    }
}