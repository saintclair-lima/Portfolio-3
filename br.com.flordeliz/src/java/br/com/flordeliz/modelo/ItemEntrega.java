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
public class ItemEntrega {
    private int codigo;
    private ItemEstoque itemEstoque;
    
    public ItemEntrega(int codigo, int quantidade, ItemEstoque itemEstoque) throws InsercaoException{
        this.setCodigo(codigo);
        this.setItemEstoque(itemEstoque);
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
        if (codigo < 0){
            throw new InsercaoException("Código Inválido: valor negativo");
        } else {
            this.codigo = codigo;
        }
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
    }
}
