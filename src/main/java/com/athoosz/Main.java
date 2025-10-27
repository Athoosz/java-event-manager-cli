package com.athoosz;

import com.athoosz.controller.EventoController;
import com.athoosz.database.DatabaseInitializer;
import java.io.FileInputStream;
import java.util.Scanner;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {
  private static final Logger logger = Logger.getLogger(Main.class.getName());

  public static void main(String[] args) {
    try {
      LogManager.getLogManager().readConfiguration(new FileInputStream("logging.properties"));
    } catch (Exception e) {
      System.out.println("Não foi possível configurar o logger: " + e.getMessage());
    }
    DatabaseInitializer.criarTabelaEventosSeNaoExistir();
    Scanner scanner = new Scanner(System.in);
    logger.info("Aplicação iniciada.");
    System.out.println("Bem-vindo ao Gerenciador de Eventos!");
    EventoController.menu();
    logger.info("Aplicação finalizada.");
    scanner.close();
  }
}
