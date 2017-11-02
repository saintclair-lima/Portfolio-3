<%-- 
    Document   : inserirCliente
    Created on : 11/10/2017, 08:32:58
    Author     : saintclair
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cliente - Inserir</title>
        
        <jsp:include page="/cabecalho.jsp"/>
        
        <h1>Novo Cliente</h1>
        <form action="ControleCliente" method="post">
            <input type="hidden" name="acao" value="inserir"/>
            <label for="cliente_nome">Nome</label>
            <input type="text" name="cliente_nome" maxlength="35"/><br/>
            <label for="cliente_loja">Nome da Loja</label>
            <input type="text" name="cliente_loja" maxlength="35"/><br/>
            <label for="cliente_fone">Telefone</label>
            <input type="text" name="cliente_fone" maxlength="11"/><br/>
            <label for="cliente_endereco">Endereço</label>
            <input type="text" name="cliente_endereco" maxlength="100"/><br/>
            <label for="cliente_cpf">CPF</label>
            <input type="text" name="cliente_cpf" maxlength="11"/><br/>
            <label for="cliente_cnpj">CNPJ</label>
            <input type="text" name="cliente_cnpj" maxlength="15"/><br/>
            <input type="submit" value="Incluir Cliente"/> 
            <input type="button" value ="Cancelar" onClick="window.location='<%=request.getContextPath() %>/home.jsp';"/><br/>
        </form>
    </body>
</html>
