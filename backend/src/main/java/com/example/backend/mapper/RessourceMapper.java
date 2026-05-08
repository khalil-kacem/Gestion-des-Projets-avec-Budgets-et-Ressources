package com.example.backend.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.backend.dto.RessourceDTO;
import com.example.backend.entity.Ressource;

@Component
public class RessourceMapper {

    @Autowired
    private ModelMapper mMapper;

    public RessourceDTO toDto(Ressource r) {
        RessourceDTO rDTO = mMapper.map(r, RessourceDTO.class);

        rDTO.setId(r.getId());
        rDTO.setNom(r.getNom());
        rDTO.setCout(r.getCout());
        rDTO.setDisponibilite(r.getDisponibilite());

        return rDTO;
    }

    public Ressource fromDto(RessourceDTO rDTO) {
        return mMapper.map(rDTO, Ressource.class);
    }
}