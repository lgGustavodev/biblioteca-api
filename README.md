📚 Biblioteca Digital - API REST com Scraping

Projeto desenvolvido como parte de um case técnico para vaga de Desenvolvedor Java Júnior. A aplicação permite:

CRUD de autores e categorias

Importação de livros via scraping de sites externos

Consulta e cadastro de livros

🚀 Tecnologias Utilizadas

Java 17

Spring Boot 3.x

Spring Web

Spring Data JPA

H2 Database (em memória)

Maven

JSoup (para scraping)

Swagger (documentação)

🛠️ Como Executar o Projeto

Pré-requisitos

Java 17+ instalado

Maven instalado

Passos

# Clone o projeto
https://github.com/seu-usuario/seu-repositorio.git

# Acesse o diretório do projeto
cd biblioteca-api

# Execute a aplicação
./mvnw spring-boot:run

🌐 Acesso Rápido

Swagger: http://localhost:8080/swagger-ui/index.html

H2 Console: http://localhost:8080/h2-console

JDBC URL: jdbc:h2:mem:testdb

User: sa

Senha: (em branco)

📌 Endpoints Disponíveis

🧑‍💼 Autores

Método

Endpoint

Descrição

POST

/api/autores

Cadastrar novo autor

GET

/api/autores

Listar todos os autores

🗂️ Categorias

Método

Endpoint

Descrição

POST

/api/categorias

Cadastrar nova categoria

GET

/api/categorias

Listar todas as categorias

📘 Livros

Método

Endpoint

Descrição

POST

/api/livros

Cadastrar novo livro (manual)

GET

/api/livros

Listar todos os livros

GET

/api/livros/{id}

Detalhar livro por ID

🌐 Scraping

Método

Endpoint

Descrição

POST

/api/livros/importar

Importar livro via URL externa (scraping)

🔁 Fluxo de Testes com Postman

1. Criar Autor

POST /api/autores
Content-Type: application/json

{
  "nome": "Luiz Gustavo",
  "email": "luizgustave.luiz.com.br",
  "dataNascimento": "2010-05-20"
}

2. Criar Categoria

POST /api/categorias
Content-Type: application/json

{
  "nome": "Ficção Histórica ",
  "descricao": " Ficção Histórica é um gênero literário que mistura fatos e personagens reais com narrativas fictícias.
 "
}

3. Importar Livro via Scraping

POST http://localhost:8080/api/categorias

{
  "nome": "Ficção Histórica",
  "descricao": "é um gênero literário que mistura fatos e personagens reais com narrativas fictícias."
}

Observação: use os autorId e categoriaId retornados nos cadastros anteriores.

4. Verificar os Livros Cadastrados

GET /api/livros

🔍 Exemplo de URL utilizada no Scraping

Utilizamos o site de testes:

https://books.toscrape.com/catalogue/the-grand-design_405/index.html

Através do scraping, são extraídas as seguintes informações:

Título do Livro

Preço

Um ISBN aleatório gerado no back-end

Ano fictício de publicação

📂 Estrutura do Projeto

📦 com.biblioteca
├── controller      // Camadas REST
├── dto             // DTOs para entrada/saída
├── entity          // Entidades JPA
├── repository      // Interfaces do Spring Data
├── service         // Lógicas de negócio + scraping
└── BibliotecaApiApplication.java // Main

🧪 Arquivo de Testes

✅ Collection Postman disponível no projeto em: documentacao/BibliotecaAPI.postman_collection.json

✅ Melhorias Futuras

Obter autor e categoria reais do livro durante scraping (atualmente fictício)

Implementar autenticação via Spring Security

Deploy automático com Docker + Railway ou Render

Testes unitários e integração com JUnit + Mockito

👨‍💻 Para o Recrutador

O projeto está pronto para avaliação. Todos os endpoints funcionais.

✅ Swagger disponível para testes
✅ Scraping funcional usando JSoup
✅ README completo como tutorial
✅ Collection Postman pronta

📎 Contato

lggustavodev@gmail.com

