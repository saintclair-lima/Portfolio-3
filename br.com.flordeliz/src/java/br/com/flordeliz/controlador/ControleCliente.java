/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.flordeliz.controlador;

import br.com.flordeliz.dao.ClienteDAO;
import br.com.flordeliz.modelo.Cliente;
import br.com.flordeliz.modelo.EntidadeNulaException;
import br.com.flordeliz.modelo.InsercaoException;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author saintclair
 */
public class ControleCliente extends HttpServlet implements InterfaceControle{
    public static final int LISTAR_CLIENTES = 4;
    public static final int DETALHAR_CLIENTE = 5;
    public static final int ATUALIZAR_CLIENTE = 6;
    public static final int INSERIR_CLIENTE = 7;
    public static final int BUSCAR_CLIENTE = 8;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String acao = request.getParameter("acao");
        int resultadoOperacao = 20;
        if (acao==null) acao = "";
        if (acao.equals("incluir")){
            resultadoOperacao = this.INSERIR_CLIENTE;


        } else if (acao.equals("inserir")){
            resultadoOperacao = inserirCliente(request, response);


        } else if (acao.equals("alterar")){
            resultadoOperacao = alterarCliente(request, response);


        }else if (acao.equals("atualizar")){
            try {
                Cliente cliente = buscarCliente(request);
                request.setAttribute("cliente", cliente);
                resultadoOperacao = this.ATUALIZAR_CLIENTE;
            } catch (EntidadeNulaException ex) {
                resultadoOperacao = ClienteDAO.ERRO_CODIGO;
            }


        } else if (acao.equals("excluir")){
            resultadoOperacao = excluirCliente(request, response);


        } else if (acao.equals("consultar")){
            List<Cliente> lista = consultarClientes();
            request.setAttribute("listaClientes", lista);
            resultadoOperacao = this.LISTAR_CLIENTES;


        } else if (acao.equals("buscar")){
            resultadoOperacao = this.BUSCAR_CLIENTE;


        } else if (acao.equals("detalhar")){
            try {
                Cliente cliente = buscarCliente(request);
                request.setAttribute("cliente", cliente);
                resultadoOperacao = this.DETALHAR_CLIENTE;
            } catch (EntidadeNulaException ex) {
                resultadoOperacao = ClienteDAO.ERRO_CODIGO;
            }
        }

        RequestDispatcher rd;
        request.setAttribute("title","Cliente - " + acao);
        switch (resultadoOperacao){
            case INSERIR_CLIENTE:
                rd = request.getRequestDispatcher("inserirCliente.jsp");
                break;
            case ClienteDAO.SUCESSO:
                String processadas = " processadas ";
                if (acao.equals("inserir")){
                    processadas = " inseridas ";
                } else if (acao.equals("excluir")){
                    processadas = " excluídas ";
                } else {
                    processadas = " alteradas ";
                }
                request.setAttribute("conteudo","<h1>Informações sobre o cliente" + processadas + "com sucesso</h1>");
                rd = request.getRequestDispatcher("/resultadoOperacao.jsp");
                break;
            case ClienteDAO.ERRO_SQL:
                request.setAttribute("conteudo","<h1>Falha ao Acessar o Banco</h1>"
                                   + "<p>Erro durante o acesso (leitura ou escrita) ao banco de dados. Comando SQL malformado. Procure o Webmaster</p>");
                rd = request.getRequestDispatcher("/resultadoOperacao.jsp");
                break;
            case ClienteDAO.ERRO_INSERCAO:
                request.setAttribute("conteudo","<h1>Falha na Operação</h1>"
                                              + "<p>Os dados inseridos eram inválidos. Por favor verifcar e tentar outra vez</p>");
                rd = request.getRequestDispatcher("/resultadoOperacao.jsp");
                break;
            case ClienteDAO.ERRO_CODIGO:
                request.setAttribute("conteudo","<h1>Falha na Operação</h1>"
                                              + "<p>O Código informado nao corresponde a um código de cliente presente no banco de dados</p>");
                rd = request.getRequestDispatcher("/resultadoOperacao.jsp");
                break;
            case LISTAR_CLIENTES:
                rd = request.getRequestDispatcher("listarClientes.jsp");
                break;
            case BUSCAR_CLIENTE:
                rd = request.getRequestDispatcher("buscarCliente.jsp");
                break;
            case DETALHAR_CLIENTE:
                rd = request.getRequestDispatcher("detalharCliente.jsp");
                break;
            case ATUALIZAR_CLIENTE:
                rd = request.getRequestDispatcher("atualizarCliente.jsp");
                break;
            default:
//                request.setAttribute("conteudo","<h1>Falha na Operação</h1>"
//                                              + "<p>Por algum motivo não identificado, a operação foi cancelada. Entre em contato com o webmaster</p>");
//                rd = request.getRequestDispatcher("/resultadoOperacao.jsp");
                
                rd = request.getRequestDispatcher("buscarCliente.jsp");
        }

        rd.forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    protected int inserirCliente(HttpServletRequest request, HttpServletResponse response){
        int resultadoOperacao = 0;
        try {
            ClienteDAO clienteDAO = new ClienteDAO();
            Cliente cliente = new Cliente(0,
                                          request.getParameter("cliente_nome"),
                                          request.getParameter("cliente_loja"),
                                          request.getParameter("cliente_fone"),
                                          request.getParameter("cliente_endereco"),
                                          request.getParameter("cliente_cpf"),
                                          request.getParameter("cliente_cnpj"));
            resultadoOperacao = clienteDAO.inserir(this.usuario, this.senha, this.endereco, cliente);
            return resultadoOperacao;
        } catch (InsercaoException ex) {
            //tratamento de falha na insercao
            return ClienteDAO.ERRO_INSERCAO;
        }
    }
    
    protected int alterarCliente(HttpServletRequest request, HttpServletResponse response){
        try {
            ClienteDAO clienteDAO = new ClienteDAO();
            Cliente cliente = new Cliente(Integer.parseInt(request.getParameter("cliente_codigo")),
                                          request.getParameter("cliente_nome"),
                                          request.getParameter("cliente_loja"),
                                          request.getParameter("cliente_fone"),
                                          request.getParameter("cliente_endereco"),
                                          request.getParameter("cliente_cpf"),
                                          request.getParameter("cliente_cnpj"));
            int resultadoOperacao = clienteDAO.alterar(this.usuario, this.senha, this.endereco, cliente);
            return resultadoOperacao;
        } catch (InsercaoException ex) {
            //tratamento de falha na insercao
            return ClienteDAO.ERRO_INSERCAO;
        }
    }
    
    protected int excluirCliente(HttpServletRequest request, HttpServletResponse response){
        ClienteDAO clienteDAO = new ClienteDAO();
        int clienteCodigo = Integer.parseInt(request.getParameter("cliente_codigo"));
        int resultadoOperacao = clienteDAO.excluir(this.usuario, this.senha, this.endereco, clienteCodigo);
        return resultadoOperacao;
    }
    
    protected List<Cliente> consultarClientes(){
        ClienteDAO clienteDAO = new ClienteDAO();
        return clienteDAO.consultar(this.usuario, this.senha, this.endereco);
    }
    
    protected Cliente buscarCliente(HttpServletRequest request) throws EntidadeNulaException{
        int clienteCodigo = Integer.parseInt(request.getParameter("cliente_codigo"));
        ClienteDAO clienteDAO = new ClienteDAO();
        return clienteDAO.buscar(this.usuario, this.senha, this.endereco, clienteCodigo);
    }
}
