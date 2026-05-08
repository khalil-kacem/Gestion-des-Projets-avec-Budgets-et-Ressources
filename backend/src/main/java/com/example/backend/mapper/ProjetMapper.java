package com.example.backend.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.backend.dto.ProjetDTO;
import com.example.backend.entity.Projet;

@Component
public class ProjetMapper {

    @Autowired
    private ModelMapper mMapper;

    public ProjetDTO toDto(Projet p) {
        ProjetDTO pDTO = mMapper.map(p, ProjetDTO.class);
        
        pDTO.setId(p.getId());
        pDTO.setNom(p.getNom());
        pDTO.setDateDebut(p.getDateDebut());
        pDTO.setDateFin(p.getDateFin());
        pDTO.setBudget(p.getBudget());

        return pDTO;
    }

    public Projet fromDto(ProjetDTO pDTO) {
        return mMapper.map(pDTO, Projet.class);
    }
}