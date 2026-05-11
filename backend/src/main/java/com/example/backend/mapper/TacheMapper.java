package com.example.backend.mapper;

import com.example.backend.dto.TacheDTO;
import com.example.backend.entity.Tache;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TacheMapper {

    @Autowired
    private ModelMapper modelMapper;

    public TacheDTO toDto(Tache tache) {
        if (tache == null) return null;
        
        TacheDTO dto = modelMapper.map(tache, TacheDTO.class);
        
        if (tache.getProjet() != null) {
            dto.setProjetId(tache.getProjet().getId());
        }
        
        if (tache.getResponsable() != null) {
            dto.setResponsableId(tache.getResponsable().getId());
            dto.setResponsableNom(tache.getResponsable().getNom());
        }
        
        if (tache.getRessources() != null) {
            dto.setRessourceIds(tache.getRessources().stream()
                .map(r -> r.getId())
                .collect(Collectors.toList()));
        }
        
        return dto;
    }

    public Tache fromDto(TacheDTO dto) {
        if (dto == null) return null;
        return modelMapper.map(dto, Tache.class);
    }

    public List<TacheDTO> toDtoList(List<Tache> taches) {
        return taches.stream().map(this::toDto).collect(Collectors.toList());
    }
}