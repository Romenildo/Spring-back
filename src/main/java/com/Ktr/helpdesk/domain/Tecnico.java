package com.Ktr.helpdesk.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.Ktr.helpdesk.domain.dtos.TecnicoDTO;
import com.Ktr.helpdesk.domain.enums.Perfil;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Tecnico extends Pessoa{
    private static final long serialVersionUID = 1L;
    /*
     * JSON IGNORE
     * caso der erro de serializacao, a lista de chamadas vai ficar chamando os tecnicos
     * ele entra em loop infinito o jsonIgnore faz com que ele nao precise chamar essa instancia
     * 
     * É essencial ao mexer com array denro de um objeto
     */
    @JsonIgnore
    @OneToMany(mappedBy = "tecnico")//deve possuir varios chamados
    private List<Chamado> chamados = new ArrayList<>();

    public Tecnico() {
        super();
        addPerfil(Perfil.TECNICO);
    }

    public Tecnico(Integer id, String nome, String cpf, String email, String senha) {
        super(id, nome, cpf, email, senha);
        addPerfil(Perfil.TECNICO);
    }

    //CRIAR UM TECNICO ATRAVE DE UM TECNICO DTO
    public Tecnico(TecnicoDTO obj) {
        super();
        this.id = obj.getId();
        this.nome = obj.getNome();
        this.cpf = obj.getCpf();
        this.email = obj.getEmail();
        this.senha = obj.getSenha();
        this.perfis = obj.getPerfis().stream().map(x -> x.getCodigo()).collect(Collectors.toSet());
        this.dataCriacao = obj.getDataCriacao();
        addPerfil(Perfil.TECNICO);
    }

    public List<Chamado> getChamados() {
        return chamados;
    }

    public void setChamados(List<Chamado> chamados) {
        this.chamados = chamados;
    }

    
}
