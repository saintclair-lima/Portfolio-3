
        <style>
            .top-bar{
                width:100%;
                text-align: center;
                padding: 25px;
                background-color: #f90000;
            }
            
            .top-bar p{
                font-size: 25px;
                font-family: Arial;
                text-decoration: none;
            }
            .dropbtn {
                background-color: #4CAF50;
                color: white;
                padding: 16px;
                font-size: 16px;
                border: none;
                cursor: pointer;
                font-family: Arial;
                text-decoration: none;
            }
            
            .dropdown {
                position: relative;
                display: inline-block;
            }
            
            .dropdown-content {
                display: none;
                position: absolute;
                background-color: #f9f9f9;
                min-width: 160px;
                box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
                z-index: 1;
            }
            
            .dropdown-content a {
                color: black;
                padding: 12px 16px;
                text-decoration: none;
                display: block;
            }
            
            .dropdown-content a:hover {background-color: #f1f1f1}
            
            .dropdown:hover .dropdown-content {
                display: block;
            }
            
            .dropdown:hover .dropbtn {
                background-color: #3e8e41;
            }
        </style>
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
    </head>
    <body>
        <div class="top-bar">FLOR DE LIZ</div>
        <div class="dropdown">
<!--            <button class="dropbtn"><div><a href="<%=request.getContextPath() %>/home.jsp">Home</a></div></button>-->
            <a href="<%=request.getContextPath() %>/home.jsp" class="dropbtn">Home</a>
        </div>
        <div class="dropdown">
            <button class="dropbtn">Clientes</button>
            <div class="dropdown-content">
                <a href="<%=request.getContextPath() %>/cliente/ControleCliente?acao=consultar">Listar Clientes</a>
                <a href="<%=request.getContextPath() %>/cliente/ControleCliente?acao=buscar">Buscar Cliente</a>
                <a href="<%=request.getContextPath() %>/cliente/ControleCliente?acao=incluir">Incluir Cliente</a>
            </div>
        </div>
        <div class="dropdown">
            <button class="dropbtn">Pedidos</button>
            <div class="dropdown-content">
                <a href="<%=request.getContextPath() %>/pedido/ControlePedido?acao=consultar">Listar Pedidos</a>
                <a href="<%=request.getContextPath() %>/pedido/ControlePedido?acao=buscar">Buscar Pedido</a>
                <a href="<%=request.getContextPath() %>/pedido/ControlePedido?acao=inserir">Incluir Pedido</a>
            </div>
        </div>
        <div class="dropdown">
            <button class="dropbtn">Lotes de Produção</button>
            <div class="dropdown-content">
                <a href="<%=request.getContextPath() %>/lote/ControleLote?acao=consultar">Listar Lotes de Produção</a>
                <a href="<%=request.getContextPath() %>/lote/ControleLote?acao=buscar">Buscar Lote de Produção</a>
                <a href="<%=request.getContextPath() %>/lote/ControleLote?acao=inserir">Incluir Lote de Produção</a>
            </div>
        </div>
        <div class="dropdown">
            <button class="dropbtn">Modelos de Calçados</button>
            <div class="dropdown-content">
                <a href="#">Listar Modelos</a>
                <a href="#">Buscar Modelo</a>
                <a href="#">Incluir Modelo</a>
            </div>
        </div>
        <div class="dropdown">
            <button class="dropbtn">Itens de Estoque</button>
            <div class="dropdown-content">
                <a href="#">Listar Itens de Estoque</a>
                <a href="#">Buscar Item de Estoque</a>
                <a href="#">Incluir Item de Estoque</a>
            </div>
        </div>
        <div class="dropdown">
            <a href="<%=request.getContextPath() %>/home.jsp" class="dropbtn">Sobre Nós</a>
        </div>
        
        
        