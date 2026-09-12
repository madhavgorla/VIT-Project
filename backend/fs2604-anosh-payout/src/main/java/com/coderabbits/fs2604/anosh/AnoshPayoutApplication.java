package com.coderabbits.fs2604.anosh;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AnoshPayoutApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnoshPayoutApplication.class, args);
    }

    @Bean
    public OpenAPI anoshOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("FS-2604: Anosh's Database, Payout & Audit Engine")
                        .version("1.0.0")
                        .description("Automated parametric claim disbursement, banking transactions, and cryptographic audit records. Connects to Ganesh (8080) and Raja (8081)."));
    }
}
