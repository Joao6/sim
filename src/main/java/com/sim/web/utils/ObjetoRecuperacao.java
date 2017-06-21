package com.sim.web.utils;

/**
 *
 * @author Joao Pedro
 */
public class ObjetoRecuperacao {
    
    private String senha;
    private Long idUsuario;
    private Long idRecuperacao;

    public Long getIdRecuperacao() {
        return idRecuperacao;
    }

    public void setIdRecuperacao(Long idRecuperacao) {
        this.idRecuperacao = idRecuperacao;
    }
    

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    
    
}
