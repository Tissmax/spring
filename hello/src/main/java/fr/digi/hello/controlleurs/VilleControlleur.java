package fr.digi.hello.controlleurs;

import fr.digi.hello.entites.Ville;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/villes")
public class VilleControlleur {

    @GetMapping
    public List<Ville> getVilles(){
        return List.of(
                new Ville("Paris", 2_140_526),
                new Ville("Marseille", 870_018),
                new Ville("Lyon", 515_695),
                new Ville("Toulouse", 479_553),
                new Ville("Nice", 342_522)
        );
    }
}
