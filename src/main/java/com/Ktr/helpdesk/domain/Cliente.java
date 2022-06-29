package com.Ktr.helpdesk.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.Ktr.helpdesk.domain.dtos.ClienteDTO;
import com.Ktr.helpdesk.domain.enums.Perfil;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class Cliente extends Pessoa{
    private static final long serialVersionUID = 1L;
    
    /*
     * JSON IGNORE
     * caso der erro de serializacao, a lista de chamadas vai ficar chamando os tecnicos
     * ele entra em loop infinito o jsonIgnore faz com que ele nao precise chamar essa instancia
     * 
     * É essencial ao mexer com array denro de um objeto
     */
    @JsonIgnore
    @OneToMany(mappedBy = "cliente")//deve possuir varios chamados
    private List<Chamado> chamados = new ArrayList<>();

    public Cliente() {
        super();
        addPerfil(Perfil.CLIENTE);
    }

    public Cliente(Integer id, String nome, String cpf, String email, String senha) {
        super(id, nome, cpf, email, senha);
        addPerfil(Perfil.CLIENTE);
    }

    //CRIAR UM CLIENTE ATRAVES DE UM CLIENTE DTO
    public Cliente(ClienteDTO obj) {
        super();
        this.id = obj.getId();
        this.nome = obj.getNome();
        this.cpf = obj.getCpf();
        this.email = obj.getEmail();
        this.senha = obj.getSenha();
        this.perfis = obj.getPerfis().stream().map(x -> x.getCodigo()).collect(Collectors.toSet());
        this.dataCriacao = obj.getDataCriacao();
        addPerfil(Perfil.CLIENTE);
    }

    public List<Chamado> getChamados() {
        return chamados;
    }

    public void setChamados(List<Chamado> chamados) {
        this.chamados = chamados;
    }

    

    
    
}
