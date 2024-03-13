package com.neo.vacationpaycalc.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Vacation pay calculator for Neoflex",
                version = "1.0",
                description = "Documentation \"Vacation pay calculator\" v1.0"),

        servers = @Server(
                description = "Http Server",
                url = "http://localhost:8080"
        ))
public class OpenApiConfig {
}
