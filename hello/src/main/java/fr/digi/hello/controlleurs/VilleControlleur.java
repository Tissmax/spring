package fr.digi.hello.controlleurs;

import fr.digi.hello.entites.Ville;
import fr.digi.hello.service.VilleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/villes")
public class VilleControlleur {

    @Autowired
    private final VilleService villeService;

    public VilleControlleur(VilleService villeService) {
        this.villeService = villeService;
    }

    @GetMapping
    public ResponseEntity<Object> getVilles() {
        return ResponseEntity.ok(villeService.getVilles());
    }

    @GetMapping(value = "/id={id}")
    public ResponseEntity<Object> getVillesById(@PathVariable Integer id) {
        if (villeService.getVilleById(id).isPresent()) {
            return ResponseEntity.ok(villeService.getVilleById(id));
        }
        return ResponseEntity.badRequest().body("La ville n'existe pas");
    }

    @GetMapping("/nom={name}")
    public ResponseEntity<Object> getVillesByName(@PathVariable String name) {
        if (villeService.getVilleByNom(name) != null) {
            return ResponseEntity.ok(villeService.getVilles().stream()
                    .filter(v -> v.getNom().equals(name)).findFirst());
        }
        return ResponseEntity.badRequest().body("La ville n'existe pas");
    }
    @GetMapping("/pagination")
    public Page<Ville> getVillesPage(@RequestParam int page, @RequestParam int size) {
        return villeService.getVillesPage(page, size);
    }
    @GetMapping("/1")
    public ResponseEntity<Object> getVillesByDepartement(@RequestParam String nomDept,
                                                         @RequestParam int max) {
        Pageable limit = PageRequest.of(0, max);
        if (villeService.getVillesByDepartement(nomDept, limit).isEmpty()) {
            return ResponseEntity.badRequest().body("Il n'y as pas de ville avec ce nom");
        }
        return ResponseEntity.ok(villeService.getVillesByDepartement(nomDept, limit));
    }
    @GetMapping("/2")
    public ResponseEntity<Object> getVillesByDepartementAndNbHabitantsGreaterThan(@RequestParam String nomDept,
                                                                                  @RequestParam int min) {
        if (villeService.getVillesByDepartementAndNbHabitantsGreaterThan(nomDept, min).isEmpty()) {
            return ResponseEntity.badRequest().body("La vile demander n'existe pas");
        }
        return ResponseEntity.ok(villeService.getVillesByDepartementAndNbHabitantsGreaterThan(nomDept, min));
    }
    @GetMapping("/3")
    public ResponseEntity<Object> getVillesByDepartementAndNbHabitantsBetween(@RequestParam String nomDept,
                                                                             @RequestParam int min,
                                                                             @RequestParam int max) {
        if (villeService.getVillesByDepartementAndNbHabitantsBetween(nomDept, min, max).isEmpty()) {
            return ResponseEntity.badRequest().body("La vile demander n'existe pas");
        }
        return ResponseEntity.ok(villeService.getVillesByDepartementAndNbHabitantsBetween(nomDept, min, max));
    }
    @GetMapping("/{nbHabitants}")
    public ResponseEntity<Object> getVillesByNbHabitantsGreaterThan(@RequestParam @PathVariable int nbHabitants) {
        if (villeService.getVillesByNbHabitantsGreaterThan(nbHabitants).isEmpty()) {
            return ResponseEntity.badRequest().body("La vile demander n'existe pas");
        }
        return ResponseEntity.ok(villeService.getVillesByNbHabitantsGreaterThan(nbHabitants));
    }

    @GetMapping("/{min}/{max}")
    public ResponseEntity<Object> getVillesByNbHabitantsLessThanAndNbHabitantsGreaterThan(@RequestParam @PathVariable int min,
                                                                                          @RequestParam @PathVariable int max) {
        if (villeService.getVillesByNbHabitantsLessThanAndNbHabitantsGreaterThan(min, max).isEmpty()) {
            return ResponseEntity.badRequest().body("La ville demander n'existe pas");
        }
        return ResponseEntity.ok(villeService.getVillesByNbHabitantsLessThanAndNbHabitantsGreaterThan(min, max));
    }

    @GetMapping("/departement/{nomDept}")
    public ResponseEntity<Object> getVilleBydepartementWithLimit(@RequestParam String nomDept,
                                                                 @RequestParam int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        if (villeService.getVilleBydepartementWithLimit(nomDept, pageable).isEmpty()) {
            return ResponseEntity.badRequest().body("La ville demander n'existe pas");
        }
        return ResponseEntity.ok(villeService.getVilleBydepartementWithLimit(nomDept, pageable));
    }
    @PostMapping
    public ResponseEntity<String> addVille(@Valid @RequestBody Ville ville,
                                           BindingResult result) throws Exception {
        if (result.hasErrors()) {
            throw new Exception(result.getAllErrors().toString());
        }
        if (villeService.getVilles().stream()
                .anyMatch(v -> v.getNom().equals(ville.getNom())
                || v.getId().equals(ville.getId())
                || v.getNbHabitants() == ville.getNbHabitants())
        ) {
            return ResponseEntity.badRequest().body("La ville existe déjà");
        }
        villeService.insertVille(ville);
        return ResponseEntity.ok("La ville a été ajoutée avec succès");
    }

    @PutMapping("/id:{id}")
    public ResponseEntity<String> updateVille(@Valid @RequestBody Ville ville,
                                              @PathVariable Integer id,
                                              BindingResult result) throws Exception {
        if (result.hasErrors()) {
            throw new Exception(result.getAllErrors().toString());
        }
        if (villeService.getVilles().stream()
                .anyMatch(v -> v.getNom().equals(ville.getNom())
                        || v.getId().equals(ville.getId())
                        || v.getNbHabitants() == ville.getNbHabitants())
        ) {
            return ResponseEntity.badRequest().body("La ville existe déjà");
        }
        villeService.modifierVille(id, ville);
        return ResponseEntity.ok("La ville a été modifiée avec succès");
    }

    @DeleteMapping("/id:{id}")
    public ResponseEntity<String> deleteVille(@PathVariable Integer id) {
        if (villeService.getVilles().stream()
                .noneMatch(v -> v.getId().equals(id))
        ) {
            return ResponseEntity.badRequest().body("La ville n'existe pas");
        }
        villeService.supprimerVille(id);
        return ResponseEntity.ok("La ville a été supprimée avec succès");
    }

}
