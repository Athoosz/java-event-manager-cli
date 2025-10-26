package com.athoosz.dao;

import java.util.List;
import java.util.Optional;

import com.athoosz.model.Evento;

public interface EventoDAOInterface {
  void criarEvento(Evento evento);

  List<Evento> listarEventos();

  Optional<Evento> buscarEventoPorId(int id);

  void atualizarEvento(Evento evento);

  void deletarEvento(int id);
}
