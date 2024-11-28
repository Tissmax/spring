package fr.digi.hello.service;

import fr.digi.hello.dao.DepartementDAO;
import fr.digi.hello.entites.Departement;
import fr.digi.hello.entites.Ville;
import fr.digi.hello.repository.DepartementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartementService {

    @Autowired
    private final DepartementRepository departementRepository;
    private List<Departement> departements;

    public DepartementService(DepartementRepository departementRepository) {
        this.departementRepository = departementRepository;
        this.departements = getDepartements();
    }

    public List<Departement> getDepartements() {
        return this.departements = departementRepository.findAll();
    }

    private Optional<Departement> getDepartementById(int idDepartement) {
        return departements.stream()
                .filter(d -> d.getId().equals(idDepartement))
                .findFirst();
    }

    private Optional<Departement> getDepartementByNom(String nom) {
        return departements.stream()
                .filter(d -> d.getNomDept().equals(nom))
                .findFirst();
    }

    public boolean supprimerDepartement(int idDepartement) {
        Optional<Departement> departement = getDepartementById(idDepartement);
        boolean departementExists = departement.isPresent();
        departement.ifPresent(departementRepository::delete);
        getDepartements();
        return departementExists;
    }

    public void modifierDepartement(int idDepartement, Departement departementModifie) {
        Optional<Departement> departement = getDepartementById(idDepartement);
        departement.ifPresent(d -> {
            d.setNomDept(departementModifie.getNomDept());
            d.setNumero(departementModifie.getNumero());
            departementRepository.save(d);
        });
        getDepartements();
    }

    public void insertDepartement(Departement departement) {
        if (departements.stream()
                .anyMatch(d -> d.getNomDept().equals(departement.getNomDept()))
        ) {
            return;
        }
        departements.add(new Departement(departement.getNomDept(), departement.getNumero()));
        departementRepository.save(departement);
        getDepartements();
    }
}
