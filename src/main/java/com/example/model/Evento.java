package com.example.model;

import java.time.LocalDate;

public class Evento {
  public int id;
  public String nome;
  public String descricao;
  public LocalDate data;
  public String local;

  public Evento() {
  }

  public Evento(int id, String nome, String descricao, LocalDate data, String local) {
    this.id = id;
    this.nome = nome;
    this.descricao = descricao;
    this.data = data;
    this.local = local;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public LocalDate getData() {
    return data;
  }

  public void setData(LocalDate data) {
    this.data = data;
  }

  public String getLocal() {
    return local;
  }

  public void setLocal(String local) {
    this.local = local;
  }

  @Override
  public String toString() {
    return "Evento [id=" + id + ", nome=" + nome + ", descricao=" + descricao + ", data=" + data + ", local=" + local
        + "]";
  }
}
