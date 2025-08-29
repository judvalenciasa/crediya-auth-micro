package co.com.crediauth.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${api.base-path:api}")
    private String apiBasePath;

    @Value("${api.version:1}")
    private String apiVersion;

    @Value("${api.endpoints.users:usuarios}")
    private String apiEndpointUsers;

    @Value("${api.endpoints.roles:roles}")
    private String apiEndpointRoles;

    @Value("${api.server.development:http://localhost:8082}")
    private String apiServerDev;

    @Value("${api.server.production:https://api.crediauth.com}")
    private String apiServerProd;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("CrediAuth API")
                        .version("1.0")
                        .description("API de autenticación y autorización para el sistema CrediAuth. " +
                                   "Proporciona endpoints para gestión de usuarios y roles usando WebFlux.")
                        .contact(new Contact()
                                .name("Equipo de Desarrollo")
                                .email("desarrollo@crediauth.com")
                                .url("https://crediauth.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url(apiServerDev)
                                .description("Servidor de desarrollo"),
                        new Server()
                                .url(apiServerProd)
                                .description("Servidor de producción")
                ));
    }
}
