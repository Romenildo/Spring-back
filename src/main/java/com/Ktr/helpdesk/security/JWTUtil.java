package com.Ktr.helpdesk.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

//CORACAO DA AUTENTICACAO
//CRIAR A GERACAO DA SEQUENCIA DO TOKEN
@Component
public class JWTUtil {
    
    //pegar os valores salvos la no aplication.properties
    @Value("${jwt.expiration}")
    private Long expiration;

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(String email){
        return Jwts.builder()
                    .setSubject(email)//gerar o token a apartir da informacao do email
                    .setExpiration(new Date(System.currentTimeMillis()+expiration))//tempo de expiracao é a hora atual mais 3 minutos
                    .signWith(SignatureAlgorithm.HS512, secret.getBytes())//tipo do algoritmo para fazer o hash e o secredo
                    .compact();
    }
    //final gerar o filtro de autorizacao

    public boolean tokenValido(String token) {
        Claims claims = getClaims(token);
        if(claims != null){
            String username = claims.getSubject();
            Date expirationDate = claims.getExpiration();
            Date now = new Date(System.currentTimeMillis());

            if(username != null && expirationDate != null && now.before(expirationDate)){
                return true;
            }   
        }
        return false;
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            return null;
        }
    }

    //A partir do token tirar o username do claims
    public String getUsername(String token) {
        Claims claims = getClaims(token);
        if(claims != null){
            return claims.getSubject();
        }
        return null;
    }

}
