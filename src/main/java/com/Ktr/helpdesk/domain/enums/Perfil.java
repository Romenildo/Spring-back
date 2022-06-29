package com.Ktr.helpdesk.domain.enums;


/*
 * Os enums sao atributos que podem ter diferentes valores é mais recomendado criar um num
 * e nele para setar os valores referente como numeros
 * 1 - admin   2 - cliente  3 - tecnico
 * Para acessar o valor do perfil é so procurar por toEnum(1), e ira retornar o valor do 1 que é admin
 */
public enum Perfil {
   ADMIN(0,"ROLE_ADMIN"), CLIENTE(1, "ROLE_CLIENTE"), TECNICO(2, "ROLE_TECNICO");

    private Integer codigo;
    private String descricao;
    
    private Perfil(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    //transformar o codigo para a string
    public static Perfil toEnum(Integer cod){
        if(cod == null){
            return null;
        }

        for(Perfil x : Perfil.values()){
            if(cod.equals(x.getCodigo())){
                return x;
            }
        }

        throw new IllegalArgumentException("Perfil invalido");
    }

    
}
