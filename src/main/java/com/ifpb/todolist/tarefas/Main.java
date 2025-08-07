package com.ifpb.todolist.tarefas;

public class Main {
    // crud para gerenciamento básico de tarefas
    // as funções podem ser extendidas no futuro
    // para incluir funcionalidades como edição, exclusão, etc.
    public static void main(String[] args) {
        private static TarefasDAO dao = new TarefasDAO();
        private static Scanner scanner = new Scanner(System.in);
        private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.println("📝 === GERENCIADOR DE TAREFAS ===");
        
        while (true) {
            exibirMenu();
            int opcao = scanner.nextInt();
            scanner.nextLine();
            
            switch (opcao) {
                case 1:
                    adicionarTarefa();
                    break;
                case 2:
                    listarTarefas();
                    break;
                case 4:
                    excluirTarefa();
                    break;
                case 12:
                    System.out.println("👋 Saindo do sistema...");
                    return;
                default:
                    System.out.println("❌ Opção inválida!");
            }
            
            System.out.println("\nPressione Enter para continuar...");
            scanner.nextLine();
        }
    }

    private static void exibirMenu() {
        System.out.println("\n==== MENU ====");
        System.out.println("1. ➕ Adicionar Tarefa");
        System.out.println("2. 📋 Listar Todas as Tarefas");
        System.out.println("4. 🗑️  Excluir Tarefa");
        System.out.println("12. 🚪 Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void adicionarTarefa() {
        System.out.println("\n➕ === ADICIONAR TAREFA ===");

        System.out.print("ID: ");
        Long id = dao.obterProximoId();
        System.out.println("ID da Tarefa: " + id);

        System.out.print("Título: ");
        String titulo = scanner.nextLine();

        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();

        LocalDate dataVencimento = null;
        while (dataVencimento == null) {
            System.out.print("Data de vencimento (dd/MM/yyyy): ");
            String dataStr = scanner.nextLine();
            try {
            dataVencimento = LocalDate.parse(dataStr, formatter);
            } catch (DateTimeParseException e) {
            System.out.println("❌ Data inválida! Use o formato dd/MM/yyyy");
            }
        }

        Tarefa tarefa = new Tarefa(id, titulo, descricao, dataVencimento);
        dao.adicionarTarefa(tarefa);
    }
    private static void listarTarefas() {
        System.out.println("\n📋 === LISTAR TAREFAS ===");
        List<Tarefa> tarefas = dao.listarTarefas();
        
        if (tarefas.isEmpty()) {
            System.out.println("Nenhuma tarefa encontrada.");
            return;
        }
        
        for (Tarefa tarefa : tarefas) {
            System.out.println(tarefa);
        }
    }
    private static void excluirTarefa() {
        System.out.println("\n🗑️ === EXCLUIR TAREFA ===");
        System.out.print("Digite o ID da tarefa a ser excluída: ");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Limpar o buffer
        
        if (dao.excluirTarefa(id)) {
            System.out.println("✅ Tarefa excluída com sucesso!");
        } else {
            System.out.println("❌ Tarefa não encontrada ou erro ao excluir.");
        }
    }
}
