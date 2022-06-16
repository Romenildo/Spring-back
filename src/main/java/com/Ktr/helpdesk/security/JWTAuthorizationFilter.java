package com.Ktr.helpdesk.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JWTAuthorizationFilter  extends BasicAuthenticationFilter{

    private JWTUtil jwtUtil;
    private UserDetailsService userDetailsService;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, UserDetailsService userDetailsService) {
        super(authenticationManager);

        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        //pega o campo authorizacao passado no header que é o beare tokenaasaisanu293232j
        String header = request.getHeader("Authorization");
        if(header != null && header.startsWith("Bearer ")){
            //salva o token da autenticacao sem o bearer com a subsstring
            UsernamePasswordAuthenticationToken authToken = getAuthentication(header.substring(7));
            
            if(authToken != null){
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        chain.doFilter(request, response);

    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token){
        
        if(jwtUtil.tokenValido(token)){
            //transforma o token no username novamente
            String username = jwtUtil.getUsername(token);
            //faz o login com o usuario pos transformado do token
            UserDetails details = userDetailsService.loadUserByUsername(username);
            //retorna o token autenticado com usuario, senha e as autoridades do usuario
            return new UsernamePasswordAuthenticationToken(details.getUsername(), null, details.getAuthorities() );
        }
        return null;
    }
    
}
