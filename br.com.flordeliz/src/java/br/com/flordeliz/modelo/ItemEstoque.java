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
public class ItemEstoque {
    private int codigo;
    private int tamanho;
    private int quantidade;
    private double precoVenda;
    private ModeloCalcado modelo;
    
    public ItemEstoque(int codigo, int tamanho, int quantidade, double precoVenda, ModeloCalcado modelo) throws InsercaoException{
        this.setCodigo(codigo);
        this.setTamanho(tamanho);
        this.setQuantidade(quantidade);
        this.setPrecoVenda(precoVenda);
        this.setModelo(modelo);
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
    public void setCodigo(int codigo) throws InsercaoException {
        if(codigo < 0){
            throw new InsercaoException("Codigo invalido: valor negativo");
        } else {
            this.codigo = codigo;
        }
    }

    /**
     * @return the tamanho
     */
    public int getTamanho() {
        return tamanho;
    }

    /**
     * @param tamanho the tamanho to set
     * @throws br.com.flordeliz.modelo.InsercaoException
     */
    public void setTamanho(int tamanho) throws InsercaoException {
        if(tamanho < 0){
            throw new InsercaoException("Tamanho invalido: valor negativo");
        } else {
            this.tamanho = tamanho;
        }
    }

    /**
     * @return the quantidade
     */
    public int getQuantidade() {
        return quantidade;
    }

    /**
     * @param quantidade the quantidade to set
     * @throws br.com.flordeliz.modelo.InsercaoException
     */
    public void setQuantidade(int quantidade) throws InsercaoException {
        if(quantidade < 0){
            throw new InsercaoException("Quantidade invalida: valor negativo");
        } else {
            this.quantidade = quantidade;
        }
    }

    /**
     * @return the precoVenda
     */
    public double getPrecoVenda() {
        return precoVenda;
    }

    /**
     * @param precoVenda the precoVenda to set
     * @throws br.com.flordeliz.modelo.InsercaoException
     */
    public void setPrecoVenda(double precoVenda) throws InsercaoException {
        if(precoVenda < 0){
            throw new InsercaoException("Preco invalido: valor negativo");
        } else {
            this.precoVenda = precoVenda;
        }
    }

        /**
     * @return the modelo
     */
    public ModeloCalcado getModelo() {
        return modelo;
    }

    /**
     * @param modelo the modelo to set
     */
    public void setModelo(ModeloCalcado modelo) {
        this.modelo = (ModeloCalcado) Utils.checaNull(modelo);
    }
}
