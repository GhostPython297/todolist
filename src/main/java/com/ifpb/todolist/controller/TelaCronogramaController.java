package com.ifpb.todolist.controller;

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
import com.ifpb.todolist.model.CSVUtils;
import com.ifpb.todolist.model.Tarefa;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Controlador da tela de cronograma
 * 
 * Responsável por:
 * - Exibir tarefas organizadas por data de vencimento
 * - Permitir marcar tarefas como concluídas
 * - Destacar tarefas atrasadas e próximas do vencimento
 * 
 * Demonstra conceitos de POO:
 * - Responsabilidade única: foca apenas na visualização cronológica
 * - Composição: usa outras classes para lógica de negócio
 * - Abstração: esconde complexidade da organização dos dados
 */
public class TelaCronogramaController {

    // === COMPONENTES DA INTERFACE ===
    @FXML private ScrollPane painelRolagemCronograma;
    @FXML private VBox containerCronograma;

    // === DADOS ===
    private ObservableList<Tarefa> listaTarefas;
    private DateTimeFormatter formatadorBrasil = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Inicialização do controlador
     * Carrega dados e configura a interface
     */
    @FXML
    public void initialize() {
        // Carrega tarefas do CSV
        listaTarefas = FXCollections.observableArrayList(CSVUtils.carregarTarefas());

        // Configura container principal
        configurarContainer();

        // Carrega e exibe o cronograma
        carregarCronograma();
    }

    /**
     * Configura o container principal do cronograma
     */
    private void configurarContainer() {
        if (containerCronograma != null) {
            containerCronograma.setSpacing(15);
            containerCronograma.setPadding(new Insets(20));
        }
    }

    // === NAVEGAÇÃO ===

    /**
     * Volta para a tela principal
     */
    @FXML
    public void voltarParaTelaPrincipal(ActionEvent event) {
        try {
            Parent telaPrincipal = FXMLLoader.load(getClass().getResource("/view/TelaPrincipal.fxml"));
            Scene novaCena = new Scene(telaPrincipal);
            
            Stage janela = (Stage) ((Node) event.getSource()).getScene().getWindow();
            janela.setScene(novaCena);
            janela.setTitle("Aplicativo de Tarefas - Lista Principal");
            
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao carregar tela");
            alert.setContentText("Não foi possível carregar a tela principal: " + e.getMessage());
            alert.showAndWait();
        }
    }

    // === CARREGAMENTO DO CRONOGRAMA ===

    /**
     * Carrega e organiza as tarefas no cronograma
     */
    private void carregarCronograma() {
        if (containerCronograma == null) return;

        // Limpa container
        containerCronograma.getChildren().clear();

        // Agrupa tarefas por data de vencimento
        Map<LocalDate, List<Tarefa>> tarefasPorData = agruparTarefasPorData();

        // Verifica se há tarefas
        if (tarefasPorData.isEmpty()) {
            exibirMensagemSemTarefas();
            return;
        }

        // Ordena datas e cria seções
        List<LocalDate> datasOrdenadas = ordenarDatas(tarefasPorData.keySet());
        
        for (LocalDate data : datasOrdenadas) {
            VBox secaoData = criarSecaoData(data, tarefasPorData.get(data));
            containerCronograma.getChildren().add(secaoData);
        }
    }

    /**
     * Agrupa tarefas por data de vencimento
     */
    private Map<LocalDate, List<Tarefa>> agruparTarefasPorData() {
        return listaTarefas.stream()
                .filter(tarefa -> tarefa.getDataVencimento() != null)
                .collect(Collectors.groupingBy(Tarefa::getDataVencimento));
    }

    /**
     * Ordena as datas cronologicamente
     */
    private List<LocalDate> ordenarDatas(Set<LocalDate> datas) {
        return datas.stream()
                .sorted()
                .collect(Collectors.toList());
    }

    /**
     * Exibe mensagem quando não há tarefas
     */
    private void exibirMensagemSemTarefas() {
        VBox containerVazio = new VBox(10);
        containerVazio.setAlignment(Pos.CENTER);
        containerVazio.setPadding(new Insets(50));

        Label mensagem = new Label("📝 Nenhuma tarefa com data de vencimento encontrada");
        mensagem.setStyle("-fx-text-fill: #666666; -fx-font-size: 16px; -fx-font-style: italic;");

        Label dica = new Label("Adicione tarefas na tela principal para vê-las aqui organizadas por data.");
        dica.setStyle("-fx-text-fill: #999999; -fx-font-size: 12px;");
        dica.setWrapText(true);

        containerVazio.getChildren().addAll(mensagem, dica);
        containerCronograma.getChildren().add(containerVazio);
    }

    // === CRIAÇÃO DE SEÇÕES ===

    /**
     * Cria uma seção para uma data específica
     */
    private VBox criarSecaoData(LocalDate data, List<Tarefa> tarefas) {
        VBox secao = new VBox(10);
        configurarEstiloSecao(secao);

        // Adiciona cabeçalho da data
        Label labelData = criarLabelData(data);
        secao.getChildren().add(labelData);

        // Adiciona separador
        Separator separador = new Separator();
        secao.getChildren().add(separador);

        // Ordena tarefas (não concluídas primeiro)
        List<Tarefa> tarefasOrdenadas = ordenarTarefas(tarefas);

        // Adiciona cada tarefa
        for (Tarefa tarefa : tarefasOrdenadas) {
            HBox itemTarefa = criarItemTarefa(tarefa);
            secao.getChildren().add(itemTarefa);
        }

        return secao;
    }

    /**
     * Configura o estilo visual da seção
     */
    private void configurarEstiloSecao(VBox secao) {
        secao.setStyle("-fx-background-color: #ffffff; " +
                      "-fx-border-color: #e0e0e0; " +
                      "-fx-border-radius: 8; " +
                      "-fx-background-radius: 8; " +
                      "-fx-padding: 15; " +
                      "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 2);");
    }

