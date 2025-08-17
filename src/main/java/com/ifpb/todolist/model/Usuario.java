package com.ifpb.todolist.model;

/**
 * Classe que representa um usuário do sistema
 * 
 * Para este projeto educacional, implementamos um sistema de login simples
 * Em uma aplicação real, as senhas seriam criptografadas e armazenadas em banco de dados
 * 
 * Demonstra conceitos de POO:
 * - Encapsulamento: dados privados com acesso controlado
 * - Responsabilidade única: representa apenas informações do usuário
 */
public class Usuario {
    
    // Atributos privados (encapsulamento)
    private String nomeUsuario;
    private String senha;
    
    /**
     * Construtor da classe Usuario
     * 
     * @param nomeUsuario nome de usuário para login
     * @param senha senha do usuário
     */
    public Usuario(String nomeUsuario, String senha) {
        this.nomeUsuario = nomeUsuario;
        this.senha = senha;
    }
    
    // === GETTERS E SETTERS ===
    
    public String getNomeUsuario() {
        return nomeUsuario;
    }
    
    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }
    
    public String getSenha() {
        return senha;
    }
    
    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    // === MÉTODOS DE VALIDAÇÃO ===
    
    /**
     * Valida se as credenciais fornecidas correspondem a este usuário
     * 
     * @param nomeUsuario nome de usuário a verificar
     * @param senha senha a verificar
     * @return true se as credenciais estão corretas
     */
    public boolean validarCredenciais(String nomeUsuario, String senha) {
        return this.nomeUsuario.equals(nomeUsuario) && this.senha.equals(senha);
    }
    
    /**
     * Verifica se o nome de usuário é válido (não vazio)
     * 
     * @return true se válido
     */
    public boolean isNomeUsuarioValido() {
        return nomeUsuario != null && !nomeUsuario.trim().isEmpty();
    }
    
    /**
     * Verifica se a senha atende aos critérios mínimos
     * Para este projeto simples, apenas verifica se não está vazia
     * 
     * @return true se válida
     */
    public boolean isSenhaValida() {
        return senha != null && senha.length() >= 4; // Mínimo de 4 caracteres
    }
    
    @Override
    public String toString() {
        return "Usuario{" +
                "nomeUsuario='" + nomeUsuario + '\'' +
                ", senha='[PROTEGIDA]'" + // Não exibe a senha real por segurança
                '}';
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Usuario usuario = (Usuario) obj;
        return nomeUsuario.equals(usuario.nomeUsuario);
    }
    
    @Override
    public int hashCode() {
        return nomeUsuario.hashCode();
    }
}
