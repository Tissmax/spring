package fr.digi.hello;

import com.fasterxml.jackson.core.JsonProcessingException;
import fr.digi.hello.DTO.VilleDTO;
import fr.digi.hello.entites.Ville;
import fr.digi.hello.repository.VilleRepository;
import fr.digi.hello.service.VilleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class TraitementFichierApplication implements CommandLineRunner {

    @Autowired
    private VilleService villeService;
    @Autowired
    private VilleRepository villeRepository;


    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(TraitementFichierApplication.class);
        application.setWebApplicationType(WebApplicationType.NONE);
        application.run(args);
    }


    @Override
    public void run(String... args) throws Exception {
    }

    public String exportVillesCSV(int min) {
        StringBuilder sb = new StringBuilder();
        String HEADER = "Nom;Nb habitants;Nom département;Numéro département\n";
        sb.append(HEADER);
        List<VilleDTO> dtos = villeRepository.findByNbHabitantsGreaterThan(min).stream()
                .map(Ville::toDto)
                .toList();
        return dtos.stream()
                .map(v -> {
                    try {
                        return v.nom() + ";" + v.nbHabitants() + ";" + villeService.nomDepartement(v.numero()).nom() + ";" + v.numero() + "\n";
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(
                        () -> sb,
                        StringBuilder::append,
                        StringBuilder::append
                ).toString();
    }


}
