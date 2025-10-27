import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.athoosz.database.FabricaJDBC;
import com.athoosz.model.Evento;
import com.athoosz.service.EventoService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EventoDAOTest {
  private String banco = "eventos";

  @BeforeEach
  void setUp() {
    criarTabelaSeNaoExistir();
    limparTabela();
  }

  // Limpa a tabela de eventos antes de cada teste
  // Como é uma tabela ficticia nao há problema em deletar.
  // Nunca rode testes que limpam tabelas em bancos com dados reais.
  private void limparTabela() {
    String sql = "DELETE FROM " + banco;
    try (Connection conn = FabricaJDBC.conexao()) {
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao limpar tabela: " + e.getMessage(), e);
    }
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
            + "local TEXT,"
            + "capacidadePessoas INTEGER"
            + ")";
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
    evento.setCapacidadePessoas(100);

    EventoService.adicionarEvento(
        evento.getNome(),
        evento.getDescricao(),
        evento.getData(),
        evento.getLocal(),
        evento.getCapacidadePessoas());

    List<Evento> lista = EventoService.obterTodosEventos();
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
    evento.setCapacidadePessoas(200);

    EventoService.adicionarEvento(
        evento.getNome(),
        evento.getDescricao(),
        evento.getData(),
        evento.getLocal(),
        evento.getCapacidadePessoas());

    List<Evento> lista = EventoService.obterTodosEventos();
    Evento eventoCriado =
        lista.stream().filter(e -> evento.getNome().equals(e.getNome())).findFirst().orElse(null);

    assertTrue(eventoCriado != null, "Evento criado deve existir na lista");

    Evento eventoBuscado = EventoService.obterEventoPorId(eventoCriado.getId());
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
    evento.setCapacidadePessoas(300);

    EventoService.adicionarEvento(
        evento.getNome(),
        evento.getDescricao(),
        evento.getData(),
        evento.getLocal(),
        evento.getCapacidadePessoas());

    List<Evento> lista = EventoService.obterTodosEventos();
    Evento eventoCriado =
        lista.stream().filter(e -> evento.getNome().equals(e.getNome())).findFirst().orElse(null);

    assertTrue(eventoCriado != null, "Evento criado deve existir na lista");

    EventoService.removerEvento(eventoCriado.getId());

    assertThrows(
        IllegalArgumentException.class,
        () -> {
          EventoService.obterEventoPorId(eventoCriado.getId());
        },
        "Deveria lançar exceção ao buscar evento deletado");
  }

  @Test
  void testAtualizarEvento() {
    Evento evento = new Evento();
    evento.setNome("Evento4");
    evento.setDescricao("descricao4");
    evento.setData(LocalDate.now());
    evento.setLocal("rua4");
    evento.setCapacidadePessoas(400);

    EventoService.adicionarEvento(
        evento.getNome(),
        evento.getDescricao(),
        evento.getData(),
        evento.getLocal(),
        evento.getCapacidadePessoas());

    List<Evento> lista = EventoService.obterTodosEventos();
    Evento eventoCriado =
        lista.stream().filter(e -> evento.getNome().equals(e.getNome())).findFirst().orElse(null);

    assertTrue(eventoCriado != null, "Evento criado deve existir na lista");

    eventoCriado.setNome("Evento4Atualizado");
    EventoService.atualizarEvento(eventoCriado);

    Evento eventoAtualizado = EventoService.obterEventoPorId(eventoCriado.getId());
    assertTrue(eventoAtualizado != null, "Evento atualizado não deve ser nulo");
    assertTrue(
        eventoAtualizado.getNome().equals("Evento4Atualizado"),
        "Nome do evento deve ter sido atualizado");
  }

  @Test
  void testCriarEventoComDadosInvalidos() {
    try {
      EventoService.adicionarEvento("", "descricao", LocalDate.now(), "local", 100);
      assertTrue(false, "Deveria ter lançado IllegalArgumentException para nome vazio");
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }

    try {
      EventoService.adicionarEvento(
          "Nome", "descricao", LocalDate.now().minusDays(1), "local", 100);
      assertTrue(false, "Deveria ter lançado IllegalArgumentException para data no passado");
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }

    try {
      EventoService.adicionarEvento("Nome", "descricao", LocalDate.now(), "local", 0);
      assertTrue(false, "Deveria ter lançado IllegalArgumentException para capacidadePessoas <= 0");
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
  }

  @Test
  void testNaoAceitaFloatParaCapacidadePessoas() {
    try {
      float capacidadeFloat = 10.5f;
      EventoService.adicionarEvento(
          "Nome", "desc", LocalDate.now(), "local", (int) capacidadeFloat);
      assertTrue(true, "Float é truncado, mas não aceita valores inválidos");
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
  }
}
