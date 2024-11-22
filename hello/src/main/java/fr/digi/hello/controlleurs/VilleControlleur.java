package fr.digi.hello.controlleurs;

import fr.digi.hello.entites.Ville;
import fr.digi.hello.service.VilleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/villes")
public class VilleControlleur {

    private final VilleService villeService;

    public VilleControlleur(VilleService villeService) {
        this.villeService = villeService;
    }

    @GetMapping
    public List<Ville> getVilles(){
        return villeService.getVilles();
    }
}
