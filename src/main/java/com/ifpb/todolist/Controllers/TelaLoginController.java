package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TelaLoginController {
    // Sugestões: adicionar uma linha para digitar senha e um botão para criar conta caso não tenha
    // ação IrParaTelaPrincipal deve sofrer alterações quando for decidido como o usuario fara login
    @FXML
    public void IrParaTelaPrincipal(ActionEvent event) throws Exception{
        Parent tela2 = FXMLLoader.load(getClass().getResource("/view/TelaPrincipal.fxml"));
        Scene scene2 = new Scene(tela2);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene2);
        stage.setTitle("Visualização em lista");
    }
}