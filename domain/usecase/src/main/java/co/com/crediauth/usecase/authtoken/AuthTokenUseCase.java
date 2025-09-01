package co.com.crediauth.usecase.authtoken;

import co.com.crediauth.model.authtoken.AuthToken;
import co.com.crediauth.model.authtoken.gateways.AuthTokenRepository;
import co.com.crediauth.model.user.User;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class AuthTokenUseCase implements IAuthTokenUseCase {
    private final AuthTokenRepository authTokenRepository;

    @Override
    public Mono<AuthToken> generateToken(User user) {

        return authTokenRepository.generateToken(user);
    }

    @Override
    public Mono<Boolean> validateToken(String token) {
        return authTokenRepository.validateToken(token);
    }

    @Override
    public Mono<String> getEmailFromToken(String token) {
        return authTokenRepository.getEmailFromToken(token);
    }

    @Override
    public Mono<String> getRoleFromToken(String token) {
        return authTokenRepository.getRoleFromToken(token);
    }

    @Override
    public Mono<Long> getUserIdFromToken(String token) {
        return authTokenRepository.getUserIdFromToken(token);
    }

    @Override
    public Mono<AuthToken> refreshToken(String refreshToken) {

        return authTokenRepository.refreshToken(refreshToken);
    }
}
