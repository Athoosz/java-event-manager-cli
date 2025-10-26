package com.athoosz.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseInitializer {
  public static void criarTabelaEventosSeNaoExistir() {
    String sql = "CREATE TABLE IF NOT EXISTS eventos ("
        + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
        + "nome TEXT NOT NULL,"
        + "descricao TEXT,"
        + "data DATE NOT NULL,"
        + "local TEXT,"
        + "capacidadePessoas INTEGER NOT NULL"
        + ")";
    try (Connection conn = FabricaJDBC.conexao()) {
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.execute();
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao criar tabela de eventos: " + e.getMessage(), e);
    }
  }
}