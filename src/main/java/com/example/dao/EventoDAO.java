package com.example.dao;

import com.example.database.FabricaJDBC;
import com.example.model.Evento;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EventoDAO implements EventoDAOInterface {

  @Override
  public void criarEvento(Evento evento) {
    try (Connection conexao = FabricaJDBC.conexao()) {
      String sql =
          "INSERT INTO eventos (nome, descricao, data, local, capacidadePessoas) VALUES (?, ?, ?,"
              + " ?, ?)";
      PreparedStatement ps = conexao.prepareStatement(sql);
      ps.setString(1, evento.getNome());
      ps.setString(2, evento.getDescricao());
      ps.setDate(3, Date.valueOf(evento.getData()));
      ps.setString(4, evento.getLocal());
      ps.setInt(5, evento.getCapacidadePessoas());
      ps.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao criar evento: " + e.getMessage());
    }
  }

  @Override
  public List<Evento> listarEventos() {
    try (Connection conexao = FabricaJDBC.conexao()) {
      String sql = "SELECT * FROM eventos";
      PreparedStatement ps = conexao.prepareStatement(sql);
      var rs = ps.executeQuery();
      List<Evento> eventos = new ArrayList<>();
      while (rs.next()) {
        Evento evento = new Evento();
        evento.setId(rs.getInt("id"));
        evento.setNome(rs.getString("nome"));
        evento.setDescricao(rs.getString("descricao"));
        evento.setData(rs.getDate("data").toLocalDate());
        evento.setLocal(rs.getString("local"));
        evento.setCapacidadePessoas(rs.getInt("capacidadePessoas"));
        eventos.add(evento);
      }
      return eventos;
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao listar eventos: " + e.getMessage());
    }
  }

  @Override
  public Optional<Evento> buscarEventoPorId(int id) {
    try (Connection conexao = FabricaJDBC.conexao()) {
      String sql = "SELECT * FROM eventos WHERE id = ?";
      PreparedStatement ps = conexao.prepareStatement(sql);
      ps.setInt(1, id);
      var rs = ps.executeQuery();
      if (rs.next()) {
        Evento evento = new Evento();
        evento.setId(rs.getInt("id"));
        evento.setNome(rs.getString("nome"));
        evento.setDescricao(rs.getString("descricao"));
        evento.setData(rs.getDate("data").toLocalDate());
        evento.setLocal(rs.getString("local"));
        evento.setCapacidadePessoas(rs.getInt("capacidadePessoas"));
        return Optional.of(evento);
      }
      return Optional.empty();
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao buscar evento por ID: " + e.getMessage());
    }
  }

  @Override
  public void atualizarEvento(Evento evento) {
    try (Connection conexao = FabricaJDBC.conexao()) {
      String sql =
          "UPDATE eventos SET nome = ?, descricao = ?, data = ?, local = ?, capacidadePessoas = ?"
              + " WHERE id = ?";
      PreparedStatement ps = conexao.prepareStatement(sql);
      ps.setString(1, evento.getNome());
      ps.setString(2, evento.getDescricao());
      ps.setDate(3, Date.valueOf(evento.getData()));
      ps.setString(4, evento.getLocal());
      ps.setInt(5, evento.getCapacidadePessoas());
      ps.setInt(6, evento.getId());
      ps.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao atualizar evento: " + e.getMessage());
    }
  }

  @Override
  public void deletarEvento(int id) {
    try (Connection conexao = FabricaJDBC.conexao()) {
      String sql = "DELETE FROM eventos WHERE id = ?";
      PreparedStatement ps = conexao.prepareStatement(sql);
      ps.setInt(1, id);
      ps.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao deletar evento: " + e.getMessage());
    }
  }
}