    /**
     * Cria o label da data com formatação especial
     */
    private Label criarLabelData(LocalDate data) {
        Label labelData = new Label(data.format(formatadorBrasil));
        
        // Estilo base
        labelData.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: #2196F3;");

        // Destaque especial baseado na data
        if (data.equals(LocalDate.now())) {
            // Hoje
            labelData.setText(labelData.getText() + " (HOJE) 📅");
            labelData.setStyle(labelData.getStyle() + 
                " -fx-background-color: #e3f2fd; " +
                "-fx-padding: 8; " +
                "-fx-border-radius: 5; " +
                "-fx-background-radius: 5;");
        } else if (data.isBefore(LocalDate.now())) {
            // Atrasada
            labelData.setText(labelData.getText() + " (ATRASADA) ⚠️");
            labelData.setStyle("-fx-font-weight: bold; " +
                "-fx-font-size: 16px; " +
                "-fx-text-fill: #f44336; " +
                "-fx-background-color: #ffebee; " +
                "-fx-padding: 8; " +
                "-fx-border-radius: 5; " +
                "-fx-background-radius: 5;");
        }

        return labelData;
    }

    /**
     * Ordena tarefas: não concluídas primeiro, depois por título
     */
    private List<Tarefa> ordenarTarefas(List<Tarefa> tarefas) {
        return tarefas.stream()
                .sorted((t1, t2) -> {
                    // Primeiro critério: status de conclusão
                    if (t1.isConcluida() != t2.isConcluida()) {
                        return t1.isConcluida() ? 1 : -1;
                    }
                    // Segundo critério: ordem alfabética do título
                    return t1.getTitulo().compareToIgnoreCase(t2.getTitulo());
                })
                .collect(Collectors.toList());
    }

    // === CRIAÇÃO DE ITENS DE TAREFA ===

    /**
     * Cria um item visual para uma tarefa
     */
    private HBox criarItemTarefa(Tarefa tarefa) {
        HBox container = new HBox(15);
        container.setAlignment(Pos.CENTER_LEFT);
        container.setPadding(new Insets(10));

        // Aplica estilo baseado no status da tarefa
        aplicarEstiloItem(container, tarefa);

        // Checkbox para marcar conclusão
        CheckBox checkConcluida = criarCheckboxConclusao(tarefa, container);

        // Informações da tarefa
        VBox infoTarefa = criarInformacoesTarefa(tarefa);

        // Ícone de status
        Label iconeStatus = criarIconeStatus(tarefa);

        container.getChildren().addAll(checkConcluida, infoTarefa, iconeStatus);

        return container;
    }

    /**
     * Aplica estilo visual baseado no status da tarefa
     */
    private void aplicarEstiloItem(HBox container, Tarefa tarefa) {
        if (tarefa.isConcluida()) {
            container.setStyle("-fx-background-color: #e8f5e8; " +
                             "-fx-border-color: #4caf50; " +
                             "-fx-border-radius: 5; " +
                             "-fx-background-radius: 5;");
        } else if (tarefa.getDataVencimento().isBefore(LocalDate.now())) {
            container.setStyle("-fx-background-color: #ffebee; " +
                             "-fx-border-color: #f44336; " +
                             "-fx-border-radius: 5; " +
                             "-fx-background-radius: 5;");
        } else {
            container.setStyle("-fx-background-color: #f9f9f9; " +
                             "-fx-border-color: #cccccc; " +
                             "-fx-border-radius: 5; " +
                             "-fx-background-radius: 5;");
        }
    }

    /**
     * Cria checkbox para marcar conclusão
     */
    private CheckBox criarCheckboxConclusao(Tarefa tarefa, HBox container) {
        CheckBox checkConcluida = new CheckBox();
        checkConcluida.setSelected(tarefa.isConcluida());
        
        // Handler para mudança de status
        checkConcluida.setOnAction(e -> {
            tarefa.setConcluida(checkConcluida.isSelected());
            CSVUtils.salvarTarefas(listaTarefas);
            
            // Recarrega cronograma para atualizar visual
            carregarCronograma();
        });
        
        return checkConcluida;
    }

    /**
     * Cria container com informações da tarefa
     */
    private VBox criarInformacoesTarefa(Tarefa tarefa) {
        VBox infoTarefa = new VBox(5);

        // Label do título
        Label labelTitulo = new Label(tarefa.getTitulo());
        labelTitulo.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        
        if (tarefa.isConcluida()) {
            labelTitulo.setStyle(labelTitulo.getStyle() + 
                " -fx-strikethrough: true; -fx-text-fill: #666666;");
        }

        // Label da data de vencimento
        Label labelVencimento = new Label("Vencimento: " + 
            tarefa.getDataVencimento().format(formatadorBrasil));
        labelVencimento.setStyle("-fx-font-size: 12px; -fx-text-fill: #666666;");

        // Descrição (se houver)
        if (tarefa.getDescricao() != null && !tarefa.getDescricao().trim().isEmpty()) {
            Label labelDescricao = new Label(tarefa.getDescricao());
            labelDescricao.setStyle("-fx-font-size: 11px; -fx-text-fill: #888888; -fx-font-style: italic;");
            labelDescricao.setWrapText(true);
            infoTarefa.getChildren().add(labelDescricao);
        }

        infoTarefa.getChildren().addAll(labelTitulo, labelVencimento);
        return infoTarefa;
    }

    /**
     * Cria ícone visual baseado no status da tarefa
     */
    private Label criarIconeStatus(Tarefa tarefa) {
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

        return iconeStatus;
    }
}
