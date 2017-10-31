/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.flordeliz.dao;
import br.com.flordeliz.modelo.EntidadeNulaException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import br.com.flordeliz.modelo.LoteProducao;
import br.com.flordeliz.modelo.InsercaoException;
import br.com.flordeliz.modelo.ItemEstoque;
import br.com.flordeliz.modelo.ModeloCalcado;
import br.com.flordeliz.utils.Utils;
import java.text.ParseException;

/**
 *
 * @author saintclair
 */
public class LoteProducaoDAO extends DAO{
    public int inserir(String usuario, String senha, String endereco, LoteProducao lote) {
        Connection conexao = ConectaBD.conectarBanco(usuario, senha, endereco);
        Utils.checaNull(conexao, "Connection", Thread.currentThread().getStackTrace()[2].getLineNumber());
        
        try {
            String comandoSQL = "INSERT INTO lote_producao(lote_codigo, lote_data, lote_tamanho, lote_quantidade, lote_item_est_codigo) VALUES(NEXTVAL('seq_lote_producao_codigo'),?,?,?,?)";
            PreparedStatement ps = conexao.prepareStatement(comandoSQL);
            Utils.checaNull(ps,"PreparedStatement", Thread.currentThread().getStackTrace()[2].getLineNumber());
            
            ps.setDate(1, lote.getDataAsSQLDate());
            ps.setInt(2, lote.getItemEstoque().getTamanho());
            ps.setInt(3, lote.getQuantidade());
            ps.setInt(4, lote.getItemEstoque().getCodigo());
            ps.execute();
            conexao.close();
            return this.SUCESSO;
        } catch (SQLException e) {
            System.err.println("ERRO: falha ao inserir lote no banco" + "\r\n " + e.getMessage());
            return this.ERRO_SQL;
        }
    }
    
    public void alterar(String usuario, String senha, String endereco, LoteProducao lote) {
        Connection conexao = ConectaBD.conectarBanco(usuario, senha, endereco);
        Utils.checaNull(conexao, "Connection", Thread.currentThread().getStackTrace()[2].getLineNumber());
        
        try {
            String comandoSQL = "UPDATE lote_producao SET lote_data = ?, lote_tamanho = ?, lote_quantidade = ?, lote_item_est_codigo = ? WHERE lote_codigo =?";

            PreparedStatement ps = conexao.prepareStatement(comandoSQL);
            ps.setDate(1, lote.getDataAsSQLDate());
            ps.setInt(2, lote.getItemEstoque().getTamanho());
            ps.setInt(3, lote.getQuantidade());
            ps.setInt(4, lote.getItemEstoque().getCodigo());
            ps.setInt(5, lote.getCodigo());
            ps.executeUpdate();
            conexao.close();
        } catch (SQLException e) {
            System.err.println("ERRO: Falha ao alterar lote de codigo " + lote.getCodigo() + "\r\n " + e.getMessage());
        }
    }
    
