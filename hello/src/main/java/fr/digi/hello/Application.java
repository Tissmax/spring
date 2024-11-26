package fr.digi.hello;

import fr.digi.hello.entites.Ville;
import fr.digi.hello.service.VilleService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

//		VilleService villeService = new VilleService();
//		for (Ville ville : villeService.getVilles()) {
//			System.out.println(ville.getId());
//		}
	}

}
