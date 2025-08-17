/**
 * Configuração do módulo para o Aplicativo de Tarefas
 * 
 * Define as dependências necessárias e exporta os pacotes adequados
 * para o funcionamento correto da aplicação JavaFX
 */
module com.ifpb.todolist {
    // Dependências do JavaFX
    requires javafx.controls;  // Para componentes de interface
    requires javafx.fxml;      // Para arquivos FXML
    
    // Exporta pacotes principais para uso externo
    exports com.ifpb.todolist;
    exports com.ifpb.todolist.model;
    exports com.ifpb.todolist.controller;
    exports com.ifpb.todolist.service;
    
    // Permite que o JavaFX acesse os controladores via reflexão
    opens com.ifpb.todolist.controller to javafx.fxml;
    opens com.ifpb.todolist.model to javafx.fxml;
    
    // Permite acesso aos recursos FXML
    opens com.ifpb.todolist to javafx.fxml;
}
