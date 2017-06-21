/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sim.web.controller;

import com.sim.model.criteria.PacienteCriteria;
import com.sim.model.entity.Instituicao;
import com.sim.model.service.PacienteService;
import com.sim.model.service.ProfissionalService;
import com.sim.web.utils.LimitesVersoes;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;
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
public class InstituicaoController {

    private final PacienteService pacienteService = new PacienteService();
    private final ProfissionalService profissionalService = new ProfissionalService();

    @RequestMapping(value = "/instituicao/home", method = RequestMethod.GET)
    public ModelAndView getInstituicaoHome(String nomeProfissional, String data, String nomePaciente, Long limit, Long offset, HttpSession session) {
        ModelAndView mv = null;
        try {
            mv = new ModelAndView("instituicao/home");
        } catch (Exception e) {
            mv = new ModelAndView("erro/error");
            mv.addObject("erro", e.getMessage());
        }
        return mv;
    }

    @RequestMapping(value = "/instituicao/pacientes", method = RequestMethod.GET)
    public ModelAndView getPacientesInstituicao(HttpSession session) {
        ModelAndView mv = null;

        try {
            mv = new ModelAndView("instituicao/pacientes");
        } catch (Exception e) {
            mv = new ModelAndView("erro/error");
            mv.addObject("erro", e.getMessage());
        }
        return mv;
    }
    
    @RequestMapping(value = "/instituicao/profissionais", method = RequestMethod.GET)
    public ModelAndView getProfisisonaisInstituicao(HttpSession session) {
        ModelAndView mv = null;

        try {
            mv = new ModelAndView("instituicao/profissionais");
        } catch (Exception e) {
            mv = new ModelAndView("erro/error");
            mv.addObject("erro", e.getMessage());
        }
        return mv;
    }

    @RequestMapping(value = "/instituicao/permissao-negada", method = RequestMethod.GET)
    public ModelAndView getPermissaoNegada() {
        ModelAndView mv = new ModelAndView("instituicao/permissao-negada");
        return mv;
    }

    @RequestMapping(value = "/instituicao/pacientes/novo", method = RequestMethod.GET)
    public ModelAndView getCadastrarPaciente(HttpSession session) {
        ModelAndView mv = null;
        try {
            Instituicao instSession = (Instituicao) session.getAttribute("usuarioLogado");
            Map<Long, Object> criteria = new HashMap<>();
            criteria.put(PacienteCriteria.INSTITUICAO, instSession.getId());
            Long count = pacienteService.countByCriteria(criteria, null, null);
            if (instSession.getConfVersao().equals("FREE")) {
                if (count < LimitesVersoes.LIMIT_PACIENTE_VERSION_FREE) {
                    mv = new ModelAndView("instituicao/cadastrar-paciente");
                } else {
                    mv = new ModelAndView("instituicao/limit-pacientes");
                }
            } else {
                mv = new ModelAndView("instituicao/cadastrar-paciente");
            }
        } catch (Exception e) {
            mv = new ModelAndView("erro/error");
            mv.addObject("erro", e.getMessage());
        }
        return mv;
    }   
    
    @RequestMapping(value = "/instituicao/profissionais/novo", method = RequestMethod.GET)
    public ModelAndView getCadastrarProfissionais() {
        ModelAndView mv = null;
        try {
            mv = new ModelAndView("instituicao/cadastrar-profissional");
        } catch (Exception e) {
            mv = new ModelAndView();
        }
        return mv;
    }

    @RequestMapping(value = "/instituicao/meus-dados", method = RequestMethod.GET)
    public ModelAndView getMeusDados() {
        ModelAndView mv = null;
        try {
            mv = new ModelAndView("instituicao/meus-dados");
        } catch (Exception e) {
            mv = new ModelAndView("erro/error");
            mv.addObject("erro", e.getMessage());
        }
        return mv;
    }

    @RequestMapping(value = "/instituicao/pacientes/{id}/editar", method = RequestMethod.GET)
    public ModelAndView getEditarPaciente(@PathVariable Long id) {
        ModelAndView mv = null;
        try {
            mv = new ModelAndView("instituicao/editar-paciente");
            mv.addObject("idPaciente", id);
        } catch (Exception e) {
            mv = new ModelAndView("erro/error");
            mv.addObject("erro", e.getMessage());
        }
        return mv;
    }

    @RequestMapping(value = "/instituicao/profissionais/{id}/editar", method = RequestMethod.GET)
    public ModelAndView getEditarProfissional(@PathVariable Long id) {
        ModelAndView mv = null;
        try {
            mv = new ModelAndView("instituicao/editar-profissional");
            mv.addObject("idProfissional", id);
        } catch (Exception e) {
            mv = new ModelAndView("erro/error");
            mv.addObject("erro", e.getMessage());
        }
        return mv;
    }

    @RequestMapping(value = "/instituicao/alterar-senha", method = RequestMethod.GET)
    public ModelAndView getAlterarSenha() {
        ModelAndView mv = null;
        try {
            mv = new ModelAndView("instituicao/alterar-senha");
        } catch (Exception e) {
            mv = new ModelAndView("erro/error");
            mv.addObject("erro", e.getMessage());
        }
        return mv;
    }
}
