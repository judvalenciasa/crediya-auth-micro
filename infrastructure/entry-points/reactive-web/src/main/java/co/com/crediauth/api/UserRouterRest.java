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
public class UserRouterRest {

    private final UserHandler userHandler;

    @Value("${api.base-path:api}")
    private String apiBasePath;

    @Value("${api.version:1}")
    private String apiVersion;

    @Value("${api.endpoints.users:usuarios}")
    private String apiEndpointUsers;

    public UserRouterRest(UserHandler userHandler) {
        this.userHandler = userHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> userRoutes() {
        String basePath = "/" + apiBasePath + "/v" + apiVersion + "/" + apiEndpointUsers;
        
        return route()
                .POST(basePath, userHandler::createUser)
                .GET(basePath + "/{documentNumber}", userHandler::existUserByDocumentNumber)

                .GET("/openapi/openapi.yaml", request ->
                        ServerResponse.ok()
                                .contentType(MediaType.parseMediaType("application/yaml"))
                                .bodyValue(new ClassPathResource("openapi/openapi.yaml"))
                )
                .build();
    }
}
