package fr.digi.hello.controlleurs.th;

import fr.digi.hello.exeptions.VilleExeption;
import fr.digi.hello.service.DepartementService;
import fr.digi.hello.service.VilleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ViewControlleur {

    @Autowired
    private VilleService villeService;
    @Autowired
    private DepartementService departementService;

    @GetMapping("/list-villes")
    public String getVilles(Model model) throws VilleExeption {
        model.addAttribute("villes", villeService.getVilles());
        return "villes/list";
    }

    @RequestMapping("/delete/ville/{nom}")
    public String deleteVille(@PathVariable("nom") String nom,
                              Authentication authentication
                              ) throws VilleExeption {
        if (authentication.getAuthorities()
                .stream()
                .noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))){
            System.out.println("Vous n'avez pas les droits pour supprimer");
            return "redirect:/list-villes";
        }
        villeService.supprimerVille(nom);
        return "redirect:/list-villes";
    }

    @GetMapping("/list-dept")
    public String getDept(Model model){
        model.addAttribute("dept", departementService.getDepartements());
        return "villes/listdept";
    }

    @RequestMapping("/delete/dept/{id}")
    public String deleteDept(@PathVariable("id") int id
    ) throws VilleExeption {

        departementService.supprimerDepartement(id);
        return "redirect:/list-dept";
    }

}
