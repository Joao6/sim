/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sim.web.controller;

import com.sim.model.entity.Instituicao;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Joao Pedro
 */
@Controller
public class ProfissionalPacienteController {
 
    @RequestMapping(value = "/instituicao/associar/profissional-paciente", method = RequestMethod.GET)
    public ModelAndView getAssociarProfissionalPaciente(HttpSession session){
        ModelAndView mv = null;
        try {
            Instituicao instituicao = (Instituicao) session.getAttribute("usuarioLogado");
            
            mv = new ModelAndView("instituicao/associar-profissional-paciente");
            mv.addObject("idInstituicao", instituicao.getId());
        } catch (Exception e) {
            mv = new ModelAndView("erro/error");
            mv.addObject("erro", e.getMessage());
        }
        return mv;
    }       
}
