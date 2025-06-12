package org.serratec.h2.grupo2.security.tokenAcesso;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import org.serratec.h2.grupo2.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

//FALTA COLOCAR AS EXCEÇÕES
@Service
public class JwtService {
	
	@Autowired
	private ContaRepository repository;

	//UMA SENHA SECRETA QUE SERÁ USADA PRA CRIAR TOKENS ÚNICOS
	private final String senha_ultra_secreta = "x9oK39sLwR89slZm02asdmqPeZ8dkQP8";
	
	/*BASICAMENTE PEGA A MINHA SENHA ULTRA SECRETA E CONVERTE NUMA KEY
	 * QUE JÁ ESTÁ NUM PADRÃO DE UTILIZAÇÃO CORRETA NA HORA DE CRIAR O
	 * TOKEN.*/
	private Key getKey() {
	    return Keys.hmacShaKeyFor(senha_ultra_secreta.getBytes());
	}
	
	//TOKEN GERADO A PARTIR DO USERDETAILS
	public String gerarToken(UserDetails usuarioLogado) {
	    Map<String, Object> claims = Map.of(
	        "authorities", usuarioLogado.getAuthorities().stream()
	                                     .map(Object::toString)
	                                     .toList()
	    );

	    return Jwts.builder()
	            .setClaims(claims)
	            .setSubject(usuarioLogado.getUsername())
	            .setIssuedAt(new Date(System.currentTimeMillis()))
	            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24h
	            .signWith(getKey())
	            .compact();
	}

    //VERIFICA SE O TOKEN É CORRETO
    public boolean tokenValido(String token) {
        final String email = extrairEmail(token);
        if(repository.existsByEmail(email) && !tokenExpirado(token)) {
        	return true;
        } else { return false; }
    }
    
    //FUNÇÕES IMPORTANTE PRA VALIDAÇÃO DO TOKEN
    
    //FUNÇÃO EXTRAI AS AUTORIZAÇÕES DO TOKEN
    public List<String> extrairAuthorities(String token) {
        Claims claims = extrairTodosClaims(token);
        Object raw = claims.get("authorities");

        if (raw instanceof List<?> list) {
            return list.stream()
                       .map(Object::toString)
                       .toList();}
        return List.of();
    }
    
    //VERIFICA SE O TOKEN ESTÁ EXPIRADO
    public boolean tokenExpirado(String token) {
        Date expiracao = extrairClaim(token, Claims::getExpiration);
        return expiracao.before(new Date());
    }
    
    //FAZ A EXTRAÇÃO DO E-MAIL PELO TOKEN PASSADO
    public String extrairEmail(String token) {
        return extrairClaim(token, Claims::getSubject);
    }
    
    public <T> T extrairClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extrairTodosClaims(token);
        return claimsResolver.apply(claims);
    }
    
    private Claims extrairTodosClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
