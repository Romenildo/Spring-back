package com.Ktr.helpdesk.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Ktr.helpdesk.domain.Pessoa;

/*
 * Classe Repository
 * Ao instanciar uma interface extendendo o JPA< NOME DO DOMAIN(ENITIY), O TIPO DO ID(long, integer,...)>
 * Ele gerara automaticamento ao instanciar a classe, as principais funcoes para manipular o banco de dados como:
 * findbyid, create, update, delete, findAll, ...
 * 
 * Entra varias outras, além de poder iniciar funcoes a mais como findBynome e adicionar os parametros
 */
public interface PessoaRepository extends JpaRepository<Pessoa, Integer>{
    
    //devem possuir esse nome, caso mude para enconrarPorCPF a funcao nao existira
    //é quase como se estivesse reecrevendo a funcao ja pronta no JPA
    Optional<Pessoa> findByCpf(String cpf);
    Optional<Pessoa> findByEmail(String email);
}
