import random

def gerar_cpf():
    def calcula_digito(digs):
       s = 0
       qtd = len(digs)
       for i in xrange(qtd):
          s += n[i] * (1+qtd-i)
       res = 11 - s % 11
       if res >= 10: return 0
       return res                                                                              
    n = [random.randrange(10) for i in xrange(9)]
    n.append(calcula_digito(n))
    n.append(calcula_digito(n))
    return "%d%d%d%d%d%d%d%d%d%d%d" % tuple(n)

def gerar_cnpj(punctuation = False):
    n = [random.randrange(10) for i in range(8)] + [0, 0, 0, 1]
    v = [2, 3, 4, 5, 6, 7, 8, 9, 2, 3, 4, 5, 6]
    # calcula digito 1 e acrescenta ao total
    s = sum(x * y for x, y in zip(reversed(n), v))
    d1 = 11 - s % 11
    if d1 >= 10:
      d1 = 0
    n.append(d1)
    # idem para o digito 2
    s = sum(x * y for x, y in zip(reversed(n), v))
    d2 = 11 - s % 11
    if d2 >= 10:
      d2 = 0
    n.append(d2)
    if punctuation:
      return "%d%d.%d%d%d.%d%d%d/%d%d%d%d-%d%d" % tuple(n)
    else:
      return "%d%d%d%d%d%d%d%d%d%d%d%d%d%d" % tuple(n)

lista_nomes = ['Acacio','Adriana','Adriano','Alan','Alana','Alex','Alexandre','Alice','Aline','Amanda','Ana','Ana Beatriz','Ana Carolina','Ana Clara','Ana Julia','Ana Luiza','Ana Paula','Anderson','Andre','Andressa','Antonio','Ariel','Ariovaldo','Aritana','Arthur','Artur','Asafe','Asher','Augusto','Barbara','Beatriz','Belarmino','Benicio','Benjamim','Benjamin','Bernardo','Bianca','Brenda','Breno','Bruna','Bruno','Bryan','Caio','Caleb','Camila','Carlos','Carolina','Caroline','Catarina','Caua','Cecilia','Christofer','Cristina','Daniel','Daniela','Danilo','Davi','David','Debora','Diego','Dino','Diogo','Douglas','Edson','Eduarda','Eduardo','Eloa','Emanuel','Emilly','Emily','Enzo','Erick','Ester','Fabio','Felipe','Fernanda','Fernando','Filipe','Francisco','Gabriel','Gabriela','Gael','Giovana','Giovanna','Guilherme','Gustavo','Hadassa','Heitor','Helena','Heloisa','Henrique','Hugo','Igor','Inara','Ingrid','Isaac','Isabel','Isabela','Isabella','Isadora','Isaque','Isis','Israel','Ivani','Jaqueline','Jefferson','Jessica','Jesus','Joana','Joao','Joao Pedro','Joao Vitor','Joaquim','Jonathan','Jorge','Jose','Julia','Juliana','Kaua','Kauan','Kevin','Lais','Lara','Larissa','Laura','Lavinia','Leandro','Leonardo','Leticia','Levi','Livia','Lorena','Lorenzo','Luan','Luana','Lucas','Luciana','Luciano','Lucifer','Ludimila','Luis','Luisa','Luiz','Luiza','Luna','Maicon','Manuela','Marcela','Marcelo','Marcio','Marcos','Maria','Maria Clara','Maria Eduarda','Maria Luiza','Mariana','Marina','Mateus','Matheus','Melissa','Miguel','Milena','Murilo','Natalia','Nicolas','Nicole','Noah','Obadias','Otavio','Patricia','Paulo','Pedro','Pedro Henrique','Pietro','Priscila','Rafael','Rafaela','Raissa','Raquel','Rayane','Rebeca','Renan','Renata','Renato','Ricardo','Robson','Rodrigo','Rosi','Ruan','Ryan','Sabrina','Samira','Samuel','Sara','Sarah','Saulo','Silva','Sofia','Sophia','Stephanie','Talita','Teo','Thais','Theo','Tiago','Valentina','Vanessa','Vicente','Victor','Vitor','Vitoria','Wesley','William','Yan','Yasmin','Yuri']
lista_sobrenomes = ['Alves', 'Abreu', 'Almeida', 
'Monteiro',
'Novaes',
'Mendes',
'Barros',
'Freitas',
'Barbosa',
'Pinto',
'Moura',
'Cavalcanti',
'Dias',
'Castro',
'Campos',
'Cardoso',
'da Silva',
'Silva',
'Souza',
'dos Santos',
'de Souza',
'Costa',
'Santos',
'Oliveira',
'Pereira',
'Rodrigues',
'Almeida',
'Nascimento',
'Lima',
'Araujo',
'Fernandes',
'Carvalho',
'Gomes',
'Martins',
'Rocha',
'Ribeiro',
'Rezende',
'Sales',
'Peixoto',
'Fogaca',
'Porto',
'Ribeiro',
'Duarte',
'Moraes',
'Ramos',
'Pereira',
'Ferreira',
'Silveira',
'Moreira',
'Teixeira',
'Caldeira',
'Vieira',
'Nogueira',
'da Costa',
'da Rocha',
'da Cruz',
'da Cunha',
'da Mata',
'da Rosa',
'da Mota',
'da Paz',
'da Luz',
'da Conceicao',
'das Neves',
'Fernandes',
'Goncalves',
'Rodrigues',
'Martins',
'Lopes',
'Gomes',
'Mendes',
'Nunes',
'Carvalho',
'Melo',
'Pires',
'Jesus',
'Aragao',
'Viana',
'Farias ']

