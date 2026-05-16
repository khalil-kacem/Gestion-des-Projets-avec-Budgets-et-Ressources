package com.example.backend.service;

import com.example.backend.dto.ProjetDTO;
import com.example.backend.dto.RapportFinancierDTO;
import com.example.backend.entity.Projet;
import com.example.backend.entity.Ressource;
import com.example.backend.mapper.ProjetMapper;
import com.example.backend.repository.ProjetRepository;
import com.example.backend.repository.RessourceRepository;
import com.example.backend.repository.TacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProjetService {

    @Autowired
    private ProjetRepository projetRepository;

    @Autowired
    private RessourceRepository ressourceRepository;

    @Autowired
    private TacheRepository tacheRepository;

    @Autowired
    private ProjetMapper projetMapper;

    public List<ProjetDTO> getAllProjets() {
        return projetMapper.toDtoList(projetRepository.findAll());
    }

    public Optional<ProjetDTO> getProjetById(Long id) {
        return projetRepository.findById(id).map(projetMapper::toDto);
    }

    public ProjetDTO createProjet(ProjetDTO projetDTO) {
        Projet projet = projetMapper.fromDto(projetDTO);
        Projet saved = projetRepository.save(projet);
        return projetMapper.toDto(saved);
    }

    public Optional<ProjetDTO> updateProjet(Long id, ProjetDTO projetDTO) {
        return projetRepository.findById(id).map(existing -> {
            existing.setNom(projetDTO.getNom());
            existing.setDateDebut(projetDTO.getDateDebut());
            existing.setDateFin(projetDTO.getDateFin());
            existing.setBudget(projetDTO.getBudget());
            existing.setStatut(projetDTO.getStatut());
            return projetMapper.toDto(projetRepository.save(existing));
        });
    }

    public boolean deleteProjet(Long id) {
        if (!projetRepository.existsById(id)) {
            return false;
        }
        projetRepository.deleteById(id);
        return true;
    }

    public Optional<ProjetDTO> assignerRessources(Long projetId, List<Long> ressourceIds) {
        return projetRepository.findById(projetId).map(projet -> {
            List<Ressource> ressources = ressourceRepository.findAllById(ressourceIds);
            projet.setRessources(ressources);
            return projetMapper.toDto(projetRepository.save(projet));
        });
    }

    public Optional<RapportFinancierDTO> genererRapportFinancier(Long projetId) {
        return projetRepository.findById(projetId).map(projet -> {
            RapportFinancierDTO rapport = new RapportFinancierDTO();
            rapport.setProjetId(projet.getId());
            rapport.setProjetNom(projet.getNom());
            rapport.setBudgetInitial(projet.getBudget());

            double coutRessources = projet.getRessources().stream()
                .mapToDouble(r -> r.getCout() != null ? r.getCout() : 0)
                .sum();

            double coutTaches = projet.getTaches().stream()
                .flatMap(t -> t.getRessources() != null ? t.getRessources().stream() : java.util.stream.Stream.empty())
                .mapToDouble(r -> r.getCout() != null ? r.getCout() : 0)
                .sum();

            double coutTotal = coutRessources + coutTaches;

            rapport.setCoutTotalRessources(coutRessources);
            rapport.setCoutTotalTaches(coutTaches);
            rapport.setBudgetRestant(projet.getBudget() - coutTotal);
            rapport.setPourcentageUtilisation(
                projet.getBudget() > 0 ? (coutTotal / projet.getBudget()) * 100 : 0
            );
            rapport.setNombreTaches(projet.getTaches().size());
            rapport.setNombreTachesTerminees(
                (int) projet.getTaches().stream()
                    .filter(t -> t.getEtat() != null && t.getEtat().toString().equals("TERMINEE"))
                    .count()
            );

            return rapport;
        });
    }
}