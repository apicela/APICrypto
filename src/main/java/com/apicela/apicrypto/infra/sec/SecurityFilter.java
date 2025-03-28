package com.apicela.apicrypto.infra.sec;

import com.apicela.apicrypto.repositories.UserRepository;
import com.apicela.apicrypto.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class SecurityFilter implements WebFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    private final ServerSecurityContextRepository securityContextRepository = new WebSessionServerSecurityContextRepository();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        // Recupera o token do header Authorization
        var token = recoverToken(exchange.getRequest().getHeaders().getFirst("Authorization"));

        System.out.println("Token : " + token);
        // Verifica se o token não é nulo
        if (token != null) {
            return tokenService.validateToken(token)
                    .flatMap(login -> userRepository.findByMail(login))  // Encontra o usuário no banco
                    .flatMap(userDetails -> {
                        // Cria o objeto de autenticação com os detalhes do usuário
                        var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        // Retorna um Mono com o SecurityContext
                        return Mono.just(new SecurityContextImpl(authentication));
                    })
                    .flatMap(securityContext -> {
                        // Aplica o SecurityContext para o fluxo reativo
                        return chain.filter(exchange)
                                .contextWrite(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(securityContext)));
                    })
                    .switchIfEmpty(chain.filter(exchange));  // Se o usuário não for encontrado, continua sem autenticação
        }

        // Caso não haja token, continua sem autenticação
        return chain.filter(exchange);
    }

    private String recoverToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        return authHeader.replace("Bearer ", "");
    }

}