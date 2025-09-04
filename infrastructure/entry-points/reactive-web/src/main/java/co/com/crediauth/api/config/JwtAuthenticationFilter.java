package co.com.crediauth.api.config;

import co.com.crediauth.usecase.seguridad.ISecurityUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements WebFilter {

    private final ISecurityUseCase securityUseCase;
    private static final String BEARER_PREFIX = "Bearer ";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String path = exchange.getRequest().getPath().value();
        String method = exchange.getRequest().getMethod().name();

        if (isPublicPath(path)) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            log.warn("Token de autorización no encontrado para la ruta: {}", path);
            return unauthorizedResponse(exchange, "Token de autorización requerido");
        }

        String token = authHeader.substring(BEARER_PREFIX.length());

        return securityUseCase.isTokenValidAndHasAccess(token, path, method)
                .flatMap(hasAccess -> {
                    if (Boolean.FALSE.equals(hasAccess)) {
                        return unauthorizedResponse(exchange, "Token inválido o sin permisos");
                    }

                    log.debug("Acceso autorizado para la ruta: {}", path);
                    return chain.filter(exchange);
                });
    }

    private boolean isPublicPath(String path) {
        return path.startsWith("/api/v1/auth/login") ||
                path.startsWith("/openapi/") ||
                path.startsWith("/webjars/") ||
                path.startsWith("/swagger-ui/") ||
                path.startsWith("/api-docs/") ||
                path.startsWith("/v3/api-docs") ||
                path.equals("/favicon.ico") ||
                path.equals("/actuator/health") ||
                path.equals("/actuator/info");
    }

    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange, String message) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        exchange.getResponse().getHeaders().add(HttpHeaders.CONTENT_TYPE, "application/json");
        String body = "{\"error\":\"" + message + "\",\"status\":401}";
        var buffer = exchange.getResponse().bufferFactory().wrap(body.getBytes());
        return exchange.getResponse().writeWith(Mono.just(buffer));
    }
}
