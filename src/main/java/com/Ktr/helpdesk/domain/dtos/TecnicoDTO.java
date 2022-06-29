package com.Ktr.helpdesk.domain.dtos;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import com.Ktr.helpdesk.domain.Tecnico;
import com.Ktr.helpdesk.domain.enums.Perfil;
import com.fasterxml.jackson.annotation.JsonFormat;

/*
 * A classe DTO
 * É uma boa pratica de programação o qual é uma camada a mais de controle
 * entre a entidade e o controller, ao invez de pegar a entidade pega somente seus valores
 * deixando assim a aplicacao mais segura e confiavel
 * 
 * Não precisa criar um dto com todos os valores da entidade somente o necessario para ser retornado ou manipulado
 */
public class TecnicoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    protected Integer id;
    //NOTNULL é do Vlidations para validar os valores antes mesmo da requisicao
    @NotNull(message = "O campo NOME é requerido")
    protected String nome;
    @NotNull(message = "O campo CPF é requerido")
    protected String cpf;
    @NotNull(message = "O campo E-MAIL é requerido")
    protected String email;
    @NotNull(message = "O campo SENHA é requerido")
    protected String senha;
    protected Set<Integer> perfis = new HashSet<>();
    
    @JsonFormat(pattern = "dd/MM/yyyy")
    protected LocalDate dataCriacao = LocalDate.now();

    public TecnicoDTO() {
        super();
    }

    //transforma um objeto em objeto DTO
    public TecnicoDTO(Tecnico obj) {
        super();
        this.id = obj.getId();
        this.nome = obj.getNome();
        this.cpf = obj.getCpf();
        this.email = obj.getEmail();
        this.senha = obj.getSenha();
        this.perfis = obj.getPerfis().stream().map(x -> x.getCodigo()).collect(Collectors.toSet());
        this.dataCriacao = obj.getDataCriacao();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Set<Perfil> getPerfis() {
        return perfis.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
    }

    public void addPerfil(Perfil perfil) {
        this.perfis.add(perfil.getCodigo());
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    

    
    
}
