package fr.digi.hello.controlleurs;


import fr.digi.hello.entites.Departement;
import fr.digi.hello.service.DepartementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departements")
public class DepartementControlleur {

    @Autowired
    private final DepartementService departementService;

    public DepartementControlleur(DepartementService departementService) {
        this.departementService = departementService;
    }

    private List<Departement> list() {
        return departementService.getDepartements();
    }

    @GetMapping("/all")
    public ResponseEntity<String> getDepartements() {
        if (list().isEmpty()) {
            return ResponseEntity.badRequest().body("Aucun département n'a été trouvé");
        }
        return ResponseEntity.ok(list().toString());
    }

    @PutMapping
    public ResponseEntity<String> updateDepartements(int idDepartement, Departement departementModifie) {
        if (list().stream().anyMatch
                (d -> d.getId().equals(idDepartement))) {
            departementService.modifierDepartement(idDepartement, departementModifie);
            return ResponseEntity.ok("Le département a été modifié");
        }
        return ResponseEntity.badRequest().body("Le département n'existe pas");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteDepartement(int idDepartement) {
        if (list().stream().anyMatch
                (d -> d.getId().equals(idDepartement))) {
            departementService.supprimerDepartement(idDepartement);
            return ResponseEntity.ok("Le département a été supprimé");
        }
        return ResponseEntity.badRequest().body("Le département n'existe pas");
    }

    @PostMapping
    public ResponseEntity<String> addDepartement(@RequestBody Departement departement) {
        if (list().stream().anyMatch
                (d -> d.getNom().equals(departement.getNom())
                || d.getNumero().equals(departement.getNumero()))) {
            return ResponseEntity.badRequest().body("Le département existe déjà");
        }
        departementService.insertDepartement(departement);
        return ResponseEntity.ok("Le département a été ajouté");
    }

    @GetMapping
    public ResponseEntity<String> getDepartementsLesPlusGrands(@RequestParam String nomDept,
                                                               @RequestParam int min,
                                                               @RequestParam int max) {
        if (departementService.getVilleRange(nomDept,min,max).isEmpty()) {
            return ResponseEntity.badRequest().body("Aucun département n'a été trouvé");
        }
        return ResponseEntity.ok(departementService.getVilleRange(nomDept,min,max).toString());
    }
}
