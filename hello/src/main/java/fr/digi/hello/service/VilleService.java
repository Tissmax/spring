package fr.digi.hello.service;

import fr.digi.hello.entites.Ville;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class VilleService {

    private ArrayList<Ville> villes;

    public ArrayList<Ville> getVilles(){
         return villes;
    }

    public  boolean addVille(Ville ville){
        if (villes.stream()
                .anyMatch(v -> v.getId().equals(ville.getId())
                        || v.getNom().equals(ville.getNom())
                        || v.getNbHabitants() == ville.getNbHabitants())
        ) {
            return false;
        }
        this.villes.add(ville);
        return true;
    }

    public boolean getVilleById(Integer id){
        return villes.stream().anyMatch(v -> v.getId().equals(id));
    }
}
