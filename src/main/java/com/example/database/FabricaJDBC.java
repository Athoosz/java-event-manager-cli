package com.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FabricaJDBC {
  private static final String URL = "jdbc:sqlite:eventos.db";

  public static Connection conexao() {
    Connection connection = null;
    try {
      connection = DriverManager.getConnection(URL);
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao abrir a conexão: " + e.getMessage());
    }
    return connection;
  }

  public static void fecharConexao(Connection connection) {
    try {
      if (connection != null && !connection.isClosed()) {
        connection.close();
      }
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao fechar a conexão: " + e.getMessage());
    }
  }
}
