<%-- 
    Document   : inserirLote
    Created on : 18/10/2017, 15:06:01
    Author     : saintclair
--%>

<%@page import="br.com.flordeliz.modelo.Cliente"%>
<%@page import="br.com.flordeliz.modelo.ItemEstoque"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Pedido - Inserir</title>
        
        <jsp:include page="/cabecalho.jsp"/>
        
        <script type="text/javascript">
            // Evita que seja feito pedido com quantidade maior que a disponível em estoque
            
            function excluirLinhaProduto(botao) {
                var linha = botao.parentNode.parentNode;
                linha.parentNode.removeChild(linha);
            }
            
            function inserirLinhaProduto(){
                var e = document.getElementById("itemEstoque");
                var idProduto = e.options[e.selectedIndex].getAttribute("value");
                var valido = true;
                var itensInseridos = document.getElementsByName("item_estoque_codigo[]");
                
                for (i=0; i<itensInseridos.length; i++){
                    if(itensInseridos[i].value === idProduto){
                        console.log(i, itensInseridos[i].value);
                        valido = false;
                        alert("Item já Presente na Lista de Pedido");
                    }
                }
                
                var e = document.getElementById("itemEstoque");
                var quantidade = parseInt(e.options[e.selectedIndex].getAttribute("data-quantidade"));
                
                var quantidadeSolicitada = parseInt(document.getElementById("quantidade").value);
                if (quantidadeSolicitada < 1 || isNaN(quantidadeSolicitada)){
                    quantidadeSolicitada = 1;
                }
                if (quantidadeSolicitada > quantidade){
                    valido = false;
                    alert("Quantidade Indisponível no Estoque");
                }
                
                
                if (valido){
                
                    var table = document.getElementById("itensSelecionados");
                    var row = table.insertRow(-1);
                    var cell1 = row.insertCell(0);
                    var cell2 = row.insertCell(1);
                    var cell3 = row.insertCell(2);

                    var e = document.getElementById("itemEstoque");
                    var idProduto = e.options[e.selectedIndex].getAttribute("value");
                    var descricaoProduto = e.options[e.selectedIndex].innerHTML;

                    var inputItemCod = document.createElement("input");
                    inputItemCod.type = "hidden";
                    inputItemCod.name = "item_estoque_codigo[]";
                    inputItemCod.value = idProduto;

                    var inputItemQuant = document.createElement("input");
                    inputItemQuant.type = "hidden";
                    inputItemQuant.name = "quantidade_item[]";
                    inputItemQuant.value = quantidadeSolicitada;

                    cell1.innerHTML = descricaoProduto;
                    cell2.innerHTML = quantidadeSolicitada;
                    //cell3.innerHTML = "NEW CELL2";

                    var button = document.createElement("input");
                    button.type = "button";
                    button.value = "Excluir Produto";
                    button.onclick = function() {
                        excluirLinhaProduto(this);
                    }

                    cell1.appendChild(inputItemCod);
                    cell2.appendChild(inputItemQuant);
                    cell3.appendChild(button);
                }
            }
            
            function setCliente(){
                var cliente = document.getElementById("cliente");
                var codigoCliente = cliente.options[cliente.selectedIndex].value;
                
                document.getElementById("cliente_codigo").value=codigoCliente;
                console.log("foi exec")
            }
        </script>
        
        <h1>Gerar Pedido</h1>
        <form action="ControlePedido" method="post">
            <input type="hidden" name="acao" value="incluir"/>
            
            <label for="cliente"><b>Cliente: </b></label>
            <select name="cliente" id="cliente" onblur="setCliente()">
                <option value="" disabled selected>Selecione o Cliente</option>
                <%
                    List<Cliente> listaClientes = (List<Cliente>) request.getAttribute("listaClientes");
                    for (Cliente cliente:listaClientes){
                %>
                        <option value ="<%=cliente.getCodigo()%>">
                            <%=cliente.getNome() + " | CPF: " + cliente.getCpf()%>
                        </option>
                <%
                    }
                %>
            </select>
            <input type="hidden" value="10" name="cliente_codigo" id="cliente_codigo"/>
            <br/>
            <br/>
            
            <label for="itemEstoque"><b>Produto: </b></label>
            <select name="itemEstoque" id="itemEstoque" onblur="validarQuantidade()">
                <option value="" disabled selected>Selecione o Produto</option>
                <%
                    List<ItemEstoque> listaItens = (List<ItemEstoque>) request.getAttribute("listaItensEstoque");
                    for (ItemEstoque item:listaItens){
                        if (item.getQuantidade() > 0){
                %>
                        <option value ="<%=item.getCodigo()%>" data-quantidade="<%=item.getQuantidade()%>">
                            <%=item.getModelo().getNome() + " | Tamanho " + item.getTamanho()+ " | " + item.getModelo().getCor() + " (" + item.getQuantidade() + ")"%>
                        </option>
                <%
                        }
                    }
                %>
            </select>
            <input type="number" min="0" step="1" name="quantidade" id="quantidade" value="1"/>
            <input type="button" value="Incluir Item" onclick="inserirLinhaProduto()"/>
            
            <br/>
            <br/>
            
            <table border = 1 name="itensSelecionados" id="itensSelecionados">
                <tr>
                    <th>Modelo</th>
                    <th>Quantidade</th>
                    <th>Ação</th>
                </tr>
            </table>
            
            <br/>
            
            <label for="desconto"><b>Desconto: </b></label>
            <input type="number" min="0" max="1" step="0.01" placeholder="0.00" value="0.00" name="desconto" id="desconto"/>
            <br/>
            <br/>
            <input type="submit">
        </form>
        
    </body>
</html>
