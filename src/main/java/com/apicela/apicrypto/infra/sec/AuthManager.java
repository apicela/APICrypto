package com.apicela.apicrypto.infra.sec;

import com.apicela.apicrypto.services.TokenService;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;

@Component
public class AuthManager implements ReactiveAuthenticationManager {
    final TokenService tokenService;
    final ReactiveUserDetailsService userDetailsService;

    public AuthManager(TokenService tokenService, ReactiveUserDetailsService userDetailsService) {
        this.tokenService = tokenService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        var b = Mono.justOrEmpty(authentication)
                .cast(BearerToken.class)
                .flatMap(auth -> {
                    String username = tokenService.getUserName(auth.getCredentials().toString());
                    Mono<UserDetails> foundUser = userDetailsService.findByUsername(username).defaultIfEmpty(new UserDetails() {
                        @Override
                        public Collection<? extends GrantedAuthority> getAuthorities() {
                            return List.of();
                        }

                        @Override
                        public String getPassword() {
                            return "";
                        }

                        @Override
                        public String getUsername() {
                            return "";
                        }
                    });

                    Mono<Authentication> a = foundUser.flatMap(u -> {
                        if (u.getUsername() == null) {
                            Mono.error(new IllegalArgumentException("Username is null"));
                        }
                        if (tokenService.validate(u, auth.getCredentials().toString())) {
                            return Mono.justOrEmpty(new UsernamePasswordAuthenticationToken(u.getUsername(), u.getPassword(), u.getAuthorities()));
                        }
                        Mono.error(new IllegalArgumentException("Username or password is incorrect"));
                        return Mono.justOrEmpty(new UsernamePasswordAuthenticationToken(u.getUsername(), u.getPassword(), u.getAuthorities()));
                    });
                    return a;
                });
        return b;
    }
}
