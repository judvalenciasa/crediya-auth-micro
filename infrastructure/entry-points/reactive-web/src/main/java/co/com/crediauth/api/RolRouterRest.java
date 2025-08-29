package co.com.crediauth.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Component
public class RolRouterRest {

    private final RolHandler rolHandler;

    @Value("${api.base-path:api}")
    private String apiBasePath;

    @Value("${api.version:1}")
    private String apiVersion;

    @Value("${api.endpoints.roles:roles}")
    private String apiEndpointRoles;

    public RolRouterRest(RolHandler rolHandler) {
        this.rolHandler = rolHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> rolRoutes() {
        String basePath = "/" + apiBasePath + "/v" + apiVersion + "/" + apiEndpointRoles;
        
        return route()
                .POST(basePath, rolHandler::createRol)
                .PUT(basePath, rolHandler::updateRol)
                .DELETE(basePath + "/{idRol}", rolHandler::deleteRol)

                .GET("/openapi/openapi.yaml", request ->
                        ServerResponse.ok()
                                .contentType(MediaType.parseMediaType("application/yaml"))
                                .bodyValue(new ClassPathResource("openapi/openapi.yaml"))
                )
                .build();
    }
}
