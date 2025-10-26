package com.example.service;

import com.example.dao.EventoDAO;
import com.example.model.Evento;
import com.example.utils.ValueUtils;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class EventoService {
  private static EventoDAO dao = new EventoDAO();

  public static void adicionarEvento(
      String nome, String descricao, LocalDate data, String local, int capacidadePessoas) {
    if (nome == null || nome.isEmpty() || data == null || data.toString().isEmpty()) {
      throw new IllegalArgumentException("Nome e data do evento são campos obrigatórios");
    }
    if (data.isBefore(LocalDate.now())) {
      throw new IllegalArgumentException("A data do evento nao pode ser no passado");
    }
    if (capacidadePessoas <= 0) {
      throw new IllegalArgumentException("Capacidade de pessoas deve ser maior que zero");
    }

    Evento evento = new Evento();
    evento.setNome(nome);
    evento.setDescricao(descricao);
    evento.setData(data);
    evento.setLocal(local);
    evento.setCapacidadePessoas(capacidadePessoas);
    dao.criarEvento(evento);
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
      throw new IllegalArgumentException("Evento nao pode ser nulo");
    }
    dao.atualizarEvento(evento);
    System.out.println("Evento atualizado com sucesso!");
  }

  public static List<Evento> obterTodosEventos() {
    return dao.listarEventos();
  }
}
