/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.flordeliz.dao;

import br.com.flordeliz.modelo.Cliente;
import br.com.flordeliz.modelo.EntidadeNulaException;
import br.com.flordeliz.modelo.Entrega;
import br.com.flordeliz.modelo.InsercaoException;
import br.com.flordeliz.modelo.ItemEntrega;
import br.com.flordeliz.modelo.ItemEstoque;
import br.com.flordeliz.modelo.ItemPedido;
import br.com.flordeliz.modelo.ModeloCalcado;
import br.com.flordeliz.modelo.Pedido;
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
public class PedidoDAO extends DAO {
    
    public int inserir(String usuario, String senha, String endereco, Pedido pedido) {
        Connection conexao = ConectaBD.conectarBanco(usuario, senha, endereco);
        Utils.checaNull(conexao, "Connection", Thread.currentThread().getStackTrace()[2].getLineNumber());
        
        try {
            
            String comandoSQL = "BEGIN;";
            PreparedStatement ps = conexao.prepareStatement(comandoSQL);           
            ps.execute();
            
            comandoSQL = "INSERT INTO pedido(pedido_codigo, pedido_desconto, pedido_cliente_codigo) VALUES(NEXTVAL('seq_pedido_codigo'),?,?)";
            ps = conexao.prepareStatement(comandoSQL);
            Utils.checaNull(ps,"PreparedStatement", Thread.currentThread().getStackTrace()[2].getLineNumber());
            
            ps.setFloat(1, pedido.getDesconto());
            ps.setInt(2, pedido.getCliente().getCodigo());            
            ps.execute();
            
            //comandoSQL = "INSERT INTO entrega(entrega_codigo, entrega_status, entrega_pedido_codigo) VALUES(NEXTVAL('seq_entrega_codigo'),?,?)";
            comandoSQL = "INSERT INTO entrega(entrega_codigo, entrega_status, entrega_pedido_codigo) VALUES(NEXTVAL('seq_entrega_codigo'),?, CURRVAL('seq_pedido_codigo'))";
            ps = conexao.prepareStatement(comandoSQL);
            Utils.checaNull(ps,"PreparedStatement", Thread.currentThread().getStackTrace()[2].getLineNumber());
            
            ps.setString(1, pedido.getEntrega().getStatus());
            //ps.setInt(2, pedido.getCodigo());
            ps.execute();
            
            //comandoSQL = "INSERT INTO item_pedido(item_ped_codigo, item_ped_quant, item_ped_pedido_codigo, item_ped_it_est_codigo) VALUES(?,?,?,?)";
            comandoSQL = "INSERT INTO item_pedido(item_ped_codigo, item_ped_quant, item_ped_pedido_codigo, item_ped_it_est_codigo) VALUES(?,?,CURRVAL('seq_pedido_codigo'),?)";
            for (ItemPedido item : pedido.getListaItens()){
                ps = conexao.prepareStatement(comandoSQL);
                Utils.checaNull(ps,"PreparedStatement", Thread.currentThread().getStackTrace()[2].getLineNumber());
                
                ps.setInt(1, item.getCodigo());
                ps.setInt(2, item.getQuantidade());
                //ps.setInt(3, pedido.getCodigo());
                //ps.setInt(4, item.getItemEstoque().getCodigo());
                ps.setInt(3, item.getItemEstoque().getCodigo());
                ps.execute();
            }
            
            //comandoSQL = "INSERT INTO item_entrega(item_ent_codigo, item_ent_entrega_codigo, item_ent_it_est_codigo) VALUES(?,?,?)";
            comandoSQL = "INSERT INTO item_entrega(item_ent_codigo, item_ent_entrega_codigo, item_ent_it_est_codigo) VALUES(?,CURRVAL('seq_entrega_codigo'),?)";
            for (ItemEntrega item : pedido.getEntrega().getListaItens()){
                ps = conexao.prepareStatement(comandoSQL);
                Utils.checaNull(ps,"PreparedStatement", Thread.currentThread().getStackTrace()[2].getLineNumber());
                
                ps.setInt(1, item.getCodigo());
                //ps.setInt(2, pedido.getEntrega().getCodigo());
                //ps.setInt(3, item.getItemEstoque().getCodigo());
                ps.setInt(2, item.getItemEstoque().getCodigo());
                ps.execute();
            }
            
            comandoSQL = "END;";
            ps = conexao.prepareStatement(comandoSQL);
            ps.execute();
            
            conexao.close();
            return this.SUCESSO;
            
        } catch (SQLException e) {
            String comandoSQL = "ROLLBACK;";
            PreparedStatement ps;           
            try {
                ps = conexao.prepareStatement(comandoSQL);
                ps.execute();
                conexao.close();
                System.err.println("ERRO: falha ao inserir Pedido no banco. RollBack realizado." + "\r\n " + e.getMessage());
            } catch (SQLException ex) {
                System.err.println("ERRO: falha ao inserir Pedido no banco. Falha ao executar Rollback." + "\r\n " + e.getMessage());
            }
            return this.ERRO_SQL;
        }
    }
     
