package com.Ktr.helpdesk.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.Ktr.helpdesk.domain.enums.Perfil;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Tecnico extends Pessoa{
    private static final long serialVersionUID = 1L;

    @JsonIgnore//caso der esse de serializacao, a lista de chamadas vai ficar chamando os tecnicos
    //ele entra em loop infinito o jsonIgnore faz com que ele nao precise chamar essa instancia
    @OneToMany(mappedBy = "tecnico")
    private List<Chamado> chamados = new ArrayList<>();

    public Tecnico() {
        super();
        addPerfil(Perfil.TECNICO);
    }

    public Tecnico(Integer id, String nome, String cpf, String email, String senha) {
        super(id, nome, cpf, email, senha);
        addPerfil(Perfil.TECNICO);
    }

    public List<Chamado> getChamados() {
        return chamados;
    }

    public void setChamados(List<Chamado> chamados) {
        this.chamados = chamados;
    }

    
}
