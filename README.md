# Prova Attornatus
## Spring Boot - CRUD

### Desafio Java:

Usando Spring boot, crie uma API simples para gerenciar Pessoas. Esta API deve permitir:  
 - Criar uma pessoa
 - Editar uma pessoa
 - Consultar uma pessoa
 - Deletar uma Pessoa
 - Listar pessoas


 - Criar endereço para pessoa
 - Listar endereços da pessoa
 - Poder informar qual endereço é o principal da pessoa

Uma Pessoa deve ter os seguintes campos:  
 - Nome
 - Data de nascimento
 - Endereço:
   -> Logradouro
   -> CEP (Tamanho deve ser igual a 9)
   -> Número (Tamanho deve ser maior que 3 e menor que 10)
   -> Cidade
   -> Principal


***OBS.:*** O endereço possui o campo *Main* indicando que é o endereço principal da pessoa. Sendo possível setar para true na criação de endereço, caso já exista um endereço principal, o endereço antigo é atualizado para false e o novo passa ser o principal.

### Foi gerado um arquivo de Postman com todos endpoints para testes na raiz do projeto: *Prova Attornatus.postman_collection.json*
 Para utilizar a API a URL é *http://localhost:8080* seguido da rota '*/pessoas*' ou '*/enderecos*'

 O acesso do banco H2 é *http://localhost:8080/h2-console* 
 - JDBC Url: jdbc:h2:mem:provadb
 - User: sa 
 - Password: sa

