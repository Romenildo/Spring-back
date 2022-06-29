package com.Ktr.helpdesk.domain.enums;
/*
 * Os enums sao atributos que podem ter diferentes valores é mais recomendado criar um num
 * e nele para setar os valores referente como numeros
 * 1 - baixa   2 - media  3 - alta
 * Para acessar o valor do Prioridade é so procurar por toEnum(1), e ira retornar o valor do 1 que é baixa
 */
public enum Prioridade {
   BAIXA(0,"BAIXA"), MEDIA(1, "MEDIA"), ALTA(2, "ALTA");

    private Integer codigo;
    private String descricao;
    
    private Prioridade(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    //transformar o codigo para o string
    public static Prioridade toEnum(Integer cod){
        if(cod == null){
            return null;
        }

        for(Prioridade x : Prioridade.values()){
            if(cod.equals(x.getCodigo())){
                return x;
            }
        }

        throw new IllegalArgumentException("Prioridade invalida");
    }

    
}
