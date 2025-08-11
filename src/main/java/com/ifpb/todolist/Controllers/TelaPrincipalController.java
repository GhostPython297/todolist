package Controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Tarefa;

import java.io.IOException;

//preciso de mais orientações sobre as funções
public class TelaPrincipalController {
    @FXML private ListView<Tarefa> listViewTarefas;
    @FXML private TextField txtTitulo;
    @FXML private TextField txtDescricao;
    @FXML private DatePicker dataVencimento;
    @FXML private CheckBox chkConcluida;
    @FXML private VBox formulario;

    private ObservableList<Tarefa> listaTarefas;


    @FXML
    public void IrParaTelaCronograma(ActionEvent event) throws IOException {
        Parent tela3 = FXMLLoader.load(getClass().getResource("/view/TelaCronograma.fxml"));
        Scene scene3 = new Scene(tela3);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene3);
        stage.setTitle("Visualização em cronograma");
    }

    public void mostrarFormulario() {
        formulario.setVisible(true);
        formulario.setManaged(true);
    }

    public void cancelarFormulario() {
        limparCampos();
        formulario.setVisible(false);
        formulario.setManaged(false);
    }



    private void limparCampos() {
        txtTitulo.clear();
        txtDescricao.clear();
        dataVencimento.setValue(null);
        chkConcluida.setSelected(false);
    }

    public void adicionarTarefa() {
        String titulo = txtTitulo.getText();
        String descricao = txtDescricao.getText();

        if (titulo.isEmpty() || dataVencimento.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Preencha todos os campos obrigatórios!");
            alert.showAndWait();
            return;
        }

        Tarefa nova = new Tarefa(titulo, descricao, dataVencimento.getValue());
        nova.setConcluida(chkConcluida.isSelected());
        listaTarefas.add(nova);
        // Adicionar sistema de persistencia
        limparCampos();
        cancelarFormulario();
    }

    public void removerTarefa(ActionEvent event) {
        Tarefa selecionada = listViewTarefas.getSelectionModel().getSelectedItem();
        if (selecionada != null) {
            listaTarefas.remove(selecionada);
            // Adicionar sistema de persistencia
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Selecione uma tarefa para remover!");
            alert.showAndWait();
        }
    }
}