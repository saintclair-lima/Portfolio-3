/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.flordeliz.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import br.com.flordeliz.modelo.ModeloCalcado;
import br.com.flordeliz.modelo.InsercaoException;

/**
 *
 * @author saintclair
 */
public class ModeloCalcadoDAO {
    public void inserir(String usuario, String senha, String endereco, ModeloCalcado modelo) {
        Connection conexao = ConectaBD.conectarBanco(usuario, senha, endereco);
        checaNull(conexao, "Connection", Thread.currentThread().getStackTrace()[2].getLineNumber());
        
        try {
            String comandoSQL = "INSERT INTO modelo_calcado(modelo_codigo, modelo_nome, modelo_colecao, modelo_tam_min, modelo_tam_max, modelo_cor, modelo_preco_custo) VALUES(NEXTVAL('seq_modelo_calcado_codigo'),?,?,?,?,?,?)";
            PreparedStatement ps = conexao.prepareStatement(comandoSQL);
            checaNull(ps,"PreparedStatement", Thread.currentThread().getStackTrace()[2].getLineNumber());
            
            ps.setString(1, modelo.getNome());
            ps.setString(2, modelo.getColecao());
            ps.setInt(3, modelo.getTamMin());
            ps.setInt(4, modelo.getTamMax());
            ps.setString(5, modelo.getCor());
            ps.setDouble(6, modelo.getPrecoCusto());
            ps.execute();
            conexao.close();
        } catch (SQLException e) {
            System.err.println("ERRO: falha ao inserir Modelo de Calcado no banco" + "\r\n " + e.getMessage());
        }
    }

    public void alterar(String usuario, String senha, String endereco, ModeloCalcado modelo) {
        Connection conexao = ConectaBD.conectarBanco(usuario, senha, endereco);
        checaNull(conexao, "Connection", Thread.currentThread().getStackTrace()[2].getLineNumber());
        
        try {
            String comandoSQL = "UPDATE modelo_calcado SET modelo_codigo = ?, modelo_nome = ?, modelo_colecao = ?, modelo_tam_min = ?, modelo_tam_max = ?, modelo_cor = ?, modelo_preco_custo = ? WHERE modelo_codigo =?";

            PreparedStatement ps = conexao.prepareStatement(comandoSQL);
            ps.setString(1, modelo.getNome());
            ps.setString(2, modelo.getColecao());
            ps.setInt(3, modelo.getTamMin());
            ps.setInt(4, modelo.getTamMax());
            ps.setString(5, modelo.getCor());
            ps.setDouble(6, modelo.getPrecoCusto());
            ps.setInt(7, modelo.getCodigo());
            ps.execute();
            ps.executeUpdate();
            conexao.close();
        } catch (SQLException e) {
            System.err.println("ERRO: Falha ao alterar cliente de codigo " + modelo.getCodigo() + "\r\n " + e.getMessage());
        }
    }

    public void excluir(String usuario, String senha, String endereco, int idExclusao) {
        Connection conexao = ConectaBD.conectarBanco(usuario, senha, endereco);
        checaNull(conexao, "Connection", Thread.currentThread().getStackTrace()[2].getLineNumber());
        
        try {
            String comandoSQL = "SELECT modelo_codigo FROM modelo_calcado WHERE modelo_codigo = ?";
            PreparedStatement ps = conexao.prepareStatement(comandoSQL);
            ps.setInt(1, idExclusao);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()){
                comandoSQL = "DELETE FROM modelo_calcado WHERE modelo_codigo =?";
                /*PreparedStatement*/ ps = conexao.prepareStatement(comandoSQL);
                ps.setInt(1, idExclusao);
                ps.executeUpdate();
            } else {
                System.err.println("ALERTA: ModeloCalcado com código " + idExclusao + " não existente.\r\nExclusão não realizada");
            }
            
            conexao.close();
        } catch (SQLException e) {
            System.err.println("Não foi possível excluir o ModeloCalcado" + "\nErro : " + e.getMessage());
        }
    }

    public List<ModeloCalcado> consultar(String usuario, String senha, String endereco) {
        List<ModeloCalcado> listaModelo = new ArrayList<ModeloCalcado>();
        Connection conexao = ConectaBD.conectarBanco(usuario, senha, endereco);
        checaNull(conexao, "Connection", Thread.currentThread().getStackTrace()[2].getLineNumber());
        
        try {
            String comandoSQL = "SELECT modelo_codigo, modelo_nome, modelo_colecao, modelo_tam_min, modelo_tam_max, modelo_cor, modelo_preco_custo FROM modelo_calcado";
            PreparedStatement ps = conexao.prepareStatement(comandoSQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ModeloCalcado modelo = null;
                try{
                    modelo = new ModeloCalcado(rs.getInt("modelo_codigo"),
                                               rs.getString("modelo_nome"),
                                               rs.getString("modelo_colecao"),
                                               rs.getInt("modelo_tam_min"),
                                               rs.getInt("modelo_tam_max"),
                                               rs.getString("modelo_cor"),
                                               rs.getDouble("modelo_preco_custo"));
                }catch(InsercaoException e){
                    System.err.println("ERRO: Falha na consulta por modelos de calçado:" + "\r\n" + e.getMessage());
                }
                listaModelo.add(modelo);
                checaNull(modelo, "ModeloCalcado", Thread.currentThread().getStackTrace()[2].getLineNumber());
            }
            
            conexao.close();
        } catch (SQLException e) {
             System.err.println("Consulta inválida!" + "\nErro : " + e.getMessage());
        }
        return listaModelo;
    }
    
    public ModeloCalcado buscar(String usuario, String senha, String endereco, int idBusca) {
        ModeloCalcado modelo = null;
        Connection conexao = ConectaBD.conectarBanco(usuario, senha, endereco);
        checaNull(conexao, "Connection", Thread.currentThread().getStackTrace()[2].getLineNumber());
        
        try {
            String comandoSQL = "SELECT modelo_codigo, modelo_nome, modelo_colecao, modelo_tam_min, modelo_tam_max, modelo_cor, modelo_preco_custo FROM modelo_calcado WHERE modelo_codigo = ?";
            PreparedStatement ps = conexao.prepareStatement(comandoSQL);
            ps.setInt(1, idBusca);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                try{
                    modelo = new ModeloCalcado(rs.getInt("modelo_codigo"),
                                               rs.getString("modelo_nome"),
                                               rs.getString("modelo_colecao"),
                                               rs.getInt("modelo_tam_min"),
                                               rs.getInt("modelo_tam_max"),
                                               rs.getString("modelo_cor"),
                                               rs.getDouble("modelo_preco_custo"));
                }catch(InsercaoException e){
                    System.err.println("ERRO: Falha na busca por modelo de calçado:" + "\r\n" + e.getMessage());
                }
                checaNull(modelo, "ModeloCalcado", Thread.currentThread().getStackTrace()[2].getLineNumber());
            }
            
            conexao.close();
        } catch (SQLException e) {
             System.err.println("Consulta inválida!" + "\nErro : " + e.getMessage());
        }
        return modelo;
    }
    
    private void checaNull(Object entidade, String objeto, int linha){
        if (entidade == null){
            System.err.println("ALERTA: " + objeto + " com valor nulo. Classe: ModeloDAO, linha: " + linha);
        }
    }
}
