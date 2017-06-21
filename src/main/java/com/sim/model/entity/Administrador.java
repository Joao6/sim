package com.sim.model.entity;

/**
 *
 * @author Joao Pedro
 */
public class Administrador extends Usuario {
    
    private Instituicao instituicao;

    public Instituicao getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(Instituicao instituicao) {
        this.instituicao = instituicao;
    }
    
}
