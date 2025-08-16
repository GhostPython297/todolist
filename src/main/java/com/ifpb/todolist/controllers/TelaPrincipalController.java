package controllers;

import javafx.collections.FXCollections;
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
import model.CSVUtils;
import model.Tarefa;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TelaPrincipalController {
    @FXML private ListView<Tarefa> listViewTarefas;
    @FXML private TextField txtTitulo;
    @FXML private TextField txtDescricao;
    @FXML private DatePicker dataVencimento;
    @FXML private CheckBox chkConcluida;
    @FXML private VBox formulario;

    // CAMPOS PARA EXIBIR DETALHES
    @FXML private Label lblTituloDetalhes;
    @FXML private TextArea txtDescricaoDetalhes;
    @FXML private Label lblDataCriacao;
    @FXML private Label lblDataVencimento;
    @FXML private Label lblStatusConclusao;
    @FXML private VBox painelDetalhes;

    private ObservableList<Tarefa> listaTarefas;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @FXML
    public void initialize() {
        listaTarefas = FXCollections.observableArrayList(CSVUtils.carregarTarefas());
        listViewTarefas.setItems(listaTarefas);

        // Configurar listener para seleção na ListView
        listViewTarefas.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        exibirDetalhesTarefa(newValue);
                    } else {
                        limparDetalhes();
                    }
                }
        );

        // Inicializar painel de detalhes como invisível
        if (painelDetalhes != null) {
            painelDetalhes.setVisible(false);
            painelDetalhes.setManaged(false);
        }
    }

    // Exibir detalhes da tarefa selecionada
    private void exibirDetalhesTarefa(Tarefa tarefa) {
        if (painelDetalhes != null) {
            painelDetalhes.setVisible(true);
            painelDetalhes.setManaged(true);
        }

        if (lblTituloDetalhes != null) {
            lblTituloDetalhes.setText(tarefa.getTitulo());
        }

        if (txtDescricaoDetalhes != null) {
            txtDescricaoDetalhes.setText(tarefa.getDescricao() != null ? tarefa.getDescricao() : "Sem descrição");
        }

        if (lblDataCriacao != null) {
            lblDataCriacao.setText("Criada em: " + tarefa.getDataCriacao().format(formatter));
        }

        if (lblDataVencimento != null) {
            String dataVenc = tarefa.getDataVencimento() != null ?
                    tarefa.getDataVencimento().format(formatter) : "Não definida";
            lblDataVencimento.setText("Vence em: " + dataVenc);
        }

        if (lblStatusConclusao != null) {
            String status = tarefa.isConcluida() ? "✅ Concluída" : "⏳ Pendente";
            lblStatusConclusao.setText(status);
            lblStatusConclusao.setStyle(tarefa.isConcluida() ?
                    "-fx-text-fill: green; -fx-font-weight: bold;" :
                    "-fx-text-fill: orange; -fx-font-weight: bold;");
        }
    }

    // Limpar detalhes
    private void limparDetalhes() {
        if (painelDetalhes != null) {
            painelDetalhes.setVisible(false);
            painelDetalhes.setManaged(false);
        }

        if (lblTituloDetalhes != null) lblTituloDetalhes.setText("");
        if (txtDescricaoDetalhes != null) txtDescricaoDetalhes.setText("");
        if (lblDataCriacao != null) lblDataCriacao.setText("");
        if (lblDataVencimento != null) lblDataVencimento.setText("");
        if (lblStatusConclusao != null) lblStatusConclusao.setText("");
    }

    // Marcar como concluída
    @FXML
    public void marcarComoConcluida() {
        Tarefa selecionada = listViewTarefas.getSelectionModel().getSelectedItem();
        if (selecionada != null) {
            selecionada.setConcluida(!selecionada.isConcluida());
            CSVUtils.salvarTarefas(listaTarefas);
            listViewTarefas.refresh(); // Atualizar a exibição
            exibirDetalhesTarefa(selecionada); // Atualizar detalhes
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Selecione uma tarefa!");
            alert.showAndWait();
        }
    }

    @FXML
    public void irParaTelaCronograma(ActionEvent event) throws IOException {
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

        if (titulo == null || titulo.trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Título é obrigatório!");
            alert.showAndWait();
            return;
        }
        if (titulo.length() > 100) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Título muito longo (máx. 100 caracteres)!");
            alert.showAndWait();
            return;
        }
        if (dataVencimento.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Data de vencimento é obrigatória!");
            alert.showAndWait();
            return;
        }
        if (dataVencimento.getValue().isBefore(LocalDate.now())) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Data de vencimento não pode ser no passado!");
            alert.showAndWait();
            return;
        }

        Tarefa nova = new Tarefa(titulo, descricao, dataVencimento.getValue());
        nova.setConcluida(chkConcluida.isSelected());
        listaTarefas.add(nova);
        CSVUtils.salvarTarefas(listaTarefas);
        limparCampos();
        cancelarFormulario();
    }

    public void removerTarefa(ActionEvent event) {
        Tarefa selecionada = listViewTarefas.getSelectionModel().getSelectedItem();
        if (selecionada != null) {
            listaTarefas.remove(selecionada);
            CSVUtils.salvarTarefas(listaTarefas);
            limparDetalhes(); // Limpar detalhes após remoção
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Selecione uma tarefa para remover!");
            alert.showAndWait();
        }
    }
}