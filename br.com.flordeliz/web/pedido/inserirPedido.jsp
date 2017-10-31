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
        <title>Pedido - Inserir</title>
        
        <jsp:include page="/cabecalho.jsp"/>
        
        <script type="text/javascript">
            // Evita que seja feito pedido com quantidade maior que a disponível em estoque
            function validarQuantidade() {
                var e = document.getElementById("itemEstoque")
                var quantidade = parseInt(e.options[e.selectedIndex].getAttribute("data-quantidade"));
                console.log(quantidade);
                document.getElementById("quantidade").max=quantidade;
            }            
        </script>
        
        <h1>Gerar Pedido</h1>
        <form action="ControlePedido" method="post">
            <input type="hidden" name="acao" value="incluir"/>
            <select name="itemEstoque" id="itemEstoque">
                
            </select><br/>
        </form>
        
    </body>
</html>
