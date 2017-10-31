<%-- 
    Document   : buscarPedido
    Created on : 18/10/2017, 02:53:19
    Author     : Samara C. Lima
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Pedido - Buscar</title>
        
        <jsp:include page="/cabecalho.jsp"/>
        
        <h1>Buscar Pedido pelo Código</h1>
        <form action="ControlePedido" method="post">
            <input type="hidden" name="acao" value="detalhar"/>
            <label for="pedido_codigo"><b>Código do Pedido: </b></label>
            <input type="number" name="pedido_codigo" max="2147483648"/>
            <input type="submit" value="Buscar"/>
        </form>
    </body>
</html>
