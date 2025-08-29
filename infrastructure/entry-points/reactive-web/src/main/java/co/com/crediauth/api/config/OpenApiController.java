package co.com.crediauth.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
public class OpenApiController {

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

    @GetMapping(value = "/openapi/openapi.yaml", produces = MediaType.APPLICATION_YAML_VALUE)
    public Mono<String> getOpenApiYaml() {
        try {
            ClassPathResource resource = new ClassPathResource("openapi/openapi.yaml");
            String content = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            
            // Reemplazar variables
            content = content.replace("${API_BASE_PATH}", apiBasePath);
            content = content.replace("${API_VERSION}", apiVersion);
            content = content.replace("${API_ENDPOINT_USERS}", apiEndpointUsers);
            content = content.replace("${API_ENDPOINT_ROLES}", apiEndpointRoles);
            content = content.replace("${API_SERVER_DEV}", apiServerDev);
            content = content.replace("${API_SERVER_PROD}", apiServerProd);
            
            return Mono.just(content);
        } catch (IOException e) {
            return Mono.error(e);
        }
    }
}
