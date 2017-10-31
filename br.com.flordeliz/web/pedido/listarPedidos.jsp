<%-- 
    Document   : listarPedidos
    Created on : 17/10/2017, 15:31:46
    Author     : saintclair
--%>

<%@page import="java.util.List"%>
<%@page import="br.com.flordeliz.modelo.Pedido"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Pedido - Listar</title>
    
        <jsp:include page="/cabecalho.jsp"/>
        
        <h1>Lista de Pedidos Registrados</h1>
        
        
        <table border="1">
            <tr>
                <th>Código</th>
                <th>Solicitante</th>
                <th>Desconto</th>
                <th>Número de Itens</th>
                <th>Código da Entrega</th>
                <th>Total (Bruto)</th>
                <th>Total Líquido</th>
                <th colspan="3">Ações</th>
            </tr>
            
            <%
                List<Pedido> lista = (List<Pedido>) request.getAttribute("listaPedidos");
                for (Pedido pedido : lista) {%>
                <tr>
                    <td>
                        <a href="ControlePedido?acao=detalhar&pedido_codigo=<%= pedido.getCodigo() %>">
                            <%=pedido.getCodigo()%>
                        </a>
                    </td>
                    <td>
                        <a href="../cliente/ControleCliente?acao=detalhar&cliente_codigo=<%= pedido.getCliente().getCodigo() %>">
                            <%=pedido.getCliente().getNome()%>
                        </a>
                    </td>
                    <td><%=pedido.getDesconto()%></td>
                    <td><%=pedido.getListaItens().size()%></td>
                    <td><%=pedido.getEntrega().getCodigo()%></td>
                    <td><%= String.format("%.2f", pedido.getPrecoTotalPedidoBruto())%></td>
                    <td><%= String.format("%.2f", pedido.getPrecoTotalPedidoLiquido())%></td>
                    <td>
                        <a href="ControlePedido?acao=detalhar&pedido_codigo=<%= pedido.getCodigo() %>">
                            Detalhar
                        </a>
                    </td>
                    <td>Alterar</td>
                    <td>
                        <a href="ControlePedido?acao=excluir&pedido_codigo=<%= pedido.getCodigo() %>">
                            Excluir
                        </a>
                    </td>
                </tr>
                <%}
            %>
    </body>
</html>
