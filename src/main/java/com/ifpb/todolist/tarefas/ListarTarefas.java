package com.ifpb.todolist.tarefas;

public class ListarTarefas {
    private Long id;
    private String titulo;
    private String descricao;
    private boolean concluida;
    private String dataVencimento;
    private String dataCriacao;

    public ListarTarefas(Long id, String titulo, String descricao, boolean concluida, String dataVencimento, String dataCriacao) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.concluida = concluida;
        this.dataVencimento = dataVencimento;
        this.dataCriacao = dataCriacao;
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

    public String getDataCriacao() {
        return dataCriacao;
    }
    @Override
    public String toString() {
        return "Tarefa{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", concluida=" + concluida +
                ", dataVencimento='" + dataVencimento + '\'' +
                ", dataCriacao='" + dataCriacao + '\'' +
                '}';
    }
}