ID_CONT = 0

uf_cods = [str(i) for i in range(1, 28)]
cidade = [str(i) for i in range(1, 10)]

def get_nome(nomes=lista_nomes, sobrenomes=lista_sobrenomes):
    num_nomes = ('1'*8) + ('2'*2)
    num_nomes = int(random.choice(num_nomes))

    prim_nome = ''
    for ciclo in range(num_nomes):
        prim_nome += random.choice(nomes) + ' '

    num_nomes = ('1'*40) + ('2'*55) + ('3'*5)
    num_nomes = int(random.choice(num_nomes))
    sobrenome = ''
    for ciclo in range(num_nomes):
        sobrenome += random.choice(sobrenomes) + ' '

    return prim_nome + sobrenome[:-1]

def gerar_rua():
    categs = ['flores',
              'pessoas',
              'datas',
              'outras']
    
    end = {'flores':['das Acacias', 'das Flores', 'das Camelias', 'das Orquideas', 'dos Girassois'],
           'pessoas':[get_nome() for i in range(10)],
           'datas':[str(random.choice('123546789') + ' de ' + random.choice(['Janeiro', 'Fevereiro', 'Marco', 'Abril', 'Maio', 'Junho']))],
           'outras':['da Esperanca', 'do Sossego', 'dos Prazeres', 'das Cadeiras']}

    return random.choice(['Rua ', 'Travessa ', 'Avenida ', 'Alameda ', 'Praca ']) + random.choice(end[random.choice(categs)])

def gerar_endereco():
    rua = gerar_rua()
    bairro = random.choice(['Ribeira','Novo Horizonte','Vilamar','Curiciu','Tremembe','Novo Jardim','Terralta','Farrapo','Vale Velho','Andiroba','Carmelitas','Passagem','Asuncao','Castical','Catrevas','Vila Gluto'])
    cidade = random.choice(['Mendonca - MG','Curacau - PA','Rio de Vento - RN','Troncoso - SP','Pirapari - RJ','Levilandia - RS'])

    return rua + ', ' + str(random.choice([i for i in range(999)])) + ', ' + cidade

def get_cliente(sobrenomes = lista_sobrenomes, tipo='PJ'):
    global ID_CONT
    ID_CONT += 1
    codigo = ID_CONT
    nome = get_nome()
    if tipo == 'PJ':
        empresa = "'Casas " + random.choice(lista_sobrenomes) + "'"
        cnpj = "'" + gerar_cnpj() + "'"
    else:
        empresa = 'null'
        cnpj = 'null'
        
    fone = ''
    for i in range(11):
        fone += random.choice('0123456789')

    endereco = gerar_endereco()
    cpf = gerar_cpf()
    return "insert into cliente values(" + str(codigo)+", '"+nome+"', "+empresa+", '"+fone+"', '"+endereco+"', '"+cpf+"',"+cnpj+");"
    

