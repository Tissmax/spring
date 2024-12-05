package fr.digi.hello.controlleurs;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import fr.digi.hello.DTO.VilleDTO;
import fr.digi.hello.entites.Ville;
import fr.digi.hello.exeptions.VilleExeption;
import fr.digi.hello.service.VilleService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/villes")
public class VilleControlleur {

    @Autowired
    private final VilleService villeService;

    public VilleControlleur(VilleService villeService) {
        this.villeService = villeService;
    }

    @GetMapping
    public ResponseEntity<Object> getVilles() throws VilleExeption {
        return ResponseEntity.ok(villeService.getVilles());
    }

    @GetMapping(value = "/id={id}")
    public ResponseEntity<Object> getVillesById(@PathVariable Integer id) throws VilleExeption {
        if (villeService.getVilleById(id).isPresent()) {
            return ResponseEntity.ok(villeService.getVilleById(id));
        }
        return ResponseEntity.badRequest().body("La ville n'existe pas");
    }

    @GetMapping("/nom={name}")
    public Optional<VilleDTO> getVillesByName(@PathVariable String name) throws VilleExeption {
        return Optional.ofNullable(villeService.getVilleByNom(name));
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
    @GetMapping("/moreThan=")
    public ResponseEntity<Object> getVillesByNbHabitantsGreaterThan(@RequestParam int nbHabitants) {
        if (villeService.getVillesByNbHabitantsGreaterThan(nbHabitants).isEmpty()) {
            return ResponseEntity.badRequest().body("La vile demander n'existe pas");
        }
        return ResponseEntity.ok(villeService.getVillesByNbHabitantsGreaterThan(nbHabitants));
    }

    @GetMapping("/{min}/{max}")
    public ResponseEntity<Object> getVillesByNbHabitantsLessThanAndNbHabitantsGreaterThan( @PathVariable int min,
                                                                                           @PathVariable int max) {
        if (villeService.getVillesByNbHabitantsLessThanAndNbHabitantsGreaterThan(min, max).isEmpty()) {
            return ResponseEntity.badRequest().body("La ville demander n'existe pas");
        }
        return ResponseEntity.ok(villeService.getVillesByNbHabitantsLessThanAndNbHabitantsGreaterThan(min, max));
    }

    @GetMapping("/departement={nomDept}&limit={limit}")
    public ResponseEntity<Object> getVilleBydepartementWithLimit(@RequestParam String nomDept,
                                                                 @RequestParam int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        if (villeService.getVilleBydepartementWithLimit(nomDept, pageable).isEmpty()) {
            return ResponseEntity.badRequest().body("La ville demander n'existe pas");
        }
        return ResponseEntity.ok(villeService.getVilleBydepartementWithLimit(nomDept, pageable));
    }

    @GetMapping("/nb-habitants>{min}")
    public ResponseEntity<Object> exportVillesCSV(@PathVariable int min,
                                                  HttpServletResponse  response) throws Exception {
        final String FILE_NAME = "villes.csv";
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; file='" + FILE_NAME + "'");
        return ResponseEntity.ok(villeService.exportVillesCSV(min));
    }

    @PostMapping
    public ResponseEntity<String> addVille(@Valid @RequestBody VilleDTO ville){

        if (villeService.insertVille(ville.toBean())) {
            return ResponseEntity.badRequest().body("La ville existe déjà");
        }
        villeService.insertVille(ville.toBean());
        return ResponseEntity.ok("La ville a été ajoutée avec succès");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateVille(@Valid @RequestBody Ville ville,
                                              @PathVariable Integer id) throws VilleExeption {
        if (villeService.modifierVille(id, ville)) {
            return ResponseEntity.badRequest().body("La ville existe déjà");
        }
        villeService.modifierVille(id, ville);
        return ResponseEntity.ok("La ville a été modifiée avec succès");
    }

    @DeleteMapping("/{nom}")
    public ResponseEntity<String> deleteVille(@PathVariable String nom) throws VilleExeption {
        if (villeService.supprimerVille(nom)){
            return ResponseEntity.badRequest().body("La ville n'existe pas");
        }
        villeService.supprimerVille(nom);
        return ResponseEntity.ok("La ville a été supprimée avec succès");
    }

}
