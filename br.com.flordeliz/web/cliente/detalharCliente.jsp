<%-- 
    Document   : detalharCliente
    Created on : 14/10/2017, 02:33:02
    Author     : Samara C. Lima
--%>

<%@page import="br.com.flordeliz.modelo.Cliente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cliente - Detalhar</title>
        
        <jsp:include page="/cabecalho.jsp"/>
        
        <h1>Detalhes do Cliente</h1>
        <%Cliente cliente = (Cliente) request.getAttribute("cliente");%>
        <p><b>Código: </b> <%= cliente.getCodigo() %></p>
        <p><b>Nome: </b><%= cliente.getNome() %></p>
        <p><b>Nome da Loja: </b><%= cliente.getLoja() %></p>
        <p><b>Telefone: </b><%= cliente.getFone() %></p>
        <p><b>Endereço: </b><%= cliente.getEndereco() %></p>
        <p><b>Número do CPF: </b><%= cliente.getCpf() %></p>
        <p><b>Número do CNPJ da Loja: </b><%= cliente.getCnpj() %></p>
        
        <a href="ControleCliente?acao=excluir&cliente_codigo=<%= cliente.getCodigo() %>">Excluir Cliente</a> 
        <a href="ControleCliente?acao=atualizar&cliente_codigo=<%= cliente.getCodigo() %>">Atualizar Cliente</a><br/><br/>
        
        <a href="inserirCliente.jsp">Inserir Cliente</a><br/>
        <a href="ControleCliente?acao=consultar">Consultar Lista de Clientes</a><br/>
        <a href="ControleCliente?acao=buscar">Buscar Cliente por Código</a>
    </body>
</html>
