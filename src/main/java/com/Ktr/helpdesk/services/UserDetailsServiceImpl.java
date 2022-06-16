package com.Ktr.helpdesk.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Ktr.helpdesk.domain.Pessoa;
import com.Ktr.helpdesk.repositories.PessoaRepository;
import com.Ktr.helpdesk.security.UserSS;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    //pegar a funcao de encontrar po email para ver se esta cadastrtado
    @Autowired
    private PessoaRepository pessoaRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Pessoa> user = pessoaRepository.findByEmail(email);
       //se email esta no banco
        if(user.isPresent()){
            return new UserSS(user.get().getId(), user.get().getEmail(), user.get().getSenha(), user.get().getPerfis());
        }
        throw new UsernameNotFoundException(email);

    }
    
}
