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
    private final List<Ville> villes;

    public VilleControlleur(VilleService villeService) {
        this.villeService = villeService;
        this.villes = villeService.getVilles();
    }

    @GetMapping
    public ResponseEntity<List<Ville>> getVilles() {
        return ResponseEntity.ok(villes);
    }

    @GetMapping(value = "/id:{id}")
    public ResponseEntity<String> getVillesById(@PathVariable Integer id) {

        if (villeService.getVilleById(id) != null) {
            return ResponseEntity.ok(villes.get(id).toString());
        }
        return ResponseEntity.badRequest().body("La ville n'existe pas");
    }
    @GetMapping("/nom:{name}")
    public ResponseEntity<Object> getVillesByName(@PathVariable String name) {

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
        villeService.modifierVille(id, ville);
        return ResponseEntity.ok("La ville a été modifiée avec succès");
    }

    @DeleteMapping("/id:{id}")
    public ResponseEntity<String> deleteVille(@PathVariable Integer id) {

        villeService.supprimerVille(id);
        return ResponseEntity.ok("La ville a été supprimée avec succès");
    }

}
