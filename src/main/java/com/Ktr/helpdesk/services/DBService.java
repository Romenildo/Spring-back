package com.Ktr.helpdesk.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.Ktr.helpdesk.domain.Tecnico;
import com.Ktr.helpdesk.repositories.ChamadoRepository;
import com.Ktr.helpdesk.repositories.PessoaRepository;

@Service
public class DBService {
    
    @Autowired
    private ChamadoRepository chamadoRepository;
    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;

    public void instanciaDB(){

        Tecnico tec1 = new Tecnico(null, "Rom", "123.234.123-11","rom@gmail.com",encoder.encode("123"));

        pessoaRepository.saveAll(Arrays.asList(tec1));
    }
}
