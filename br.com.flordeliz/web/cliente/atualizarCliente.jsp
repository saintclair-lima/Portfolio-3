<%-- 
    Document   : atualizarCliente
    Created on : 16/10/2017, 05:35:19
    Author     : Samara C. Lima
--%>

<%@page import="br.com.flordeliz.modelo.Cliente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cliente - Atualizar</title>
   
        <jsp:include page="/cabecalho.jsp"/>
        
        <%Cliente cliente = (Cliente) request.getAttribute("cliente");%>
        <h1>Alterar Dados do Cliente</h1>
        <form action="ControleCliente" method="post">
            <input type="hidden" name="acao" value="alterar"/>
            <p><b>Código: </b> <input type="text" name="cliente_codigo" readonly="true" value="<%= cliente.getCodigo() %>"/></p>
            <p><b>Nome: </b><input type="text" name="cliente_nome" value="<%= cliente.getNome() %>"/></p>
            <p><b>Nome da Loja: </b><input type="text" name="cliente_loja" value="<%= cliente.getLoja() %>"/></p>
            <p><b>Telefone: </b><input type="text" name="cliente_fone" value="<%= cliente.getFone() %>"/></p>
            <p><b>Endereço: </b><input type="text" name="cliente_endereco" value="<%= cliente.getEndereco() %>"/></p>
            <p><b>Número do CPF: </b><input type="text" name="cliente_cpf" value="<%= cliente.getCpf() %>"/></p>
            <p><b>Número do CNPJ da Loja: </b><input type="text" name="cliente_cnpj" value="<%= cliente.getCnpj() %>"/></p>

            <input type="submit" value="Alterar Dados"/><br/><br/>
        </form>
        
        <a href="inserirCliente.jsp">Inserir Cliente</a><br/>
        <a href="ControleCliente?acao=consultar">Consultar Lista de Clientes</a><br/>
        <a href="ControleCliente?acao=buscar">Buscar Cliente por Código</a>
    </body>
</html>
