package com.ifpb.todolist.tarefas;

import java.time.LocalDate;

public class Tarefa {
    private long id;
    private String titulo;
    private String descricao;
    private boolean concluida;
    private LocalDate dataCriacao;
    private LocalDate dataVencimento;

    public Tarefa(long id, String titulo, String descricao, boolean concluida, LocalDate dataCriacao, LocalDate dataVencimento) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.concluida = concluida;
        this.dataCriacao = dataCriacao;
        this.dataVencimento = dataVencimento;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isConcluida() {
        return concluida;
    }

    public void setConcluida(boolean concluida) {
        this.concluida = concluida;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    // Método para converter a tarefa em uma string CSV
    public String toCSV() {
        return id + ";" + titulo + ";" + descricao + ";" + concluida + ";" +
               (dataVencimento != null ? dataVencimento.toString() : "") + ";" +
               dataCriacao.toString();
    }

    // Método para criar uma tarefa a partir de uma string CSV
    public static Tarefa fromCSV(String csv) {
        String[] partes = csv.split(";");

        // conteúdos esperados: id;titulo;descricao;concluida;dataVencimento;dataCriacao
        long id = Long.parseLong(partes[0]);
        String titulo = partes[1];
        String descricao = partes[2];
        boolean concluida = Boolean.parseBoolean(partes[3]);
        LocalDate dataVencimento = partes[4].isEmpty() ? null : LocalDate.parse(partes[4]);
        LocalDate dataCriacao = LocalDate.parse(partes[5]);

        return new Tarefa(id, titulo, descricao, concluida, dataCriacao, dataVencimento);
    }
    @Override
    public String toString() {
        return "Tarefa{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", concluida=" + concluida +
                ", dataCriacao=" + dataCriacao +
                ", dataVencimento=" + dataVencimento +
                '}';
    }
}