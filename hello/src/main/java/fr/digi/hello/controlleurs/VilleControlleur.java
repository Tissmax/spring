package fr.digi.hello.controlleurs;

import fr.digi.hello.entites.Ville;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ville")
public class VilleControlleur {

    @GetMapping
    public List<Ville> getVille() {
        return List.of(new Ville(100000, "Paris"),
                new Ville(200000, "Marseille"));
    }
}
