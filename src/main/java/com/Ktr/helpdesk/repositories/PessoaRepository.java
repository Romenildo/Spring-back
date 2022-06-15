package com.Ktr.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Ktr.helpdesk.domain.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer>{
    
}
