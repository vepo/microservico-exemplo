# Exemplo de Microserviços

Trabalho do Grupo 1 FIAP 4ASOO 

* Bruno Ribeiro Santiago - brunorsantiago_at_gmail.com
* Rogério Alves Cunha - rogerio.cunha_at_conductor.com.br
* Victor Emanuel Perticarrari Osório - victor.perticarrari_at_gmail.com
* Vitor Barbosa Paiva - paiva.vb_at_gmail.com

## Escolhas Arquiteturais

Para desenvolver esse exemplo de microservices, vamos escolhoer o Framework [Quarkus.io](https://quarkus.io/) por ser um framework que segue especificações abertas, com facilidade de se criar plugins e bibliotecas, com baixo uso de mémoria e otimizado para serviços Cloud/Kubernetes Native.

Para base de dados, escolhemos o [PostgreSQL](https://www.postgresql.org/) por ser uma base de dados relacionado segura e escalável. O Quarkus oferece suporte a um enorme catalogo bases relacionais através do JDBC e de bases NoSQL por meio dos plugins e bibliotecas existentes. O suporte Apache Kafka também é disponibilizado e os serviços Pub/Sub também.

### Requisitos

* Maven
* Java 11
* Docker

## Execução

Para executar, é preciso criar o container docker segundo o comando abaixo.

```cmd
docker run --rm --name pagamentos-db -e POSTGRES_USER=pagamentos -e POSTGRES_PASSWORD=password -e POSTGRES_DB=pagamentos -p 5432:5432 -d postgres:13-alpine
```

Para iniciar a aplicação em modo de desenvolvimento:

```bash
mvn clean quarkus:dev
```
Para iniciar a aplicação:

```bash
mvn clean package
java -jar target/quarkus-app/quarkus-run.jar
```

Ao criar o pacote, toda aplicação está em `target/quarkus-app`.

## Metadados do serviço

O serviço em tempo de execução provê uma série de metadados que são listados abaixo:

| Metadado | URL |
|----------|-----|
| Health Check | http://localhost:8080/q/health |
| Health Check - Kubernetes Liveness | http://localhost:8080/q/health/live |
| Health Check - Kubernetes Readiness | http://localhost:8080/q/health/ready |
| Health Check | http://localhost:8080/q/health |
| Métricas de Execução | http://localhost:8080/q/metrics |
| Documentação OpenAPI - UI | http://localhost:8080/q/swagger-ui/#/ |
| Documentação OpenAPI - YAML | http://localhost:8080/q/openapi |

## Containers

Para criar imagem docker

```bash
mvn clean package -Dquarkus.container-image.build=true
```

## Postman

Para acessar os testes no Postman, use o link: https://www.getpostman.com/collections/efca6b85e988bf45810e

[![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/efca6b85e988bf45810e?action=collection%2Fimport)

## Limitações

Esse exemplo não consta com:

* Testes unitários
* Testes de integração
* Serviço de mensageria