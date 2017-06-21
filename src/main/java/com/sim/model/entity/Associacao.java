/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sim.model.entity;

import com.sim.model.base.BaseEntity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Joao Pedro
 */
public class Associacao extends BaseEntity {
    
    private Long idProfissional;
    private List<Long> pacienteList;

    public Long getIdProfissional() {
        return idProfissional;
    }

    public void setIdProfissional(Long idProfissional) {
        this.idProfissional = idProfissional;
    }  

    public List<Long> getPacienteList() {
        return pacienteList;
    }

    public void setPacienteList(List<Long> pacienteList) {
        this.pacienteList = pacienteList;
    }
    
    
    public Associacao(){
        pacienteList = new ArrayList<>();
    }
}
