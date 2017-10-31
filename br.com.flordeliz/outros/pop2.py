'''create table item_estoque(item_estoque_codigo int not null constraint item_estoque_pk primary key,
                          item_estoque_tamanho int not null,
                          item_estoque_quantidade int,
                          item_estoque_preco_venda money,
                          item_estoque_modelo_codigo int references modelo_calcado(modelo_codigo) on update cascade on delete cascade,
                          constraint item_estoque_unq unique(item_estoque_tamanho, item_estoque_modelo_codigo));
'''
import random

def gerar_item_estoque(ini=1, fim=21, ini_chave=1):
    KEY = ini_chave

    preco_dic = {1:930.00,
    2:930.00,
    3:930.00,
    4:700.00,
    5:700.00,
    6:700.00,
    7:130.00,
    8:130.00,
    9:130.00,
    10:99.00,
    11:99.00,
    12:99.00,
    13:230.00,
    14:230.00,
    15:250.00,
    16:250.00,
    17:78.00,
    18:78.00,
    19:78.00,
    20:78.00}

    for mod_cod in range(ini, fim):
        tamanhos = [i for i in range(28, 45)]
        random.shuffle(tamanhos)
        for dummy_i in range(random.choice([5,6,7,8])):
            codigo = str(KEY)
            tamanho = str(tamanhos.pop())
            preco = str(round(preco_dic[mod_cod] * (1 + (random.random() / 10)), 2))
            quant = str(random.choice([i for i in range(100,700)]))
            print 'insert into item_estoque values(' + codigo + ',' + tamanho + ',' + quant + ',' + preco + ',' + str(mod_cod) + ');'
            
            KEY += 1

def gerar_lote(fim_item=125, ini_chave=1):
    KEY = ini_chave

    for cod_item in range(1,fim_item+1):
        tamanhos = [i for i in range(28, 45)]
        for dummy_i in range(random.choice([1,3,5,7])):
            cod_lote = str(KEY)
            KEY += 1
            data = "'2017/" + str(random.choice([i for i in range(1,13)])) + "/" + str(random.choice([i for i in range(1,29)])) + "'"
            tamanho = "'" + str(random.choice(tamanhos)) + "'"
            quant = str(random.choice([i for i in range(100,700)]))
            codigo_item = str(cod_item)
            print 'insert into lote_producao values(' + cod_lote + ',' + data + ',' + tamanho + ',' + quant + ',' + codigo_item + ');'
            
        
def gerar_pedido(num_pedidos = 101, num_estoque = 125):
    Key_ped = 1
    
    clientes = [i for i in range(1,251)]
    quants = [i for i in range(1,100)]
    num_itens = [i for i in range(1,10)]
    
    for num_pedido in range(1, num_pedidos):
        cod_pedido = str(num_pedido)
        desconto = str(round(random.random() / 10, 2))
        cod_cli = str(random.choice(clientes))
        #Gerando o pedido
        print 'insert into pedido values(' + cod_pedido + ',' + desconto + ',' + cod_cli + ');'
        print 'insert into entrega values (' + cod_pedido + ", 'ENCERRADO'," + cod_pedido + ');'

        
        cod_estoques = [i for i in range(1,num_estoque)]
        random.shuffle(cod_estoques)
        for num_item in range(1, random.choice(num_itens) + 1):
            cod_item = str(num_item)
            cod_estoque = str(cod_estoques.pop())
            quant = str(random.choice(quants))

            print 'insert into item_pedido values(' + cod_item + ',' + quant + ',' + cod_pedido + ',' + cod_estoque + ');'
            print 'insert into item_entrega values(' + cod_item + ',' + cod_pedido + ',' + cod_estoque + ');'
        
        
#gerar_pedido(1,125)
