package com.Ktr.helpdesk.services.exceptions;

/*
 * DataIntegrityViolationException
 * É a valdiacao caso os parametros enviados pelo usuario entejam errados
 * seja por cpf ja existente, ou id nao existe, formato de valor errado , etc
*/
public class DataIntegrityViolationException extends RuntimeException {

    public DataIntegrityViolationException(String message) {
        super(message);
    }

    public DataIntegrityViolationException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
