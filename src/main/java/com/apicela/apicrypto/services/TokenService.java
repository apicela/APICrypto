package com.apicela.apicrypto.services;


import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import io.jsonwebtoken.*;

import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class TokenService {
  //  @Value("${api.security.token.secret}")
    private String secret = "OSadosj3i4j3sJISAJsiaDHSROKEOREWSI93";
    private JwtParser jwtParser;
    private Key key;
    public TokenService() {
        // Gerando a chave com base no segredo
        this.key = Keys.hmacShaKeyFor(this.secret.getBytes());
        // Usando o parser() que está disponível na versão atual
        this.jwtParser = Jwts.parser().setSigningKey(this.key).build();
    }
    //return a jwt token based on the username
    public String generate(String userName) {
        JwtBuilder builder = Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(15, ChronoUnit.MINUTES)))
                .signWith(key);

        return builder.compact();
    }

    public String getUserName(String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public boolean validate(UserDetails user, String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();
        boolean unexpired = claims.getExpiration().after(Date.from(Instant.now()));
        return unexpired && user.getUsername().equals(claims.getSubject());
    }

    private Instant genExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
