package com.example.dao;

import java.util.List;

import com.example.model.Evento;

public interface EventoDAOInterface {
  void criarEvento(Evento evento);

  List<Evento> listarEventos();

  Evento buscarEventoPorId(int id);

  void atualizarEvento(Evento evento);

  void deletarEvento(int id);
}
