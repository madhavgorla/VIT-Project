package com.coderabbits.fs2604.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("FS-2604: Parametric Micro-Insurance Backend & API Hub")
                        .version("1.0.0")
                        .description("### Team Code Rabbits (Madhav, Ganesh, Raja, Anosh)\n\n" +
                                "**Ganesh's Module**: Backend & APIs (Java + Spring Boot + REST API)\n\n" +
                                "**System Flow**:\n" +
                                "`Farmer → Madhav (Flutter) → Ganesh (Spring Boot) → Raja (Rainfall+Trigger) → Anosh (Payout+PostgreSQL+Audit) → Ganesh (API) → Madhav (App+Voice Notification)`\n\n" +
                                "**Key Capabilities**:\n" +
                                "- Offline-first background synchronization for rural low-connectivity areas\n" +
                                "- Multilingual Text-To-Speech (Telugu, Hindi, English) speech generator for farmers\n" +
                                "- Automatic parametric trigger processing based on IMD/CHIRPS rainfall deficit data\n" +
                                "- Instant automated claims & payouts with tamper-evident audit trails")
                        .contact(new Contact()
                                .name("Ganesh (Backend Lead) - Team Code Rabbits")
                                .email("coderabbits.hackathon@gmail.com"))
                        .license(new License().name("Apache 2.0").url("https://spring.io")));
    }
}
