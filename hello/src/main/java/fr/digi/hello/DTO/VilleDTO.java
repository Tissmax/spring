package fr.digi.hello.DTO;

import fr.digi.hello.entites.Departement;
import fr.digi.hello.entites.Ville;

public record VilleDTO(int nbHabitants, String nom, String nomDept, String numero) {


    public Ville toBean() {
        Ville ville = new Ville();
        ville.setNom(nom);
        ville.setNbHabitants(nbHabitants);
        Departement departement = new Departement();
        departement.setNomDept(nomDept);
        departement.setNumero(numero);
        ville.setDepartement(departement);
        return ville;
    }
}
