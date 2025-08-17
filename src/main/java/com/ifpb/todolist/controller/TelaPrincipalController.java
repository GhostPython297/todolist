package com.ifpb.todolist.controller;

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
import com.ifpb.todolist.model.CSVUtils;
import com.ifpb.todolist.model.Tarefa;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Controlador da tela principal do aplicativo
 * 
 * Responsável por:
 * - Gerenciar a lista de tarefas
 * - Adicionar, remover e editar tarefas
 * - Exibir detalhes das tarefas selecionadas
 * - Navegar para outras telas
 * 
 * Demonstra conceitos de POO:
 * - Responsabilidade única: controla apenas a tela principal
 * - Composição: usa CSVUtils para persistência
 * - Abstração: esconde complexidade da interface do usuário
 */
public class TelaPrincipalController {

    // === COMPONENTES DA LISTA ===
    @FXML private ListView<Tarefa> listaViewTarefas;
    
    // === COMPONENTES DO FORMULÁRIO ===
    @FXML private TextField campoTitulo;
    @FXML private TextField campoDescricao;
    @FXML private DatePicker seletorDataVencimento;
    @FXML private CheckBox checkboxConcluida;
    @FXML private VBox painelFormulario;

    // === COMPONENTES DE DETALHES ===
    @FXML private Label labelTituloDetalhes;
    @FXML private TextArea areaDescricaoDetalhes;
    @FXML private Label labelDataCriacao;
    @FXML private Label labelDataVencimento;
    @FXML private Label labelStatusConclusao;
    @FXML private VBox painelDetalhes;

    // === DADOS ===
    private ObservableList<Tarefa> listaTarefas;
    private DateTimeFormatter formatadorData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Método chamado automaticamente após carregamento do FXML
     * Inicializa componentes e carrega dados
     */
    @FXML
    public void initialize() {
        // Carrega tarefas do arquivo CSV
        listaTarefas = FXCollections.observableArrayList(CSVUtils.carregarTarefas());
        listaViewTarefas.setItems(listaTarefas);

        // Configura listener para seleção na lista
        listaViewTarefas.getSelectionModel().selectedItemProperty().addListener(
                (observable, tarefaAnterior, tarefaNova) -> {
                    if (tarefaNova != null) {
                        exibirDetalhesTarefa(tarefaNova);
                    } else {
                        limparDetalhes();
                    }
                }
        );

        // Inicializa painéis como invisíveis
        configurarVisibilidadeInicial();
    }

    /**
     * Configura a visibilidade inicial dos painéis
     */
    private void configurarVisibilidadeInicial() {
        if (painelFormulario != null) {
            painelFormulario.setVisible(false);
            painelFormulario.setManaged(false);
        }
        
        if (painelDetalhes != null) {
            painelDetalhes.setVisible(false);
            painelDetalhes.setManaged(false);
        }
    }

    // === MÉTODOS DE NAVEGAÇÃO ===

    /**
     * Navega para a tela de cronograma
     */
    @FXML
    public void irParaTelaCronograma(ActionEvent event) {
        try {
            Parent telaCronograma = FXMLLoader.load(getClass().getResource("/view/TelaCronograma.fxml"));
            Scene novaCena = new Scene(telaCronograma);
            
            Stage janela = (Stage) ((Node) event.getSource()).getScene().getWindow();
            janela.setScene(novaCena);
            janela.setTitle("Aplicativo de Tarefas - Cronograma");
            
        } catch (IOException e) {
            exibirAlertaErro("Erro ao carregar cronograma", 
                           "Não foi possível carregar a tela de cronograma: " + e.getMessage());
        }
    }

    // === MÉTODOS DE GERENCIAMENTO DE TAREFAS ===

    /**
     * Mostra o formulário para adicionar nova tarefa
     */
    @FXML
    public void mostrarFormulario() {
        limparCamposFormulario();
        
        if (painelFormulario != null) {
            painelFormulario.setVisible(true);
            painelFormulario.setManaged(true);
        }
        
        // Foca no campo de título
        if (campoTitulo != null) {
            campoTitulo.requestFocus();
        }
    }

    /**
     * Cancela a adição de tarefa e esconde o formulário
     */
    @FXML
    public void cancelarFormulario() {
        limparCamposFormulario();
        
        if (painelFormulario != null) {
            painelFormulario.setVisible(false);
            painelFormulario.setManaged(false);
        }
    }

    /**
     * Adiciona uma nova tarefa à lista
     */
    @FXML
    public void adicionarTarefa() {
        try {
            // Validação dos campos obrigatórios
            String titulo = campoTitulo.getText();
            String descricao = campoDescricao.getText();
            LocalDate dataVencimento = seletorDataVencimento.getValue();

            // Validações específicas
            if (!validarDadosTarefa(titulo, dataVencimento)) {
                return;
            }

            // Cria nova tarefa
            Tarefa novaTarefa = new Tarefa(titulo, descricao, dataVencimento);
            novaTarefa.setConcluida(checkboxConcluida.isSelected());

            // Adiciona à lista
            listaTarefas.add(novaTarefa);
            
            // Salva no arquivo
            CSVUtils.salvarTarefas(listaTarefas);
            
            // Limpa formulário e o esconde
            cancelarFormulario();
            
            // Exibe mensagem de sucesso
            exibirAlertaInformacao("Sucesso", "Tarefa adicionada com sucesso!");
            
        } catch (Exception e) {
            exibirAlertaErro("Erro ao adicionar tarefa", 
                           "Ocorreu um erro ao adicionar a tarefa: " + e.getMessage());
        }
    }

