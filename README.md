# GE — Sistema de Garçom Eletrônico

Disciplina: Desenvolvedor Backend

Profa. Dra. Sofia Costa Paiva

Aluno: Nícolas Georgeos Mantzos

Matrícula: 2025200254

## Introdução

O sistema modela o fluxo completo de atendimento ao cliente:

1.  Um Gerente define o Cardápio, que é organizado por Categorias e contém Itens (pratos, bebidas).
2.  Um Garçom é funcionário do Restaurante e atende várias Mesas.
3.  Um Cliente chega, ocupa uma mesa e faz Pedidos.
4.  Cada Pedido contém vários Itens do Cardápio e é enviado para a Cozinha (que é um tipo de usuário).
5.  Todos os pedidos de uma mesa são agrupados em uma Conta.
6.  Ao final, a Conta é gerenciada por um Caixa (outro tipo de usuário) e é paga usando um método de Pagamento (Dinheiro, Cartão ou Cheque).

---

## Arquitetura & Tech Stack


**Backend**
- Java 21, Spring Boot 3, Spring Web, Spring Data JPA, Validation
- HikariCP, PostgreSQL
- Autenticação com **JWT**

**Banco**
- PostgreSQL (Docker Compose já incluso no repositório)

---

## Como rodar (com Docker)

> Requer: **Docker** e **Docker Compose**.

1) Ajuste as variáveis no `docker-compose.yml` se necessário (DB, portas, secrets).

2) Suba os serviços:
```bash
docker-compose up --build -d
```

> **Nota:** na primeira subida é comum o backend iniciar antes do PostgreSQL e falhar a conexão.
> Se ocorrer erro de conexão no backend, suba novamente:
> ```bash
> docker-compose up -d
> ```
> ou reinicie apenas o serviço do backend.

3) Acesse:
- **Backend (API)**: http://localhost:8080

### Scripts de inicialização do banco (`ge-db-init`)

- A pasta **`ge-db-init`** contém scripts **.sql** que criam o banco e as tabelas, além de populá-las com alguns dados iniciais. (**usuários, categorias e tags**)
- Os usuários inseridos possuem senha padrão `usuario123` (hash BCrypt já presente nos scripts).
- Exemplos de e-mails criados:
  - `arthur_barros@scuderiagwr.com.br`
  - `calebe_pietro_bernardes@contjulioroberto.com.br`
  - `mariane-nascimento97@bemarius.com.br`
  - `filipe_mendes@zaniniengenharia.com.br`
  - `reginasabrinafernandes@geopx.com.br`
  - `luis_silva@msn.com.br`
  - `daiane-araujo71@bessa.net.br`
  - `gustavo-pereira88@limao.com.br`
  - `andre_mateus_melo@performa.com.br`
  - `emanuel_manuel_drumond@vnews.com.br`

---

## Como rodar (local / dev)

### Pré‑requisitos
- Java 21 e Maven 3.9+
- PostgreSQL (local) ou use o do Docker Compose

### 1) Banco
Crie o database e usuário (ou use o que está no Compose):
```sql
CREATE DATABASE ge;
CREATE USER ge WITH ENCRYPTED PASSWORD 'ge';
GRANT ALL PRIVILEGES ON DATABASE ge TO ge;
```

### 2) Backend
Configure as variáveis (veja seção **Variáveis de ambiente**). Em dev, você pode usar `application.yml` ou variáveis de ambiente.

Rodar:
```bash
cd ge-backend
./mvnw spring-boot:run
# ou
mvn spring-boot:run
```

API em: `http://localhost:8080`

---

## Variáveis de ambiente

**Backend (exemplos):**
```
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/ge
SPRING_DATASOURCE_USERNAME=ge
SPRING_DATASOURCE_PASSWORD=ge

# DDL (dev): validate | update | create | create-drop
SPRING_JPA_HIBERNATE_DDL_AUTO=validate

# JWT
JWT_SECRET=troque-este-segredo
JWT_EXPIRATION_MINUTES=120

# CORS (se necessário)
ALLOWED_ORIGINS=http://localhost:4200
```

---

## Comandos úteis

**Backend**
```bash
# rodar
mvn spring-boot:run

# empacotar
mvn clean package

# testes
mvn test
```

---

## Fluxos principais

### Login
- `POST /api/auth/login` → retorna **JWT**
- Front armazena o token e decodifica **payload base64url UTF‑8** para exibir o nome.

---

## Licença

Licenciado sob a **MIT License**.

```
MIT License

Copyright (c) 2025 Nícolas Mantzos

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
