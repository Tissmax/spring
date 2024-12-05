package fr.digi.hello.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Villes API")
                        .version("1.0")
                        .description("Cette API gère la création et la modification de villes et de leurs départements")
                        .contact(new Contact().name("mathis basier").email("mathis.basier@gmail.com")));
    }
}
