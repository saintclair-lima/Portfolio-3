/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.flordeliz.dao;

import br.com.flordeliz.modelo.InsercaoException;
import br.com.flordeliz.modelo.ItemEstoque;
import br.com.flordeliz.modelo.ModeloCalcado;
import br.com.flordeliz.utils.Utils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author saintclair
 */
public class ItemEstoqueDAO {
    public void inserir(String usuario, String senha, String endereco, ItemEstoque itemEstoque) {
        Connection conexao = ConectaBD.conectarBanco(usuario, senha, endereco);
        Utils.checaNull(conexao, "Connection", Thread.currentThread().getStackTrace()[2].getLineNumber());
        
        try {
            String comandoSQL = "INSERT INTO item_estoque(item_estoque_codigo,"
                                                        + "item_estoque_tamanho,"
                                                        + "item_estoque_quantidade,"
                                                        + "item_estoque_preco_venda,"
                                                        + "item_estoque_modelo_codigo) VALUES(NEXTVAL('seq_item_estoque_codigo'),?,?,?,?)";
            PreparedStatement ps = conexao.prepareStatement(comandoSQL);
            Utils.checaNull(ps,"PreparedStatement", Thread.currentThread().getStackTrace()[2].getLineNumber());
            
            ps.setInt(1, itemEstoque.getTamanho());
            ps.setInt(2, itemEstoque.getQuantidade());
            ps.setDouble(3, itemEstoque.getPrecoVenda());
            ps.setInt(4, itemEstoque.getModelo().getCodigo());
            ps.execute();
            conexao.close();
        } catch (SQLException e) {
            System.err.println("ERRO: falha ao inserir item de estoque no banco" + "\r\n " + e.getMessage());
        }
    }
    
    public void alterar(String usuario, String senha, String endereco, ItemEstoque itemEstoque) {
        Connection conexao = ConectaBD.conectarBanco(usuario, senha, endereco);
        Utils.checaNull(conexao, "Connection", Thread.currentThread().getStackTrace()[2].getLineNumber());
        
        try {
            String comandoSQL = "UPDATE item_estoque SET item_estoque_tamanho = ?,"
                                                         + "item_estoque_quantidade = ?,"
                                                         + "item_estoque_preco_venda = ?,"
                                                         + "item_estoque_modelo_codigo = ? WHERE item_estoque_codigo =?";

            PreparedStatement ps = conexao.prepareStatement(comandoSQL);
            ps.setInt(1, itemEstoque.getTamanho());
            ps.setInt(2, itemEstoque.getQuantidade());
            ps.setDouble(3, itemEstoque.getPrecoVenda());
            ps.setInt(4, itemEstoque.getModelo().getCodigo());
            ps.setInt(5, itemEstoque.getCodigo());
            ps.executeUpdate();
            conexao.close();
        } catch (SQLException e) {
            System.err.println("ERRO: Falha ao alterar item de estoque de codigo " + itemEstoque.getCodigo() + "\r\n " + e.getMessage());
        }
    }
    
