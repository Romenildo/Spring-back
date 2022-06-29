package com.Ktr.helpdesk.domain;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.Ktr.helpdesk.domain.enums.Prioridade;
import com.Ktr.helpdesk.domain.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;

/*
 * As classes de Entity, são as entidades do sistema, como aluno, cliente, professor, projetos, turmar, objetos
 * 
 * O Serializable é recomendado para o controle da transmissao dos dados e controle, é obrigatorio
 * ao implementar a classe colocar o serialVersionUID, porem ficara somente na classe nao deve possuir getter ou setter
 * 
 * Os atributos referentes a classe todas que irao ser salvas no banco de dados deve possuir a notacao @Column
 * para gerar a coluna no banco, elas podem ser @Column(name = "cpf"), ou simples @Column e ele pegara o nome da variavel
 * 
 * 
 * 
 OBS: OS GETTERS E SETTERS PODEM SER SUBSTITUIDOS COM A UTILIZACAO DO LOMBOOK COM A ANOTACAO @Data
 */
@Entity
public class Chamado implements Serializable {
    private static final long serialVersionUID = 1L;
     /*
     * O id possui a notacao especial de @id, e a geracao de seu valor de forma que seja sequencial ou outras formas
     * para gerar automaticamente o id toda vez que um novo objeto é criado
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //Padrao ao utilizar datas
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataAbertura = LocalDate.now();
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataFechamento;

    private Prioridade prioridade;
    private Status status;
    private String titulo;
    private String observacoes;

    //Relacionamentos
    /*
     * Muitos para um Um tecnico ou ciente pode ter varios chamados,
     *  porem um chamado so pode ter um recnico e um cliente, então eles possui o tecnico e o cliente 
     * referente ao id deles para ligar e acessar o tecnico pelo  chamado
     */
    @ManyToOne
    @JoinColumn(name = "tecnico_id")
    private Tecnico tecnico;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    

    public Chamado() {
        super();
    }


    public Chamado(Integer id, Prioridade prioridade, Status status,
            String titulo, String observacoes, Tecnico tecnico, Cliente cliente) {
        this.id = id;
       
        this.prioridade = prioridade;
        this.status = status;
        this.titulo = titulo;
        this.observacoes = observacoes;
        this.tecnico = tecnico;
        this.cliente = cliente;
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
    public Prioridade getPrioridade() {
        return prioridade;
    }
    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
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
    public Tecnico getTecnico() {
        return tecnico;
    }
    public void setTecnico(Tecnico tecnico) {
        this.tecnico = tecnico;
    }
    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Chamado other = (Chamado) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }


}
