package com.Ktr.helpdesk.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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

}
