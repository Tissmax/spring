package fr.digi.hello.controlleurs.th;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexControlleur {


    @GetMapping
    public String getIndex() {
        return "index";
    }
}
