package com.sim.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Joao Pedro
 */
@Controller
public class MensagemController {
    
    @RequestMapping(value = "/profissional/mensagens", method = RequestMethod.GET)
    public ModelAndView getMensagemList(){
        ModelAndView mv = null;
        try {
            mv = new ModelAndView("mensagem/list");
        } catch (Exception e) {
            mv = new ModelAndView("erro/error");
            mv.addObject("erro", e.getMessage());
        }
        return mv;
    }
}
