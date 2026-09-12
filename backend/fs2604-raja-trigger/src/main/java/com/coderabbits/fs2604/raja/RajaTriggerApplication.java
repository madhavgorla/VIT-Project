package com.coderabbits.fs2604.raja;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RajaTriggerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RajaTriggerApplication.class, args);
    }

    @Bean
    public OpenAPI rajaOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("FS-2604: Raja's Rainfall & Trigger Engine")
                        .version("1.0.0")
                        .description("Rainfall ingestion (IMD AWS & CHIRPS satellite grids), district normalization, and parametric trigger decision. Connected to Ganesh (8080) and Anosh (8082)."));
    }
}
