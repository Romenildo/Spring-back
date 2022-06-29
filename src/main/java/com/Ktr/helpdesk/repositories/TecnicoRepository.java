package com.Ktr.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Ktr.helpdesk.domain.Tecnico;

/*
 * Classe Repository
 * Ao instanciar uma interface extendendo o JPA< NOME DO DOMAIN(ENITIY), O TIPO DO ID(long, integer,...)>
 * Ele gerara automaticamento ao instanciar a classe, as principais funcoes para manipular o banco de dados como:
 * findbyid, create, update, delete, findAll, ...
 * 
 * Entra varias outras, além de poder iniciar funcoes a mais como findBynome e adicionar os parametros
 */
public interface TecnicoRepository extends JpaRepository<Tecnico, Integer>{
    
}
