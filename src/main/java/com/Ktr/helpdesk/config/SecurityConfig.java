package com.Ktr.helpdesk.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.Ktr.helpdesk.security.JWTAithenticationFilter;
import com.Ktr.helpdesk.security.JWTUtil;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    //lista de links referentes ao h2
    private static final String[] PUBLIC_MATCHES = {"/h2-console/**"};

    @Autowired
    private Environment env;//permitir ou nao o h2 para banco de testes
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       if(Arrays.asList(env.getActiveProfiles()).contains("test")){
        http.headers().frameOptions().disable();
       }
       
        http.cors().and().csrf().disable();

       //adicionar os filtros
       http.addFilter(new JWTAithenticationFilter(authenticationManager(), jwtUtil));

        http.authorizeRequests()//autorize as requisicoes de
            .antMatchers(PUBLIC_MATCHES).permitAll()//permitir todod que tem no link da lista
            .anyRequest().authenticated();//para qualquer outra requisição eu quero que ela esteja antenticada

        //garantir que nao ai criar uma sessao do usuario 
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

    //confiuracao do filtro
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }


    //configurar o cors para receber multiplas requisicoes
    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        //adicionar as permisoes do cors e umas amais dos methods post, get...
        CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
        configuration.setAllowedMethods(Arrays.asList("POST","GET","PUT","DELETE","OPTIONS"));

        //aadicionar para todas as requisicoes em todos os url /** */
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
