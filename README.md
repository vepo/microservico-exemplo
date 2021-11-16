# Exemplo de Microserviços

[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=vepo_microservico-exemplo&metric=bugs)](https://sonarcloud.io/dashboard?id=vepo_microservico-exemplo) [![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=vepo_microservico-exemplo&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=vepo_microservico-exemplo) [![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=vepo_microservico-exemplo&metric=sqale_index)](https://sonarcloud.io/dashboard?id=vepo_microservico-exemplo)

## Escolhas Arquiteturais

Para desenvolver esse exemplo de microservices, vamos escolhoer o Framework [Quarkus.io](https://quarkus.io/) por ser um framework que segue especificações abertas, com facilidade de se criar plugins e bibliotecas, com baixo uso de mémoria e otimizado para serviços Cloud/Kubernetes Native.

Para base de dados, escolhemos o [PostgreSQL](https://www.postgresql.org/) por ser uma base de dados relacionado segura e escalável. O Quarkus oferece suporte a um enorme catalogo bases relacionais através do JDBC e de bases NoSQL por meio dos plugins e bibliotecas existentes. O suporte Apache Kafka também é disponibilizado e os serviços Pub/Sub também.

### Requisitos

* Maven
* Java 11
* Quarkus
* JPA + Panache
* Jakarta Bean Validation
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

* Testes unitários (JUnit 5)
* Testes de integração (Testcontainers)
* Configurar Cobertura de Testes (JaCoCo)
* Configurar CI/CD
* Serviço de Mensageria

## Separação de Camadas

Esse sistema não divide as camadas usando pacotes, os pacotes são a representação de unidades lógicas contendo todas as camadas. Para esse exemplo apenas implementamos Pagamentos e Perfil, o modelo do dominio não é o melhor, é apenas demonstrativo.

| Camadas | Classes |
|---------|---------|
| Domain | Perfil, Pagamento |
| Repository | Implementado pelo Panache, encapsulado pelas classes de Dominio |
| Service | PagamentoService |
| Controller | PagamentoEndpoint, CriarPagamentoRequest, PagamentoResponse |

As validações do Controller são implementadas na modelagem dos dados `CriarPagamentoRequest` e `PagamentoResponse` usando Jakarta Bean Validation.

## Criar imagem Docker

```bash
mvn clean package -Dquarkus.container-image.build=true
```

## Observabilidade

As métricas podem ser coletadas usando http://localhost:8080/q/metrics e podem ser consultadas usando o [Prometheus](https://prometheus.io/docs/introduction/overview/) ou qualquer serviço de monitoramento em cloud (como o CloudWatch).

```
# HELP http_server_requests_seconds  
# TYPE http_server_requests_seconds summary
http_server_requests_seconds_count{method="GET",outcome="SUCCESS",status="200",uri="/pagamento",} 3.0
http_server_requests_seconds_sum{method="GET",outcome="SUCCESS",status="200",uri="/pagamento",} 3.1490952
http_server_requests_seconds_count{method="GET",outcome="SUCCESS",status="200",uri="/pagamento/perfil/{idPagador}",} 5.0
http_server_requests_seconds_sum{method="GET",outcome="SUCCESS",status="200",uri="/pagamento/perfil/{idPagador}",} 0.2202462
http_server_requests_seconds_count{method="PUT",outcome="SUCCESS",status="200",uri="/pagamento",} 10.0
http_server_requests_seconds_sum{method="PUT",outcome="SUCCESS",status="200",uri="/pagamento",} 0.3782745
```

## Documentação

A documentação da API é gerada automáticamente e pode ser acessa pelas interfaces [web](http://localhost:8080/q/swagger-ui/#/) ou [YAML](http://localhost:8080/q/openapi).

![Interface web com Swagger](/recursos/swagger.png)
