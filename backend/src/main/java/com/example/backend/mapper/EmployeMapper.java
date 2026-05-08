package com.example.backend.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.backend.dto.EmployeDTO;
import com.example.backend.entity.Employe;

@Component
public class EmployeMapper {

    @Autowired
    private ModelMapper mMapper;

    public EmployeDTO toDto(Employe e) {
        EmployeDTO eDTO = mMapper.map(e, EmployeDTO.class);

        eDTO.setId(e.getId());
        eDTO.setNom(e.getNom());
        eDTO.setEmail(e.getEmail());
        eDTO.setEquipe(e.getEquipe());

        return eDTO;
    }

    public Employe fromDto(EmployeDTO eDTO) {
        return mMapper.map(eDTO, Employe.class);
    }
}