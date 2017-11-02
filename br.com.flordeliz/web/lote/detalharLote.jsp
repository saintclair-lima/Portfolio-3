<%-- 
    Document   : detalharLote
    Created on : 18/10/2017, 15:04:51
    Author     : saintclair
--%>

<%@page import="br.com.flordeliz.modelo.LoteProducao"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lote - Detalhar</title>
        
        <jsp:include page="/cabecalho.jsp"/>
        
        <h1>Detalhes do Lote de Produção</h1>
        <%LoteProducao lote = (LoteProducao) request.getAttribute("lote");%>
        <p><b>Código: </b><%=lote.getCodigo()%></p>
        <p><b>Data de Produção: </b><%=lote.getDataStringFormatada()%></p>
        <p><b>Modelo: </b><%=lote.getItemEstoque().getModelo().getNome()%></p>
        <p><b>Coleção: </b><%=lote.getItemEstoque().getModelo().getColecao()%></p>
        <p><b>Tamanho: </b><%=lote.getItemEstoque().getTamanho()%></p>
        <p><b>Quantidade: </b><%=lote.getQuantidade()%></p>
        <p><b>Cor: </b><%=lote.getItemEstoque().getModelo().getCor()%></p>
        
        <a href="ControleLote?acao=excluir&lote_codigo=<%= lote.getCodigo() %>">Excluir Lote de Produção</a><br/>
    </body>
</html>
