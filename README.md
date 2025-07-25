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

## Funcionalidades principais

- Cadastro de Livros, Categorias e Autores
- Filtro de livros por título, autor, categoria ou ano
- Atualização e exclusão de livros
- Importação de dados de um livro via scraping (título, preço, ISBN e ano)
- Validações de regras de negócio
- Documentação da API com Swagger

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


Acesso Rápido
Recurso	URL	Observações
Swagger UI	http://localhost:8080/swagger-ui/index.html	Documentação e testes interativos
H2 Console	http://localhost:8080/h2-console	JDBC URL: jdbc:h2:mem:testdb
User: sa
Senha: (vazio)

Endpoints Disponíveis
Autores
Método	Endpoint	Descrição
POST	/api/autores	Cadastrar autor
GET	/api/autores	Listar todos autores

Categorias
Método	Endpoint	Descrição
POST	/api/categorias	Cadastrar categoria
GET	/api/categorias	Listar todas categorias

Livros
Método	Endpoint	Descrição
POST	/api/livros	Cadastrar livro manualmente
GET	/api/livros	Listar todos os livros
GET	/api/livros/{id}	Detalhar livro por ID

Scraping
Método	Endpoint	Descrição
POST	/api/livros/importar	Importar livro via URL externa (scraping)

