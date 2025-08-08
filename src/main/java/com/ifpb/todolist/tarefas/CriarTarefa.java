package com.ifpb.todolist.tarefas;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CriarTarefa {
    private Long id;
    private String titulo;
    private String descricao;

    public CriarTarefa(Long id, String titulo, String descricao) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    // salvar tarefa em arquivo CSV
    private void salvarTarefas(List<Tarefa> tarefas) {
        String ARQUIVO_CSV = "tarefas.csv";
        String CABECALHO = "id;titulo;descricao;concluida;dataVencimento;dataCriacao";

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
}
