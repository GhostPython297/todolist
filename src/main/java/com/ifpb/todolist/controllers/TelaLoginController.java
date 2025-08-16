package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TelaLoginController {

    @FXML
    public TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Label mensagemLabel;

    private final String loggedInUser = "Gabriel";
    private final String loggedInPassword = "123456";


    @FXML
    public void irParaTelaPrincipal() throws IOException {
        String nome = username.getText();
        String senha = password.getText();

        if (nome.equals(loggedInUser) && senha.equals(loggedInPassword)) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaPrincipal.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) username.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Tela Principal");
        } else {
            mensagemLabel.setText("Email ou senha incorretos.");
        }
    }
}
