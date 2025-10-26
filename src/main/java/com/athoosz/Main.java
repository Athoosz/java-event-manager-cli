package com.athoosz;

import com.athoosz.controller.EventoController;
import com.athoosz.database.DatabaseInitializer;

import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    DatabaseInitializer.criarTabelaEventosSeNaoExistir();
    Scanner scanner = new Scanner(System.in);
    System.out.println("Bem-vindo ao Gerenciador de Eventos!");
    EventoController.menu();
    scanner.close();
  }
}
