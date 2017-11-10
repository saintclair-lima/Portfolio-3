# Principal

Arquivos do desenvolvimento do 3º Portfolio do curso de ADS.

Objetiva-se, nesse estudo, o planejamento, esboço/prototipação e desenvolvimento (1) das estruturas de armazenamento de dados, (2) processos de negócio envolvendo a produção, venda e entrega de produtos, (3) sistema informacional de gerenciamento dos dados modelados e implementados na camada de armazenamento de dados. De forma a prover escalabilidade e facilidade de acesso do sistema (já se antecipando à possibilidade de posterior ampliação da empresa em diversas filiais), optar-se-á pelo uso de uma aplicação web, a qual será hospedada em um servidor local ou cloud based. Destarte, poder-se-á acessar a aplicação independentemente de restrições espaciais.

Para satisfazer a exigência de levantamento e análise de requisitos, será utilizada a Unified Modeling Language – UML como ferramenta para melhor compreensão das atividades a serem realizadas pelo sistema, descrevendo e especificando os requisitos funcionais do sistema. O uso da UML proporcionará, também, refinamento gradual das especificações do sistema, caminhando na direção da implementação definitiva da solução. Para se elaborar os diagramas em notação UML utilizar-se-á a ferramenta Visual Paradigm Community Edition, que possui algumas limitações, por ser versão não paga, sem, não obstante, interferir nas metas a serem alcançadas por este trabalho.

Como base para a modelagem e gerenciamento de dados, utilizar-se-á a ferramenta BrModelo para fazer a modelagem da estrutura de dados, consistindo em um Diagrama Entidade Relacionamento – DER, tanto em uma visão conceitual quanto uma visão lógica, mais perto da implantação em um Sistema de Gerenciamento de Banco de Dados – SGBD, propriamente dito.

Optou-se por utilizar um banco de dados seguindo o modelo Relacional, utilizando a Structured Query Language – SQL como linguagem de gerenciamento e consulta de dados. O SGBD utilizado será o PostgreSQL, juntamente com o programa PgAdmin, para gerenciamento do banco em alto nível.

A aplicação será desenvolvida utilizando tecnologia Java, que se comunicará com o banco de dados subjacente por meio do Java Database Connectivity – JDBC, responsável por fazer a interface entre aplicação e dados. Isso facilitará o envio de comandos SQL para o SGBD. Essa combinação de SGBD e JDBC permite realizar a comunicação entre aplicação e banco de dados de maneira fluida, leve e robusta.

A interface entre as classes/objetos Java e a apresentação das informações ao usuário será feita por meio do uso de JavaServer Pages, encapsulando muitos dos procedimentos repetitivos e passíveis de erro que consiste da geração manual e declarativa de páginas por meio de outras classes Java. Objetiva-se seguir o Padrão Model-View-Controller – MVC no desenvolvimento. Com isso, poderá ser mantida a modularidade de partes do sistema, de maneira a facilitar a manutenção e a organização das classes/objetos e outros componentes da aplicação.

De forma a otimizar o processo de desenvolvimento, o Ambiente Integrado de Desenvolvimento (IDE) utilizado será o Netbeans, integrando-o com o WebContainer Apache Tomcat, em sua versão 7.

De maneira a garantir concisão na descrição desse trabalho, o código fonte dos principais arquivos descritos no texto pode ser encontrado nos apêndices. Foram omitidos os métodos setters e getters que não possuíam comportamento específico diferente do de um Plain Old Java Object, apresentando somente os métodos que possuem alguma lógica interna específica ou cuja apresentação seja de relevância para a compreensão da descrição.
