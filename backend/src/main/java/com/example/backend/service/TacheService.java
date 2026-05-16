package com.example.backend.service;

import com.example.backend.dto.TacheDTO;
import com.example.backend.entity.Employe;
import com.example.backend.entity.Projet;
import com.example.backend.entity.Ressource;
import com.example.backend.entity.Tache;
import com.example.backend.mapper.TacheMapper;
import com.example.backend.repository.EmployeRepository;
import com.example.backend.repository.ProjetRepository;
import com.example.backend.repository.RessourceRepository;
import com.example.backend.repository.TacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
            List<Ressource> ressources = ressourceRepository.findAllById(ressourceIds);
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
}