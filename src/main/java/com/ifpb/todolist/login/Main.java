package com.ifpb.todolist.login;

import java.util.Scanner;

public class Main {
    // todo: criar uma tela de login com JavaFX

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Bem-vindo ao ToDo List!");
        System.out.print("Digite seu nome de usuário: ");

        String username = scanner.nextLine();
        System.out.print("Digite sua senha: ");

        String password = scanner.nextLine();
        User user = new User(username, password);

        // referência para o usuário logado
        String loggedInUser = "Gabriel";
        String loggedInPassword = "123456";

        // eu poderia ter criado um crud para isso,
        // mas como é um exemplo simples, vou comparar diretamente
        // com o usuário e senha pré-definidos
        if (user.getUsername().equals(loggedInUser) && user.getPassword().equals(loggedInPassword)) {
            System.out.println("Login bem-sucedido!");
            System.out.println("Bem-vindo, " + user.getUsername() + "!");

            // todo: adicionar lógica para abrir a janela principal do ToDo List
        } else {
            System.out.println("Usuário ou senha incorretos. Tente novamente.");
        }
        scanner.close();
    }
}
