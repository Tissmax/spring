package fr.digi.hello.service;

import fr.digi.hello.dao.VilleDao;
import fr.digi.hello.entites.Ville;
import fr.digi.hello.repository.VilleRepository;
import org.hibernate.query.spi.Limit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VilleService {
    @Autowired
    private final VilleRepository villeRepository;
    private List<Ville> villes;

    public VilleService(VilleRepository villeRepository) {
        this.villeRepository = villeRepository;
        this.villes = getVilles();
    }

    public Page<Ville> getVillesPage(int page, int size) {
        Pageable limit = PageRequest.of(page, size);
        return villeRepository.findAll(limit);
    }

    public List<Ville> getVilles(){
        return villeRepository.findAll();
    }

    public Ville getVilleById(Integer id){
        getVilles();
        return getVillesPage(0, villes.size()).stream().toList().get(id);
    }

    public List<Ville> getVilleByNom(String nom){
        if (villeRepository.findAllByNom(nom).isEmpty()) {
            return getVilles().stream().toList();
        }
        return villeRepository.findAllByNom(nom);
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

    public List<Ville> getVillesByNbHabitantsLessThanAndNbHabitantsGreaterThan(int min, int max){
        return villeRepository.findByNbHabitantsLessThanAndNbHabitantsGreaterThan(min, max);
    }

    public List<Ville> insertVille(Ville ville){
        if (villes.stream()
                .anyMatch(v -> v.getNom().equals(ville.getNom()))
        ) {
            return getVilles().stream().toList();
        }
        villes.add(new Ville(ville.getNom(), ville.getNbHabitants()));
        villeRepository.save(ville);
        return getVilles().stream().toList();
    }

    public List<Ville> modifierVille(int idVille, Ville villeModifiee) {
        Ville ville = getVilleById(idVille);
        if (ville != null) {
            ville.setNom(villeModifiee.getNom());
            ville.setNbHabitants(villeModifiee.getNbHabitants());
            villeRepository.save(ville);
            return getVilles().stream().toList();
        }
        return getVilles().stream().toList();
    }

    public List<Ville> supprimerVille(int idVille) {
        Ville ville = getVilleById(idVille);
        if (ville != null) {
            villes.remove(ville);
            villeRepository.delete(ville);
            return getVilles().stream().toList();
        }
        return getVilles().stream().toList();
    }


}
