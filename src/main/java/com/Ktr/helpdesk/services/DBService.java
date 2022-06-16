package com.Ktr.helpdesk.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.Ktr.helpdesk.domain.Chamado;
import com.Ktr.helpdesk.domain.Cliente;
import com.Ktr.helpdesk.domain.Tecnico;
import com.Ktr.helpdesk.domain.enums.Perfil;
import com.Ktr.helpdesk.domain.enums.Prioridade;
import com.Ktr.helpdesk.domain.enums.Status;
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
        tec1.addPerfil(Perfil.ADMIN);

        Cliente cli1 = new Cliente(null, "joao", "1821921831911", "joao@gmail.com", encoder.encode("12345"));

        Chamado c1 = new Chamado(null, Prioridade.MEDIA,Status.ANDAMENTO, "Chamado 01","Primeiro Chamado",tec1, cli1);

        pessoaRepository.saveAll(Arrays.asList(tec1, cli1));
        chamadoRepository.saveAll(Arrays.asList(c1));
    }
}
