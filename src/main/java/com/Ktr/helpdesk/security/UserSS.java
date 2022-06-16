package com.Ktr.helpdesk.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.Ktr.helpdesk.domain.enums.Perfil;

//CONTRATO DO SPRING SECURITY
//é tipo o contrato do user datails do secutiry
public class UserSS implements UserDetails{
    private static final long serialVerionUID = 1L;

    private Integer id;
    private String email;
    private String senha;
    private Collection<? extends GrantedAuthority> Authorities;


    public Integer getId(){
        return id;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    //conta nao esta expirada?
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //conta nao esta fechada?
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //as credencias nao estao expiradas?
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //esta habilitado?
    @Override
    public boolean isEnabled() {
        return true;
    }
    public UserSS(Integer id, String email, String senha, Set<Perfil> perfis) {
        this.id = id;
        this.email = email;
        this.senha = senha;
        Authorities = perfis.stream().map(x -> new SimpleGrantedAuthority(x.getDescricao())).collect(Collectors.toSet());
    }

    
    
}
