
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/style/estiloPagina.css">
        
        
    </head>
    <body>
        <div class="top-bar shadow text3">FLOR DE LIZ</div>
        <div class="script"><span>Shoe Shop</span></div>
        <div class="dropdown">
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
        
        
        