package co.com.crediauth.api;

import co.com.crediauth.api.config.ApiConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {

    final String BASE_URL_MATCHER = ApiConfig.API_BASE_PATH ;

    @Bean
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        
        return route(POST("/api/v1/user"), handler::createUser);
    }
}
