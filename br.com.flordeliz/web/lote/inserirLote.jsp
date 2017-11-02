<%-- 
    Document   : inserirLote
    Created on : 18/10/2017, 15:06:01
    Author     : saintclair
--%>

<%@page import="br.com.flordeliz.modelo.ItemEstoque"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lote - Inserir</title>
        
        <jsp:include page="/cabecalho.jsp"/>
        
        <script type="text/javascript">
            // Evita que seja feito pedido com quantidade maior que a disponível em estoque
            function validarQuantidade() {
                var e = document.getElementById("itemEstoque");
                var quantidade = parseInt(e.options[e.selectedIndex].getAttribute("data-quantidade"));
                console.log(quantidade);
                document.getElementById("quantidade").max=quantidade;
            }            
        </script>
        
        <h1>Inserir Lote no Cadastro</h1>
        <form action="ControleLote" method="post">
            <input type="hidden" name="acao" value="incluir"/>
            <select name="itemEstoque" id="itemEstoque">
                <%
                    List<ItemEstoque> listaItens = (List<ItemEstoque>) request.getAttribute("listaItensEstoque");
                    for (ItemEstoque item:listaItens){
                        if (item.getQuantidade() > 0){
                %>
                        <option value ="<%=item.getCodigo()%>" data-quantidade="<%=item.getQuantidade()%>">
                            <%=item.getModelo().getNome() + " | Tamanho " + item.getTamanho()+ " | " + item.getModelo().getCor() + " (" + item.getQuantidade() + ")"%>
                        </option>
                <%
                        }
                    }
                %>
            </select><br/>
            
            <label for="quantidade"><b>Quantidade: </b></label>
            <input type="number" min="0" step="1" name="quantidade" id="quantidade"/><br/>
            <input type="submit" value="Incluir Lote"/>
            <input type="button" value ="Cancelar" onClick="window.location='<%=request.getContextPath() %>/home.jsp';"/><br/>
        </form>
        
    </body>
</html>
