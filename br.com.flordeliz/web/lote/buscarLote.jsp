<%-- 
    Document   : buscarLote
    Created on : 18/10/2017, 15:01:15
    Author     : saintclair
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lote - Buscar</title>
        
        <jsp:include page="/cabecalho.jsp"/>
        
        <h1>Buscar Lote pelo Código</h1>
        <form action="ControleLote" method="post">
            <input type="hidden" name="acao" value="detalhar"/>
            <label for=lote_codigo"><b>Código do Lote: </b></label>
            <input type="number" name="lote_codigo" max="2147483648"/>
            <input type="submit" value="Buscar"/>
        </form>
    </body>
</html>
