package fr.digi.hello.service;

import fr.digi.hello.DTO.VilleDTO;
import fr.digi.hello.entites.Ville;
import fr.digi.hello.repository.VilleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VilleService {
    @Autowired
    private final VilleRepository villeRepository;
    private final List<VilleDTO> villes;

    public VilleService(VilleRepository villeRepository) {
        this.villeRepository = villeRepository;
        this.villes = getVilles();
    }

    public Page<Ville> getVillesPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return villeRepository.findAll(pageable);
    }

    public List<VilleDTO> getVilles(){
        return villeRepository.findAll()
                .stream().map(Ville::toDto)
                .toList();
    }

    public Optional<VilleDTO> getVilleById(Integer id){
        return villeRepository.findById(id)
                .map(Ville::toDto);
    }

    public List<VilleDTO> getVilleByNom(String nom){
        if (villeRepository.findAllByNom(nom).isEmpty()) {
            return getVilles().stream().toList();
        }
        return villeRepository.findAllByNom(nom)
                .stream().map(Ville::toDto)
                .toList();
    }

    public List<Ville> getVillesByDepartement(String nomDept, Pageable limit){
        return villeRepository.findVillesByDepartement_NomDept(nomDept, limit);
    }

    public List<Ville> getVillesByDepartementAndNbHabitantsGreaterThan(String nomDept, int min){
        return villeRepository.findVillesByDepartement_NomDeptAndNbHabitantsGreaterThan(nomDept, min);
    }

    public List<Ville> getVilleBydepartementWithLimit(String nomDept, Pageable limit){
        return villeRepository.findVillesByDepartement_NomDept(nomDept, limit);
    }

    public List<Ville> getVillesByDepartementAndNbHabitantsBetween(String nomDept, int min, int max){
        return villeRepository.findVillesByDepartement_NomDeptAndNbHabitantsBetween(nomDept, min, max);
    }

    public List<Ville> getVillesByNbHabitantsGreaterThan(int nbHabitants){
        return villeRepository.findByNbHabitantsGreaterThan(nbHabitants);
    }

    public List<VilleDTO> getVillesByNbHabitantsLessThanAndNbHabitantsGreaterThan(int min, int max){
        return villeRepository.findByNbHabitantsLessThanAndNbHabitantsGreaterThan(min, max)
                .stream().map(Ville::toDto).toList();
    }

    public boolean insertVille(Ville ville){
        if (villes.stream()
                .noneMatch(v -> v.nom().equals(ville.getNom()))
        ) {
            villeRepository.save(ville);
            return true;
        }
        return false;
    }

    public boolean modifierVille(int idVille, Ville villeModifiee) {
        Optional<Ville> ville = villeRepository.findById(idVille);
        if (ville.isPresent()) {
            ville.get().setNom(villeModifiee.getNom());
            ville.get().setNbHabitants(villeModifiee.getNbHabitants());
            villeRepository.save(ville.get());
            getVilles();
            return true;
        }
        return false;
    }

    public boolean supprimerVille(String nom) {
        Optional<Ville> ville = Optional.ofNullable(villeRepository.findVilleByNom(nom));
        if (ville.isPresent()) {
            villeRepository.delete(ville.get());
            getVilles();
            return true;
        }
        return false;
    }


}
