package br.com.alterdata.vendas.exception;

public class BusinessException extends RuntimeException {

    public BusinessException(String msg) {
        super(msg);
    }

}
