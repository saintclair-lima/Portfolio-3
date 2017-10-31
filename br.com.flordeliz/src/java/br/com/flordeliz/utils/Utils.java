/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.flordeliz.utils;

/**
 *
 * @author saintclair
 */
public class Utils {
    public static String checaNull(String string){
        if (string == null){
            return "";
        } else {
            return string;
        }
    }
    
    public static Object checaNull(Object objeto){
        if (objeto == null){
            System.err.println("ALERTA: Objeto com valor nulo.");
        }
        
        return objeto;
    }
    
    public static void checaNull(Object entidade, String objeto, int linha){
        if (entidade == null){
            System.err.println("ALERTA: " + objeto + " com valor nulo. Classe: ClienteDAO, linha: " + linha);
        }
    }
}
