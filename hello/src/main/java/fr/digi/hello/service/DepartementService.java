package fr.digi.hello.service;

import fr.digi.hello.dao.DepartementDAO;
import fr.digi.hello.entites.Departement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartementService {

    @Autowired
    private final DepartementDAO departementDao;
    private List<Departement> departements;

    public DepartementService(DepartementDAO departementDao) {
        this.departementDao = departementDao;
        this.departements = getDepartements();
    }

    public List<Departement> getDepartements() {
        return this.departements = departementDao.extractDepartements();
    }

    private Departement getDepartementById(int idDepartement) {
        return departements.stream()
                .filter(d -> d.getId().equals(idDepartement))
                .findFirst()
                .orElse(null);
    }

    private Departement getDepartementByNom(String nom) {
        return departements.stream()
                .filter(d -> d.getNom().equals(nom))
                .findFirst()
                .orElse(null);
    }

    public List<Departement> supprimerDepartement(int idDepartement) {
        Departement departement = getDepartementById(idDepartement);
        if (departement != null) {
            departements.remove(departement);
            departementDao.supprimerDepartement(departement);
        }
        return getDepartements();
    }

    public List<Departement> modifierDepartement(int idDepartement, Departement departementModifie) {
        Departement departement = getDepartementById(idDepartement);
        if (departement != null) {
            departement.setNom(departementModifie.getNom());
            departement.setNumero(departementModifie.getNumero());
            departementDao.modifierDepartement(departement);
        }
        return getDepartements();
    }

    public List<Departement> insertDepartement(Departement departement) {
        if (departements.stream()
                .anyMatch(d -> d.getNom().equals(departement.getNom()))
        ) {
            return getDepartements();
        }
        departements.add(new Departement(departement.getNom(), departement.getNumero()));
        departementDao.insertDepartement(departement);
        return getDepartements();
    }
}
