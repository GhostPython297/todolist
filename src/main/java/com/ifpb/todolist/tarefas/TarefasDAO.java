package com.ifpb.todolist.tarefas;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class TarefasDAO {
    private static final String ARQUIVO_CSV = "tarefas.csv";
    private static final String CABECALHO = "id;titulo;descricao;concluida;dataVencimento;dataCriacao";

    // CREATE
    public void adicionarTarefa(Tarefa tarefa) {
        try {
            int novoId = obterProximoId();
            tarefa.setId((long) novoId);

            File arquivo = new File(ARQUIVO_CSV);
            boolean arquivoNovo = !arquivo.exists();

            FileWriter writer = new FileWriter(ARQUIVO_CSV, true);

            if (arquivoNovo) {
                writer.write(CABECALHO + "\n");
            }

            writer.write(tarefa.toCSV() + "\n");
            writer.close();

            System.out.println("✅ Tarefa adicionada com sucesso! ID: " + novoId);

        } catch (IOException e) {
            System.err.println("❌ Erro ao adicionar tarefa: " + e.getMessage());
        }
    }

    // READ
    public List<Tarefa> listarTarefas() {
        List<Tarefa> tarefas = new ArrayList<>();
        try {
            File arquivo = new File(ARQUIVO_CSV);
            if (!arquivo.exists()) {
                return tarefas;
            }

            BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_CSV));
            String linha;
            boolean primeiraLinha = true;

            while ((linha = reader.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }

                try {
                    Tarefa tarefa = Tarefa.fromCSV(linha);
                    tarefas.add(tarefa);
                } catch (Exception e) {
                    System.err.println("Erro ao ler linha: " + linha);
                }
            }
            reader.close();

        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo: " + e.getMessage());
        }

        return tarefas;
    }

    // READ
    public Tarefa buscarPorId(int id) {
        return listarTarefas().stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElse(null);
    }
    public List<Tarefa> buscarPorStatus(boolean concluida) {
        return listarTarefas().stream()
                .filter(t -> t.isConcluida() == concluida)
                .collect(Collectors.toList());
    }
    public List<Tarefa> buscarTarefasVencidas() {
        return listarTarefas().stream()
                .filter(t -> t.getDataVencimento() != null && t.getDataVencimento().isBefore(java.time.LocalDate.now()))
                .collect(Collectors.toList());
    }

    // UPDATE
    public boolean atualizarTarefa(int id, Tarefa tarefaAtualizada) {
        List<Tarefa> tarefas = listarTarefas();
        boolean encontrado = false;

        for (int i = 0; i < tarefas.size(); i++) {
            if (tarefas.get(i).getId() == id) {
                tarefaAtualizada.setId((long) id);
                tarefas.set(i, tarefaAtualizada);
                encontrado = true;
                break;
            }
        }

        if (encontrado) {
            salvarTarefas(tarefas);
            System.out.println("✅ Tarefa atualizada com sucesso!");
            return true;
        }

        return false;
    }

    // marcar tarefa como concluída
    public boolean concluirTarefa(int id) {
        List<Tarefa> tarefas = listarTarefas();
        for (Tarefa tarefa : tarefas) {
            if (tarefa.getId() == id) {
                tarefa.setConcluida(true);
                salvarTarefas(tarefas);
                System.out.println("✅ Tarefa concluída com sucesso!");
                return true;
            }
        }
        return false;
    }

    // DELETE
    public boolean removerTarefa(int id) {
        List<Tarefa> tarefas = listarTarefas();
        boolean encontrado = false;
        for (Tarefa tarefa : tarefas) {
            if (tarefa.getId() == id) {
                tarefas.remove(tarefa);
                encontrado = true;
                break;
            }
        }
        if (encontrado) {
            salvarTarefas(tarefas);
            System.out.println("✅ Tarefa removida com sucesso!");
            return true;
        }
        return false;
    }

    // salvar tarefas no arquivo CSV
    private void salvarTarefas(List<Tarefa> tarefas) {
        try (FileWriter writer = new FileWriter(ARQUIVO_CSV)) {
            writer.write(CABECALHO + "\n");
            for (Tarefa tarefa : tarefas) {
                writer.write(tarefa.toCSV() + "\n");
            }
            writer.close();
            System.out.println("✅ Tarefas salvas com sucesso!");

        } catch (IOException e) {
            System.err.println("Erro ao salvar tarefas: " + e.getMessage());
        }
    }

    // gerar próximo ID
    private int obterProximoId() {
        int i = listarTarefas().stream()
                .mapToInt(Tarefa::getId)
                .max()
                .orElse(0) + 1;
        return i;
    }
}