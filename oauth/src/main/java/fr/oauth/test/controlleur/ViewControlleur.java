package fr.oauth.test.controlleur;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class ViewControlleur {

    @GetMapping("/")
    public String getIndex(){
        return "index";
    }

    @GetMapping("/auth")
    public String getAuth(){
        return "auth";
    }
}
