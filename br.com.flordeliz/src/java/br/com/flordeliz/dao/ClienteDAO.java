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
import br.com.flordeliz.modelo.Cliente;
import br.com.flordeliz.modelo.EntidadeNulaException;
import br.com.flordeliz.modelo.InsercaoException;
import br.com.flordeliz.utils.Utils;

/**
 *
 * @author saintclair
 */
public class ClienteDAO extends DAO{
    
    public int inserir(String usuario, String senha, String endereco, Cliente cliente) {
        Connection conexao = ConectaBD.conectarBanco(usuario, senha, endereco);
        Utils.checaNull(conexao, "Connection", Thread.currentThread().getStackTrace()[2].getLineNumber());
        
        try {
            String comandoSQL = "INSERT INTO cliente (cliente_codigo, cliente_nome, cliente_loja, cliente_fone, cliente_endereco, cliente_cpf, cliente_cnpj) VALUES(NEXTVAL('seq_cliente_codigo'),?,?,?,?,?,?)";
            PreparedStatement ps = conexao.prepareStatement(comandoSQL);
            Utils.checaNull(ps,"PreparedStatement", Thread.currentThread().getStackTrace()[2].getLineNumber());
            
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getLoja());
            ps.setString(3, cliente.getFone());
            ps.setString(4, cliente.getEndereco());
            ps.setString(5, cliente.getCpf());
            ps.setString(6, cliente.getCnpj());
            ps.execute();
            conexao.close();
            return this.SUCESSO;
        } catch (SQLException e) {
            System.err.println("ERRO: falha ao inserir cliente no banco" + "\r\n " + e.getMessage());
            return this.ERRO_SQL;
        }
    }

    public int alterar(String usuario, String senha, String endereco, Cliente cliente) {
        Connection conexao = ConectaBD.conectarBanco(usuario, senha, endereco);
        Utils.checaNull(conexao, "Connection", Thread.currentThread().getStackTrace()[2].getLineNumber());
        
        try {
            String comandoSQL = "UPDATE cliente SET cliente_nome = ?, cliente_loja = ?, cliente_fone = ?, cliente_endereco = ?, cliente_cpf = ?, cliente_cnpj = ? WHERE cliente_codigo =?";

            PreparedStatement ps = conexao.prepareStatement(comandoSQL);
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getLoja());
            ps.setString(3, cliente.getFone());
            ps.setString(4, cliente.getEndereco());
            ps.setString(5, cliente.getCpf());
            ps.setString(6, cliente.getCnpj());
            ps.setInt(7, cliente.getCodigo());
            ps.executeUpdate();
            conexao.close();
            
            return this.SUCESSO;
        } catch (org.postgresql.util.PSQLException e){
            System.err.println("ERRO: Falha ao alterar cliente de codigo " + cliente.getCodigo() + ". Violação de valor único\r\n " + e.getMessage());
            return this.ERRO_SQL;
        } catch (SQLException e) {
            System.err.println("ERRO: Falha ao alterar cliente de codigo " + cliente.getCodigo() + "\r\n " + e.getMessage());
            return this.ERRO_SQL;
        }
    }

    public int excluir(String usuario, String senha, String endereco, int idExclusao) {
        Connection conexao = ConectaBD.conectarBanco(usuario, senha, endereco);
        Utils.checaNull(conexao, "Connection", Thread.currentThread().getStackTrace()[2].getLineNumber());
        int resultadoOperacao = 0;
        try {
            String comandoSQL = "SELECT cliente_codigo FROM cliente WHERE cliente_codigo = ?";
            PreparedStatement ps = conexao.prepareStatement(comandoSQL);
            ps.setInt(1, idExclusao);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()){
                comandoSQL = "DELETE FROM cliente WHERE cliente_codigo =?";
                /*PreparedStatement*/ ps = conexao.prepareStatement(comandoSQL);
                ps.setInt(1, idExclusao);
                ps.executeUpdate();
            } else {
                System.err.println("ALERTA: Cliente com código " + idExclusao + " não existente.\r\nExclusão não realizada");
                resultadoOperacao = ClienteDAO.ERRO_CODIGO;
            }
            
            conexao.close();
            return resultadoOperacao = ClienteDAO.SUCESSO;
        } catch (SQLException e) {
            System.err.println("Não foi possível excluir o Cliente" + "\nErro : " + e.getMessage());
            resultadoOperacao = ClienteDAO.ERRO_SQL;
            return resultadoOperacao;
        }
    }

    public List<Cliente> consultar(String usuario, String senha, String endereco) {
        List<Cliente> listaCliente = new ArrayList<Cliente>();
        Connection conexao = ConectaBD.conectarBanco(usuario, senha, endereco);
        Utils.checaNull(conexao, "Connection", Thread.currentThread().getStackTrace()[2].getLineNumber());
        
        try {
            String comandoSQL = "SELECT cliente_codigo, cliente_nome, cliente_loja, cliente_fone, cliente_endereco, cliente_cpf, cliente_cnpj FROM cliente ORDER BY cliente_codigo";
            PreparedStatement ps = conexao.prepareStatement(comandoSQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Cliente cliente = null;
                try{
                    cliente = new Cliente(rs.getInt("cliente_codigo"),
                                                  rs.getString("cliente_nome"),
                                                  rs.getString("cliente_loja"),
                                                  rs.getString("cliente_fone"),
                                                  rs.getString("cliente_endereco"),
                                                  rs.getString("cliente_cpf"),
                                                  rs.getString("cliente_cnpj"));
                }catch(InsercaoException e){
                    System.err.println("ERRO: Falha na consulta por clientes:" + "\r\n" + e.getMessage());
                }
                listaCliente.add(cliente);
                Utils.checaNull(cliente, "Cliente", Thread.currentThread().getStackTrace()[2].getLineNumber());
            }
            
            conexao.close();
        } catch (SQLException e) {
             System.err.println("Consulta inválida!" + "\nErro : " + e.getMessage());
        }
        return listaCliente;
    }
    
    public Cliente buscar(String usuario, String senha, String endereco, int idBusca) throws EntidadeNulaException {
        Connection conexao = ConectaBD.conectarBanco(usuario, senha, endereco);
        Utils.checaNull(conexao, "Connection", Thread.currentThread().getStackTrace()[2].getLineNumber());
        Cliente cliente = null;
        
        try {
            String comandoSQL = "SELECT cliente_codigo, cliente_nome, cliente_loja, cliente_fone, cliente_endereco, cliente_cpf, cliente_cnpj FROM cliente WHERE cliente_codigo = ?";
            PreparedStatement ps = conexao.prepareStatement(comandoSQL);
            ps.setInt(1, idBusca);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                try{
                    cliente = new Cliente(rs.getInt("cliente_codigo"),
                                                  rs.getString("cliente_nome"),
                                                  rs.getString("cliente_loja"),
                                                  rs.getString("cliente_fone"),
                                                  rs.getString("cliente_endereco"),
                                                  rs.getString("cliente_cpf"),
                                                  rs.getString("cliente_cnpj"));
                }catch(InsercaoException e){
                    System.err.println("ERRO: Falha na busca por cliente:" + "\r\n" + e.getMessage());
                }
                Utils.checaNull(cliente, "Cliente", Thread.currentThread().getStackTrace()[2].getLineNumber());
            }
            
            conexao.close();
        } catch (SQLException e) {
             System.err.println("Consulta inválida!" + "\nErro : " + e.getMessage());
        }
        Utils.checaNull(cliente, "Cliente", Thread.currentThread().getStackTrace()[2].getLineNumber());
        if (cliente==null){
            throw new EntidadeNulaException("Cliente não encontrado no banco de dados");
        }
        return cliente;
    }
}
