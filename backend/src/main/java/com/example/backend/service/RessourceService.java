package com.example.backend.service;

import com.example.backend.dto.RessourceDTO;
import com.example.backend.entity.Ressource;
import com.example.backend.mapper.RessourceMapper;
import com.example.backend.repository.RessourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RessourceService {

    @Autowired
    private RessourceRepository ressourceRepository;

    @Autowired
    private RessourceMapper ressourceMapper;

    public List<RessourceDTO> getAllRessources() {
        return ressourceMapper.toDtoList(ressourceRepository.findAll());
    }

    public Optional<RessourceDTO> getRessourceById(Long id) {
        return ressourceRepository.findById(id).map(ressourceMapper::toDto);
    }

    public RessourceDTO createRessource(RessourceDTO ressourceDTO) {
        Ressource ressource = ressourceMapper.fromDto(ressourceDTO);
        Ressource saved = ressourceRepository.save(ressource);
        return ressourceMapper.toDto(saved);
    }

    public Optional<RessourceDTO> updateRessource(Long id, RessourceDTO ressourceDTO) {
        return ressourceRepository.findById(id).map(existing -> {
            existing.setNom(ressourceDTO.getNom());
            existing.setType(ressourceDTO.getType());
            existing.setCout(ressourceDTO.getCout());
            existing.setDisponibilite(ressourceDTO.getDisponibilite());
            return ressourceMapper.toDto(ressourceRepository.save(existing));
        });
    }

    public boolean deleteRessource(Long id) {
        if (!ressourceRepository.existsById(id)) {
            return false;
        }
        ressourceRepository.deleteById(id);
        return true;
    }

    public List<RessourceDTO> searchByNom(String nom) {
        return ressourceMapper.toDtoList(ressourceRepository.findByNomContainingIgnoreCase(nom));
    }
}