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
public abstract class DAO {
    public static final int SUCESSO = 0;
    public static final int ERRO_SQL = 1;
    public static final int ERRO_INSERCAO = 2;
    public static final int ERRO_CODIGO = 3;
    public static final int ERRO_SQL_VIOLACAO = 4;
}
