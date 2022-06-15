package com.Ktr.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Ktr.helpdesk.domain.Chamado;

public interface ChamadoRepository extends JpaRepository<Chamado, Integer>{
    
}
