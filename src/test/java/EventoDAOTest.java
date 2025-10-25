import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.dao.EventoDAO;
import com.example.database.FabricaJDBC;
import com.example.model.Evento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EventoDAOTest {
  private EventoDAO dao;
  private String banco = "eventos";

  @BeforeEach
  void setUp() {
    dao = new EventoDAO();
    criarTabelaSeNaoExistir();
    limparTabela();
  }

  @AfterEach
  public void tearDown() {
    limparTabela();
  }

  private void criarTabelaSeNaoExistir() {
    String sql =
        "CREATE TABLE IF NOT EXISTS "
            + banco
            + "("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "nome TEXT,"
            + "descricao TEXT,"
            + "data DATE,"
            + "local TEXT"
            + ")";
    try (Connection conn = FabricaJDBC.conexao()) {
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.execute();
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao criar tabela: " + e.getMessage(), e);
    }
  }

  private void limparTabela() {
    String sql = "DELETE FROM " + banco;
    try (Connection conn = FabricaJDBC.conexao()) {
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.execute();
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao criar tabela: " + e.getMessage(), e);
    }
  }

  @Test
  void testCriarEListarEventos() {
    Evento evento = new Evento();
    evento.setNome("Evento1");
    evento.setDescricao("descricao");
    evento.setData(LocalDate.now());
    evento.setLocal("rua1");

    dao.criarEvento(evento);

    List<Evento> lista = dao.listarEventos();
    assertFalse(lista.isEmpty(), "Lista não deve estar vazia");
    boolean existe = lista.stream().anyMatch(e -> evento.getNome().equals(e.getNome()));
    assertTrue(existe, "Evento criado deve existir na lista");
  }

  @Test
  void testBuscarEventoPorId() {
    Evento evento = new Evento();
    evento.setNome("Evento2");
    evento.setDescricao("descricao2");
    evento.setData(LocalDate.now());
    evento.setLocal("rua2");

    dao.criarEvento(evento);

    List<Evento> lista = dao.listarEventos();
    Evento eventoCriado = lista.stream()
        .filter(e -> evento.getNome().equals(e.getNome()))
        .findFirst()
        .orElse(null);

    assertTrue(eventoCriado != null, "Evento criado deve existir na lista");

    Evento eventoBuscado = dao.buscarEventoPorId(eventoCriado.getId());
    assertTrue(eventoBuscado != null, "Evento buscado não deve ser nulo");
    assertTrue(eventoBuscado.getNome().equals(evento.getNome()), "Nomes devem ser iguais");
  }

  @Test
  void testDeletarEvento() {
    Evento evento = new Evento();
    evento.setNome("Evento3");
    evento.setDescricao("descricao3");
    evento.setData(LocalDate.now());
    evento.setLocal("rua3");

    dao.criarEvento(evento);

    List<Evento> lista = dao.listarEventos();
    Evento eventoCriado = lista.stream()
        .filter(e -> evento.getNome().equals(e.getNome()))
        .findFirst()
        .orElse(null);

    assertTrue(eventoCriado != null, "Evento criado deve existir na lista");

    dao.deletarEvento(eventoCriado.getId());

    Evento eventoDeletado = dao.buscarEventoPorId(eventoCriado.getId());
    assertTrue(eventoDeletado == null, "Evento deve ter sido deletado");
  }

  @Test
  void testAtualizarEvento() {
    Evento evento = new Evento();
    evento.setNome("Evento4");
    evento.setDescricao("descricao4");
    evento.setData(LocalDate.now());
    evento.setLocal("rua4");

    dao.criarEvento(evento);

    List<Evento> lista = dao.listarEventos();
    Evento eventoCriado = lista.stream()
        .filter(e -> evento.getNome().equals(e.getNome()))
        .findFirst()
        .orElse(null);

    assertTrue(eventoCriado != null, "Evento criado deve existir na lista");

    eventoCriado.setNome("Evento4Atualizado");
    dao.atualizarEvento(eventoCriado);

    Evento eventoAtualizado = dao.buscarEventoPorId(eventoCriado.getId());
    assertTrue(eventoAtualizado != null, "Evento atualizado não deve ser nulo");
    assertTrue(eventoAtualizado.getNome().equals("Evento4Atualizado"),
        "Nome do evento deve ter sido atualizado");
  }
}
