<%-- 
    Document   : clienteHome
    Created on : 17/10/2017, 13:51:09
    Author     : saintclair
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cliente - Home</title>
    
        <jsp:include page="/cabecalho.jsp"/>
        
        <h1>Cliente - Dashboard</h1>
        <a href="inserirCliente.jsp">Inserir Cliente</a><br/>
        <a href="ControleCliente?acao=consultar">Consultar Lista de Clientes</a><br/>
        <a href="ControleCliente?acao=buscar">Buscar Cliente por Código</a>
    </body>
</html>