def get_part(tipo='PF'):
    uf_cods = [str(i) for i in range(1, 28)]
    cidade = [str(i) for i in range(1, 27)]

    nums = '0123456789'
    ddd =  ''.join([random.choice(nums) for i in range(2)])
    fone = ''.join([random.choice(nums) for i in range(9)])
    cod_cidade = random.choice(cidade)
    nome = get_nome()
    if tipo == 'PF':
        num_chars = 11
    else:
        num_chars = 14
        nome += ' - ME'
    cpf_cnpj = ''.join([random.choice(nums) for i in range(num_chars)])

    fone = ''.join([random.choice(nums) for i in range(9)])
    insert_command = "INSERT INTO PARTICIPANTE (PARTICIPANTE_NOME, PARTICIPANTE_CIDADE_CODIGO, PARTICIPANTE_CNPJ_CPF, PARTICIPANTE_TIPO, PARTICIPANTE_DDD, PARTICIPANTE_TELEFONE) VALUES('"
    dados = nome + "','" + cod_cidade + "','" + cpf_cnpj + "','" + tipo +  "','" + ddd + "','" + fone + "');"

    return insert_command + dados

def get_entradas():
    data = "'2017/" + str(random.choice([i for i in range(1,13)])) + "/" + str(random.choice([i for i in range(1,29)])) + "'"
    part_codigo = random.choice([i for i in range(1,171)])
    insert_command = "INSERT INTO ENTRADA_PRODUTO(ENTRADA_DATA, ENTRADA_PARTICIPANTE_CODIGO) VALUES ("
    dados = data + ',' + str(part_codigo) + ');\n'
    return (insert_command + dados)[:-1]

def get_itens_entrada(num_entradas):
    insert_command = ''
    for entrada in range(1, num_entradas+1):
        numitens = random.choice([i for i in range(15)])
        for i in range(numitens):
            cod_prod = random.choice([i for i in range(1,24)])
            quant = random.choice([i for i in range(1,100)])
            insert_command += "INSERT INTO ITEM_ENTRADA(ITEM_ENTRADA_CODIGO, ITEM_ENTRADA_PRODUTO_CODIGO, ITEM_ENTRADA_QUANTIDADE) VALUES(" + str(entrada) + "," + str(cod_prod) + "," + str(quant) + ");\n"

    return insert_command[:-1]

def get_saidas():
    data = "'2017/" + str(random.choice([i for i in range(1,13)])) + "/" + str(random.choice([i for i in range(1,29)])) + "'"
    part_codigo = random.choice([i for i in range(1,171)])
    insert_command = "INSERT INTO SAIDA_PRODUTO(SAIDA_DATA, SAIDA_PARTICIPANTE_CODIGO) VALUES ("
    dados = data + ',' + str(part_codigo) + ');\n'
    return (insert_command + dados)[:-1]

def get_itens_saida(num_saidas):
    insert_command = ''
    for saida in range(1, num_saidas+1):
        numitens = random.choice([i for i in range(15)])
        for i in range(numitens):
            cod_prod = random.choice([i for i in range(1,24)])
            quant = random.choice([i for i in range(1,20)])
            insert_command += "INSERT INTO ITEM_SAIDA(ITEM_SAIDA_CODIGO, ITEM_SAIDA_PRODUTO_CODIGO, ITEM_SAIDA_QUANTIDADE) VALUES(" + str(saida) + "," + str(cod_prod) + "," + str(quant) + ");\n"

    return insert_command[:-1]

import random



##num_entradas = 40
##num_saidas = 113
##
##
##for i in range(num_entradas):
##    print get_entradas()
##print get_itens_entrada(num_entradas)
##
##for i in range(num_saidas):
##    print get_saidas()
##print get_itens_saida(num_saidas)
