package com.ifpb.todolist;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;

/**
 * Classe principal do aplicativo de tarefas
 * Responsável por inicializar a aplicação JavaFX
 */
public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Carrega o arquivo FXML da tela de login
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/TelaLogin.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        // Configurações básicas da janela principal
        primaryStage.setTitle("Aplicativo de Tarefas - IFPB");
        primaryStage.setScene(scene);

        // Configurações de responsividade
        primaryStage.setResizable(true);
        primaryStage.setMinWidth(800);  // Largura mínima
        primaryStage.setMinHeight(600); // Altura mínima

        // Tamanho inicial da janela
        primaryStage.setWidth(1000);
        primaryStage.setHeight(650);

        // Centralizar a janela na tela
        primaryStage.centerOnScreen();

        // Mostrar a janela
        primaryStage.show();
    }

    /**
     * Método main - ponto de entrada da aplicação
     * @param args argumentos da linha de comando
     */
    public static void main(String[] args) {
        launch(args);
    }
}
