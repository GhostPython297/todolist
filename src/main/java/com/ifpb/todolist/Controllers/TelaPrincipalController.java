package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

//preciso de mais orientações sobre as funções
public class TelaPrincipalController {
    @FXML
    public void IrParaTelaCronograma(ActionEvent event) throws IOException {
        Parent tela3 = FXMLLoader.load(getClass().getResource("/view/TelaCronograma.fxml"));
        Scene scene3 = new Scene(tela3);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene3);
        stage.setTitle("Visualização em cronograma");
    }
}