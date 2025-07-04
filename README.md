# 🐘 Oracle XE com Spring Boot – Mientien

Este projeto utiliza uma instância do **Oracle Database Express Edition (XE) 21c** rodando em container Docker para ser consumida por uma aplicação Spring Boot.

---

## 📦 Requisitos

- Docker instalado ([Download aqui](https://www.docker.com/products/docker-desktop))
- Conta Oracle gratuita para acesso ao Container Registry: https://container-registry.oracle.com
- Java 17+
- Maven ou Gradle

---

## 🚀 Passo a passo para rodar o banco Oracle

### 1️⃣ Autenticar no Oracle Container Registry

> A Oracle exige login para baixar a imagem oficial.

Execute:

```bashScript
docker login container-registry.oracle.com
```
Digite seu e-mail e senha cadastrados no Oracle Container Registry.

## 2️⃣ Baixar a imagem do Oracle XE

```bashScript
 docker pull container-registry.oracle.com/database/express:21.3.0-xe
 ```

## 3️⃣ Rodar o container Oracle
```bashScript
 docker run -d \
  --name oracle-db \
  -p 1521:1521 \
  -e ORACLE_PWD=mientien123 \
  container-registry.oracle.com/database/express:21.3.0-xe
 ```

# 🛠️ Configuração da aplicação Spring Boot
Você pode usar .env ou application-env.properties. O conteúdo é equivalente.

## 📁 Exemplo de .env
```env
OPENAI_API_KEY=sua-chave-da-openai
SPRING_DATASOURCE_USERNAME=system
SPRING_DATASOURCE_PASSWORD=mientien123
SPRING_DATASOURCE_URL=jdbc:oracle:thin:@localhost:1521/XEPDB1
```

## 📁 Exemplo de application.properties

```properties
spring.application.name=mientien
spring.config.import=classpath:application-env.properties

# OpenAI
spring.ai.openai.api-key=${OPENAI_API_KEY}
spring.ai.openai.chat.api-key=${OPENAI_API_KEY}
spring.ai.openai.enabled=true

# Datasource Oracle
spring.datasource.url=jdbc:oracle:thin:@localhost:1521/XEPDB1
spring.datasource.username=${SPRING_DATASOURCE_USERNAME}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver

# JPA e Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.OracleDialect

# Logging SQL
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.tool.hbm2ddl=DEBUG

```