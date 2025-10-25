CREATE TABLE IF NOT EXISTS eventos (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome TEXT NOT NULL,
    descricao TEXT,
    data DATETIME NOT NULL,
    local TEXT,
    capacidadePessoas INTEGER NOT NULL
);
