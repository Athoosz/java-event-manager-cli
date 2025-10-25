# Java Event Manager CLI

Aplicação de linha de comando para gerenciar eventos (recurso: Evento) usando Java 17 e SQLite.

---

## 1. Recurso escolhido: Evento

**Propriedades:**
- `id` *(inteiro)* — chave primária, gerada automaticamente pelo banco.
- `nome` *(string)* — **obrigatório**
- `descricao` *(string)* — opcional
- `data` *(date)* — **obrigatório** (formato `yyyy-MM-dd`)
- `local` *(string)* — opcional
- `capacidadePessoas` *(inteiro)* — **obrigatório** (valor > 0)

Tipos variados incluídos: **string**, **integer** e **date**.

---

## 2. Banco de Dados (SQLite)

**Arquivo:** `eventos.db`  
Local: raiz do projeto  
Acesso via classe `com.example.database.FabricaJDBC`

### Script SQL (`schema.sql`)
```sql
CREATE TABLE IF NOT EXISTS eventos (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  nome TEXT NOT NULL,
  descricao TEXT,
  data DATE NOT NULL,
  local TEXT,
  capacidadePessoas INTEGER NOT NULL
);
```

> O teste automatizado cria a tabela automaticamente, caso ainda não exista.

---

## 3. Funcionalidades da Aplicação (CLI)

No menu interativo:

- 📋 **Listar Eventos** — exibe todos os registros.
- 🔍 **Buscar Evento por ID** — informa o ID e exibe o evento correspondente.
- ➕ **Cadastrar Evento** — solicita os campos; valida obrigatórios, data e capacidade.
- ✏️ **Atualizar Evento** — solicita o ID e novos dados; mesmas validações do cadastro.
- ❌ **Deletar Evento** — remove o registro com base no ID.

### Validações principais
- `nome` e `data` são obrigatórios.  
- `data` não pode ser anterior à data atual.  
- `capacidadePessoas` deve ser maior que zero.  
- Campos opcionais (`descricao`, `local`) podem ser deixados em branco (salvos como `NULL`).

---

## 4. Linguagem e Ferramentas

- ☕ **Java 17**
- 🧱 **Maven**
- 🗃️ **SQLite (sqlite-jdbc)**
- 🧪 **JUnit 5** para testes

---

## 5. Compilação e Execução

1. Instale **JDK 17** e **Maven**.  

- Recursos para instalação:
- JDK 17 Ou superior::
  - Página de downloads : https://www.oracle.com/br/java/technologies/downloads/
  - Documentação oficial Java SE 17 (Oracle): https://docs.oracle.com/en/java/javase/17/
  - Vídeos-tutoriais (busca no YouTube): https://www.youtube.com/results?search_query=install+jdk+17+windows

- Apache Maven:
  - Página de download: https://maven.apache.org/download.cgi
  - Guia de instalação: https://maven.apache.org/install.html
  - Vídeos-tutoriais (busca no YouTube): https://www.youtube.com/results?search_query=install+apache+maven+windows

2. Você pode executar a aplicação diretamente pelo Visual Studio Code:

 -  Abra o arquivo Main.java

 -  Clique em “Run Java” (ou use Ctrl + F5)

 -  Certifique-se de selecionar a classe principal com.example.Main

 - Os testes podem ser executados clicando no ícone de ▶️ ao lado dos métodos de teste ou pela aba “Testing”.

3. Ou no terminal, na raiz do projeto:

```bash
# Compilar
mvn compile

# Executar testes
mvn test

# Empacotar dependências e classes
mvn dependency:copy-dependencies package

# Executar a aplicação (Windows)
java -cp "target/classes;target/dependency/*" com.example.Main
```

> O banco SQLite (`eventos.db`) será criado automaticamente na primeira execução.

---

## 6. Como usar (exemplos)

Ao iniciar a aplicação, um menu será exibido.

### Criar Evento
1. Escolha **"1. Criar Evento"**  
2. Informe o nome (obrigatório)  
3. Informe a descrição (ou pressione Enter)  
4. Informe a data no formato `yyyy-MM-dd` (ex: `2025-12-31`)  
5. Informe o local (ou Enter)  
6. Informe a capacidade (ex: `100`)

### Listar Eventos
- Escolha **"2. Listar Eventos"**

### Buscar por ID
- Escolha **"3. Buscar Evento por ID"** e informe o ID retornado na listagem.

### Atualizar Evento
- Escolha **"4. Atualizar Evento"**, informe o ID e insira novamente os campos.

### Deletar Evento
- Escolha **"5. Deletar Evento"** e informe o ID.

---

## 7. Testes Unitários

Arquivo: `src/test/java/EventoDAOTest.java`

Os testes:
- Criam e limpam a tabela `eventos`
- Validam operações de CRUD
- Testam as principais regras de validação

Para executar:
```bash
mvn test
```
---

## 8. Estrutura de Arquivos

```
src/
 ├── main/java/com/example/
 │   ├── controller/
 │   │   └── EventoController.java
 │   ├── dao/
 │   │   └── EventoDAO.java
 │   ├── database/
 │   │   └── FabricaJDBC.java
 │   ├── model/
 │   │   └── Evento.java
 │   ├── service/
 │   │   └── EventoService.java
 │   └── Main.java
 ├── test/java/
 │   └── EventoDAOTest.java
pom.xml
schema.sql
README.md
.gitignore
```

---
