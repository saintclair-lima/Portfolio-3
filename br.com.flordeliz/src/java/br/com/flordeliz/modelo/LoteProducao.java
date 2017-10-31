/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.flordeliz.modelo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author saintclair
 */
public class LoteProducao {
    private int codigo;
    private GregorianCalendar data;
    private int quantidade;
    private ItemEstoque itemEstoque;
    
    public LoteProducao(int codigo, String data, int quantidade, ItemEstoque itemEstoque) throws InsercaoException, ParseException{
        this.setCodigo(codigo);
        this.setDataString(data);
        this.setQuantidade(quantidade);
        this.setItemEstoque(itemEstoque);
    }
    
    public LoteProducao(int codigo, GregorianCalendar data, int quantidade, ItemEstoque itemEstoque) throws InsercaoException, ParseException{
        this.setCodigo(codigo);
        this.setData(data);
        this.setQuantidade(quantidade);
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
        if(codigo < 0){
            throw new InsercaoException("Codigo invalido: valor negativo");
        } else {
            this.codigo = codigo;
        }
    }

    /**
     * @return the data
     */
    public GregorianCalendar getData() {
        return data;
    }
    
    public String getDataString() {
        Date dataString = (Date) this.data.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(dataString);
    }
    
    public Date getDataAsDate(){
        return new Date(this.getData().getTimeInMillis());
    }
    
    public java.sql.Date getDataAsSQLDate(){
        return new java.sql.Date(this.getData().getTimeInMillis());
    }
    
    public String getDataStringFormatada() {
        Date dataString = (Date) this.data.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(dataString);
    }

    /**
     * @param data the data to set
     */
    public void setData(GregorianCalendar data) {
        this.data = data;
    }
    
    public void setDataString(String dataString) throws ParseException {        
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = (Date) df.parse(dataString);
        this.data = new GregorianCalendar();
        this.data.setTime(date);
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
     * @return the itemEstoque
     */
    public ItemEstoque getItemEstoque() {
        return itemEstoque;
    }

    /**
     * @param itemEstoque the itemEstoque to set
     */
    public void setItemEstoque(ItemEstoque itemEstoque) {
        this.itemEstoque = itemEstoque;
    }
}
