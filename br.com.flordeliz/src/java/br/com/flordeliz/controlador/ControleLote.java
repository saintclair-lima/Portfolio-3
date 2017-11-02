/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.flordeliz.controlador;

import br.com.flordeliz.dao.ItemEstoqueDAO;
import br.com.flordeliz.dao.LoteProducaoDAO;
import br.com.flordeliz.modelo.LoteProducao;
import br.com.flordeliz.modelo.EntidadeNulaException;
import br.com.flordeliz.modelo.InsercaoException;
import br.com.flordeliz.modelo.ItemEstoque;
import java.io.IOException;
import java.text.ParseException;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author saintclair
 */
public class ControleLote extends HttpServlet implements InterfaceControle{    
    public static final int LISTAR_LOTES = 4;
    public static final int DETALHAR_LOTE = 5;
    public static final int ATUALIZAR_LOTE = 6;
    public static final int INSERIR_LOTE = 7;
    public static final int BUSCAR_LOTE = 8;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String acao = request.getParameter("acao");
        if (acao==null) acao = "";
        int resultadoOperacao = 20;
        if (acao.equals("consultar")){
            List<LoteProducao> lista = consultarLotes();
            request.setAttribute("listaLotes", lista);
            resultadoOperacao = this.LISTAR_LOTES;
        
        
        } else if (acao.equals("inserir")){
            List<ItemEstoque> listaItensEstoque = this.consultarItensEstoque();
            request.setAttribute("listaItensEstoque", listaItensEstoque);
            resultadoOperacao = this.INSERIR_LOTE;


        } else if (acao.equals("incluir")){
            resultadoOperacao = this.inserirLote(request, response);


        } else if (acao.equals("buscar")){
            resultadoOperacao = this.BUSCAR_LOTE;


        } else if (acao.equals("detalhar")){
            try {
                LoteProducao lote = buscarLote(request);
                request.setAttribute("lote", lote);
                resultadoOperacao = this.DETALHAR_LOTE;
            } catch (EntidadeNulaException ex) {
                resultadoOperacao = LoteProducaoDAO.ERRO_CODIGO;
            }
        } else if (acao.equals("excluir")){
            resultadoOperacao = excluirLote(request, response);

        }
        
        RequestDispatcher rd;
        request.setAttribute("title","Lote - " + acao);
        switch (resultadoOperacao){
            
            case LoteProducaoDAO.SUCESSO:
                String processadas = " processadas ";
                if (acao.equals("incluir")){
                    processadas = " inseridas ";
                } else if (acao.equals("excluir")){
                    processadas = " excluídas ";
                } else {
                    processadas = " alteradas ";
                }
                request.setAttribute("conteudo","<h1>Informações sobre o lote"+ processadas + "com sucesso</h1>");
                rd = request.getRequestDispatcher("/resultadoOperacao.jsp");
                break;
            case LoteProducaoDAO.ERRO_SQL:
                request.setAttribute("conteudo","<h1>Falha ao Acessar o Banco</h1>"
                                   + "<p>Erro durante o acesso (leitura ou escrita) ao banco de dados. Comando SQL malformado. Procure o Webmaster</p>");
                rd = request.getRequestDispatcher("/resultadoOperacao.jsp");
                break;
            case LoteProducaoDAO.ERRO_INSERCAO:
                request.setAttribute("conteudo","<h1>Falha na Operação</h1>"
                                              + "<p>Os dados inseridos eram inválidos. Por favor verifcar e tentar outra vez</p>");
                rd = request.getRequestDispatcher("/resultadoOperacao.jsp");
                break;
            case LoteProducaoDAO.ERRO_CODIGO:
                request.setAttribute("conteudo","<h1>Falha na Operação</h1>"
                                              + "<p>O Código informado nao corresponde a um código de lote presente no banco de dados</p>");
                rd = request.getRequestDispatcher("/resultadoOperacao.jsp");
                break;
            case LISTAR_LOTES:
                rd = request.getRequestDispatcher("listarLotes.jsp");
                break;
            case INSERIR_LOTE:
                rd = request.getRequestDispatcher("inserirLote.jsp");
                break;
            case BUSCAR_LOTE:
                rd = request.getRequestDispatcher("buscarLote.jsp");
                break;
            case DETALHAR_LOTE:
                rd = request.getRequestDispatcher("detalharLote.jsp");
                break;
            default:
                rd = request.getRequestDispatcher("buscarLote.jsp");
                break;
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

    protected int inserirLote(HttpServletRequest request, HttpServletResponse response){
        int resultadoOperacao = 0;
        try {
            LoteProducaoDAO loteProducaoDAO = new LoteProducaoDAO();
            ItemEstoqueDAO itemEstoqueDAO = new ItemEstoqueDAO();
            
            ItemEstoque item = itemEstoqueDAO.buscar(usuario, senha, endereco, Integer.parseInt(request.getParameter("itemEstoque")));
            LoteProducao lote = new LoteProducao(0,
                                                 new GregorianCalendar(),
                                                 Integer.parseInt(request.getParameter("quantidade")),
                                                 item);
            
            resultadoOperacao = loteProducaoDAO.inserir(usuario, senha, endereco, lote);
            System.out.print("Deu Certo");
            return resultadoOperacao;
            
        } catch (InsercaoException ex) {
            //System.out.print("Insercao Exception");
            Logger.getLogger("Insercao Exception", ControleLote.class.getName());
            
            return LoteProducaoDAO.ERRO_INSERCAO;
        } catch (ParseException ex) {
            //System.out.print("Parse Exception");
            Logger.getLogger("Parse Exception", ControleLote.class.getName());
            return LoteProducaoDAO.ERRO_INSERCAO;
        }
    }
    
    protected List<LoteProducao> consultarLotes(){
        LoteProducaoDAO loteDAO = new LoteProducaoDAO();
        return loteDAO.consultar(this.usuario, this.senha, this.endereco);
    }
    
     protected LoteProducao buscarLote(HttpServletRequest request) throws EntidadeNulaException{
        int loteCodigo = Integer.parseInt(request.getParameter("lote_codigo"));
        LoteProducaoDAO loteDAO = new LoteProducaoDAO();
        return loteDAO.buscar(this.usuario, this.senha, this.endereco, loteCodigo);
    }
     
     protected List<ItemEstoque> consultarItensEstoque(){
         ItemEstoqueDAO itemEstoqueDAO = new ItemEstoqueDAO();
         return itemEstoqueDAO.consultar(this.usuario, this.senha, this.endereco);
     }

    private int excluirLote(HttpServletRequest request, HttpServletResponse response) {
        LoteProducaoDAO loteDAO = new LoteProducaoDAO();
        int loteCodigo = Integer.parseInt(request.getParameter("lote_codigo"));
        int resultadoOperacao = loteDAO.excluir(this.usuario, this.senha, this.endereco, loteCodigo);
        return resultadoOperacao;
    }
}
