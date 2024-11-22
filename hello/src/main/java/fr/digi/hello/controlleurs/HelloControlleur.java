package fr.digi.hello.controlleurs;

import fr.digi.hello.service.HelloService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloControlleur {

    HelloService helloService = new HelloService();

    @GetMapping
    public String sayHello() {
        return helloService.salutations();
    }
}
