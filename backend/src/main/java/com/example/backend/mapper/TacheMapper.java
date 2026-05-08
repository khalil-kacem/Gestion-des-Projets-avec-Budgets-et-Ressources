package com.example.backend.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.backend.dto.TacheDTO;
import com.example.backend.entity.Tache;

@Component
public class TacheMapper {

    @Autowired
    private ModelMapper mMapper;

    public TacheDTO toDto(Tache t) {
        TacheDTO tDTO = mMapper.map(t, TacheDTO.class);

        tDTO.setId(t.getId());
        tDTO.setDescription(t.getDescription());
     

        return tDTO;
    }

    public Tache fromDto(TacheDTO tDTO) {
        return mMapper.map(tDTO, Tache.class);
    }
}