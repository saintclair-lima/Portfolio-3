<%-- 
    Document   : listarLotes
    Created on : 18/10/2017, 15:05:18
    Author     : saintclair
--%>

<%@page import="br.com.flordeliz.modelo.LoteProducao"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lote - Listar</title>
        
        <jsp:include page="/cabecalho.jsp"/>
        
        <h1>Lista de Lotes Registrados</h1>
        
        <table border="1">
            <tr>
                <th>Código</th>
                <th>Data de Produção</th>
                <th>Modelo</th>
                <th>Coleção</th>
                <th>Tamanho</th>
                <th>Quantidade</th>
                <th>Cor</th>
                <th colspan="3">Ações</th>
            </tr>
            
            <%
                List<LoteProducao> lista = (List<LoteProducao>) request.getAttribute("listaLotes");
                for (LoteProducao lote : lista) {%>
                <tr>
                    <td><%=lote.getCodigo()%></td>
                    <td><%=lote.getDataStringFormatada()%></td>
                    <td><%=lote.getItemEstoque().getModelo().getNome()%></td>
                    <td><%=lote.getItemEstoque().getModelo().getColecao()%></td>
                    <td><%=lote.getItemEstoque().getTamanho()%></td>
                    <td><%=lote.getQuantidade()%></td>
                    <td><%=lote.getItemEstoque().getModelo().getCor()%></td>
                    <td>
                        <a href="ControleLote?acao=detalhar&lote_codigo=<%= lote.getCodigo() %>">
                            Detalhar
                        </a>
                    </td>
                    <td>Atualizar</td>
                    <td>
                        <a href="ControleLote?acao=excluir&lote_codigo=<%= lote.getCodigo() %>">
                            Excluir
                        </a>
                    </td>
                </tr>
                <%}
            %>
        </table>
    </body>
</html>
