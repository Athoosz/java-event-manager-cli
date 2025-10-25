package com.example.service;

import com.example.dao.EventoDAO;
import com.example.model.Evento;
import java.time.LocalDate;
import java.util.List;

public class EventoService {
  private static EventoDAO dao = new EventoDAO();

  public static void adicionarEvento(
      String nome, String descricao, LocalDate data, String local, int capacidadePessoas) {
    if (nome == null || nome.isEmpty() || data == null) {
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
      System.out.println("Nenhum evento cadastrado.");
    } else {
      eventos.forEach(System.out::println);
    }
  }

  public static Evento obterEventoPorId(int id) {
    return dao.buscarEventoPorId(id);
  }

  public static void removerEvento(int id) {
    dao.deletarEvento(id);
    System.out.println("Evento removido com sucesso!");
  }

  public static void atualizarEvento(
      String nome, String descricao, LocalDate data, String local, int capacidadePessoas, int id) {
    if (nome == null || nome.isEmpty() || data == null) {
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
    evento.setId(id);
    dao.atualizarEvento(evento);
    System.out.println("Evento atualizado com sucesso!");
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
