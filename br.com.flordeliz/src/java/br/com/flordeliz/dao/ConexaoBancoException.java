/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.flordeliz.dao;
/**
 *
 * @author saintclair
 */
public class ConexaoBancoException extends Exception{
    public ConexaoBancoException() { super(); }
    public ConexaoBancoException(String message) { super(message); }
    public ConexaoBancoException(String message, Throwable cause) { super(message, cause); }
    
}
