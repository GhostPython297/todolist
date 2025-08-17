package com.ifpb.todolist.service;

import com.ifpb.todolist.model.Usuario;

/**
 * Classe de serviço responsável pela autenticação de usuários
 * 
 * Demonstra conceitos de POO:
 * - Responsabilidade única: focada apenas em lógica de autenticação
 * - Abstração: esconde a complexidade da validação de usuários
 * - Singleton: poderia ser implementado como singleton se necessário
 */
public class ServicoAutenticacao {
    
    // Para este projeto educacional, definimos um usuário fixo
    // Em um projeto real, estes dados viriam de um banco de dados
    private static final String USUARIO_PADRAO = "Gabriel";
    private static final String SENHA_PADRAO = "123456";
    
    /**
     * Autentica um usuário no sistema
     * 
     * @param nomeUsuario nome de usuário fornecido
     * @param senha senha fornecida
     * @return Usuario autenticado se credenciais válidas, null caso contrário
     */
    public static Usuario autenticar(String nomeUsuario, String senha) {
        // Validações básicas
        if (nomeUsuario == null || senha == null) {
            return null;
        }
        
        // Remove espaços em branco extras
        nomeUsuario = nomeUsuario.trim();
        
        // Verifica se as credenciais estão corretas
        if (USUARIO_PADRAO.equals(nomeUsuario) && SENHA_PADRAO.equals(senha)) {
            return new Usuario(nomeUsuario, senha);
        }
        
        return null; // Credenciais inválidas
    }
    
    /**
     * Valida se as credenciais têm formato válido
     * 
     * @param nomeUsuario nome de usuário a validar
     * @param senha senha a validar
     * @return mensagem de erro ou null se válido
     */
    public static String validarFormatoCredenciais(String nomeUsuario, String senha) {
        if (nomeUsuario == null || nomeUsuario.trim().isEmpty()) {
            return "Nome de usuário é obrigatório.";
        }
        
        if (senha == null || senha.trim().isEmpty()) {
            return "Senha é obrigatória.";
        }
        
        if (senha.length() < 4) {
            return "A senha deve ter pelo menos 4 caracteres.";
        }
        
        return null; // Válido
    }
    
    /**
     * Retorna informações sobre as credenciais padrão
     * (Método para fins educacionais/demonstração)
     * 
     * @return String com informações de login
     */
    public static String getInformacoesLogin() {
        return "Para este projeto de demonstração, use:\n" +
               "Usuário: " + USUARIO_PADRAO + "\n" +
               "Senha: " + SENHA_PADRAO;
    }
    
    /**
     * Verifica se um nome de usuário existe no sistema
     * (Para futuras expansões)
     * 
     * @param nomeUsuario nome a verificar
     * @return true se existe
     */
    public static boolean usuarioExiste(String nomeUsuario) {
        return USUARIO_PADRAO.equals(nomeUsuario);
    }
}
