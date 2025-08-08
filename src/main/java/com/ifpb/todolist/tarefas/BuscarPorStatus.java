package com.ifpb.todolist.tarefas;

public class BuscarPorStatus {
    private boolean concluida;

    public BuscarPorStatus(boolean concluida) {
        this.concluida = concluida;
    }

    public boolean isConcluida() {
        return concluida;
    }
    public void setConcluida(boolean concluida) {
        this.concluida = concluida;
    }
}
