package com.example.backend.mapper;

import com.example.backend.dto.RessourceDTO;
import com.example.backend.entity.Ressource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RessourceMapper {

    @Autowired
    private ModelMapper modelMapper;

    public RessourceDTO toDto(Ressource ressource) {
        if (ressource == null) return null;
        return modelMapper.map(ressource, RessourceDTO.class);
    }

    public Ressource fromDto(RessourceDTO dto) {
        if (dto == null) return null;
        return modelMapper.map(dto, Ressource.class);
    }

    public List<RessourceDTO> toDtoList(List<Ressource> ressources) {
        return ressources.stream().map(this::toDto).collect(Collectors.toList());
    }
}