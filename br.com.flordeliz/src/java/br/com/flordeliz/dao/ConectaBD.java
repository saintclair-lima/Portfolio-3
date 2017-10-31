/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.flordeliz.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectaBD {

    public static Connection conectarBanco(String usuario, String senha, String endereco){
        try {
            // Classe referente ao Driver ao banco de dados.
            Class.forName("org.postgresql.Driver");
            //Criar atributo para receber url.
            String url = "jdbc:postgresql://" + endereco;
            return DriverManager.getConnection(url, usuario, senha);
        } catch(ClassNotFoundException e){
          System.err.println("Está faltando a classe (Driver) do banco!" + e.getMessage());  
        }        
        catch (SQLException e) {
            System.err.println("Erro ao tentar conectar!" + e);
        }
        return null;
    }
}
