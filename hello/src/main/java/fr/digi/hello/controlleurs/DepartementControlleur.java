package fr.digi.hello.controlleurs;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import fr.digi.hello.entites.Departement;
import fr.digi.hello.exeptions.VilleExeption;
import fr.digi.hello.service.DepartementService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
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
                                                     @Valid @RequestBody Departement departementModifie) {
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
    public ResponseEntity<String> addDepartement(@Valid @RequestBody Departement departement) {
        if (list().stream().anyMatch
                (d -> d.getNomDept().equals(departement.getNomDept())
                || d.getNumero().equals(departement.getNumero()))) {
            return ResponseEntity.badRequest().body("Le département existe déjà");
        }
        departementService.insertDepartement(departement);
        return ResponseEntity.ok("Le département a été ajouté");
    }

    @GetMapping("/pdf={code}")
    public void exportVillesPDF(@PathVariable String code,
                                HttpServletResponse response) throws DocumentException, IOException {
        response.setHeader("Content-Disposition", "attachment; filename=\"villes.pdf\"");

        departementService.exportVillesPDF(code, response);
    }

}
