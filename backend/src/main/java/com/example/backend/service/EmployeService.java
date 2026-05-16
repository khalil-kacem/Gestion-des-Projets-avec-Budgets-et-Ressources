package com.example.backend.service;

import com.example.backend.dto.EmployeDTO;
import com.example.backend.entity.Employe;
import com.example.backend.mapper.EmployeMapper;
import com.example.backend.repository.EmployeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmployeService {

    @Autowired
    private EmployeRepository employeRepository;

    @Autowired
    private EmployeMapper employeMapper;

    public List<EmployeDTO> getAllEmployes() {
        return employeMapper.toDtoList(employeRepository.findAll());
    }

    public Optional<EmployeDTO> getEmployeById(Long id) {
        return employeRepository.findById(id).map(employeMapper::toDto);
    }

    public Optional<EmployeDTO> createEmploye(EmployeDTO employeDTO) {
        if (employeRepository.findByEmail(employeDTO.getEmail()).isPresent()) {
            return Optional.empty();
        }
        Employe employe = employeMapper.fromDto(employeDTO);
        return Optional.of(employeMapper.toDto(employeRepository.save(employe)));
    }

    public Optional<EmployeDTO> updateEmploye(Long id, EmployeDTO employeDTO) {
        return employeRepository.findById(id).map(existing -> {
            existing.setNom(employeDTO.getNom());
            existing.setEmail(employeDTO.getEmail());
            existing.setRole(employeDTO.getRole());
            existing.setEquipe(employeDTO.getEquipe());
            return employeMapper.toDto(employeRepository.save(existing));
        });
    }

    public boolean deleteEmploye(Long id) {
        if (!employeRepository.existsById(id)) {
            return false;
        }
        employeRepository.deleteById(id);
        return true;
    }
}