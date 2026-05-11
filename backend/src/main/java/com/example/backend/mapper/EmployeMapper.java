package com.example.backend.mapper;

import com.example.backend.dto.EmployeDTO;
import com.example.backend.entity.Employe;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmployeMapper {

    @Autowired
    private ModelMapper modelMapper;

    public EmployeDTO toDto(Employe employe) {
        if (employe == null) return null;
        return modelMapper.map(employe, EmployeDTO.class);
    }

    public Employe fromDto(EmployeDTO dto) {
        if (dto == null) return null;
        return modelMapper.map(dto, Employe.class);
    }

    public List<EmployeDTO> toDtoList(List<Employe> employes) {
        return employes.stream().map(this::toDto).collect(Collectors.toList());
    }
}