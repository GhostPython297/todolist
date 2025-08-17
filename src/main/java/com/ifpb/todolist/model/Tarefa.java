package com.ifpb.todolist.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Classe que representa uma tarefa no sistema
 * Contém todas as informações e comportamentos relacionados a uma tarefa
 * 
 * Demonstra conceitos de POO:
 * - Encapsulamento: atributos privados com getters/setters
 * - Abstração: métodos que encapsulam lógica complexa
 * - Responsabilidade única: classe focada apenas em representar uma tarefa
 */
public class Tarefa {
    
    // Atributos privados (encapsulamento)
    private String titulo;
    private String descricao;
    private boolean concluida;
    private LocalDate dataCriacao;
    private LocalDate dataVencimento;

    // Formatador para exibição de datas no padrão brasileiro
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Construtor completo - usado principalmente na leitura do CSV
     */
    public Tarefa(String titulo, String descricao, boolean concluida, 
                  LocalDate dataCriacao, LocalDate dataVencimento) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.concluida = concluida;
        this.dataCriacao = dataCriacao;
        this.dataVencimento = dataVencimento;
    }

    /**
     * Construtor simplificado - usado para criar novas tarefas
     * Automaticamente define a data de criação como hoje e a tarefa como não concluída
     */
    public Tarefa(String titulo, String descricao, LocalDate dataVencimento) {
        this(titulo, descricao, false, LocalDate.now(), dataVencimento);
    }

    // === GETTERS E SETTERS (Encapsulamento) ===
    
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

    // === MÉTODOS DE LÓGICA DE NEGÓCIO ===

    /**
     * Verifica se a tarefa está atrasada
     * Uma tarefa está atrasada se não foi concluída e a data de vencimento já passou
     */
    public boolean isAtrasada() {
        if (concluida || dataVencimento == null) {
            return false;
        }
        return dataVencimento.isBefore(LocalDate.now());
    }

    /**
     * Calcula quantos dias restam para o vencimento
     * Retorna 0 se a tarefa já foi concluída ou não tem data de vencimento
     */
    public long diasRestantes() {
        if (concluida || dataVencimento == null) {
            return 0;
        }
        return java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), dataVencimento);
    }

    // === MÉTODOS PARA PERSISTÊNCIA ===

    /**
     * Converte a tarefa para formato CSV
     * Usado para salvar no arquivo
     */
    public String toCSV() {
        return titulo + ";" +
                (descricao != null ? descricao : "") + ";" +
                concluida + ";" +
                (dataVencimento != null ? dataVencimento.toString() : "") + ";" +
                dataCriacao.toString();
    }

    /**
     * Cria uma tarefa a partir de uma linha CSV
     * Método estático (factory method) para criar instâncias a partir de dados persistidos
     */
    public static Tarefa fromCSV(String csv) {
        try {
            // Divide a linha CSV em partes (usando -1 para manter campos vazios)
            String[] partes = csv.split(";", -1);
            
            if (partes.length < 5) {
                throw new IllegalArgumentException("Formato CSV inválido: " + csv);
            }

            String titulo = partes[0];
            String descricao = partes[1].isEmpty() ? null : partes[1];
            boolean concluida = Boolean.parseBoolean(partes[2]);
            LocalDate dataVencimento = partes[3].isEmpty() ? null : LocalDate.parse(partes[3]);
            LocalDate dataCriacao = LocalDate.parse(partes[4]);

            return new Tarefa(titulo, descricao, concluida, dataCriacao, dataVencimento);
            
        } catch (Exception e) {
            System.err.println("Erro ao converter CSV: " + e.getMessage());
            throw new RuntimeException("Erro na conversão CSV", e);
        }
    }

    // === MÉTODOS PARA EXIBIÇÃO ===

    /**
     * Define como a tarefa será exibida na ListView
     * Inclui ícones visuais e informações sobre prazos
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // Adiciona ícone baseado no status
        if (concluida) {
            sb.append("✅ ");
        } else if (isAtrasada()) {
            sb.append("🔴 ");
        } else {
            sb.append("⏳ ");
        }

        // Adiciona o título
        sb.append(titulo);

        // Adiciona informações sobre prazo (se não estiver concluída)
        if (dataVencimento != null && !concluida) {
            long dias = diasRestantes();
            if (dias < 0) {
                sb.append(" (").append(Math.abs(dias)).append(" dias atrasada)");
            } else if (dias == 0) {
                sb.append(" (vence hoje!)");
            } else if (dias <= 3) {
                sb.append(" (").append(dias).append(" dias restantes)");
            }
        }

        return sb.toString();
    }

    /**
     * Retorna a descrição formatada para exibição
     */
    public String getDescricaoFormatada() {
        if (descricao == null || descricao.trim().isEmpty()) {
            return "Sem descrição disponível";
        }
        return descricao;
    }

    /**
     * Retorna a data de vencimento formatada (dd/MM/yyyy)
     */
    public String getDataVencimentoFormatada() {
        if (dataVencimento == null) {
            return "Não definida";
        }
        return dataVencimento.format(FORMATTER);
    }

    /**
     * Retorna a data de criação formatada (dd/MM/yyyy)
     */
    public String getDataCriacaoFormatada() {
        return dataCriacao.format(FORMATTER);
    }

    /**
     * Retorna o status completo da tarefa com ícone
     */
    public String getStatusCompleto() {
        if (concluida) {
            return "✅ Concluída";
        } else if (isAtrasada()) {
            return "🔴 Atrasada";
        } else {
            return "⏳ Pendente";
        }
    }
}
