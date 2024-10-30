package itst.social_raccoon_api.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    //Esto me sirve para personalizar la documentación de Swagger
    //En este caso, se cambia el título, la versión y la descripción
    //de la documentación
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Social Raccoon API")
                        .version("1.0")
                        .description("API for the social network of the Instituto Tecnológico Superior de Teziutlán")
                        .termsOfService("http://swagger.io/terms/")
                        .license(new io.swagger.v3.oas.models.info.License().name("Apache 2.0").url("http://springdoc.org"))
                );
    }


}
