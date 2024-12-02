package fr.digi.hello.mapper;

import fr.digi.hello.DTO.VilleDTO;
import fr.digi.hello.entites.Departement;
import fr.digi.hello.entites.Ville;

public class VilleMapper {

    public static Ville toBean(VilleDTO villeDTO) {
        Ville ville = new Ville();
        ville.setNom(villeDTO.nom());
        ville.setNbHabitants(villeDTO.nbHabitants());
        Departement departement = new Departement();
        departement.setNomDept(villeDTO.nomDept());
        departement.setNumero(villeDTO.numero());
        ville.setDepartement(departement);
        return ville;
    }

    public static VilleDTO toDTO(Ville ville) {
        return new VilleDTO(ville.getNbHabitants(),
                ville.getNom(),
                ville.getDepartement().getNomDept(),
                ville.getDepartement().getNumero());
    }
}
