package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.CSVUtils;
import model.Tarefa;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class TelaCronogramaController {

    @FXML private ScrollPane scrollPaneCronograma;
    @FXML private VBox containerCronograma;

    private ObservableList<Tarefa> listaTarefas;
    private DateTimeFormatter formatterBR = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @FXML
    public void initialize() {
        // Carregar tarefas do CSV
        listaTarefas = FXCollections.observableArrayList(CSVUtils.carregarTarefas());

        // Configurar container principal
        if (containerCronograma != null) {
            containerCronograma.setSpacing(15);
            containerCronograma.setPadding(new Insets(20));
        }

        // Carregar cronograma
        carregarCronograma();
    }

    @FXML
    public void voltarParaTelaPrincipal(ActionEvent event) {
        try {
            Parent tela4 = FXMLLoader.load(getClass().getResource("/view/TelaPrincipal.fxml"));
            Scene scene4 = new Scene(tela4);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene4);
            stage.setTitle("Visualização em lista");
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao carregar tela");
            alert.setContentText("Não foi possível carregar a tela principal: " + e.getMessage());
            alert.showAndWait();
        }
    }

    private void carregarCronograma() {
        if (containerCronograma == null) return;

        containerCronograma.getChildren().clear();

        // Agrupar tarefas por data de vencimento
        Map<LocalDate, List<Tarefa>> tarefasPorData = listaTarefas.stream()
                .filter(tarefa -> tarefa.getDataVencimento() != null)
                .collect(Collectors.groupingBy(Tarefa::getDataVencimento));

        // Ordenar as datas
        List<LocalDate> datasOrdenadas = tarefasPorData.keySet().stream()
                .sorted()
                .collect(Collectors.toList());

        if (datasOrdenadas.isEmpty()) {
            Label semTarefas = new Label("Nenhuma tarefa com data de vencimento encontrada");
            semTarefas.setStyle("-fx-text-fill: #666666; -fx-font-size: 14px; -fx-font-style: italic;");
            containerCronograma.getChildren().add(semTarefas);
            return;
        }

        // Criar seção para cada data
        for (LocalDate data : datasOrdenadas) {
            VBox secaoData = criarSecaoData(data, tarefasPorData.get(data));
            containerCronograma.getChildren().add(secaoData);
        }
    }

    private VBox criarSecaoData(LocalDate data, List<Tarefa> tarefas) {
        VBox secao = new VBox(10);
        secao.setStyle("-fx-background-color: #ffffff; -fx-border-color: #e0e0e0; " +
                "-fx-border-radius: 8; -fx-background-radius: 8; -fx-padding: 15;");

        // Cabeçalho da data
        Label labelData = new Label(data.format(formatterBR));
        labelData.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: #2196F3;");

        // Destacar se for hoje
        if (data.equals(LocalDate.now())) {
            labelData.setText(labelData.getText() + " (HOJE)");
            labelData.setStyle(labelData.getStyle() + " -fx-background-color: #e3f2fd; -fx-padding: 8; -fx-border-radius: 5; -fx-background-radius: 5;");
        }
        // Destacar se estiver atrasada
        else if (data.isBefore(LocalDate.now())) {
            labelData.setText(labelData.getText() + " (ATRASADA)");
            labelData.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: #f44336; " +
                    "-fx-background-color: #ffebee; -fx-padding: 8; -fx-border-radius: 5; -fx-background-radius: 5;");
        }

        secao.getChildren().add(labelData);

        // Adicionar separator
        Separator separator = new Separator();
        secao.getChildren().add(separator);

        // Ordenar tarefas: não concluídas primeiro
        List<Tarefa> tarefasOrdenadas = tarefas.stream()
                .sorted((t1, t2) -> {
                    if (t1.isConcluida() != t2.isConcluida()) {
                        return t1.isConcluida() ? 1 : -1;
                    }
                    return t1.getTitulo().compareToIgnoreCase(t2.getTitulo());
                })
                .collect(Collectors.toList());

        // Adicionar cada tarefa
        for (Tarefa tarefa : tarefasOrdenadas) {
            HBox itemTarefa = criarItemTarefa(tarefa);
            secao.getChildren().add(itemTarefa);
        }

        return secao;
    }

    private HBox criarItemTarefa(Tarefa tarefa) {
        HBox container = new HBox(15);
        container.setAlignment(Pos.CENTER_LEFT);
        container.setPadding(new Insets(10));

        // Definir estilo baseado no status
        if (tarefa.isConcluida()) {
            container.setStyle("-fx-background-color: #e8f5e8; -fx-border-color: #4caf50; " +
                    "-fx-border-radius: 5; -fx-background-radius: 5;");
        } else if (tarefa.getDataVencimento().isBefore(LocalDate.now())) {
            container.setStyle("-fx-background-color: #ffebee; -fx-border-color: #f44336; " +
                    "-fx-border-radius: 5; -fx-background-radius: 5;");
        } else {
            container.setStyle("-fx-background-color: #f9f9f9; -fx-border-color: #cccccc; " +
                    "-fx-border-radius: 5; -fx-background-radius: 5;");
        }

        // Checkbox para marcar como concluída
        CheckBox checkConcluida = new CheckBox();
        checkConcluida.setSelected(tarefa.isConcluida());
        checkConcluida.setOnAction(e -> {
            tarefa.setConcluida(checkConcluida.isSelected());
            CSVUtils.salvarTarefas(listaTarefas);
            carregarCronograma(); // Recarregar para atualizar visual
        });

        // Informações da tarefa
        VBox infoTarefa = new VBox(5);

        // Título
        Label labelTitulo = new Label(tarefa.getTitulo());
        labelTitulo.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        if (tarefa.isConcluida()) {
            labelTitulo.setStyle(labelTitulo.getStyle() + " -fx-strikethrough: true; -fx-text-fill: #666666;");
        }

        // Data de vencimento
        Label labelVencimento = new Label("Vencimento: " + tarefa.getDataVencimento().format(formatterBR));
        labelVencimento.setStyle("-fx-font-size: 12px; -fx-text-fill: #666666;");

        infoTarefa.getChildren().addAll(labelTitulo, labelVencimento);

        // Ícone de status
        Label iconeStatus = new Label();
        iconeStatus.setStyle("-fx-font-size: 18px;");

        if (tarefa.isConcluida()) {
            iconeStatus.setText("✅");
        } else if (tarefa.getDataVencimento().isBefore(LocalDate.now())) {
            iconeStatus.setText("🔴");
        } else if (tarefa.getDataVencimento().equals(LocalDate.now())) {
            iconeStatus.setText("⚠️");
        } else {
            iconeStatus.setText("⏳");
        }

        container.getChildren().addAll(checkConcluida, infoTarefa, iconeStatus);

        return container;
    }
}