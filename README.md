# Exemplo de Microserviços

Trabalho do Grupo 1 FIAP 4ASOO 

* Bruno Ribeiro Santiago - brunorsantiago_at_gmail.com
* Rogério Alves Cunha - rogerio.cunha_at_conductor.com.br
* Victor Emanuel Perticarrari Osório - victor.perticarrari_at_gmail.com
* Vitor Barbosa Paiva - paiva.vb_at_gmail.com

## Escolhas Arquiteturais

## Execução

```cmd
docker run --rm --name pagamentos-db -e POSTGRES_USER=pagamentos -e POSTGRES_PASSWORD=password -e POSTGRES_DB=pagamentos -p 5432:5432 -d postgres:13-alpine
mvn clean quarkus:dev
```
