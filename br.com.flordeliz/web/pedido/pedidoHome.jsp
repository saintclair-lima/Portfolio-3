<%-- 
    Document   : pedidoHome
    Created on : 18/10/2017, 00:53:00
    Author     : Samara C. Lima
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Pedido - Home</title>
   
        <jsp:include page="/cabecalho.jsp"/> 
        
        <h1>Pedido - Dashboard</h1>
        <a href="inserirPedido.jsp">Inserir Pedido</a><br/>
        <a href="ControlePedido?acao=consultar">Consultar Lista de Pedidos</a><br/>
        <a href="ControlePedido?acao=buscar">Buscar Pedido por Código</a>
    </body>
</html>
