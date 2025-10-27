package com.athoosz.service;

import com.athoosz.dao.EventoDAO;
import com.athoosz.model.Evento;
import com.athoosz.utils.ValueUtils;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class EventoService {
  private static final Logger logger = Logger.getLogger(EventoService.class.getName());
  private static EventoDAO dao = new EventoDAO();

  public static void adicionarEvento(
      String nome, String descricao, LocalDate data, String local, int capacidadePessoas) {
    if (nome == null || nome.isEmpty() || data == null || data.toString().isEmpty()) {
      logger.severe("Nome e data do evento são obrigatórios");
      throw new IllegalArgumentException("Nome e data do evento são campos obrigatórios");
    }
    if (data.isBefore(LocalDate.now())) {
      logger.severe("Data do evento no passado: " + data);
      throw new IllegalArgumentException("A data do evento nao pode ser no passado");
    }
    if (capacidadePessoas <= 0) {
      logger.severe("Capacidade inválida: " + capacidadePessoas);
      throw new IllegalArgumentException("Capacidade de pessoas deve ser maior que zero");
    }

    Evento evento = new Evento();
    evento.setNome(nome);
    evento.setDescricao(descricao);
    evento.setData(data);
    evento.setLocal(local);
    evento.setCapacidadePessoas(capacidadePessoas);
    dao.criarEvento(evento);
    logger.info("Evento criado: " + nome);
    System.out.println("Evento criado com sucesso!");
  }

  public static void listarEventos() {
    var eventos = dao.listarEventos();
    if (eventos.isEmpty()) {
      throw new IllegalArgumentException("Nenhum evento cadastrado");
    } else {
      eventos.forEach(System.out::println);
    }
  }

  public static Evento obterEventoPorId(int id) {
    Optional<Evento> eventoOptional = dao.buscarEventoPorId(id);
    return eventoOptional.orElseThrow(() -> new IllegalArgumentException("Evento nao encontrado"));
  }

  public static void removerEvento(int id) {
    Optional<Evento> eventoOptional = dao.buscarEventoPorId(id);
    if (eventoOptional.isEmpty()) {
      logger.severe("Evento não encontrado para remoção: " + id);
      throw new IllegalArgumentException("Evento não encontrado para remoção");
    }
    dao.deletarEvento(id);
    System.out.println("Evento removido com sucesso!");
  }

  public static void atualizarEvento(
      Evento eventoFromDB,
      String nome,
      String descricao,
      LocalDate data,
      String local,
      Integer capacidadePessoas) {
    if (data != null && data.isBefore(LocalDate.now())) {
      logger.severe("Nova data do evento no passado: " + data);
      throw new IllegalArgumentException("A data do evento nao pode ser no passado");
    }
    ValueUtils.setValueIfNotNull(nome, eventoFromDB::setNome);
    ValueUtils.setValueIfNotNull(descricao, eventoFromDB::setDescricao);
    ValueUtils.setValueIfNotNull(data, eventoFromDB::setData);
    ValueUtils.setValueIfNotNull(local, eventoFromDB::setLocal);
    ValueUtils.setValueIfNotNull(capacidadePessoas, eventoFromDB::setCapacidadePessoas);
    dao.atualizarEvento(eventoFromDB);
  }

  // Overload usado pelos testes;
  public static void atualizarEvento(Evento evento) {
    if (evento == null) {
      logger.severe("Evento nulo ao atualizar.");
      throw new IllegalArgumentException("Evento nao pode ser nulo");
    }
    dao.atualizarEvento(evento);
    System.out.println("Evento atualizado com sucesso!");
  }

  public static List<Evento> obterTodosEventos() {
    return dao.listarEventos();
  }
}
