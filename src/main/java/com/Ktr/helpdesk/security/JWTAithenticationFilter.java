package com.Ktr.helpdesk.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.Ktr.helpdesk.domain.dtos.CredenciaisDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

//Ao extender essa classe o spring entende que ela deve ser interceptada antes da requisicao POST
public class JWTAithenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private JWTUtil jwtUtil;


    //cosntrutor
    public JWTAithenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        super();
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    //tentativa de autenticacao
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            //cria uma credencial (email e senha), e pega esses valores do pedido da request passada no http
            CredenciaisDTO creds = new ObjectMapper().readValue(request.getInputStream(), CredenciaisDTO.class);

            //cria a autenticacao com email, senha e autenticacoes(array)
            UsernamePasswordAuthenticationToken authenticationToken =
                                      new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getSenha(), new ArrayList<>());

            //verifica essas autenticacoes com a interface que autentifica esses dados 
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            return authentication;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //caso der sucesso na autenticacao
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
    
            //authResult é o resultado da authenticcacao e ja pega o email autenticado
            String username = ((UserSS) authResult.getPrincipal()).getUsername();
            String token = jwtUtil.generateToken(username);//cria o token com o email
            //retorno do response deve mandar se autorizo e o token
            response.setHeader("access-control-expose-headers", "Authorization");
            response.setHeader("Authorization", "Bearer "+ token);//deve retornar o bearer antes do token
        }

    //caso nao der sucesso na autenticacao
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        
        response.setStatus(401);
        response.setContentType("application/json");
        response.getWriter().append(json());

    }

    private CharSequence json(){
        long date = new Date().getTime();
        return "{"
                + "\"timestamp\": " + date + ", "
                + "\"status\": 401, "
                + "\"error\": \"Não autorizado\", "
                + "\"message\": \"Email ou senha inválidos\" , "
                + "\"path\": \"/login\"}";
    }
    
    
}
