/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sim.web.utils.responseForm;

/**
 *
 * @author Joao Pedro
 */
public class InstituicaoResponseForm {
    private Long id;    
    private String nome;
    private String email;
    private String telefone;
    private String confVersao;
    private Long qtdPacientes;
    
    public InstituicaoResponseForm(){}
    
    public InstituicaoResponseForm(Long _id, String _nome, String _email, String _telefone,
                                        String _confVersao, Long _qtdPacientes){  
        id = _id;
        nome = _nome;
        email = _email;
        telefone = _telefone;
        confVersao = _confVersao;
        qtdPacientes = _qtdPacientes;
        
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getConfVersao() {
        return confVersao;
    }

    public void setConfVersao(String confVersao) {
        this.confVersao = confVersao;
    }

    public Long getQtdPacientes() {
        return qtdPacientes;
    }

    public void setQtdPacientes(Long qtdPacientes) {
        this.qtdPacientes = qtdPacientes;
    }
}
