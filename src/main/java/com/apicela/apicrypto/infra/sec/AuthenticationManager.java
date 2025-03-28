package com.apicela.apicrypto.infra.sec;

import com.apicela.apicrypto.services.TokenService;
import com.apicela.apicrypto.services.UserService;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {

    private final UserService userService;
    private final TokenService tokenService;

    public AuthenticationManager(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        System.out.println("authenticate called");
        String token = (String) authentication.getCredentials();
        return tokenService.validateToken(token)
                .flatMap(username -> userService.findByUsername(username)
                        .map(userDetails -> (Authentication) new UsernamePasswordAuthenticationToken(
                                userDetails,
                                token,
                                userDetails.getAuthorities())))
                .switchIfEmpty(Mono.empty());
    }
}