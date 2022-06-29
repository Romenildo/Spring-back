package com.Ktr.helpdesk.services.exceptions;

/*
 * ObjectNotFoundException
 * Caso o objeto nao tenha sido enconrado no abnco de dados
 * Seja ele buscado por id ou nome, cpf , etc
 */
public class ObjectNotFoundException extends RuntimeException {

    public ObjectNotFoundException(String message) {
        super(message);
    }

    public ObjectNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
