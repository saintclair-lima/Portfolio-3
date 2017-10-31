/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.flordeliz.controlador;

import br.com.flordeliz.dao.ClienteDAO;
import br.com.flordeliz.dao.ItemEstoqueDAO;
import br.com.flordeliz.dao.PedidoDAO;
import br.com.flordeliz.modelo.Cliente;
import br.com.flordeliz.modelo.EntidadeNulaException;
import br.com.flordeliz.modelo.ItemEstoque;
import br.com.flordeliz.modelo.Pedido;
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
public class ControlePedido extends HttpServlet implements InterfaceControle{

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    public static final int LISTAR_PEDIDOS = 4;
    public static final int DETALHAR_PEDIDO = 5;
    public static final int ATUALIZAR_PEDIDO = 6;
    public static final int INSERIR_PEDIDO = 7;
    public static final int BUSCAR_PEDIDO = 8;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String acao = request.getParameter("acao");
        int resultadoOperacao = 20;
        if (acao==null) acao = "";
        if (acao.equals("consultar")){
            List<Pedido> lista = consultarPedidos();
            request.setAttribute("listaPedidos", lista);
            resultadoOperacao = this.LISTAR_PEDIDOS;
            
            
        } else if (acao.equals("inserir")){
            List<ItemEstoque> listaItensEstoque = this.consultarItensEstoque();
            request.setAttribute("listaItensEstoque", listaItensEstoque);
            
            List<Cliente> listaClientes = this.consultarClientes();
            request.setAttribute("listaItensEstoque", listaClientes);
            resultadoOperacao = this.INSERIR_PEDIDO;
            
            
        } else if(acao.equals("detalhar")){
            try {
                Pedido pedido = buscarPedido(request);
                request.setAttribute("pedido", pedido);
                resultadoOperacao = this.DETALHAR_PEDIDO;
            } catch (EntidadeNulaException ex) {
                resultadoOperacao = PedidoDAO.ERRO_CODIGO;
            }
            
            
        } else if (acao.equals("buscar")){
            resultadoOperacao = this.BUSCAR_PEDIDO;
            
        } else if (acao.equals("excluir")){
            resultadoOperacao = excluirPedido(request, response);
        }
        
        
        RequestDispatcher rd;
        switch (resultadoOperacao){
            case INSERIR_PEDIDO:
                rd = request.getRequestDispatcher("inserirPedido.jsp");
                break;
            case DETALHAR_PEDIDO:
                rd = request.getRequestDispatcher("detalharPedido.jsp");
                break;
            case LISTAR_PEDIDOS:
                    rd = request.getRequestDispatcher("listarPedidos.jsp");
                    break;
            case BUSCAR_PEDIDO:
                    rd = request.getRequestDispatcher("buscarPedido.jsp");
                    break;
            case PedidoDAO.SUCESSO:
                String processadas = " processadas ";
                if (acao.equals("inserir")){
                    processadas = " inseridas ";
                } else if (acao.equals("excluir")){
                    processadas = " excluídas ";
                } else {
                    processadas = " alteradas ";
                }
                request.setAttribute("conteudo","<h1>Informações sobre o pedido"+ processadas + "com sucesso</h1>");
                rd = request.getRequestDispatcher("/resultadoOperacao.jsp");
                break;
            case PedidoDAO.ERRO_SQL:
                request.setAttribute("conteudo","<h1>Falha ao Acessar o Banco</h1>"
                                   + "<p>Erro durante o acesso (leitura ou escrita) ao banco de dados. Comando SQL malformado. Procure o Webmaster</p>");
                rd = request.getRequestDispatcher("/resultadoOperacao.jsp");
                break;
            case PedidoDAO.ERRO_INSERCAO:
                request.setAttribute("conteudo","<h1>Falha na Operação</h1>"
                                              + "<p>Os dados inseridos eram inválidos. Por favor verifcar e tentar outra vez</p>");
                rd = request.getRequestDispatcher("/resultadoOperacao.jsp");
                break;
            case PedidoDAO.ERRO_CODIGO:
                request.setAttribute("conteudo","<h1>Falha na Operação</h1>"
                                              + "<p>O Código informado nao corresponde a um código de pedido presente no banco de dados</p>");
                rd = request.getRequestDispatcher("/resultadoOperacao.jsp");
                break;
            default:
                rd = request.getRequestDispatcher("buscarPedido.jsp");
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
    
    protected List<Pedido> consultarPedidos(){
        PedidoDAO pedidoDAO = new PedidoDAO();
        return pedidoDAO.consultar(usuario, senha, endereco);
    }

    protected Pedido buscarPedido(HttpServletRequest request) throws EntidadeNulaException{
        int pedidoCodigo = Integer.parseInt(request.getParameter("pedido_codigo"));
        PedidoDAO pedidoDAO = new PedidoDAO();
        return pedidoDAO.buscar(this.usuario, this.senha, this.endereco, pedidoCodigo);
    }
    
    protected int excluirPedido(HttpServletRequest request, HttpServletResponse response){
        PedidoDAO pedidoDAO = new PedidoDAO();
        int pedidoCodigo = Integer.parseInt(request.getParameter("pedido_codigo"));
        int resultadoOperacao = pedidoDAO.excluir(this.usuario, this.senha, this.endereco, pedidoCodigo);
        return resultadoOperacao;
    }
    
    protected List<ItemEstoque> consultarItensEstoque(){
         ItemEstoqueDAO itemEstoqueDAO = new ItemEstoqueDAO();
         return itemEstoqueDAO.consultar(this.usuario, this.senha, this.endereco);
    }
    
    protected List<Cliente> consultarClientes(){
        ClienteDAO clienteDAO = new ClienteDAO();
        return clienteDAO.consultar(this.usuario, this.senha, this.endereco);
    }
}
