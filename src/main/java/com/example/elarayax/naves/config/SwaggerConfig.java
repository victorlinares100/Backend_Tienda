package com.example.elarayax.naves.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        // Configuramos el servidor de Railway para que Swagger sepa dónde disparar
        Server railwayServer = new Server();
        railwayServer.setUrl("https://backendtienda-production-fcfd.up.railway.app");
        railwayServer.setDescription("Servidor de Producción Railway");

        // Configuramos el servidor local para tus pruebas en PC
        Server localServer = new Server();
        localServer.setUrl("http://localhost:8090");
        localServer.setDescription("Servidor Local");

        return new OpenAPI()
            .info(new Info()
                .title("API TIENDA ONLINE AUREA")
                .version("0.5.1")
                .description("API TIENDA ONLINE RESTFUL con Spring Boot AUREA")
            )
            .servers(List.of(railwayServer, localServer)); // Importante: añade ambos aquí
    }
}