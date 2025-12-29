#  API de Autenticação com Spring Boot, JWT e PostgreSQL

Esta é uma API REST desenvolvida em Spring Boot com autenticação baseada em JWT (JSON Web Token), controle de acesso por roles (USER / ADMIN), documentação via Swagger e persistência em PostgreSQL.

---

##  Funcionalidades

-  Registro de usuários  
-  Login com geração de **Access Token + Refresh Token**  
-  Endpoint para **refresh do token**  
-  Logout  
-  Endpoint `/me` para retornar os dados do usuário autenticado  
-  Acesso restrito a ADMIN (`/admin/**`)  
-  Acesso restrito a USER e ADMIN (`/user/**`)  
-  JWT Filter + SecurityFilterChain configurados  
-  Banco de dados PostgreSQL  
-  Documentação em Swagger  
-  Exception Handler com respostas amigáveis  

---

##  Tecnologias Utilizadas

- Java 21 
- Spring Boot 3.5.9
- Spring Security
- JWT
- JPA / Hibernate
- PostgreSQL
- Lombok
- Swagger / Springdoc OpenAPI

---

## Banco de Dados

###  Configuração do `application.properties`

spring.application.name=Security


# =============================
# POSTGRESQL
# =============================
spring.datasource.url=jdbc:postgresql://localhost:5432/security_api
spring.datasource.username=user
spring.datasource.password=1234567
spring.datasource.driver-class-name=org.postgresql.Driver


# =============================
# JPA / HIBERNATE
# =============================
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true


# =============================
# LOG
# =============================
logging.level.com.Security.Security=INFO
logging.level.org.springframework.security=DEBUG


# =============================
# JWT
# =============================
jwt.secret=chave-super-secreta-com-32-caracteres-ou-mais
security.jwt.refresh-expiration=604800000


# BANCO UTILIZADO PARA TESTES LOCAIS
# spring.h2.console.enabled=true
# spring.h2.console.path=/h2-console
# spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
# spring.datasource.driver-class-name=org.h2.Driver
# spring.datasource.username=sa
# spring.datasource.password=
# spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

---

##  Endpoints principais
Autenticação:

POST: /auth/register - Cria um novo usuário

POST: /auth/login - Autêntica e retorna tokens

POST: /auth/refresh - Gera novo Access Token

POST: /auth/logout - Efetua logout

Protegidos: 
GET: /me - USER/ADMIN 

GET: /user/** - USER/ADMIN

GET: /admin/** - apenas ADMIN

Tokens JWT devem ser enviados no header - Authorization: Bearer access_token

---

##  Swagger:
Após iniciar o projeto, acesse: http://localhost:8080/swagger-ui.html

---

##  Segurança:
- API stateless


- Passwords armazenados com hash (BCrypt)


- JWT com expiração


- Refresh Token separado


- Controle de acesso por Role


Autor:
Alisson Dias Pinheiro
📧 Email: Alissondias7@outlook.com	
 🔗 LinkedIn: https://www.linkedin.com/in/alisson-dias-0a8b77356/
 🐙 GitHub: https://github.com/AlissonDiaspi







  









