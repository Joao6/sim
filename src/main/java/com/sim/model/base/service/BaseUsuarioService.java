package com.sim.model.base.service;

import com.sim.model.entity.Usuario;

/**
 *
 * @author Joao Pedro
 */
public interface BaseUsuarioService{
    
    public Usuario login(String email, String senha) throws Exception;
    
    public Usuario validaEmail(String email) throws Exception;
}
