package com.ifpb.todolist.tarefas;

import java.time.LocalDate;

public class Tarefa {
    private long id; // ID único da tarefa
    private String titulo;
    private String descricao;
    private boolean concluida;
    private LocalDate dataCriacao;
    private LocalDate dataVencimento;

    public Tarefa(Long id, String titulo, String descricao) {
        // a validação dos parâmetros foram feitas usando setters
        // para garantir que as validações sejam aplicadas
        setId(id);
        setTitulo(titulo);
        setDescricao(descricao);
        setConcluida(false);
        setDataCriacao(LocalDate.now());
        setDataVencimento(null);
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID deve ser um número positivo.");
        }
        this.id = id;
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

    // Método para converter para linha CSV
    public String toCSV() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return id + ";" + titulo + ";" + descricao + ";" + concluida + ";"+
               (dataVencimento != null ? dataVencimento.format(formatter) : "") + ";" +
               (dataCriacao != null ? dataCriacao.format(formatter) : "");
    }
    
    // Método para criar Tarefa a partir de linha CSV
    public static Tarefa fromCSV(String csvLine) {
        String[] dados = csvLine.split(";");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate dataVenc = dados[4].isEmpty() ? null : LocalDate.parse(dados[4], formatter);
        LocalDate dataCr = dados[5].isEmpty() ? LocalDate.now() : LocalDate.parse(dados[5], formatter);

        return new Tarefa(
            Long.parseLong(dados[0]), // id
            dados[1], // titulo
            dados[2], // descricao
            Boolean.parseBoolean(dados[3]), // concluida
            dataVenc, // dataVencimento
            dataCr    // dataCriacao
        );
    }

    // output de Tarefa como String
    @Override
    public String toString() {
        String concluidaIndicador = concluida ? "✅" : "⏳";
        
        String dataVencimento = (dataVencimento != null) ? " ⚠️ VENCIDA " : "Sem vencimento";

        return String.format("ID: %d %s %s | %s | %s | Vence: %s",
                           id, titulo, descricao, concluidaIndicador, dataVencimento, dataCriacao);
    }
}