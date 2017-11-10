<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cliente - Inserir</title>
        <style type="text/css">
            .container {
                width: 500px;
                clear: both;
            }
            .container input {
                width: 100%;
                clear: both;
            }
        </style>
        
        <jsp:include page="/cabecalho.jsp"/>
        
        <h1>Novo Cliente</h1>
        <form action="ControleCliente" method="post">
            <div class="container">
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
            </div>
            <input class="bot_envio" type="submit" value="Incluir Cliente"/> 
            <input class="bot_cancela" type="button" value ="Cancelar" onClick="window.location='<%=request.getContextPath() %>/home.jsp';"/><br/>
        </form>
    </body>
</html>
