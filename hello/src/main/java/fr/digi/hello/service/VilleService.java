package fr.digi.hello.service;

import fr.digi.hello.dao.VilleDao;
import fr.digi.hello.entites.Ville;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VilleService {

    @Autowired
    private final VilleDao villeDao;
    private List<Ville> villes;

    public VilleService(VilleDao villeDao) {
        this.villeDao = villeDao;
        this.villes = villeDao.extractVilles();
    }

    public List<Ville> getVilles(){
        villes = villeDao.extractVilles();
        return villes;
    }

    public Ville getVilleById(Integer id){
        return villeDao.extractVilleById(id);
    }

    public Ville getVilleByNom(String nom){
        return villeDao.extractVilleByNom(nom);
    }

    public List<Ville> insertVille(Ville ville){
        if (villes.stream()
                .anyMatch(v -> v.getNom().equals(ville.getNom()))
        ) {
            return getVilles();
        }
        villes.add(new Ville(ville.getNom(), ville.getNbHabitants()));
        villeDao.insertVille(ville);
        return getVilles();
    }

    public List<Ville> modifierVille(int idVille, Ville villeModifiee) {
        Ville ville = getVilleById(idVille);
        if (ville != null) {
            ville.setNom(villeModifiee.getNom());
            ville.setNbHabitants(villeModifiee.getNbHabitants());
            villeDao.modifierVille(ville);
        }
        return getVilles();
    }

    public List<Ville> supprimerVille(int idVille) {
        Ville ville = getVilleById(idVille);
        if (ville != null) {
            getVilles().remove(ville);
            villeDao.supprimerVille(ville);
        }
        return getVilles();
    }
}
