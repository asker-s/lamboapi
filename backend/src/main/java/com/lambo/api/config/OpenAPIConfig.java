package com.lambo.api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "Lambo API")
)
@Configuration
public class OpenAPIConfig {

    @Bean
    public GroupedOpenApi authApi() {
        return GroupedOpenApi.builder()
                .group("Auth")
                .pathsToMatch("/api/login")
                .build();
    }

    @Bean
    public GroupedOpenApi carsApi() {
        // excludes the assignGarageToCar in the GarageController,
        // as it has the same path as removeCarFromGarageByOwnerIdAndGarageId in the CarController
        return GroupedOpenApi.builder()
                .group("Cars")
                .pathsToMatch("/api/car",
                        "/api/owner/*/garages/*/cars",
                        "/api/owner/*/garages/*/cars/*",
                        "/api/car/*/garages/*")
                .addOpenApiMethodFilter(method -> !method.getName().equals("assignCarToGarage"))
                .build();
    }

    @Bean
    public GroupedOpenApi garageApi() {
        // excludes the assignGarageToCar in the GarageController,
        // as it has the same path as removeCarFromGarageByOwnerIdAndGarageId in the CarController
        return GroupedOpenApi.builder()
                .group("Garage")
                .pathsToMatch("/api/owner/*/garage",
                        "/api/owner/*/garages/{garageId}/cars/*",
                        "/api/owner/*/garages/*",
                        "/api/cars/*/garages")
                .addOpenApiMethodFilter(method -> !method.getName().equals("removeCarFromGarageByOwnerIdAndGarageId"))
                .build();
    }

    @Bean
    public GroupedOpenApi ownerApi() {
        return GroupedOpenApi.builder()
                .group("Owner")
                .pathsToMatch("/api/owner", "/api/owner/*")
                .build();
    }


}

