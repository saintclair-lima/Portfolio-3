/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.flordeliz.modelo;

import br.com.flordeliz.utils.Utils;

/**
 *
 * @author saintclair
 */
public class ModeloCalcado {
    private int codigo;
    private String nome; //25
    private String colecao; //25
    private int tamMin;
    private int tamMax;
    private String cor; //15
    private double precoCusto;
    
    public ModeloCalcado(int codigo, String nome, String colecao, int tamMin, int tamMax, String cor, double precoCusto) throws InsercaoException{
        this.setCodigo(codigo);
        this.setNome(nome);
        this.setColecao(colecao);
        this.setTamMin(tamMin);
        this.setTamMax(tamMax);
        this.setCor(cor);
        this.setPrecoCusto(precoCusto);
    }

    /**
     * @return the codigo
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     * @throws br.com.flordeliz.modelo.InsercaoException
     */
    public void setCodigo(int codigo) throws br.com.flordeliz.modelo.InsercaoException {
        if(codigo < 0){
            throw new InsercaoException("Codigo invalido: valor negativo");
        } else {
            this.codigo = codigo;
        }
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     * @throws br.com.flordeliz.modelo.InsercaoException
     */
    public void setNome(String nome) throws InsercaoException {
        if(nome.length() > 25){
            throw new InsercaoException("Nome invalido: mais que 25 caracteres");
        } else {
            this.nome = nome;
        }
    }

    /**
     * @return the colecao
     */
    public String getColecao() {
        return colecao;
    }

    /**
     * @param colecao the colecao to set
     * @throws br.com.flordeliz.modelo.InsercaoException
     */
    public void setColecao(String colecao) throws InsercaoException {
        if(colecao.length() > 25){
            throw new InsercaoException("Colecao invalida: mais que 25 caracteres");
        } else {
            this.colecao = colecao;
        }
    }

    /**
     * @return the tamMin
     */
    public int getTamMin() {
        return tamMin;
    }

    /**
     * @param tamMin the tamMin to set
     * @throws br.com.flordeliz.modelo.InsercaoException
     */
    public void setTamMin(int tamMin) throws InsercaoException {
        if(tamMin < 0){
            throw new InsercaoException("Tamanho invalido: valor negativo");
        } else {
            this.tamMin = tamMin;
        }
    }

    /**
     * @return the tamMax
     */
    public int getTamMax() {
        return tamMax;
    }

    /**
     * @param tamMax the tamMax to set
     * @throws br.com.flordeliz.modelo.InsercaoException
     */
    public void setTamMax(int tamMax) throws InsercaoException {
        if(tamMax < 0){
            throw new InsercaoException("Tamanho invalido: valor negativo");
        } else {
            this.tamMax = tamMax;
        }
    }

    /**
     * @return the cor
     */
    public String getCor() {
        return cor;
    }

    /**
     * @param cor the cor to set
     * @throws br.com.flordeliz.modelo.InsercaoException
     */
    public void setCor(String cor) throws InsercaoException {
        cor = Utils.checaNull(cor);
        
        if(cor.length() > 25){
            throw new InsercaoException("Cor invalida: mais que 15 caracteres");
        } else {
            this.cor = cor;
        }
    }

    /**
     * @return the precoCusto
     */
    public double getPrecoCusto() {
        return precoCusto;
    }

    /**
     * @param precoCusto the precoCusto to set
     * @throws br.com.flordeliz.modelo.InsercaoException
     */
    public void setPrecoCusto(double precoCusto) throws InsercaoException {
        if(precoCusto < 0){
            throw new InsercaoException("Preco invalido: valor negativo");
        } else {
            this.precoCusto = precoCusto;
        }
    }
}
