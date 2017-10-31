<%-- 
    Document   : buscarCliente
    Created on : 17/10/2017, 13:54:49
    Author     : saintclair
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cliente - Buscar</title>
        
        <jsp:include page="/cabecalho.jsp"/>
        
        <h1>Buscar Cliente pelo Código</h1>
        <form action="ControleCliente" method="post">
            <input type="hidden" name="acao" value="detalhar"/>
            <label for="cliente_codigo"><b>Código do Cliente: </b></label>
            <input type="number" name="cliente_codigo" max="2147483648"/>
            <input type="submit" value="Buscar"/>
        </form>
        
        <a href="inserirCliente.jsp">Inserir Cliente</a><br/>
        <a href="ControleCliente?acao=consultar">Consultar Lista de Clientes</a><br/>
    </body>
</html>
