package com.EChowk.EChowk.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;

public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .info(new Info().title("Echowk API Documentation")
                        .version("1.0.0")
                        .description("Peer-to-Peer Skill Exchange platform API")
                );

    }

}
