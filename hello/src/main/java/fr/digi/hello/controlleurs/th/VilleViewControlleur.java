package fr.digi.hello.controlleurs.th;

import fr.digi.hello.entites.Departement;
import fr.digi.hello.entites.Ville;
import fr.digi.hello.exeptions.VilleExeption;
import fr.digi.hello.service.DepartementService;
import fr.digi.hello.service.VilleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class VilleViewControlleur {

    @Autowired
    private VilleService villeService;
    @Autowired
    private DepartementService departementService;

    @GetMapping("/list-villes")
    public String getVilles(Model model) throws VilleExeption {
        model.addAttribute("villes", villeService.getVilles());
        return "villes/list";
    }

    @RequestMapping("/delete/{nom}")
    public String deleteVille(@PathVariable("nom") String nom
                              ) throws VilleExeption {
        villeService.supprimerVille(nom);
        return "redirect:/list-villes";
    }

    @GetMapping("/new")
    public String villeForm(Model model){
        model.addAttribute("ville", new Ville());
        model.addAttribute("departements", departementService.getDepartements());
        return "villes/form";
    }

    @PostMapping("/new")
    public String saveVille(@ModelAttribute("ville") Ville ville,
                            @ModelAttribute("departement") Departement departement)
            throws VilleExeption {
        ville.setDepartement(departement);
        villeService.insertVille(ville);
        return "redirect:/list-villes";
    }

}
