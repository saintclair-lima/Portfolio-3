/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.flordeliz.modelo;

import br.com.flordeliz.utils.Utils;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author saintclair
 */
public class Pedido {
    private int codigo;
    private float desconto;
    private Cliente cliente;
    private List <ItemPedido> listaItens = new ArrayList<ItemPedido>();
    private Entrega entrega;
    private double precoTotalPedidoBruto;
    private double precoTotalPedidoLiquido;
    
    public Pedido (int codigo, float desconto, Cliente cliente){
        this.setCodigo(codigo);
        this.setDesconto(desconto);
        this.setCliente(cliente);
        this.atualizarPrecoTotal();
    }
    
    public Pedido (int codigo, float desconto, Cliente cliente, List <ItemPedido> listaItens){
        this.setCodigo(codigo);
        this.setDesconto(desconto);
        this.setCliente(cliente);
        this.setListaItens(listaItens);
        this.atualizarPrecoTotal();
    }
     
    public Pedido (int codigo, float desconto, Cliente cliente, List <ItemPedido> listaItens, Entrega entrega){
        this.setCodigo(codigo);
        this.setDesconto(desconto);
        this.setCliente(cliente);
        this.setListaItens(listaItens);
        this.setEntrega(entrega);
        this.atualizarPrecoTotal();
    }
    
    private void atualizarPrecoTotal(){
        double precoTotal = 0;
        for (ItemPedido item : this.listaItens){
            precoTotal += item.getPrecoTotalItem();
        }
        this.precoTotalPedidoBruto = precoTotal;
        this.setPrecoTotalPedidoLiquido(this.getPrecoTotalPedidoBruto() * (1 - this.desconto));
    }
    
    public void inserirItem(ItemPedido itemPedido) throws InsercaoException {
        boolean jaInserido = false;
        for (ItemPedido item : this.listaItens){
            if(item.getItemEstoque().getCodigo() == itemPedido.getItemEstoque().getCodigo()){
                jaInserido = true;
                break;
            }
        }                
        if (!jaInserido){
            this.listaItens.add(itemPedido);
        }else{
            throw new InsercaoException("ERRO: Item já presente no pedido");
        }
        
        this.atualizarPrecoTotal();
    }
    
    public void removerItem(int codigo){
        for (ItemPedido item : this.listaItens){
            if (item.getCodigo() == codigo){
                this.listaItens.remove(item);
                break;
            }
        }
        
        this.atualizarPrecoTotal();
    }
    
    public ItemPedido getItem(int codigo){
        ItemPedido itemRetorno = null;
        for (ItemPedido item : this.listaItens){
            if (item.getCodigo() == codigo){
                itemRetorno = item;
                break;
            }
        }        
        return itemRetorno;
    }
    
    public void atualizarItem(int codigo, int quantidade, ItemEstoque itemEstoque){
        for (ItemPedido item : this.listaItens){
            if (item.getCodigo() == itemEstoque.getCodigo()){
                item.setQuantidade(quantidade);
                item.setItemEstoque(itemEstoque);
                break;
            }
        }
        
        this.atualizarPrecoTotal();
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
     * @return the desconto
     */
    public float getDesconto() {
        return desconto;
    }

    /**
     * @param desconto the desconto to set
     */
    public void setDesconto(float desconto) {
        this.desconto = desconto;
        
        this.atualizarPrecoTotal();
    }

    /**
     * @return the listaItens
     */
    public List <ItemPedido> getListaItens() {
        return listaItens;
    }

    /**
     * @param listaItens the listaItens to set
     */
    public void setListaItens(List <ItemPedido> listaItens) {
        this.listaItens = listaItens;
        
        this.atualizarPrecoTotal();
    }

    /**
     * @return the entrega
     */
    public Entrega getEntrega() {
        return entrega;
    }

    /**
     * @param entrega the entrega to set
     */
    public void setEntrega(Entrega entrega) {
        this.entrega = entrega;
    }

    /**
     * @return the cliente
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(Cliente cliente) {
        this.cliente = (Cliente) Utils.checaNull(cliente);
    }

    /**
     * @return the precoTotalPedidoBruto
     */
    public double getPrecoTotalPedidoBruto() {
        return precoTotalPedidoBruto;
    }

    /**
     * @return the precoTotalPedidoLiquido
     */
    public double getPrecoTotalPedidoLiquido() {
        return precoTotalPedidoLiquido;
    }

    /**
     * @param precoTotalPedidoLiquido the precoTotalPedidoLiquido to set
     */
    public void setPrecoTotalPedidoLiquido(double precoTotalPedidoLiquido) {
        this.precoTotalPedidoLiquido = precoTotalPedidoLiquido;
    }
    
    public void print(){
        /*
        this.setCodigo(codigo);
        this.setDesconto(desconto);
        this.setCliente(cliente);
        this.setListaItens(listaItens);
        this.setEntrega(entrega);
        this.atualizarPrecoTotal();
        */
        System.out.println("DESCRICAO DO PEDIDO");
        System.out.println("Codigo: " + this.getCodigo());
        System.out.println("Desconto: " + this.getDesconto());
        System.out.println(this.getCliente() == null);
        System.out.println("Cliente: " + this.getCliente().getNome());
        System.out.println("Codigo Entrega: " + this.getEntrega().getCodigo());
        System.out.println(this.getEntrega().getListaItens().size() + "ITENS PARA ENTREGA");
        for (ItemEntrega item : this.getEntrega().getListaItens()){
            System.out.println(item.getItemEstoque().getModelo().getNome());
        }
        
        System.out.println(this.getListaItens().size() + "ITENS NO PEDIDO");
        for (ItemPedido item : this.getListaItens()){
            System.out.println(item.getItemEstoque().getModelo().getNome() + item.getItemEstoque().getTamanho());
        }
    }
}
