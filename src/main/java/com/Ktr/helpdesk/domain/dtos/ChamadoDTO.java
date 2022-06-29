package com.Ktr.helpdesk.domain.dtos;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import com.Ktr.helpdesk.domain.Chamado;
import com.fasterxml.jackson.annotation.JsonFormat;

/*
 * A classe DTO
 * É uma boa pratica de programação o qual é uma camada a mais de controle
 * entre a entidade e o controller, ao invez de pegar a entidade pega somente seus valores
 * deixando assim a aplicacao mais segura e confiavel
 * 
 * Não precisa criar um dto com todos os valores da entidade somente o necessario para ser retornado ou manipulado
 */

public class ChamadoDTO implements Serializable{
    private static final long serialVersionUID = 1L;
    
   private Integer id;
   //NOTNULL é do Vlidations para validar os valores antes mesmo da requisicao
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataAbertura = LocalDate.now();
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataFechamento;
    @NotNull(message = "O campo PRIORIDADE é requerido")
    private Integer prioridade;
    @NotNull(message = "O campo STATUS é requerido")
    private Integer status;
    @NotNull(message = "O campo TIRULO é requerido")
    private String titulo;
    @NotNull(message = "O campo OBSERVACOES é requerido")
    private String observacoes;

   //so precisa guardar os id deles, so fazer uma busca no banco para enconrar o tecnico e cliente
    private Integer tecnico;
    private Integer cliente;
    //mas mesmo assim ainda o torna ineficiente por isso é recomendado ja guardar os nome deles
    private String nomeTecnico;
    private String nomeCliente;

    public ChamadoDTO() {
        super();
    }

    //transforma um objeto em objeto DTO
    public ChamadoDTO(Chamado obj) {
        this.id = obj.getId();
        this.dataAbertura = obj.getDataAbertura();
        this.dataFechamento = obj.getDataFechamento();
        this.prioridade = obj.getPrioridade().getCodigo();
        this.status = obj.getStatus().getCodigo();
        this.titulo = obj.getTitulo();
        this.observacoes = obj.getObservacoes();
        this.tecnico = obj.getTecnico().getId();
        this.cliente = obj.getCliente().getId();
        this.nomeTecnico = obj.getTecnico().getNome();
        this.nomeCliente = obj.getCliente().getNome();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(LocalDate dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public LocalDate getDataFechamento() {
        return dataFechamento;
    }

    public void setDataFechamento(LocalDate dataFechamento) {
        this.dataFechamento = dataFechamento;
    }

    public Integer getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Integer prioridade) {
        this.prioridade = prioridade;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Integer getTecnico() {
        return tecnico;
    }

    public void setTecnico(Integer tecnico) {
        this.tecnico = tecnico;
    }

    public Integer getCliente() {
        return cliente;
    }

    public void setCliente(Integer cliente) {
        this.cliente = cliente;
    }

    public String getNomeTecnico() {
        return nomeTecnico;
    }

    public void setNomeTecnico(String nomeTecnico) {
        this.nomeTecnico = nomeTecnico;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    
    

    
}