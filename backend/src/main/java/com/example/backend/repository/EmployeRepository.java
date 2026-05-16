package com.example.backend.repository;

import com.example.backend.entity.Employe;
import com.example.backend.enums.RoleEmploye;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeRepository extends JpaRepository<Employe, Long> {

    Optional<Employe> findByEmail(String email);

    List<Employe> findByRole(RoleEmploye role);

    List<Employe> findByEquipe(String equipe);
}