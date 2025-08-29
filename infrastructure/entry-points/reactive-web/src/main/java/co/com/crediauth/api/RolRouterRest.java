package co.com.crediauth.api;

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

    public RolRouterRest(RolHandler rolHandler) {
        this.rolHandler = rolHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> userRoutes() {
        return route()
                .POST("/rol", rolHandler::createRol)
                .PUT("/rol/{documentNumber}", rolHandler::updateRol)
                .GET("/rol/{documentNumber}", rolHandler::deleteRol)

                .GET("/openapi/openapi.yaml", request ->
                        ServerResponse.ok()
                                .contentType(MediaType.parseMediaType("application/yaml"))
                                .bodyValue(new ClassPathResource("openapi/openapi.yaml"))
                )
                .build();
    }
}
