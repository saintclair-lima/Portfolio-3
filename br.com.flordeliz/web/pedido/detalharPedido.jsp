<%@page import="br.com.flordeliz.modelo.ItemPedido"%>
<%@page import="br.com.flordeliz.modelo.Pedido"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Pedido - Detalhar</title>
        
        <jsp:include page="/cabecalho.jsp"/>
        
        <h1>Detalhes do Pedido</h1>
        <%Pedido pedido = (Pedido) request.getAttribute("pedido");%>
        <p><b>Código: </b> <%=pedido.getCodigo()%> </p>
        <p><b>Solicitante: </b>
            <a href="../cliente/ControleCliente?acao=buscar&cliente_codigo=<%= pedido.getCliente().getCodigo() %>">
                <%=pedido.getCliente().getNome()%>
            </a>
        </p>
        <p><b>Desconto: </b> <%=pedido.getDesconto()%> </p>
        <p><b>Número de Itens: </b> <%=pedido.getListaItens().size()%> </p>
        <p><b>Código da Entrega: </b> <%=pedido.getEntrega().getCodigo()%> </p>
        <p><b>Total (Bruto): </b> <%= String.format("%.2f", pedido.getPrecoTotalPedidoBruto())%> </p>
        <p><b>Total Líquido: </b> <%= String.format("%.2f", pedido.getPrecoTotalPedidoLiquido())%> </p>
        
        <br/>
        
        <p><b>DISCRIMINAÇÃO DOS ITENS NO PEDIDO</b></p>
        <table border="1">
            <tr>
                <th>Item</th>
                <th>Código</th>
                <th>Modelo</th>
                <th>Coleção</th>
                <th>Cor</th>
                <th>Tamanho</th>
                <th>Quantidade</th>
                <th>Valor Custo</th>
                <th>Preco Venda</th>
                <th>Total Bruto do Item</th>
                <th>Total com Desconto do Item</th>
            </tr>
            <%
                List<ItemPedido> lista = pedido.getListaItens();
                for (ItemPedido item : lista) {%>
                    <tr>
                        <td><%=item.getCodigo()%></td>
                        <td><%=item.getItemEstoque().getCodigo()%></td>
                        <td><%=item.getItemEstoque().getModelo().getNome()%></td>
                        <td><%=item.getItemEstoque().getModelo().getColecao()%></td>
                        <td><%=item.getItemEstoque().getModelo().getCor()%></td>
                        <td><%=item.getItemEstoque().getTamanho()%></td>
                        <td><%=item.getQuantidade()%></td>
                        <td><%=item.getItemEstoque().getModelo().getPrecoCusto()%></td>
                        <td><%= String.format("%.2f", item.getItemEstoque().getPrecoVenda())%></td>
                        <td><%=String.format("%.2f", item.getItemEstoque().getPrecoVenda() * item.getQuantidade())%></td>
                        <td><%=String.format("%.2f", (item.getItemEstoque().getPrecoVenda() * item.getQuantidade()) * (1 - pedido.getDesconto()))%></td>
                    </tr>
                <%}
            %>
        </table>
        <br/>
        <a class="bot_excluir" href="ControlePedido?acao=excluir&pedido_codigo=<%= pedido.getCodigo() %>" onclick="return confirm('Deseja realmente realizar a exclusão do registro?')">
            Excluir Pedido
        </a><br/>
    </body>
</html>
