package com.sim.model.entity;

import com.sim.model.base.BaseEntity;
import java.util.Date;

/**
 *
 * @author Joao Pedro
 */
public class Atendimento extends BaseEntity {

    private Paciente paciente;
    private Profissional profissional;
    private Date dataApontamento;
    private String apontamento;

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Profissional getProfissional() {
        return profissional;
    }

    public void setProfissional(Profissional profissional) {
        this.profissional = profissional;
    }

    public Date getDataApontamento() {
        return dataApontamento;
    }

    public void setDataApontamento(Date dataApontamento) {
        this.dataApontamento = dataApontamento;
    }

    public String getApontamento() {
        return apontamento;
    }

    public void setApontamento(String apontamento) {
        this.apontamento = apontamento;
    }
}
