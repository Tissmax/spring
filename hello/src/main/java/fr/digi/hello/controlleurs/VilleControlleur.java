package fr.digi.hello.controlleurs;

import fr.digi.hello.entites.Ville;
import fr.digi.hello.service.VilleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/villes")
public class VilleControlleur {

    private final VilleService villeService;
    private final ArrayList<Ville> villes;

    public VilleControlleur(VilleService villeService) {
        this.villeService = villeService;
        this.villes = villeService.getVilles();
    }

    @GetMapping
    public ResponseEntity<ArrayList<Ville>> getVilles() {
        return ResponseEntity.ok(villes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getVillesById(@PathVariable Integer id) {

        if (villeService.getVilleById(id)) {
            return ResponseEntity.ok(villes.get(id));
        }
        return ResponseEntity.badRequest().body("La ville n'existe pas");
    }

    @PostMapping
    public ResponseEntity<String> addVille(@Valid @RequestBody Ville ville,
                                           BindingResult result) throws Exception {
        if (result.hasErrors()) {
            throw new Exception(result.getAllErrors().toString());
        }
        if (villeService.addVille(ville)) {
            return ResponseEntity.ok("La ville a été ajoutée avec succès");
        }
        return ResponseEntity.badRequest().body("La ville existe déjà");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateVille(@Valid @RequestBody Ville ville,
                                              @PathVariable Integer id,
                                              BindingResult result) throws Exception {
        if (result.hasErrors()) {
            throw new Exception(result.getAllErrors().toString());
        }
        if (villeService.getVilleById(id)) {
            villes.set(id, ville);
            return ResponseEntity.ok("La ville a été modifiée avec succès");
        }
        return ResponseEntity.badRequest().body("La ville n'existe pas");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVille(@PathVariable Integer id) {

        if (villeService.getVilleById(id)) {
            villes.stream()
                    .filter(v -> v.getId().equals(id))
                    .findFirst()
                    .ifPresent(villes::remove);
            return ResponseEntity.ok("La ville a été supprimée avec succès");
        }
        return ResponseEntity.badRequest().body("La ville n'existe pas");
    }
}