    public int excluir(String usuario, String senha, String endereco, int idExclusao) {
        Connection conexao = ConectaBD.conectarBanco(usuario, senha, endereco);
        Utils.checaNull(conexao, "Connection", Thread.currentThread().getStackTrace()[2].getLineNumber());
        
        int resultadoOperacao = 10;
        try {
            
            String comandoSQL = "BEGIN;";
            PreparedStatement ps = conexao.prepareStatement(comandoSQL);           
            ps.execute();
            
            comandoSQL = "SELECT pedido_codigo FROM pedido WHERE pedido_codigo = ?";
            ps = conexao.prepareStatement(comandoSQL);
            ps.setInt(1, idExclusao);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()){
                comandoSQL = "DELETE FROM pedido WHERE pedido_codigo =?";
                /*PreparedStatement*/ ps = conexao.prepareStatement(comandoSQL);
                ps.setInt(1, idExclusao);
                ps.executeUpdate();
                
                comandoSQL = "DELETE FROM entrega WHERE entrega_pedido_codigo =?";
                /*PreparedStatement*/ ps = conexao.prepareStatement(comandoSQL);
                ps.setInt(1, idExclusao);
                ps.executeUpdate();
                
                comandoSQL = "DELETE FROM item_pedido WHERE item_ped_pedido_codigo =?";
                /*PreparedStatement*/ ps = conexao.prepareStatement(comandoSQL);
                ps.setInt(1, idExclusao);
                ps.executeUpdate();
                
                comandoSQL = "DELETE FROM item_entrega USING entrega, pedido WHERE item_ent_entrega_codigo = entrega_codigo AND entrega_pedido_codigo = pedido_codigo AND pedido_codigo = ?";
                /*PreparedStatement*/ ps = conexao.prepareStatement(comandoSQL);
                ps.setInt(1, idExclusao);
                ps.executeUpdate();
            } else {
                resultadoOperacao = this.ERRO_CODIGO;
                System.err.println("ALERTA: Pedido com código " + idExclusao + " não existente.\r\nExclusão não realizada");
            }
            
            comandoSQL = "END;";
            ps = conexao.prepareStatement(comandoSQL);
            ps.execute();
            conexao.close();
            resultadoOperacao = this.SUCESSO;
            return resultadoOperacao;
            
        } catch (SQLException e) {
            
            String comandoSQL = "ROLLBACK;";
            PreparedStatement ps;           
            try {
                ps = conexao.prepareStatement(comandoSQL);
                ps.execute();
                conexao.close();
                System.err.println("ERRO: Falha ao excluir o Pedido. RollBack realizado." + "\r\n " + e.getMessage());
            } catch (SQLException ex) {
                System.err.println("ERRO: Falha ao excluir o Pedido. Falha ao executar Rollback." + "\r\n " + e.getMessage());
            }
            resultadoOperacao = this.ERRO_SQL;
            return resultadoOperacao;
        }
        
        
    }
    
    public void alterar(String usuario, String senha, String endereco, Pedido pedido){
        Connection conexao = ConectaBD.conectarBanco(usuario, senha, endereco);
        Utils.checaNull(conexao, "Connection", Thread.currentThread().getStackTrace()[2].getLineNumber());
        
        try {
            
            String comandoSQL = "BEGIN;";
            PreparedStatement ps = conexao.prepareStatement(comandoSQL);           
            ps.execute();
            
            comandoSQL = "UPDATE pedido SET pedido_codigo = ?, pedido_desconto = ?, pedido_cliente_codigo = ? WHERE pedido_codigo = ?";
            ps = conexao.prepareStatement(comandoSQL);
            Utils.checaNull(ps,"PreparedStatement", Thread.currentThread().getStackTrace()[2].getLineNumber());
            
            ps.setInt(1, pedido.getCodigo());
            ps.setFloat(2, pedido.getDesconto());
            ps.setInt(3, pedido.getCliente().getCodigo());
            ps.setInt(4, pedido.getCodigo());
            ps.execute();
            
            comandoSQL = "UPDATE entrega SET entrega_codigo = ?, entrega_status = ?, entrega_pedido_codigo = ? WHERE entrega_codigo = ?";
            ps = conexao.prepareStatement(comandoSQL);
            Utils.checaNull(ps,"PreparedStatement", Thread.currentThread().getStackTrace()[2].getLineNumber());
            
            ps.setInt(1, pedido.getEntrega().getCodigo());
            ps.setString(2, pedido.getEntrega().getStatus());
            ps.setInt(3, pedido.getCodigo());
            ps.setInt(4, pedido.getEntrega().getCodigo());
            ps.execute();
            
            comandoSQL = "UPDATE item_pedido SET item_ped_codigo = ?, item_ped_quant = ?, item_ped_pedido_codigo = ?, item_ped_it_est_codigo = ? WHERE item_ped_codigo = ?";
            for (ItemPedido item : pedido.getListaItens()){
                ps = conexao.prepareStatement(comandoSQL);
                Utils.checaNull(ps,"PreparedStatement", Thread.currentThread().getStackTrace()[2].getLineNumber());
                
                ps.setInt(1, item.getCodigo());
                ps.setInt(2, item.getQuantidade());
                ps.setInt(3, pedido.getCodigo());
                ps.setInt(4, item.getItemEstoque().getCodigo());
                ps.setInt(5, item.getCodigo());
            }
            
            comandoSQL = "UPDATE item_entrega SET item_ent_codigo = ?, item_ent_entrega_codigo = ?, item_ent_it_est_codigo = ? WHERE item_ent_codigo = ?";
            for (ItemEntrega item : pedido.getEntrega().getListaItens()){
                ps = conexao.prepareStatement(comandoSQL);
                Utils.checaNull(ps,"PreparedStatement", Thread.currentThread().getStackTrace()[2].getLineNumber());
                
                ps.setInt(1, item.getCodigo());
                ps.setInt(2, pedido.getEntrega().getCodigo());
                ps.setInt(3, item.getItemEstoque().getCodigo());
                ps.setInt(4, item.getCodigo());
            }
            
            comandoSQL = "END;";
            ps = conexao.prepareStatement(comandoSQL);
            ps.execute();
            
            conexao.close();
            
        } catch (SQLException e) {
            String comandoSQL = "ROLLBACK;";
            PreparedStatement ps;           
            try {
                ps = conexao.prepareStatement(comandoSQL);
                ps.execute();
                conexao.close();
                System.err.println("ERRO: falha ao atualizar Pedido no banco. RollBack realizado." + "\r\n " + e.getMessage());
            } catch (SQLException ex) {
                System.err.println("ERRO: falha ao atualizar Pedido no banco. Falha ao executar Rollback." + "\r\n " + e.getMessage());
            }
        }
    }
    
    public List<Pedido> consultar(String usuario, String senha, String endereco){
        List<Pedido> listaPedido = new ArrayList<Pedido>();
        Connection conexao = ConectaBD.conectarBanco(usuario, senha, endereco);
        Utils.checaNull(conexao, "Connection", Thread.currentThread().getStackTrace()[2].getLineNumber());
        
        ModeloCalcado modelo;
        ItemEstoque itemEstoque;
        Cliente cliente;
        ItemEntrega itemEntrega;
        Entrega entrega = null;
        ItemPedido itemPedido;
        Pedido pedido = null;
        
        try {
            String comandoSQL = "SELECT "
                                    + "pedido_codigo, pedido_desconto, pedido_cliente_codigo,"
                                    + "item_ped_codigo, item_ped_quant, item_ped_pedido_codigo, item_ped_it_est_codigo,"
                                    + "entrega_codigo, entrega_status, entrega_pedido_codigo,"
                                    + "item_ent_codigo, item_ent_entrega_codigo, item_ent_it_est_codigo,"
                                    + "cliente_codigo, cliente_nome, cliente_loja, cliente_fone, cliente_endereco, cliente_cpf, cliente_cnpj,"
                                    + "item_estoque_codigo, item_estoque_tamanho, item_estoque_quantidade, item_estoque_preco_venda, item_estoque_modelo_codigo,"
                                    + "modelo_codigo, modelo_nome, modelo_colecao, modelo_tam_min, modelo_tam_max, modelo_cor, modelo_preco_custo "
                                + "FROM pedido, item_pedido, entrega, item_entrega, cliente, item_estoque, modelo_calcado "
                                + "WHERE item_ped_pedido_codigo = pedido_codigo "
                                    + "AND entrega_pedido_codigo = pedido_codigo "
                                    + "AND item_ent_entrega_codigo = entrega_codigo "
                                    + "AND pedido_cliente_codigo = cliente_codigo "
                                    + "AND item_ped_it_est_codigo = item_estoque_codigo "
                                    + "AND item_ent_it_est_codigo = item_estoque_codigo "
                                    + "AND item_estoque_modelo_codigo = modelo_codigo "
                                    + "ORDER BY pedido_codigo;";
            
            PreparedStatement ps;
            ps = conexao.prepareStatement(comandoSQL);
            ResultSet rs = ps.executeQuery();
            

            //flags para identificar novo pedido/entrega no loop
            int codigoEntregaAnterior = -1;
            int codigoPedidoAnterior = -1;
            
            while (rs.next()){
                try{
                    cliente = new Cliente(rs.getInt("cliente_codigo"),
                    rs.getString("cliente_nome"),
                    rs.getString("cliente_loja"),
                    rs.getString("cliente_fone"),
                    rs.getString("cliente_endereco"),
                    rs.getString("cliente_cpf"),
                    rs.getString("cliente_cnpj"));
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
                    //"item_ent_codigo, item_ent_entrega_codigo, item_ent_it_est_codigo,"
                    itemEntrega = new ItemEntrega(rs.getInt("item_ent_codigo"),
                                                  rs.getInt("item_ped_quant"),//quantidade, derivada da quantidade de item de pedido
                                                  itemEstoque);
                    
                    //item_ped_codigo, item_ped_quant, item_ped_pedido_codigo, item_ped_it_est_codigo
                    itemPedido = new ItemPedido(rs.getInt("item_ped_codigo"),
                                                rs.getInt("item_ped_quant"),//quantidade, derivada da quantidade de item de pedido
                                                itemEstoque);
                    if (rs.getInt("entrega_codigo") != codigoEntregaAnterior){
                        entrega = new Entrega(rs.getInt("entrega_codigo"),
                                              rs.getString("entrega_status"));
                        codigoEntregaAnterior = rs.getInt("entrega_codigo");
                    }
                    entrega.inserirItem(itemEntrega);
                    if (rs.getInt("pedido_codigo") != codigoPedidoAnterior){
                        
                        pedido = new Pedido(rs.getInt("pedido_codigo"),
                                            rs.getFloat("pedido_desconto"),
                                            cliente);
                    }
                    pedido.inserirItem(itemPedido);
                    pedido.setEntrega(entrega); 
                    
                    if (pedido.getCodigo() != codigoPedidoAnterior){
                        listaPedido.add(pedido);
                        codigoPedidoAnterior = pedido.getCodigo();
                    }
                }catch(SQLException e){
                    System.err.println("ERRO: Falha na consulta por pedido:" + "\r\n" + e.getMessage());
                } catch (InsercaoException e){
                    System.err.println("ERRO: Falha em criacao de estrutura de exibicao de dados do pedido:" + "\r\n" + e.getMessage());
                }
                Utils.checaNull(pedido, "Pedido", Thread.currentThread().getStackTrace()[2].getLineNumber());
            }
            conexao.close();
        } catch (SQLException e){
            System.err.println("Consulta inválida!" + "\nErro : " + e.getMessage());
        }
        
        return listaPedido;
    }
    public Pedido buscar (String usuario, String senha, String endereco, int idBusca) throws EntidadeNulaException{
        Connection conexao = ConectaBD.conectarBanco(usuario, senha, endereco);
        Utils.checaNull(conexao, "Connection", Thread.currentThread().getStackTrace()[2].getLineNumber());
        
        ModeloCalcado modelo;
        ItemEstoque itemEstoque;
        Cliente cliente;
        ItemEntrega itemEntrega;
        Entrega entrega = null;
        ItemPedido itemPedido;
        Pedido pedido = null;
        
        try {
            String comandoSQL = "SELECT "
                                    + "pedido_codigo, pedido_desconto, pedido_cliente_codigo,"
                                    + "item_ped_codigo, item_ped_quant, item_ped_pedido_codigo, item_ped_it_est_codigo,"
                                    + "entrega_codigo, entrega_status, entrega_pedido_codigo,"
                                    + "item_ent_codigo, item_ent_entrega_codigo, item_ent_it_est_codigo,"
                                    + "cliente_codigo, cliente_nome, cliente_loja, cliente_fone, cliente_endereco, cliente_cpf, cliente_cnpj,"
                                    + "item_estoque_codigo, item_estoque_tamanho, item_estoque_quantidade, item_estoque_preco_venda, item_estoque_modelo_codigo,"
                                    + "modelo_codigo, modelo_nome, modelo_colecao, modelo_tam_min, modelo_tam_max, modelo_cor, modelo_preco_custo "
                                + "FROM pedido, item_pedido, entrega, item_entrega, cliente, item_estoque, modelo_calcado "
                                + "WHERE item_ped_pedido_codigo = pedido_codigo "
                                    + "AND entrega_pedido_codigo = pedido_codigo "
                                    + "AND item_ent_entrega_codigo = entrega_codigo "
                                    + "AND pedido_cliente_codigo = cliente_codigo "
                                    + "AND item_ped_it_est_codigo = item_estoque_codigo "
                                    + "AND item_ent_it_est_codigo = item_estoque_codigo "
                                    + "AND item_estoque_modelo_codigo = modelo_codigo "
                                    + "AND pedido_codigo = ?;";
            PreparedStatement ps;
            ps = conexao.prepareStatement(comandoSQL);
            ps.setInt(1, idBusca);
            ResultSet rs = ps.executeQuery();
            
            //flags para identificar novo pedido/entrega no loop
            int codigoEntregaAnterior = -1;
            int codigoPedidoAnterior = -1;
            while (rs.next()){                
                try{
                    cliente = new Cliente(rs.getInt("cliente_codigo"),
                    rs.getString("cliente_nome"),
                    rs.getString("cliente_loja"),
                    rs.getString("cliente_fone"),
                    rs.getString("cliente_endereco"),
                    rs.getString("cliente_cpf"),
                    rs.getString("cliente_cnpj"));
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
                    //"item_ent_codigo, item_ent_entrega_codigo, item_ent_it_est_codigo,"
                    itemEntrega = new ItemEntrega(rs.getInt("item_ent_codigo"),
                                                  rs.getInt("item_ped_quant"),//quantidade, derivada da quantidade de item de pedido
                                                  itemEstoque);
                    
                    //item_ped_codigo, item_ped_quant, item_ped_pedido_codigo, item_ped_it_est_codigo
                    itemPedido = new ItemPedido(rs.getInt("item_ped_codigo"),
                                                rs.getInt("item_ped_quant"),//quantidade, derivada da quantidade de item de pedido
                                                itemEstoque);
                    if (rs.getInt("entrega_codigo") != codigoEntregaAnterior){
                        entrega = new Entrega(rs.getInt("entrega_codigo"),
                                              rs.getString("entrega_status"));
                        codigoEntregaAnterior = rs.getInt("entrega_codigo");
                    }
                    
                    entrega.inserirItem(itemEntrega);
                    
                    if (rs.getInt("pedido_codigo") != codigoPedidoAnterior){
                        pedido = new Pedido(rs.getInt("pedido_codigo"),
                                            rs.getFloat("pedido_desconto"),
                                            cliente);
                        codigoPedidoAnterior = rs.getInt("pedido_codigo");
                    }
                    pedido.inserirItem(itemPedido);
                    pedido.setEntrega(entrega);
                    
                }catch(SQLException e){
                    System.err.println("ERRO: Falha na consulta por pedido:" + "\r\n" + e.getMessage());
                } catch (InsercaoException e){
                    System.err.println("ERRO: Falha em criacao de estrutura de exibicao de dados do pedido:" + "\r\n" + e.getMessage());
                }
            }
        } catch(SQLException e){
            System.err.println("Consulta inválida!" + "\nErro : " + e.getMessage());
        }
        
        Utils.checaNull(conexao, "Connection", Thread.currentThread().getStackTrace()[2].getLineNumber());
        if (pedido == null){
            throw new EntidadeNulaException("Pedido não encontrado no banco de dados");
        }
        return pedido;
    }
    
    /*
    
    */
}
