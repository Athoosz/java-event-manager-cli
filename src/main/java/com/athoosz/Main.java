package com.athoosz;

import com.athoosz.controller.EventoController;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Bem-vindo ao Gerenciador de Eventos!");
    EventoController.menu();
    scanner.close();
  }
}
