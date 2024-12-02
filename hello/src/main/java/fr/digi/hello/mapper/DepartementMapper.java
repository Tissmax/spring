package fr.digi.hello.mapper;

import fr.digi.hello.DTO.DepartementDTO;
import fr.digi.hello.entites.Departement;
import fr.digi.hello.entites.Ville;

public class DepartementMapper {

    public static Departement toBean(DepartementDTO departementDTO) {
        Departement departement = new Departement();
        departement.setNomDept(departementDTO.nomDept());
        departement.setNumero(departementDTO.numero());
        return departement;
    }

    public static DepartementDTO toDTO(Departement departement) {
        return new DepartementDTO(departement.getNomDept(),
                departement.getNumero(),
                departement.getVilles().stream()
                        .map(Ville::getNbHabitants)
                        .reduce(0, Integer::sum));
    }
}
