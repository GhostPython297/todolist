package applications;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/TelaLogin.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        // Configurações básicas da janela
        primaryStage.setTitle("Aplicativo de Tarefas");
        primaryStage.setScene(scene);

        // Permitir redimensionamento
        primaryStage.setResizable(true);

        // Definir tamanhos mínimos para responsividade
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);

        // Tamanho inicial
        primaryStage.setWidth(1000);
        primaryStage.setHeight(650);

        // Centralizar na tela
        primaryStage.centerOnScreen();

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}