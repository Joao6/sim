package com.sim.model.entity;

import com.sim.model.base.BaseEntity;
import java.util.Date;

/**
 *
 * @author Joao Pedro
 */
public class Mensagem extends BaseEntity {
    
    private Profissional remetente;
    private Profissional destinatario;
    private String conteudo;
    private Date dataEnvio;
    private Date dataLeitura;
    private Boolean isLida;

    public Profissional getRemetente() {
        return remetente;
    }

    public void setRemetente(Profissional remetente) {
        this.remetente = remetente;
    }

    public Profissional getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(Profissional destinatario) {
        this.destinatario = destinatario;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public Date getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(Date dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public Date getDataLeitura() {
        return dataLeitura;
    }

    public void setDataLeitura(Date dataLeitura) {
        this.dataLeitura = dataLeitura;
    }

    public Boolean getIsLida() {
        return isLida;
    }

    public void setIsLida(Boolean isLida) {
        this.isLida = isLida;
    }
}
