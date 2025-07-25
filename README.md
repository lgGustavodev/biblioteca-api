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
git clone https://github.com/seu-usuario/seu-repositorio.git

# Acesse o diretório do projeto
cd biblioteca-api

# Execute a aplicação
./mvnw spring-boot:run
```

---

## Acesso Rápido

| Recurso        | URL                                         | Observações                      |
|----------------|---------------------------------------------|---------------------------------|
| Swagger UI     | [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html) | Documentação e testes interativos |
| H2 Console     | [http://localhost:8080/h2-console](http://localhost:8080/h2-console) | JDBC URL: `jdbc:h2:mem:testdb`<br>User: `sa`<br>Senha: (vazio) |

---

## Endpoints Disponíveis

### Autores

| Método | Endpoint      | Descrição         |
|--------|---------------|-------------------|
| POST   | `/api/autores`| Cadastrar autor   |
| GET    | `/api/autores`| Listar todos autores |

### Categorias

| Método | Endpoint          | Descrição           |
|--------|-------------------|---------------------|
| POST   | `/api/categorias` | Cadastrar categoria |
| GET    | `/api/categorias` | Listar todas categorias |

### Livros

| Método | Endpoint         | Descrição                   |
|--------|------------------|-----------------------------|
| POST   | `/api/livros`    | Cadastrar livro manualmente |
| GET    | `/api/livros`    | Listar todos os livros      |
| GET    | `/api/livros/{id}` | Detalhar livro por ID       |

### Scraping

| Método | Endpoint              | Descrição                         |
|--------|-----------------------|----------------------------------|
| POST   | `/api/livros/importar`| Importar livro via URL externa (scraping) |

---

## 🚀 Funcionalidade Destaque: Importação de Livros via Scraping

Uma das principais funcionalidades deste projeto é a capacidade de importar dados de livros diretamente de sites externos através de **scraping** usando a biblioteca **JSoup**.

### Como funciona?

- Você envia uma requisição POST para o endpoint `/api/livros/importar` com a URL do livro em um site externo (exemplo: [Books to Scrape](https://books.toscrape.com)).
- O sistema faz a extração automática dos dados essenciais do livro, como:
  - **Título**  
  - **Preço**  
  - **ISBN** (gerado aleatoriamente para simular)  
  - **Ano de publicação** (fictício)  
- Os dados extraídos são vinculados ao autor e à categoria já cadastrados, facilitando o cadastro rápido e automatizado de novos livros.

### Exemplo de requisição para importar um livro

```json
POST /api/livros/importar
Content-Type: application/json

{
  "url": "https://books.toscrape.com/catalogue/the-grand-design_405/index.html",
  "autorId": 1,
  "categoriaId": 1
}
```

Essa funcionalidade traz agilidade e praticidade, evitando a necessidade de inserir manualmente todos os dados do livro.

---

## Fluxo de Testes com Postman

### 1. Cadastrar Autor

```json
POST /api/autores
Content-Type: application/json

{
  "nome": "Luiz Gustavo",
  "email": "lggustavodev@gmail.com",
  "dataNascimento": "2010-05-20"
}
```

### 2. Cadastrar Categoria

```json
POST /api/categorias
Content-Type: application/json

{
  "nome": "Ficção Histórica",
  "descricao": "Gênero que mistura fatos e personagens reais com narrativas fictícias."
}
```

### 3. Importar Livro via Scraping

```json
POST /api/livros/importar
Content-Type: application/json

{
  "url": "https://books.toscrape.com/catalogue/the-grand-design_405/index.html",
  "autorId": 1,
  "categoriaId": 1
}
```

### 4. Verificar Livros Cadastrados

```http
GET /api/livros
```

---

## Exemplo de URL para Scraping

- Site de testes:  
  https://books.toscrape.com/catalogue/the-grand-design_405/index.html

- Dados extraídos via JSoup:  
  - Título do livro  
  - Preço  
  - ISBN (gerado aleatoriamente no back-end)  
  - Ano fictício de publicação  

---

## Estrutura do Projeto

```
com.biblioteca
├── controller      // Camada REST
├── dto             // Objetos de transferência (DTOs)
├── entity          // Entidades JPA
├── repository      // Interfaces Spring Data JPA
├── service         // Lógicas de negócio e scraping
└── BibliotecaApiApplication.java // Classe principal
```

---

## Testes e Documentação

- Collection Postman disponível em:  
  `documentacao/BibliotecaAPI.postman_collection.json`

- Swagger UI para testes interativos:  
  [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

---

## Melhorias Futuras

- Obter autor e categoria reais durante o scraping  
- Autenticação e autorização com Spring Security  
- Deploy automático com Docker + Railway ou Render  
- Testes unitários e de integração com JUnit e Mockito  

---

## Contato

Em caso de dúvidas ou feedback, entre em contato:  
**lggustavodev@gmail.com**

---

*Projeto desenvolvido por Luiz Gustavo*
