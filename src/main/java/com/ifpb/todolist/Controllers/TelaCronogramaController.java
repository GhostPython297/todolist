package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class TelaCronogramaController {
    @FXML
    public void VoltarParaTelaPrincipal(ActionEvent event) throws IOException {
        Parent tela4 = FXMLLoader.load(getClass().getResource("/view/TelaPrincipal.fxml"));
        Scene scene4 = new Scene(tela4);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene4);
        stage.setTitle("Visualização em lista");
    }
}