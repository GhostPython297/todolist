package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TelaLoginController {

    @FXML
    public TextField usename;

    @FXML
    private PasswordField password;

    @FXML
    private Label mensagemLabel;

    private final String loggedInUser = "Gabriel";
    private final String loggedInPassword = "123456";


    @FXML
    public void IrParaTelaPrincipal() {
        String nome = usename.getText();
        String senha = password.getText();

        if (nome.equals(loggedInUser) && senha.equals(loggedInPassword)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/telaPrincipal.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) usename.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Tela Principal");
            } catch (IOException e) {
                mensagemLabel.setText("Erro ao carregar a tela.");
                e.printStackTrace();
            }
        } else {
            mensagemLabel.setText("Email ou senha incorretos.");
        }
    }
}
