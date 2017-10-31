package br.com.flordeliz.modelo;

public class InsercaoException extends Exception{
    public InsercaoException() { super(); }
    public InsercaoException(String message) { super(message); }
    public InsercaoException(String message, Throwable cause) { super(message, cause); }
    //public InsercaoException(Throwable cause) { super(cause); }
}
