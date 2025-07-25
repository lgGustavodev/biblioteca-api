# Biblioteca Digital – API REST com Scraping

Uma aplicação em **Java 17** que oferece um sistema de biblioteca digital com CRUD para autores, categorias e livros, além da importação de livros via scraping de sites externos.

---

## Tecnologias Utilizadas

- Java 17  
- Spring Boot 3.x  
- Spring Web  
- Spring Data JPA  
- H2 Database (in-memory)  
- Maven  
- JSoup (para scraping)  
- Swagger (documentação)  

---

## Pré-requisitos

- Java 17+ instalado  
- Maven instalado  

---

## Instalação e Execução

```bash
# Clone o repositório
git clone https://github.com/lgGustavodev/biblioteca-api.git

# Acesse o diretório do projeto
cd biblioteca-api

# Execute a aplicação
./mvnw spring-boot:run
