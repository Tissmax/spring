package fr.digi.hello.DTO;

import fr.digi.hello.entites.Departement;

public record DepartementDTO(String nomDept, String numero, int nbHabitants) {

    public Departement toBean() {
        Departement departement = new Departement();
        departement.setNomDept(nomDept);
        departement.setNumero(numero);
        return departement;
    }
}
