package com.sim.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Joao Pedro
 */
@Controller
public class MasterController {
    
    @RequestMapping(value = "/master/home", method = RequestMethod.GET)
    public ModelAndView getMasterHome(){
        ModelAndView mv = new ModelAndView("master/home");
        
        return mv;
    }
    
    @RequestMapping(value = "/master/permissao-negada", method = RequestMethod.GET)
    public ModelAndView getPermissaoNegada(){
        ModelAndView mv = new ModelAndView("master/permissao-negada");        
        return mv;
    }
    
    @RequestMapping(value = "/master/cadastrar-instituicao", method = RequestMethod.GET)
    public ModelAndView getCadastrarInstituicao(){
        ModelAndView mv = null; 
        try {
            mv = new ModelAndView("master/cadastrar-instituicao");
        } catch (Exception e) {
            mv = new ModelAndView("erro/error");
            mv.addObject("erro", e.getMessage());
        }
        return mv;
    }
    
    @RequestMapping(value = "/master/meus-dados", method = RequestMethod.GET)
    public ModelAndView getEditarDados(){
        ModelAndView mv = null; 
        try {
            mv = new ModelAndView("master/meus-dados");
        } catch (Exception e) {
            mv = new ModelAndView("erro/error");
            mv.addObject("erro", e.getMessage());
        }
        return mv;
    }
    
    @RequestMapping(value = "/master/instituicao/{id}/editar", method = RequestMethod.GET)
    public ModelAndView getEditarInstituicao(@PathVariable Long id){
        ModelAndView mv = null; 
        try {
            mv = new ModelAndView("master/editar-instituicao");
            mv.addObject("idInstituicao", id);
        } catch (Exception e) {
            mv = new ModelAndView("erro/error");
            mv.addObject("erro", e.getMessage());
        }
        return mv;
    }
    
    @RequestMapping(value = "/master/alterar-senha", method = RequestMethod.GET)
    public ModelAndView getAlterarSenha(){
        ModelAndView mv = null; 
        try {
            mv = new ModelAndView("master/alterar-senha");
        } catch (Exception e) {
            mv = new ModelAndView("erro/error");
            mv.addObject("erro", e.getMessage());
        }
        return mv;
    }
}
