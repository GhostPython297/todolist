# 📝 Aplicativo de Tarefas - IFPB

## Descrição do Projeto

Este é um sistema desktop de gerenciamento de tarefas desenvolvido como projeto acadêmico para o IFPB. O aplicativo permite criar, visualizar, editar e excluir tarefas de forma simples e intuitiva, com duas formas de visualização: lista tradicional e cronograma organizativo.

## 🚀 Funcionalidades

### ✅ Principais Recursos

- **Sistema de Login**: Autenticação simples (usuário: Gabriel, senha: 123456)
- **Gerenciamento de Tarefas**: Criar, editar, excluir e marcar como concluída
- **Duas Visualizações**:
  - **Lista**: Visualização tradicional com detalhes expandidos
  - **Cronograma**: Organização por datas de vencimento
- **Persistência de Dados**: Armazenamento em arquivo CSV
- **Interface Responsiva**: Adaptável a diferentes tamanhos de tela
- **Indicadores Visuais**: Status colorido para tarefas (pendente, atrasada, concluída)

### Capturas de tela

_As imagens devem ser adicionadas aqui uma abaixo da outra._

### 📋 Funcionalidades Detalhadas

1. **Tela de Login**
   - Validação de credenciais
   - Mensagens de feedback
   - Botão de ajuda com informações

2. **Tela Principal (Lista)**
   - Lista interativa de tarefas
   - Formulário para adicionar novas tarefas
   - Painel de detalhes para tarefa selecionada
   - Ações: adicionar, remover, alterar status

3. **Tela de Cronograma**
   - Organização por data de vencimento
   - Destaque para tarefas de hoje e atrasadas
   - Interação direta para marcar como concluída
   - Legenda visual completa

## 🛠️ Tecnologias Utilizadas

- **Java 17**: Linguagem principal
- **JavaFX 17**: Interface gráfica
- **Maven**: Gerenciamento de dependências
- **FXML**: Definição de interfaces
- **CSS**: Estilização personalizada

## 📁 Estrutura do Projeto

```
src/main/java/com/ifpb/todolist/
├── Main.java                          # Classe principal da aplicação
├── controller/                        # Controladores das telas
│   ├── TelaLoginController.java
│   ├── TelaPrincipalController.java
│   └── TelaCronogramaController.java
├── model/                             # Modelos de dados
│   ├── Tarefa.java                   # Classe principal de tarefa
│   ├── Usuario.java                  # Modelo de usuário
│   └── CSVUtils.java                 # Utilitários para CSV
└── service/                          # Serviços de negócio
    └── ServicoAutenticacao.java      # Lógica de autenticação

src/main/resources/view/              # Interfaces FXML
├── TelaLogin.fxml
├── TelaPrincipal.fxml
└── TelaCronograma.fxml
```

## 🎯 Conceitos de POO Aplicados

### 1. **Encapsulamento**

- Atributos privados com getters/setters
- Controle de acesso aos dados das classes

### 2. **Abstração**

- Métodos que escondem complexidade interna
- Interfaces claras entre componentes

### 3. **Responsabilidade Única**

- Cada classe tem uma responsabilidade específica
- Separação clara entre modelo, visualização e controle

### 4. **Composição**

- Classes trabalham juntas através de composição
- CSVUtils usado pelos controladores para persistência

## 🚦 Como Executar

### Pré-requisitos

- Java JDK 17 ou superior
- Maven 3.6 ou superior

### Passos para Execução

1. **Clone ou baixe o projeto**

   ```bash
   git clone https://github.com/GhostPython297/todolist
   cd todolist
   ```

2. **Compile o projeto (usando Maven)**

   ```bash
   mvn clean compile
   ```

3. **Execute a aplicação (com JavaFX)**

   ```bash
   mvn javafx:run
   ```

   **Ou usando os scripts wrapper:**

   ```bash
   # Linux/Mac
   ./mvnw javafx:run
   
   # Windows
   mvnw.cmd javafx:run
   ```

### Credenciais de Teste

- **Usuário**: Gabriel
- **Senha**: 123456

## 💾 Persistência de Dados

O aplicativo utiliza um arquivo CSV (`tarefas.csv`) para armazenar as tarefas:

- **Localização**: Diretório raiz do projeto
- **Formato**: Separado por ponto e vírgula (;)
- **Campos**: titulo;descrição;concluída;dataVencimento;dataCriacao
- **Criação Automática**: O arquivo é criado automaticamente na primeira execução

## 🎨 Interface do Usuário

### Design Principles

- **Responsividade**: Adapta-se a diferentes tamanhos de tela
- **Usabilidade**: Interface intuitiva com ícones descritivos
- **Feedback Visual**: Cores e ícones indicam status das tarefas
- **Consistência**: Padrão visual mantido em todas as telas

### Paleta de Cores

- **Azul (#2196F3)**: Elementos principais e navegação
- **Verde (#4caf50)**: Tarefas concluídas e ações de sucesso
- **Vermelho (#f44336)**: Tarefas atrasadas e alertas
- **Laranja (#ff9800)**: Tarefas pendentes
- **Cinza**: Elementos secundários e bordas

## 📝 Exemplos de Uso

### Adicionando uma Nova Tarefa

1. Na tela principal, clique em "➕ Nova Tarefa"
2. Preencha o título (obrigatório)
3. Adicione descrição (opcional)
4. Selecione data de vencimento (obrigatória)
5. Marque como concluída se necessário
6. Clique em "💾 Salvar"

### Visualizando no Cronograma

1. Clique em "📅 Cronograma" no menu lateral
2. Veja tarefas organizadas por data
3. Identifique visualmente:
   - 📅 (HOJE): Tarefas que vencem hoje
   - ⚠️ (ATRASADA): Tarefas em atraso
   - ✅: Tarefas concluídas
   - ⏳: Tarefas pendentes

## 🔧 Personalização e Extensão

### Adicionando Novas Funcionalidades

1. **Novos Campos**: Edite a classe `Tarefa.java` e atualize CSV
2. **Nova Tela**: Crie controlador, FXML e adicione navegação
3. **Novos Validadores**: Adicione no service apropriado

### Modificando Persistência

- Substitua `CSVUtils` por implementação de banco de dados
- Mantenha interface compatível para não quebrar controladores

## 📚 Recursos de Aprendizado

Este projeto demonstra:

- **Arquitetura MVC**: Model-View-Controller com JavaFX
- **Manipulação de Arquivos**: Leitura e escrita de CSV
- **Interface Gráfica**: Componentes JavaFX e FXML
- **Validação de Dados**: Tratamento de entrada do usuário
- **Tratamento de Exceções**: Gestão de erros
- **Design Patterns**: Factory Method, Observer Pattern

## 🤝 Contribuição

Este é um projeto acadêmico, mas sugestões são bem-vindas:

1. Fork o projeto
2. Crie uma feature branch
3. Commit suas mudanças
4. Push para a branch
5. Abra um Pull Request

## 📄 Licença

Este projeto foi desenvolvido para fins educacionais no IFPB.

## 🎓 Autor

Desenvolvido como projeto acadêmico para demonstrar conceitos de:

- Programação Orientada a Objetos
- Desenvolvimento de Interfaces Gráficas
- Persistência de Dados
- Arquitetura de Software

---

### 💡 Dicas para Desenvolvimento

- **IDE Recomendada**: IntelliJ IDEA ou Eclipse com plugin JavaFX
- **Debugging**: Use breakpoints nos controladores para entender o fluxo
- **Personalização**: Modifique os arquivos FXML para alterar a interface
- **Testes**: Adicione classes de teste para validar funcionalidades
