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

    @GetMapping
    public ResponseEntity<Object> getDepartements() {
        if (list().isEmpty()) {
            return ResponseEntity.badRequest().body("Aucun département n'a été trouvé");
        }
        return ResponseEntity.ok(list());
    }

    @PutMapping
    public ResponseEntity<String> updateDepartements(@RequestParam int id,
                                                     @RequestBody Departement departementModifie) {
        if (list().stream().anyMatch
                (d -> d.getId().equals(id))) {
            departementService.modifierDepartement(id, departementModifie);
            return ResponseEntity.ok("Le département a été modifié");
        }
        return ResponseEntity.badRequest().body("Le département n'existe pas");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteDepartement(@RequestParam int id) {
        if (departementService.supprimerDepartement(id)) {
            return ResponseEntity.ok("Le département a été supprimé");
        }
        return ResponseEntity.badRequest().body("Le département n'existe pas");
    }

    @PostMapping
    public ResponseEntity<String> addDepartement(@RequestBody Departement departement) {
        if (list().stream().anyMatch
                (d -> d.getNomDept().equals(departement.getNomDept())
                || d.getNumero().equals(departement.getNumero()))) {
            return ResponseEntity.badRequest().body("Le département existe déjà");
        }
        departementService.insertDepartement(departement);
        return ResponseEntity.ok("Le département a été ajouté");
    }
}
