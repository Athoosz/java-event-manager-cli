package com.example.controller;

import com.example.model.Evento;
import com.example.service.EventoService;
import java.time.LocalDate;
import java.util.Scanner;

public class EventoController {
  private static Scanner sc = new Scanner(System.in);

  public static void menu() {
    int opcao;
    System.out.println("Menu:");
    System.out.println("1. Criar Evento");
    System.out.println("2. Listar Eventos");
    System.out.println("3. Buscar Evento Por ID");
    System.out.println("4. Atualizar Evento");
    System.out.println("5. Deletar Evento");
    System.out.println("0. Sair");
    System.out.println("Digite a opçao que deseja: ");

    opcao = sc.nextInt();
    sc.nextLine();

    switch (opcao) {
      case 1:
        criarEvento();
        break;
      case 2:
        listarEventos();
        break;
      case 3:
        buscarEventoPorId();
        break;
      case 4:
        atualizarEvento();
        break;
      case 5:
        deletarEvento();
        break;
      case 0:
        System.out.println("Saindo...");
        break;
      default:
        System.out.println("Opção inválida");
        break;
    }
    while (opcao != 0) {
      menu();
    }
  }

  public static void criarEvento() {
    try {
      System.out.println("Digite o nome do evento (Campo Obrigatorio): ");
      String nome = sc.nextLine();
      System.out.println(
          "Digite a descrição do evento (Campo Opcional, Digite Enter para deixar em branco): ");
      String descricao = sc.nextLine();
      if (descricao != null && descricao.isBlank()) {
        descricao = null;
      }
      System.out.println("Digite a data do evento (yyyy-mm-dd) (Campo Obrigatorio): ");
      LocalDate data = LocalDate.parse(sc.nextLine());
      System.out.println(
          "Digite o local do evento (Campo Opcional, Digite Enter para deixar em branco): ");
      String local = sc.nextLine();
      if (local != null && local.isBlank()) {
        local = null;
      }
      System.out.println("Digite a capacidade de pessoas (Campo Obrigatorio): ");
      int capacidadePessoas = sc.nextInt();
      sc.nextLine();

      EventoService.adicionarEvento(nome, descricao, data, local, capacidadePessoas);
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }

  public static void listarEventos() {
    try {
      System.out.println("Eventos cadastrados: ");
      EventoService.listarEventos();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public static void buscarEventoPorId() {
    try {
      System.out.println("Digite o Id: ");
      int id = sc.nextInt();
      sc.nextLine();
      Evento evento = EventoService.obterEventoPorId(id);
      System.out.println(evento);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public static void atualizarEvento() {
    try {
      System.out.println("Digite o Id do evento que deseja atualizar: ");
      int id = sc.nextInt();
      sc.nextLine();

      Evento eventoFromDB = EventoService.obterEventoPorId(id);

      System.out.println("Digite o novo nome do evento (Digite Enter para manter o mesmo): ");
      String nome = sc.nextLine();

      System.out.println("Digite a nova descrição do evento (Digite Enter para manter a mesma): ");
      String descricao = sc.nextLine();

      System.out.println(
          "Digite a nova data do evento (yyyy-mm-dd) (Digite Enter para manter a mesma): ");
      String dataInput = sc.nextLine();
      LocalDate data =
          dataInput != null && !dataInput.isBlank() ? LocalDate.parse(dataInput) : null;

      System.out.println("Digite o novo local do evento (Digite Enter para manter o mesmo): ");
      String local = sc.nextLine();

      System.out.println(
          "Digite a nova capacidade de pessoas (Digite Enter para manter a mesma): ");
      String capacidadePessoasInput = sc.nextLine();
      Integer capacidadePessoas =
          capacidadePessoasInput != null && !capacidadePessoasInput.isBlank()
              ? Integer.parseInt(capacidadePessoasInput)
              : null;

      EventoService.atualizarEvento(eventoFromDB, nome, descricao, data, local, capacidadePessoas);
      System.out.println("Evento atualizado com sucesso!");
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }

  public static void deletarEvento() {
    try {
      System.out.println("Digite o Id do evento que deseja deletar: ");
      int id = sc.nextInt();
      sc.nextLine();
      EventoService.removerEvento(id);
      System.out.println("Evento deletado com sucesso!");
    } catch (IllegalArgumentException e) {
      System.out.println("Erro: " + e.getMessage());
    } catch (Exception e) {
      System.out.println("Erro inesperado: " + e.getMessage());
    }
  }
}
