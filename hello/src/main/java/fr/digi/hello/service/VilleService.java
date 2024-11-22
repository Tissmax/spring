package fr.digi.hello.service;

import fr.digi.hello.entites.Ville;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VilleService {

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
