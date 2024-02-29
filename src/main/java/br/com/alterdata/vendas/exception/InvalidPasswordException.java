package br.com.alterdata.vendas.exception;

public class InvalidPasswordException extends RuntimeException{

    public InvalidPasswordException(String msg){
        super(msg);
    }
}
