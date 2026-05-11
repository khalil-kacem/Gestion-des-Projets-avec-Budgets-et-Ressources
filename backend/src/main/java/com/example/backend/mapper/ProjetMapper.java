package com.example.backend.mapper;

import com.example.backend.dto.ProjetDTO;
import com.example.backend.dto.TacheDTO;
import com.example.backend.entity.Projet;
import com.example.backend.entity.Tache;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProjetMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TacheMapper tacheMapper;

    public ProjetDTO toDto(Projet projet) {
        if (projet == null) return null;
        
        ProjetDTO dto = modelMapper.map(projet, ProjetDTO.class);
        
        // Set calculated fields
        if (projet.getTaches() != null) {
            dto.setNombreTaches(projet.getTaches().size());
            
            double coutTotal = projet.getTaches().stream()
                .flatMap(t -> t.getRessources().stream())
                .mapToDouble(r -> r.getCout() != null ? r.getCout() : 0)
                .sum();
            
            // Add projet-level resources
            if (projet.getRessources() != null) {
                coutTotal += projet.getRessources().stream()
                    .mapToDouble(r -> r.getCout() != null ? r.getCout() : 0)
                    .sum();
            }
            
            dto.setCoutTotal(coutTotal);
            dto.setBudgetRestant(projet.getBudget() - coutTotal);
        }
        
        // Map taches
        if (projet.getTaches() != null) {
            List<TacheDTO> tachesDto = projet.getTaches().stream()
                .map(tacheMapper::toDto)
                .collect(Collectors.toList());
            dto.setTaches(tachesDto);
        }
        
        // Map resource IDs
        if (projet.getRessources() != null) {
            dto.setRessourceIds(projet.getRessources().stream()
                .map(r -> r.getId())
                .collect(Collectors.toList()));
        }
        
        return dto;
    }

    public Projet fromDto(ProjetDTO dto) {
        if (dto == null) return null;
        return modelMapper.map(dto, Projet.class);
    }

    public List<ProjetDTO> toDtoList(List<Projet> projets) {
        return projets.stream().map(this::toDto).collect(Collectors.toList());
    }
}