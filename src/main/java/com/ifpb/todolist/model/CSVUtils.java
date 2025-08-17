package com.ifpb.todolist.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe utilitária para manipulação de arquivos CSV
 * 
 * Demonstra conceitos de POO:
 * - Responsabilidade única: focada apenas em operações de arquivo
 * - Métodos estáticos: operações que não dependem de estado da instância
 * - Abstração: esconde a complexidade das operações de arquivo
 */
public class CSVUtils {

    // Nome do arquivo onde as tarefas serão salvas
    private static final String ARQUIVO_CSV = "tarefas.csv";
    
    // Cabeçalho do arquivo CSV
    private static final String CABECALHO = "titulo;descricao;concluida;dataVencimento;dataCriacao";

    /**
     * Salva uma lista de tarefas no arquivo CSV
     * 
     * @param tarefas Lista de tarefas a serem salvas
     */
    public static void salvarTarefas(List<Tarefa> tarefas) {
        try (FileWriter writer = new FileWriter(ARQUIVO_CSV)) {
            // Escreve o cabeçalho
            writer.write(CABECALHO + "\n");
            
            // Escreve cada tarefa convertida para CSV
            for (Tarefa tarefa : tarefas) {
                writer.write(tarefa.toCSV() + "\n");
            }
            
            System.out.println("✅ Tarefas salvas com sucesso no arquivo: " + ARQUIVO_CSV);
            
        } catch (IOException e) {
            System.err.println("❌ Erro ao salvar tarefas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Carrega as tarefas do arquivo CSV
     * 
     * @return Lista de tarefas carregadas (vazia se arquivo não existir ou houver erro)
     */
    public static List<Tarefa> carregarTarefas() {
        List<Tarefa> tarefas = new ArrayList<>();
        File arquivo = new File(ARQUIVO_CSV);

        // Verifica se o arquivo existe
        if (!arquivo.exists()) {
            System.out.println("📁 Arquivo CSV não encontrado. Será criado na primeira operação de salvamento.");
            return tarefas; // Retorna lista vazia
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_CSV))) {
            String linha;
            boolean primeiraLinha = true;

            // Lê cada linha do arquivo
            while ((linha = reader.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false; // Pula o cabeçalho
                    continue;
                }
                
                // Ignora linhas vazias
                if (!linha.trim().isEmpty()) {
                    try {
                        // Converte a linha CSV em objeto Tarefa
                        tarefas.add(Tarefa.fromCSV(linha));
                    } catch (Exception e) {
                        System.err.println("⚠️ Erro ao processar linha: " + linha);
                        System.err.println("   Motivo: " + e.getMessage());
                        // Continua processando as outras linhas
                    }
                }
            }
            
            System.out.println("✅ " + tarefas.size() + " tarefa(s) carregada(s) com sucesso!");
            
        } catch (IOException e) {
            System.err.println("❌ Erro ao carregar tarefas: " + e.getMessage());
            e.printStackTrace();
        }

        return tarefas;
    }

    /**
     * Verifica se existe um arquivo de dados
     * 
     * @return true se o arquivo existe, false caso contrário
     */
    public static boolean arquivoExiste() {
        return new File(ARQUIVO_CSV).exists();
    }

    /**
     * Retorna o nome do arquivo CSV usado
     * 
     * @return nome do arquivo
     */
    public static String getNomeArquivo() {
        return ARQUIVO_CSV;
    }

    /**
     * Cria um backup do arquivo atual (opcional - para funcionalidade avançada)
     * 
     * @return true se o backup foi criado com sucesso
     */
    public static boolean criarBackup() {
        File arquivo = new File(ARQUIVO_CSV);
        if (!arquivo.exists()) {
            return false;
        }

        try {
            File backup = new File(ARQUIVO_CSV + ".backup");
            
            // Copia o conteúdo
            try (BufferedReader reader = new BufferedReader(new FileReader(arquivo));
                 FileWriter writer = new FileWriter(backup)) {
                
                String linha;
                while ((linha = reader.readLine()) != null) {
                    writer.write(linha + "\n");
                }
            }
            
            System.out.println("✅ Backup criado: " + backup.getName());
            return true;
            
        } catch (IOException e) {
            System.err.println("❌ Erro ao criar backup: " + e.getMessage());
            return false;
        }
    }
}
