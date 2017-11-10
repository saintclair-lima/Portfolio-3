<%@page import="br.com.flordeliz.modelo.Cliente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page import="java.util.List" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cliente - Listar</title>
        
        <jsp:include page="/cabecalho.jsp"/>
        
        <h1>Lista de Clientes Registrados</h1>
        
        <table border="1">
            <tr>
                <th>Código</th>
                <th>Nome</th>
                <th>Nome da Loja</th>
                <th>Número de Telefone</th>
                <th>Endereco</th>
                <th>CPF</th>
                <th>CNPJ</th>
                <th colspan="2">Ações</th>
            </tr>
            <%
                List<Cliente> lista = (List<Cliente>) request.getAttribute("listaClientes");
                for (Cliente cliente : lista) {%>
                    <tr>
                        <td><%= cliente.getCodigo() %></td>
                        <td>
                            <a href="ControleCliente?acao=detalhar&cliente_codigo=<%= cliente.getCodigo() %>">
                                <%= cliente.getNome() %>
                            </a>
                        </td>
                        <td><%= cliente.getLoja() %></td>
                        <td><%= cliente.getFone() %></td>
                        <td><%= cliente.getEndereco() %></td>
                        <td><%= cliente.getCpf() %></td>
                        <td><%= cliente.getCnpj() %></td>
                        <td>
                            <a href="ControleCliente?acao=atualizar&cliente_codigo=<%= cliente.getCodigo() %>">
                                Atualizar
                            </a>
                        </td>
                        <td>
                            <a href="ControleCliente?acao=excluir&cliente_codigo=<%= cliente.getCodigo() %>" onclick="return confirm('Deseja realmente realizar a exclusão do registro?')">
                                Excluir
                            </a>
                        </td>
                    </tr>
                <%}
            %>
            </table>
               
    </body>
</html>
