package com.ifpb.todolist.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.ifpb.todolist.model.Usuario;
import com.ifpb.todolist.service.ServicoAutenticacao;

import java.io.IOException;

/**
 * Controlador da tela de login
 * 
 * Responsável por:
 * - Capturar dados de entrada do usuário
 * - Validar credenciais
 * - Navegar para a tela principal após login bem-sucedido
 * 
 * Demonstra conceitos de POO:
 * - Responsabilidade única: controla apenas a tela de login
 * - Composição: usa ServicoAutenticacao para lógica de negócio
 */
public class TelaLoginController {

    // === COMPONENTES DA INTERFACE (FXML) ===
    
    @FXML
    private TextField campoUsuario; // Campo para nome de usuário
    
    @FXML
    private PasswordField campoSenha; // Campo para senha
    
    @FXML
    private Label labelMensagem; // Label para exibir mensagens de erro/sucesso
    
    @FXML
    private Button botaoEntrar; // Botão de login

    /**
     * Método chamado automaticamente após o carregamento do FXML
     * Usado para configurações iniciais da tela
     */
    @FXML
    public void initialize() {
        // Configura a mensagem inicial (opcional)
        if (labelMensagem != null) {
            labelMensagem.setText("");
            labelMensagem.setStyle("-fx-text-fill: #666666;");
        }
        
        // Permite login pressionando Enter no campo de senha
        if (campoSenha != null) {
            campoSenha.setOnAction(this::processarLogin);
        }
        
        // Foco inicial no campo de usuário
        if (campoUsuario != null) {
            campoUsuario.requestFocus();
        }
    }

    /**
     * Método chamado quando o botão "Entrar" é clicado
     * Processa o login e navega para a tela principal se bem-sucedido
     */
    @FXML
    public void processarLogin(ActionEvent event) {
        try {
            // Obtém os dados dos campos
            String nomeUsuario = campoUsuario.getText();
            String senha = campoSenha.getText();
            
            // Validação de formato
            String erroValidacao = ServicoAutenticacao.validarFormatoCredenciais(nomeUsuario, senha);
            if (erroValidacao != null) {
                exibirMensagemErro(erroValidacao);
                return;
            }
            
            // Tenta autenticar
            Usuario usuarioAutenticado = ServicoAutenticacao.autenticar(nomeUsuario, senha);
            
            if (usuarioAutenticado != null) {
                // Login bem-sucedido
                exibirMensagemSucesso("Login bem-sucedido!");
                
                // Navega para a tela principal
                navegarParaTelaPrincipal(event);
                
            } else {
                // Login falhou
                exibirMensagemErro("Usuário ou senha incorretos.");
                limparCampoSenha();
            }
            
        } catch (Exception e) {
            exibirMensagemErro("Erro interno. Tente novamente.");
            e.printStackTrace();
        }
    }

    /**
     * Navega para a tela principal do aplicativo
     */
    private void navegarParaTelaPrincipal(ActionEvent event) throws IOException {
        // Carrega o arquivo FXML da tela principal
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaPrincipal.fxml"));
        Parent telaPrincipal = loader.load();
        
        // Cria nova cena
        Scene novaCena = new Scene(telaPrincipal);
        
        // Obtém a janela atual
        Stage janela = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        // Muda para a nova tela
        janela.setScene(novaCena);
        janela.setTitle("Aplicativo de Tarefas - Tela Principal");
        
        // Centraliza a janela novamente (opcional)
        janela.centerOnScreen();
    }

    /**
     * Exibe mensagem de erro na interface
     */
    private void exibirMensagemErro(String mensagem) {
        if (labelMensagem != null) {
            labelMensagem.setText(mensagem);
            labelMensagem.setStyle("-fx-text-fill: #d32f2f; -fx-font-weight: bold;");
        }
    }

    /**
     * Exibe mensagem de sucesso na interface
     */
    private void exibirMensagemSucesso(String mensagem) {
        if (labelMensagem != null) {
            labelMensagem.setText(mensagem);
            labelMensagem.setStyle("-fx-text-fill: #4caf50; -fx-font-weight: bold;");
        }
    }

    /**
     * Limpa o campo de senha (por segurança)
     */
    private void limparCampoSenha() {
        if (campoSenha != null) {
            campoSenha.clear();
            campoSenha.requestFocus();
        }
    }

    /**
     * Limpa todos os campos do formulário
     */
    @FXML
    public void limparCampos() {
        if (campoUsuario != null) {
            campoUsuario.clear();
        }
        if (campoSenha != null) {
            campoSenha.clear();
        }
        if (labelMensagem != null) {
            labelMensagem.setText("");
        }
        
        // Retorna foco ao campo de usuário
        if (campoUsuario != null) {
            campoUsuario.requestFocus();
        }
    }

    /**
     * Método para exibir informações de ajuda (opcional)
     */
    @FXML
    public void exibirAjuda() {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Informações de Login");
        alerta.setHeaderText("Credenciais para teste");
        alerta.setContentText(ServicoAutenticacao.getInformacoesLogin());
        alerta.showAndWait();
    }
}
