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
public class ItemPedido {
    private int codigo;
    private int quantidade;
    private ItemEstoque itemEstoque;
    private double precoTotalItem = 0;
    
    public ItemPedido(int codigo, int quantidade, ItemEstoque itemEstoque){
        this.setCodigo(codigo);
        this.quantidade = quantidade;
        this.setItemEstoque(itemEstoque);
        this.inicializarPrecoTotal(quantidade, itemEstoque.getPrecoVenda());
    }
    
    private void inicializarPrecoTotal(int quantidade, double precoVendaItem){
        this.precoTotalItem = quantidade * precoVendaItem;
    }
    private void atualizarPrecoTotal(){
        this.precoTotalItem = this.quantidade * this.itemEstoque.getPrecoVenda();
    }
    /**
     * @return the codigo
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the quantidade
     */
    public int getQuantidade() {
        return quantidade;
    }

    /**
     * @param quantidade the quantidade to set
     */
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
        this.atualizarPrecoTotal();
    }

    /**
     * @return the itemEstoque
     */
    public ItemEstoque getItemEstoque() {
        return itemEstoque;
    }

    /**
     * @param itemEstoque the itemEstoque to set
     */
    public void setItemEstoque(ItemEstoque itemEstoque) {
        this.itemEstoque = (ItemEstoque) Utils.checaNull(itemEstoque);
        this.atualizarPrecoTotal();
    }

    /**
     * @return the precoTotalItem
     */
    public double getPrecoTotalItem() {
        return precoTotalItem;
    }
}
