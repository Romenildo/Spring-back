package com.Ktr.helpdesk.domain.enums;

/*
 * Os enums sao atributos que podem ter diferentes valores é mais recomendado criar um num
 * e nele para setar os valores referente como numeros
 * 1 - Aberto   2 - Andamento  3 - encerrado
 * Para acessar o valor do Status é so procurar por toEnum(1), e ira retornar o valor do 1 que é aberto
 */
public enum Status {
   ABERTO(0,"ABERTO"), ANDAMENTO(1, "ANDAMENTO"), ENCERRADO(2, "ENCERRADO");

    private Integer codigo;
    private String descricao;
    
    private Status(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    //transformar o codigo na string
    public static Status toEnum(Integer cod){
        if(cod == null){
            return null;
        }

        for(Status x : Status.values()){
            if(cod.equals(x.getCodigo())){
                return x;
            }
        }

        throw new IllegalArgumentException("Status invalido");
    }

    
}
