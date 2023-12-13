# Back-End para ChatApp

![Static Badge](https://img.shields.io/badge/Java-Jdk?logo=openjdk&logoColor=white&labelColor=orange&color=orange)
![Static Badge](https://img.shields.io/badge/Spring_Boot-Spring?logo=Spring&logoColor=white&labelColor=green&color=green)
![Static Badge](https://img.shields.io/badge/JWT-JWT?logo=JSON%20Web%20Tokens&logoColor=white&labelColor=black&color=black)
![Static Badge](https://img.shields.io/badge/PostgreSQL-PostgreSQl?logo=PostgreSQL&logoColor=white&labelColor=blue&color=blue)

Este projeto é uma API Rest com Web Socket para um aplicativo de chat contruído com: **Java, Spring Boot, PostgreSQL, Spring Security com JWT para authenticação 
e Web Socket para comunicação em tempo real.**

## Instalação 
 - Clone o repositório
 - Instale as dependências com o Maven
 - Instale [PostgresSQL](https://www.postgresql.org/)

## Modo de uso
 - Inicie a aplicação com o Maven
 - A API será acessível em http://localhost:8080
   
Obs: O Web Socket está implementado de forma a funcionar de acordo com as requisições nos Endpoints sendo verificado e salvos os dados no banco de dados
e retornando também para os usuários conectados em tempo real.

## API Endpoints
 - GET /chatroom/rooms - Retorna uma lista com todas as salas de bate papo. (Usuário autenticado)
 - GET /chatroom/{id} - Retorna uma sala de acordo com o id. (Usuário autenticado)
 - GET /chatroom/{id}/messages - Retorna uma lista com todas as mensagens de acordo com o id da sala. (Usuário autenticado)
 - GET /messages/{id} - Retorna uma mensagem com autor de acordo com o id da mensagem procurada. (Usuário autenticado)
 - POST /chatroom - Registra uma nova sala. (Usuário autenticado)
 - POST /messages - Registra uma nova mensagem. (Usuário autenticado)
 - POST /auth/register - Registra um novo usuário. (Permite todos)
 - POST /auth/login - Loga no app retornando um token para o usuário. (Permite todos)

## Autenticação
A API utiliza Spring Security para o controle de autenticação. 
Funções disponíveis:
 - USER -> Função padrão para um usuário logado.
 - ADMIN -> Função acima de USER (Sem utilização até então)

Para acessar os Endpoist bloqueados como ADMIN ou USER é necessário informar corretamente as credenciais (Token) no cabeçalho (Header) do pedido.

## Banco de dados
O projeto foi construído utilizando [PostgresSQL](https://www.postgresql.org/) como banco de dados.

## Contribuindo
Contruibuições são bem-vindas!
