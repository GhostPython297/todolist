package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class CSVUtils {

    private static final String ARQUIVO_CSV = "Tarefa.csv";
    private static final String CABECALHO = "titulo;descricao;concluida;dataVencimento;dataCriacao";

    // Salvar tarefas no CSV
    public static void salvarTarefas(List<Tarefa> tarefas) {
        try (FileWriter writer = new FileWriter(ARQUIVO_CSV)) {
            writer.write(CABECALHO + "\n");
            for (Tarefa tarefa : tarefas) {
                writer.write(tarefa.toCSV() + "\n");
            }
            System.out.println("✅ Tarefas salvas com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao salvar tarefas: " + e.getMessage());
        }
    }

    // Carregar tarefas do CSV
    public static List<Tarefa> carregarTarefas() {
        List<Tarefa> tarefas = new ArrayList<>();
        File arquivo = new File(ARQUIVO_CSV);

        // Verificar se o arquivo existe
        if (!arquivo.exists()) {
            System.out.println("📁 Arquivo CSV não encontrado. Criando nova lista.");
            return tarefas; // Retorna lista vazia
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_CSV))) {
            String linha;
            boolean primeiraLinha = true;

            while ((linha = reader.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false; // pular cabeçalho
                    continue;
                }
                if (!linha.trim().isEmpty()) {
                    tarefas.add(Tarefa.fromCSV(linha));
                }
            }
            System.out.println("✅ Tarefas carregadas com sucesso!");
        } catch (IOException e) {
            System.err.println("⚠️ Erro ao carregar tarefas: " + e.getMessage());
        }

        return tarefas;
    }
}