    public void excluir(String usuario, String senha, String endereco, int idExclusao) {
        Connection conexao = ConectaBD.conectarBanco(usuario, senha, endereco);
        Utils.checaNull(conexao, "Connection", Thread.currentThread().getStackTrace()[2].getLineNumber());
        
        try {
            String comandoSQL = "SELECT item_estoque_codigo FROM item_estoque WHERE item_estoque_codigo = ?";
            PreparedStatement ps = conexao.prepareStatement(comandoSQL);
            ps.setInt(1, idExclusao);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()){
                comandoSQL = "DELETE FROM item_estoque WHERE item_estoque_codigo =?";
                /*PreparedStatement*/ ps = conexao.prepareStatement(comandoSQL);
                ps.setInt(1, idExclusao);
                ps.executeUpdate();
            } else {
                System.err.println("ALERTA: Item de estoque com código " + idExclusao + " não existente.\r\nExclusão não realizada");
            }
            
            conexao.close();
        } catch (SQLException e) {
            System.err.println("Não foi possível excluir o itemde estoque solicitado" + "\nErro : " + e.getMessage());
        }
    
    }
    
    public List<ItemEstoque> consultar (String usuario, String senha, String endereco){
        List<ItemEstoque> listaItemEstoque = new ArrayList<ItemEstoque>();
        Connection conexao = ConectaBD.conectarBanco(usuario, senha, endereco);
        Utils.checaNull(conexao, "Connection", Thread.currentThread().getStackTrace()[2].getLineNumber());
        ModeloCalcado modelo;
        
        try {
            
            String comandoSQL = "SELECT item_estoque_codigo, item_estoque_tamanho, item_estoque_quantidade, item_estoque_preco_venda, item_estoque_modelo_codigo,"
                                + "modelo_codigo, modelo_nome, modelo_colecao, modelo_tam_min, modelo_tam_max, modelo_cor, modelo_preco_custo "
                                + "FROM item_estoque INNER JOIN modelo_calcado ON item_estoque_modelo_codigo = modelo_codigo "
                                + "ORDER BY item_estoque_codigo";
            
            PreparedStatement ps = conexao.prepareStatement(comandoSQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ItemEstoque itemEstoque = null;
                try{
                    modelo = new ModeloCalcado( rs.getInt("modelo_codigo"),
                                                rs.getString("modelo_nome"),
                                                rs.getString("modelo_colecao"),
                                                rs.getInt("modelo_tam_min"),
                                                rs.getInt("modelo_tam_max"),
                                                rs.getString("modelo_cor"),
                                                rs.getDouble("modelo_preco_custo"));
                    
                    itemEstoque = new ItemEstoque(rs.getInt("item_estoque_codigo"),
                                                  rs.getInt("item_estoque_tamanho"),
                                                  rs.getInt("item_estoque_quantidade"),
                                                  rs.getDouble("item_estoque_preco_venda"),
                                                  modelo);
                }catch(SQLException e){
                    System.err.println("ERRO: Falha na consulta por itens de estoque:" + "\r\n" + e.getMessage());
                } catch (InsercaoException e){
                    System.err.println("ERRO: Falha incluir informacao de modelo de calcado no item de estoque:" + "\r\n" + e.getMessage());
                }
                listaItemEstoque.add(itemEstoque);
                Utils.checaNull(itemEstoque, "ItemEstoque", Thread.currentThread().getStackTrace()[2].getLineNumber());
            }
            conexao.close();
        } catch (SQLException e) {
             System.err.println("Consulta inválida!" + "\nErro : " + e.getMessage());
        }
        return listaItemEstoque;
        
    }
    
    public ItemEstoque buscar (String usuario, String senha, String endereco, int idBusca) {
        ItemEstoque itemEstoque = null;
        Connection conexao = ConectaBD.conectarBanco(usuario, senha, endereco);
        Utils.checaNull(conexao, "Connection", Thread.currentThread().getStackTrace()[2].getLineNumber());
        ModeloCalcado modelo;
        
        try {
            String comandoSQL = "SELECT item_estoque_codigo, item_estoque_tamanho, item_estoque_quantidade, item_estoque_preco_venda, item_estoque_modelo_codigo,"
                                + "modelo_codigo, modelo_nome, modelo_colecao, modelo_tam_min, modelo_tam_max, modelo_cor, modelo_preco_custo "
                                + "FROM item_estoque INNER JOIN modelo_calcado ON (item_estoque_modelo_codigo = modelo_codigo) "
                                + "WHERE item_estoque_codigo = ?";
            
            PreparedStatement ps = conexao.prepareStatement(comandoSQL);
            ps.setInt(1, idBusca);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                try{
                    modelo = new ModeloCalcado( rs.getInt("modelo_codigo"),
                                                rs.getString("modelo_nome"),
                                                rs.getString("modelo_colecao"),
                                                rs.getInt("modelo_tam_min"),
                                                rs.getInt("modelo_tam_max"),
                                                rs.getString("modelo_cor"),
                                                rs.getDouble("modelo_preco_custo"));
                    
                    itemEstoque = new ItemEstoque(rs.getInt("item_estoque_codigo"),
                                                  rs.getInt("item_estoque_tamanho"),
                                                  rs.getInt("item_estoque_quantidade"),
                                                  rs.getDouble("item_estoque_preco_venda"),
                                                  modelo);
                }catch(SQLException e){
                    System.err.println("ERRO: Falha na consulta por itens de estoque:" + "\r\n" + e.getMessage());
                } catch (InsercaoException e){
                    System.err.println("ERRO: Falha incluir informacao de modelo de calcado no item de estoque:" + "\r\n" + e.getMessage());
                }
                Utils.checaNull(itemEstoque, "ItemEstoque", Thread.currentThread().getStackTrace()[2].getLineNumber());
            }
            
            conexao.close();
        } catch (SQLException e) {
             System.err.println("Consulta inválida!" + "\nErro : " + e.getMessage());
        }
        return itemEstoque;
    }
}
