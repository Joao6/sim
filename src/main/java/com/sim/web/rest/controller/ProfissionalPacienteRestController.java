/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sim.web.rest.controller;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Joao Pedro
 */
@RestController
public class ProfissionalPacienteRestController {
    
    @RequestMapping(value = "/instituicao/associar/profissionais", method = RequestMethod.GET)
    @ResponseBody
    public String getProfissionalList(HttpSession session, HttpServletResponse response){
        List<Profissional> profissionalList = new ArrayList<>();       
        String profissionais = null;
        try {
            Instituicao instituicao = (Instituicao) session.getAttribute("usuarioLogado");
            ProfissionalService s = new ProfissionalService();
            Map<Long, Object> criteria = new HashMap<>();
            criteria.put(ProfissionalCriteria.INSTITUICAO_ID, instituicao.getId());
            criteria.put(UsuarioCriteria.PROFISSIONAL, true);
            profissionalList = s.readByCriteria(criteria, null, null);
            Gson g = new Gson();
            profissionais = g.toJson(profissionalList);
            response.setStatus(200);
        } catch (Exception e) {
            response.setStatus(500);
            response.setHeader("erro", e.getMessage());
        }
        
        return profissionais;
    }
    
    @RequestMapping(value = "/instituicao/associar/pacientes", method = RequestMethod.GET)
    @ResponseBody
    public String getPacienteList(HttpSession session, HttpServletResponse response){
        List<Paciente> pacienteList = new ArrayList<>();       
        String pacientes = null;
        try {
            Instituicao instituicao = (Instituicao) session.getAttribute("usuarioLogado");
            PacienteService s = new PacienteService();
            Map<Long, Object> criteria = new HashMap<>();
            criteria.put(PacienteCriteria.INSTITUICAO, instituicao.getId());
            pacienteList = s.readByCriteria(criteria, null, null);
            Gson g = new Gson();
            pacientes = g.toJson(pacienteList);
            response.setStatus(200);
        } catch (Exception e) {
            response.setStatus(500);
            response.setHeader("erro", e.getMessage());
        }
        
        return pacientes;
    }
    
    @RequestMapping(value = "/instituicao/associar/paciente-associado/{idProfissional}", method = RequestMethod.GET)
    @ResponseBody
    public String getPacienteAssociado(HttpServletResponse response, @PathVariable Long idProfissional){
        List<Paciente> pacienteList = new ArrayList<>();       
        String pacientes = null;
        try {
            PacienteService s = new PacienteService();
            Map<Long, Object> criteria = new HashMap<>();
            criteria.put(PacienteCriteria.PROFISSIONAL_ASSOCIADO, idProfissional);
            pacienteList = s.readPacienteAssociado(criteria);
            Gson g = new Gson();
            pacientes = g.toJson(pacienteList);
            response.setStatus(200);
        } catch (Exception e) {
            response.setStatus(500);
            response.setHeader("erro", e.getMessage());
        }
        
        return pacientes;
    }
    
    
    @RequestMapping(value = "/instituicao/associar/associar", method = RequestMethod.POST)
    @ResponseBody
    public void getPacienteList(@RequestBody String associacao, HttpServletResponse response){
        try {                        
            Type type = new TypeToken<Associacao>() {
            }.getType();
            Gson g = new GsonBuilder().create();
            Associacao a = g.fromJson(associacao, type); 
            AssociacaoService s = new AssociacaoService();
            s.create(a);
            response.setStatus(200);
        } catch (Exception e) {
            response.setStatus(500);
            response.setHeader("erro", e.getMessage());
        }
           
    }
    
}
