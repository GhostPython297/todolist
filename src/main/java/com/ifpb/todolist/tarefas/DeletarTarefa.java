package com.ifpb.todolist.tarefas;

public class DeletarTarefa {
    private Long id;

    public DeletarTarefa(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // removendo a tarefa
    public void remover() {
        // lógica para remover a tarefa
        System.out.println("Tarefa com ID " + id + " removida.");
    }

    @Override
    public String toString() {
        return "DeletarTarefa{" +
                "id=" + id +
                '}';
    }
}
