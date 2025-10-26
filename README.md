# Java Event Manager CLI

**Java Event Manager CLI** é uma aplicação de linha de comando desenvolvida em Java 17, que permite gerenciar eventos de forma simples e eficiente utilizando um banco de dados SQLite.  

Este projeto foi desenvolvido como parte de um exercício técnico para demonstrar habilidades em desenvolvimento de software, incluindo:

- Estruturação de uma aplicação CLI (Console)
- Implementação de operações CRUD (Create, Read, Update, Delete)
- Uso de banco de dados relacional (SQLite)
- Validação de dados e regras de negócio
- Documentação

---

## 📑 Sumário
- [1. Recurso escolhido](#1-recurso-escolhido-evento)
- [2. Banco de Dados](#2-banco-de-dados-sqlite)
- [3. Funcionalidades](#3-funcionalidades-da-aplicação-cli)
- [4. Linguagem e Ferramentas](#4-linguagem-e-ferramentas)
- [5. Compilação e Execução](#5-compilação-e-execução)
- [6. Como usar](#6-como-usar-exemplos)
- [7. Testes Unitários](#7-testes-unitários)
- [8. Conteinerização com Docker](#8-conteinerização-com-docker)
- [9. Estrutura de Arquivos](#9-estrutura-de-arquivos)
- [10. Autor](#10-autor)
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
Acesso via classe `com.athoosz.database.FabricaJDBC`

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

 -  Certifique-se de selecionar a classe principal com.athoosz.Main

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
java -cp "target/classes;target/dependency/*" com.athoosz.Main
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

---

## 8. Conteinerização com Docker

### O que é Docker?
Docker é uma plataforma que permite empacotar uma aplicação e suas dependências em um “container”. Um container é um ambiente isolado e padronizado, que pode ser executado em qualquer máquina que tenha Docker instalado. Isso facilita a distribuição, execução e escalabilidade da aplicação, pois elimina problemas de configuração do ambiente.

### Dockerfile explicado linha a linha
```Dockerfile
# Imagem base com Java 17
FROM eclipse-temurin:17-jre

# Diretório de trabalho dentro do container
WORKDIR /app

# Copia as classes compiladas e dependências
COPY target/classes /app/classes
COPY target/dependency /app/dependency

# Copia o arquivo do banco de dados SQLite (persistente via volume)
COPY schema.sql /app/

# Comando para rodar a aplicação
CMD ["java", "-cp", "classes:dependency/*", "com.athoosz.Main"]
```
- **FROM**: Usa uma imagem oficial do Java 17 (JRE) como base para o container.
- **WORKDIR**: Define o diretório `/app` como local onde os comandos serão executados e arquivos serão armazenados.
- **COPY**: Copia os arquivos compilados da aplicação (`classes`), dependências externas (`dependency`) e o script de criação do banco (`schema.sql`) para dentro do container.
- **CMD**: Define o comando que será executado quando o container iniciar: roda a aplicação Java usando o classpath correto.

### Passo a passo para rodar com Docker

1. Compile o projeto e copie as dependências:
  ```powershell
  mvn clean compile dependency:copy-dependencies
  ```

2. Construa a imagem Docker:
  ```powershell
  docker build -t java-event-manager-cli .
  ```

3. Execute o container com persistência do banco:
  ```powershell
  docker run -it --rm -v ${PWD}/eventos.db:/app/eventos.db java-event-manager-cli
  ```
  - O banco de dados será salvo e reutilizado fora do container.


## 9. Estrutura de Arquivos

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
## 10. Autor
Desenvolvido por **Athoosz**  
💻 Projeto técnico desenvolvido como parte de um processo seletivo de estágio.
