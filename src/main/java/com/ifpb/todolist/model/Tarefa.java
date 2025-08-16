package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Tarefa {
    private String titulo;
    private String descricao;
    private boolean concluida;
    private LocalDate dataCriacao;
    private LocalDate dataVencimento;

    // Formatador para exibição de datas
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Construtor principal (usado na leitura do CSV)
    public Tarefa(String titulo, String descricao, boolean concluida, LocalDate dataCriacao, LocalDate dataVencimento) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.concluida = concluida;
        this.dataCriacao = dataCriacao;
        this.dataVencimento = dataVencimento;
    }

    // Construtor simplificado (para novas tarefas)
    public Tarefa(String titulo, String descricao, LocalDate dataVencimento) {
        this(titulo, descricao, false, LocalDate.now(), dataVencimento);
    }

    // Getters e Setters
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public boolean isConcluida() { return concluida; }
    public void setConcluida(boolean concluida) { this.concluida = concluida; }

    public LocalDate getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDate dataCriacao) { this.dataCriacao = dataCriacao; }

    public LocalDate getDataVencimento() { return dataVencimento; }
    public void setDataVencimento(LocalDate dataVencimento) { this.dataVencimento = dataVencimento; }

    // Verificar se a tarefa está atrasada
    public boolean isAtrasada() {
        if (concluida || dataVencimento == null) {
            return false;
        }
        return dataVencimento.isBefore(LocalDate.now());
    }

    // Calcular dias restantes
    public long diasRestantes() {
        if (concluida || dataVencimento == null) {
            return 0;
        }
        return java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), dataVencimento);
    }

    // Converte para CSV
    public String toCSV() {
        return titulo + ";" +
                (descricao != null ? descricao : "") + ";" +
                concluida + ";" +
                (dataVencimento != null ? dataVencimento.toString() : "") + ";" +
                dataCriacao.toString();
    }

    // Cria a tarefa a partir do CSV
    public static Tarefa fromCSV(String csv) {
        try {
            String[] partes = csv.split(";", -1); // -1 para manter campos vazios
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

    // Exibição na ListView
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // Ícone de status
        if (concluida) {
            sb.append("✅ ");
        } else if (isAtrasada()) {
            sb.append("🔴 ");
        } else {
            sb.append("⏳ ");
        }

        // Título
        sb.append(titulo);

        // Informações adicionais
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

    // Obter descrição formatada para exibição
    public String getDescricaoFormatada() {
        if (descricao == null || descricao.trim().isEmpty()) {
            return "Sem descrição disponível";
        }
        return descricao;
    }

    // Obter data de vencimento formatada
    public String getDataVencimentoFormatada() {
        if (dataVencimento == null) {
            return "Não definida";
        }
        return dataVencimento.format(FORMATTER);
    }

    // Obter data de criação formatada
    public String getDataCriacaoFormatada() {
        return dataCriacao.format(FORMATTER);
    }

    // Obter status completo
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