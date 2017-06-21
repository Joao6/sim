/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sim.web.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sim.model.criteria.PacienteCriteria;
import com.sim.model.criteria.ProfissionalCriteria;
import com.sim.model.criteria.UsuarioCriteria;
import com.sim.model.entity.Associacao;
import com.sim.model.entity.Instituicao;
import com.sim.model.entity.Paciente;
import com.sim.model.entity.Profissional;
import com.sim.model.service.AssociacaoService;
import com.sim.model.service.PacienteService;
import com.sim.model.service.ProfissionalService;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
