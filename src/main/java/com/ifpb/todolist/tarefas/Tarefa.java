package com.ifpb.todolist.tarefas;

import java.time.LocalDate;

public class Tarefa {
    private String titulo;
    private String descricao;
    private boolean concluida;
    private LocalDate dataCriacao;
    private LocalDate dataVencimento;

    public Tarefa(String titulo, String descricao) {
        // a validação dos parâmetros foram feitas usando setters
        // para garantir que as validações sejam aplicadas
        setTitulo(titulo);
        setDescricao(descricao);
        setConcluida(false);
        setDataCriacao(LocalDate.now());
        setDataVencimento(null);
    }

    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
        if (titulo == null || titulo.isEmpty()) {
            throw new IllegalArgumentException("Título não pode ser vazio.");
        }
    }

    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
        if (descricao == null || descricao.isEmpty()) {
            throw new IllegalArgumentException("Descrição não pode ser vazia.");
        }
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
        if (dataCriacao == null) {
            throw new IllegalArgumentException("Data de criação não pode ser nula.");
        }
        this.dataCriacao = dataCriacao;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }
    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
        if (dataVencimento != null && dataVencimento.isBefore(dataCriacao)) {
            throw new IllegalArgumentException("Data de vencimento não pode ser anterior à data de criação.");
        }
    }

    // formatação string para exibição mandar pro CSV
    public String toCSV() {
        return String.join(",", titulo, descricao, String.valueOf(concluida),
                           dataCriacao.toString(),
                           dataVencimento != null ? dataVencimento.toString() : "");
    }

    /* retornando o conteúdo da string em lista para ser usado no CSV
    public static Tarefa fromCSV(String csv) {
        String[] linha = csv.split(",");

        return new Tarefa(
                linha[0], // título
                linha[1]  // descrição
        );
    }
     */
}