    /**
     * Remove a tarefa selecionada
     */
    @FXML
    public void removerTarefa() {
        Tarefa tarefaSelecionada = listaViewTarefas.getSelectionModel().getSelectedItem();
        
        if (tarefaSelecionada == null) {
            exibirAlertaAviso("Nenhuma tarefa selecionada", 
                            "Selecione uma tarefa para remover.");
            return;
        }

        // Confirmação de remoção
        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmar remoção");
        confirmacao.setHeaderText("Remover tarefa");
        confirmacao.setContentText("Tem certeza que deseja remover a tarefa:\n\"" + 
                                 tarefaSelecionada.getTitulo() + "\"?");

        if (confirmacao.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            listaTarefas.remove(tarefaSelecionada);
            CSVUtils.salvarTarefas(listaTarefas);
            limparDetalhes();
            
            exibirAlertaInformacao("Sucesso", "Tarefa removida com sucesso!");
        }
    }

    /**
     * Alterna o status de conclusão da tarefa selecionada
     */
    @FXML
    public void alternarStatusConclusao() {
        Tarefa tarefaSelecionada = listaViewTarefas.getSelectionModel().getSelectedItem();
        
        if (tarefaSelecionada == null) {
            exibirAlertaAviso("Nenhuma tarefa selecionada", 
                            "Selecione uma tarefa para alterar o status.");
            return;
        }

        // Alterna o status
        tarefaSelecionada.setConcluida(!tarefaSelecionada.isConcluida());
        
        // Salva as alterações
        CSVUtils.salvarTarefas(listaTarefas);
        
        // Atualiza a exibição
        listaViewTarefas.refresh();
        exibirDetalhesTarefa(tarefaSelecionada);
        
        String novoStatus = tarefaSelecionada.isConcluida() ? "concluída" : "pendente";
        exibirAlertaInformacao("Status alterado", 
                             "Tarefa marcada como " + novoStatus + "!");
    }

    // === MÉTODOS DE VALIDAÇÃO ===

    /**
     * Valida os dados de entrada para uma nova tarefa
     */
    private boolean validarDadosTarefa(String titulo, LocalDate dataVencimento) {
        if (titulo == null || titulo.trim().isEmpty()) {
            exibirAlertaAviso("Título obrigatório", "O título da tarefa é obrigatório!");
            return false;
        }
        
        if (titulo.length() > 100) {
            exibirAlertaAviso("Título muito longo", 
                            "O título deve ter no máximo 100 caracteres!");
            return false;
        }
        
        if (dataVencimento == null) {
            exibirAlertaAviso("Data obrigatória", 
                            "A data de vencimento é obrigatória!");
            return false;
        }
        
        if (dataVencimento.isBefore(LocalDate.now())) {
            exibirAlertaAviso("Data inválida", 
                            "A data de vencimento não pode ser no passado!");
            return false;
        }
        
        return true;
    }

    // === MÉTODOS DE EXIBIÇÃO DE DETALHES ===

    /**
     * Exibe os detalhes da tarefa selecionada
     */
    private void exibirDetalhesTarefa(Tarefa tarefa) {
        if (painelDetalhes != null) {
            painelDetalhes.setVisible(true);
            painelDetalhes.setManaged(true);
        }

        if (labelTituloDetalhes != null) {
            labelTituloDetalhes.setText(tarefa.getTitulo());
        }

        if (areaDescricaoDetalhes != null) {
            areaDescricaoDetalhes.setText(tarefa.getDescricaoFormatada());
        }

        if (labelDataCriacao != null) {
            labelDataCriacao.setText("Criada em: " + tarefa.getDataCriacaoFormatada());
        }

        if (labelDataVencimento != null) {
            labelDataVencimento.setText("Vence em: " + tarefa.getDataVencimentoFormatada());
        }

        if (labelStatusConclusao != null) {
            labelStatusConclusao.setText(tarefa.getStatusCompleto());
            
            // Aplica estilo baseado no status
            String estilo = tarefa.isConcluida() ?
                    "-fx-text-fill: #4caf50; -fx-font-weight: bold;" :
                    "-fx-text-fill: #ff9800; -fx-font-weight: bold;";
            labelStatusConclusao.setStyle(estilo);
        }
    }

    /**
     * Limpa os detalhes exibidos
     */
    private void limparDetalhes() {
        if (painelDetalhes != null) {
            painelDetalhes.setVisible(false);
            painelDetalhes.setManaged(false);
        }

        if (labelTituloDetalhes != null) labelTituloDetalhes.setText("");
        if (areaDescricaoDetalhes != null) areaDescricaoDetalhes.setText("");
        if (labelDataCriacao != null) labelDataCriacao.setText("");
        if (labelDataVencimento != null) labelDataVencimento.setText("");
        if (labelStatusConclusao != null) labelStatusConclusao.setText("");
    }

    // === MÉTODOS AUXILIARES ===

    /**
     * Limpa todos os campos do formulário
     */
    private void limparCamposFormulario() {
        if (campoTitulo != null) campoTitulo.clear();
        if (campoDescricao != null) campoDescricao.clear();
        if (seletorDataVencimento != null) seletorDataVencimento.setValue(null);
        if (checkboxConcluida != null) checkboxConcluida.setSelected(false);
    }

    /**
     * Exibe alerta de erro
     */
    private void exibirAlertaErro(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(titulo);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    /**
     * Exibe alerta de aviso
     */
    private void exibirAlertaAviso(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Atenção");
        alert.setHeaderText(titulo);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    /**
     * Exibe alerta de informação
     */
    private void exibirAlertaInformacao(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informação");
        alert.setHeaderText(titulo);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
