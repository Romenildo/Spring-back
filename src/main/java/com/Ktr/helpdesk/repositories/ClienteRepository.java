package com.Ktr.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Ktr.helpdesk.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{
    
}
