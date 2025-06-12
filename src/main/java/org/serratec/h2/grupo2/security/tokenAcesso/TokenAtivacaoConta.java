package org.serratec.h2.grupo2.security.tokenAcesso;

import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Optional;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenAtivacaoConta {

    private static final String SECRET_KEY = "28474NJ4JNIF9483948934J3J39R3R39U";

    private Key getKey() {
        return new SecretKeySpec(SECRET_KEY.getBytes(), SignatureAlgorithm.HS256.getJcaName());
    }


    public String gerarToken(LocalDateTime dataHoraGeracao) {
        Instant instant = dataHoraGeracao.toInstant(ZoneOffset.UTC);

        return Jwts.builder()
                .claim("horaGeracao", instant.toEpochMilli())
                .setIssuedAt(new Date())
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    public Optional<LocalDateTime> extrairHoraDoToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            Long epochMillis = claims.get("horaGeracao", Long.class);
            return Optional.of(LocalDateTime.ofInstant(Instant.ofEpochMilli(epochMillis), ZoneOffset.UTC));
        } catch (Exception e) {
            return Optional.empty(); 
        }
    }


    public boolean tokenValido(String token) {
        Optional<LocalDateTime> horaGerada = extrairHoraDoToken(token);
        return horaGerada
                .map(hora -> Duration.between(hora, LocalDateTime.now(ZoneOffset.UTC)).toMinutes() < 120)
                .orElse(false);
    }
}