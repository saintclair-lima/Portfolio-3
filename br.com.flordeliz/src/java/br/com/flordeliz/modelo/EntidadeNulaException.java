/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.flordeliz.modelo;

/**
 *
 * @author saintclair
 */
public class EntidadeNulaException extends Exception{
    public EntidadeNulaException() { super(); }
    public EntidadeNulaException(String message) { super(message); }
    public EntidadeNulaException(String message, Throwable cause) { super(message, cause); }
}
