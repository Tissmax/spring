package fr.digi.hello.controlleurs;

import fr.digi.hello.entites.Ville;
import fr.digi.hello.service.VilleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    private List<Ville> villes;

    public VilleControlleur(VilleService villeService) {
        this.villeService = villeService;
    }

    private List<Ville> updateList() {
        return villes = villeService.getVilles();
    }

    @GetMapping
    public ResponseEntity<List<Ville>> getVilles() {
        updateList();
        return ResponseEntity.ok(villes);
    }

    @GetMapping(value = "/id:{id}")
    public ResponseEntity<String> getVillesById(@PathVariable Integer id) {
        updateList();
        if (villeService.getVilleById(id) != null) {
            return ResponseEntity.ok(villes.get(id).toString());
        }
        return ResponseEntity.badRequest().body("La ville n'existe pas");
    }
    @GetMapping("/nom:{name}")
    public ResponseEntity<Object> getVillesByName(@PathVariable String name) {
        updateList();
        if (villeService.getVilleByNom(name) != null) {
            return ResponseEntity.ok(villes.stream()
                    .filter(v -> v.getNom().equals(name)).findFirst());
        }
        return ResponseEntity.badRequest().body("La ville n'existe pas");
    }

    @PostMapping
    public ResponseEntity<String> addVille(@Valid @RequestBody Ville ville,
                                           BindingResult result) throws Exception {
        if (result.hasErrors()) {
            throw new Exception(result.getAllErrors().toString());
        }
        updateList();
        if (villes.stream()
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
        updateList();
        if (villes.stream()
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
        updateList();
        if (villes.stream()
                .noneMatch(v -> v.getId().equals(id))
        ) {
            return ResponseEntity.badRequest().body("La ville n'existe pas");
        }
        villeService.supprimerVille(id);
        return ResponseEntity.ok("La ville a été supprimée avec succès");
    }

}
