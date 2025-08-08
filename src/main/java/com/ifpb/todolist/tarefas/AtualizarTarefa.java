package com.ifpb.todolist.tarefas;

public class AtualizarTarefa {
    private Long id;
    private String titulo;
    private String descricao;
    private boolean concluida;
    private String dataVencimento;

    public AtualizarTarefa(Long id, String titulo, String descricao, boolean concluida, String dataVencimento) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.concluida = concluida;
        this.dataVencimento = dataVencimento;
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

    public boolean isConcluida() {
        return concluida;
    }

    public String getDataVencimento() {
        return dataVencimento;
    }

    // marcar como concluída
    public void marcarComoConcluida() {
        this.concluida = true;
    }
}