    public int excluir(String usuario, String senha, String endereco, int idExclusao) {
        Connection conexao = ConectaBD.conectarBanco(usuario, senha, endereco);
        Utils.checaNull(conexao, "Connection", Thread.currentThread().getStackTrace()[2].getLineNumber());
        int resultadoOperacao = 0;
        try {
            String comandoSQL = "SELECT lote_codigo FROM lote_producao WHERE lote_codigo = ?";
            PreparedStatement ps = conexao.prepareStatement(comandoSQL);
            ps.setInt(1, idExclusao);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()){
                comandoSQL = "DELETE FROM lote_producao WHERE lote_codigo =?";
                /*PreparedStatement*/ ps = conexao.prepareStatement(comandoSQL);
                ps.setInt(1, idExclusao);
                ps.executeUpdate();
            } else {
                System.err.println("ALERTA: Lote com código " + idExclusao + " não existente.\r\nExclusão não realizada");
                resultadoOperacao = LoteProducaoDAO.ERRO_CODIGO;
            }
            
            conexao.close();
            
            return resultadoOperacao = LoteProducaoDAO.SUCESSO;
        } catch (SQLException e) {
            System.err.println("Não foi possível excluir o lote" + "\nErro : " + e.getMessage());
            resultadoOperacao = LoteProducaoDAO.ERRO_SQL;
            return resultadoOperacao;
        }
    
    }
    
    public List<LoteProducao> consultar (String usuario, String senha, String endereco) {
        List<LoteProducao> listaLote = new ArrayList<LoteProducao>();
        Connection conexao = ConectaBD.conectarBanco(usuario, senha, endereco);
        Utils.checaNull(conexao, "Connection", Thread.currentThread().getStackTrace()[2].getLineNumber());
        
        try {
            String comandoSQL = "SELECT lote_codigo,"
                                + "lote_data, lote_tamanho, lote_quantidade, lote_item_est_codigo,"
                                + "item_estoque_codigo, item_estoque_tamanho, item_estoque_quantidade, item_estoque_preco_venda, item_estoque_modelo_codigo,"
                                + "modelo_codigo, modelo_nome, modelo_colecao, modelo_tam_min, modelo_tam_max, modelo_cor, modelo_preco_custo "
                                + "FROM lote_producao, item_estoque, modelo_calcado "
                                + "WHERE lote_item_est_codigo = item_estoque_codigo and item_estoque_modelo_codigo = modelo_codigo "
                                + "ORDER BY lote_codigo";
            
            PreparedStatement ps = conexao.prepareStatement(comandoSQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LoteProducao lote = null;
                try{
                    ModeloCalcado modelo = new ModeloCalcado(rs.getInt("modelo_codigo"),
                                                             rs.getString("modelo_nome"),
                                                             rs.getString("modelo_colecao"),
                                                             rs.getInt("modelo_tam_min"),
                                                             rs.getInt("modelo_tam_max"),
                                                             rs.getString("modelo_cor"),
                                                             rs.getDouble("modelo_preco_custo"));
                    
                    ItemEstoque itemEstoque = new ItemEstoque(rs.getInt("item_estoque_codigo"),
                                                              rs.getInt("item_estoque_tamanho"),
                                                              rs.getInt("item_estoque_quantidade"),
                                                              rs.getDouble("item_estoque_preco_venda"),
                                                              modelo);
                    
                    lote = new LoteProducao(rs.getInt("lote_codigo"),
                                            rs.getString("lote_data"),
                                            //rs.getInt("lote_tamanho"),
                                            rs.getInt("lote_quantidade"),
                                            itemEstoque);
                }catch(SQLException e){
                    System.err.println("ERRO: Falha na consulta por lotes:" + "\r\n" + e.getMessage());
                }catch(InsercaoException e){
                    System.err.println("ERRO: Falha incluir informacao de item de estoque no lote:" + "\r\n" + e.getMessage());
                }catch (ParseException e) {
                    System.err.println("ERRO: data com formato incorreto registrada no banco:" + "\r\n" + e.getMessage());
                }
                listaLote.add(lote);
                Utils.checaNull(lote, "LoteProducao", Thread.currentThread().getStackTrace()[2].getLineNumber());
            }
            conexao.close();
        } catch (SQLException e) {
             System.err.println("Consulta inválida!" + "\nErro : " + e.getMessage());
        }
        return listaLote;
    }
    
    public LoteProducao buscar (String usuario, String senha, String endereco, int idBusca) throws EntidadeNulaException {
        LoteProducao lote = null;
        Connection conexao = ConectaBD.conectarBanco(usuario, senha, endereco);
        Utils.checaNull(conexao, "Connection", Thread.currentThread().getStackTrace()[2].getLineNumber());
        
        try {
            String comandoSQL = "SELECT lote_codigo,"
                    + "lote_data, lote_tamanho, lote_quantidade, lote_item_est_codigo,"
                    + "item_estoque_codigo, item_estoque_tamanho, item_estoque_quantidade, item_estoque_preco_venda, item_estoque_modelo_codigo,"
                    + "modelo_codigo, modelo_nome, modelo_colecao, modelo_tam_min, modelo_tam_max, modelo_cor, modelo_preco_custo "
                    + "FROM lote_producao, item_estoque, modelo_calcado "
                    + "WHERE lote_item_est_codigo = item_estoque_codigo and item_estoque_modelo_codigo = modelo_codigo "
                    + "AND lote_codigo = ?;";
            
            PreparedStatement ps = conexao.prepareStatement(comandoSQL);
            ps.setInt(1, idBusca);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                try{
                    ModeloCalcado modelo = new ModeloCalcado(rs.getInt("modelo_codigo"),
                                                             rs.getString("modelo_nome"),
                                                             rs.getString("modelo_colecao"),
                                                             rs.getInt("modelo_tam_min"),
                                                             rs.getInt("modelo_tam_max"),
                                                             rs.getString("modelo_cor"),
                                                             rs.getDouble("modelo_preco_custo"));
                    
                    ItemEstoque itemEstoque = new ItemEstoque(rs.getInt("item_estoque_codigo"),
                                                              rs.getInt("item_estoque_tamanho"),
                                                              rs.getInt("item_estoque_quantidade"),
                                                              rs.getDouble("item_estoque_preco_venda"),
                                                              modelo);
                    
                    lote = new LoteProducao(rs.getInt("lote_codigo"),
                                            rs.getString("lote_data"),
                                            //rs.getInt("lote_tamanho"),
                                            rs.getInt("lote_quantidade"),
                                            itemEstoque);
                }catch(SQLException e){
                    System.err.println("ERRO: Falha na consulta por lotes:" + "\r\n" + e.getMessage());
                }catch(InsercaoException e){
                    System.err.println("ERRO: Falha incluir informacao de item de estoque no lote:" + "\r\n" + e.getMessage());
                }catch (ParseException e) {
                    System.err.println("ERRO: data com formato incorreto registrada no banco:" + "\r\n" + e.getMessage());
                }
                
                Utils.checaNull(lote, "LoteProducao", Thread.currentThread().getStackTrace()[2].getLineNumber());
            }
            conexao.close();
        } catch (SQLException e) {
             System.err.println("Consulta inválida!" + "\nErro : " + e.getMessage());
        }
        Utils.checaNull(lote, "Lote", Thread.currentThread().getStackTrace()[2].getLineNumber());
        if (lote==null){
            throw new EntidadeNulaException("Lote não encontrado no banco de dados");
        }
        return lote;
        
    }
}
