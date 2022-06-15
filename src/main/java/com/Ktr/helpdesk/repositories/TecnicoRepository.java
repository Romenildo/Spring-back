package com.Ktr.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Ktr.helpdesk.domain.Tecnico;

public interface TecnicoRepository extends JpaRepository<Tecnico, Integer>{
    
}
