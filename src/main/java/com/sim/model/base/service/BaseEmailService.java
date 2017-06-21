/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sim.model.base.service;

/**
 *
 * @author Joao
 */
public interface BaseEmailService {
    
    public void sendEmail(String destino, String assunto, String texto) throws Exception;    
}
