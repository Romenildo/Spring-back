package com.Ktr.helpdesk.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.br.CPF;

import com.Ktr.helpdesk.domain.enums.Perfil;
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
public abstract class Pessoa implements Serializable {
    private static final long serialVersionUID = 1L;

    /*
     * O id possui a notacao especial de @id, e a geracao de seu valor de forma que seja sequencial ou outras formas
     * para gerar automaticamente o id toda vez que um novo objeto é criado
     */
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    protected Integer id;
    protected String nome;

    @CPF
    @Column(unique = true)
    protected String cpf;
    @Column(unique = true)
    protected String email;
    protected String senha;

    //pesquisar sobre elementCollection
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "PERFIS")
    protected Set<Integer> perfis = new HashSet<>();
    //Set nao permite ter valores repetidos
    //hashset bloqueai a exceção caso o ponteiro aponte para null

    @JsonFormat(pattern = "dd/MM/yyyy")//padrao ao utilizar algum tipo de data
    protected LocalDate dataCriacao = LocalDate.now();
    
    public Pessoa() {
        super();
        //adicionar  um perfil padrao
        addPerfil(Perfil.CLIENTE);
    }

    public Pessoa(Integer id, String nome, String cpf, String email, String senha) {
        super();
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        addPerfil(Perfil.CLIENTE);
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
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
        Pessoa other = (Pessoa) obj;
        if (cpf == null) {
            if (other.cpf != null)
                return false;
        } else if (!cpf.equals(other.cpf))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    

    


}
