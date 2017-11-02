/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.flordeliz.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author saintclair
 */
public class Entrega {
    public static final String ENCERRADO = "ENCERRADO";
    public static final String PENDENTE = "PENDENTE";
    public static final String EM_CURSO = "EM CURSO";
    private int codigo;
    private String status;
    private List <ItemEntrega> listaItens = new ArrayList<ItemEntrega>();
    
    public Entrega (int codigo, String status) throws InsercaoException{
        this.setCodigo(codigo);
        this.setStatus(status);
    }
    
    public Entrega (int codigo, String status, List <ItemEntrega> listaItens) throws InsercaoException{
        this.setCodigo(codigo);
        this.setStatus(status);
        this.setListaItens(listaItens);
    }
    
    public void inserirItem(ItemEntrega itemEntrega) throws InsercaoException {
        boolean jaInserido = false;
        for (ItemEntrega item : this.listaItens){
            if(item.getItemEstoque().getCodigo() == itemEntrega.getItemEstoque().getCodigo()){
                jaInserido = true;
                break;
            }
        }                
        if (jaInserido){
            throw new InsercaoException("ERRO: Item já presente no pedido");
        }else{
            this.listaItens.add(itemEntrega);
            
        }
    }
    
    public void removerItem(int codigo){
        for (ItemEntrega item : this.listaItens){
            if (item.getCodigo() == codigo){
                this.listaItens.remove(item);
                break;
            }
        }
    }
    
    public ItemEntrega getItem(int codigo){
        ItemEntrega itemRetorno = null;
        for (ItemEntrega item : this.listaItens){
            if (item.getCodigo() == codigo){
                itemRetorno = item;
                break;
            }
        }        
        return itemRetorno;
    }
    
        public void atualizarItem(int codigo, ItemEstoque itemEstoque){
        for (ItemEntrega item : this.listaItens){
            if (item.getItemEstoque().getCodigo() == itemEstoque.getCodigo()){
                item.setItemEstoque(itemEstoque);
                break;
            }
        }  
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
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     * @throws java.lang.IllegalArgumentException
     */
    public void setStatus(String status) throws IllegalArgumentException {
        if ((!status.equals(this.EM_CURSO) && !status.equals(this.PENDENTE) && !status.equals(this.ENCERRADO)) || status == null){
            throw new IllegalArgumentException("");
        } else {
            this.status = status;
        }
    }

    /**
     * @return the itenEntrega
     */
    public List <ItemEntrega> getListaItens() {
        return listaItens;
    }

    /**
     * @param itenEntrega the itenEntrega to set
     * @throws br.com.flordeliz.modelo.InsercaoException
     */
    public void setListaItens(List <ItemEntrega> listaItens) throws InsercaoException {
        if (listaItens == null){
            throw new InsercaoException("Item de Entrega nulo.");
        } else {
            this.listaItens = listaItens;
        }
    }
}
