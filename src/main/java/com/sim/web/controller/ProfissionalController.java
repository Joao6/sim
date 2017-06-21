package com.sim.web.controller;

import com.sim.model.criteria.PacienteCriteria;
import com.sim.model.entity.Atendimento;
import com.sim.model.entity.Paciente;
import com.sim.model.entity.Profissional;
import com.sim.model.service.AtendimentoService;
import com.sim.model.service.PacienteService;
import com.sim.web.utils.ComplementaApontamento;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
public class ProfissionalController {

    private final PacienteService pacienteService = new PacienteService();

    @RequestMapping(value = "/profissional/home", method = RequestMethod.GET)
    public ModelAndView getProfissionalHome() {
        ModelAndView mv = null;
        try {
            mv = new ModelAndView("profissional/home");
        } catch (Exception e) {
            mv = new ModelAndView("erro/error");
            mv.addObject("erro", e.getMessage());
        }

        return mv;
    }

    @RequestMapping(value = "/profissional/meus-atendimentos", method = RequestMethod.GET)
    public ModelAndView getMeusAtendimentos() {
        ModelAndView mv = null;
        try {
            mv = new ModelAndView("profissional/meus-atendimentos");
        } catch (Exception e) {
            mv = new ModelAndView("erro/error");
            mv.addObject("erro", e.getMessage());
        }
        return mv;
    }

    @RequestMapping(value = "/profissional/meus-atendimentos/novo", method = RequestMethod.GET)
    public ModelAndView getNovoAtendimento(HttpSession session) {
        ModelAndView mv = null;
        try {
            Profissional profissionalSession = (Profissional) session.getAttribute("usuarioLogado");
            List<Paciente> pacienteList = new ArrayList<>();
            Map<Long, Object> criteria = new HashMap<>();
            criteria.put(PacienteCriteria.INSTITUICAO, profissionalSession.getInstituicao().getId());
            criteria.put(PacienteCriteria.PROFISSIONAL_ASSOCIADO, profissionalSession.getId());
            //pegar somente os pacientes que estão associados ao profissional.
            pacienteList = pacienteService.readPacienteAssociado(criteria);
            mv = new ModelAndView("profissional/novo-atendimento");
            mv.addObject("pacienteList", pacienteList);
        } catch (Exception e) {
            mv = new ModelAndView("erro/error");
            mv.addObject("erro", e.getMessage());
        }
        return mv;
    }

    @RequestMapping(value = "/profissional/meus-atendimentos/novo", method = RequestMethod.POST)
    public ModelAndView postNovoAtendimento(HttpSession session, Long paciente, String dataApontamento, String apontamento) {
        ModelAndView mv = null;
        try {
            Profissional profissionalSession = (Profissional) session.getAttribute("usuarioLogado");
            Atendimento atendimento = new Atendimento();
            atendimento.setProfissional(profissionalSession);
            Paciente pacienteAtendido = new Paciente();
            pacienteAtendido.setId(paciente);
            atendimento.setPaciente(pacienteAtendido);
            DateFormat readFormat = new SimpleDateFormat("dd/MM/yyyy");
            atendimento.setDataApontamento(readFormat.parse(dataApontamento));
            atendimento.setApontamento(apontamento);
            AtendimentoService s = new AtendimentoService();
            s.create(atendimento);
            mv = new ModelAndView("redirect:/profissional/meus-atendimentos");
        } catch (Exception e) {
            mv = new ModelAndView("erro/error");
            mv.addObject("erro", e.getMessage());
        }
        return mv;
    }

    @RequestMapping(value = "/profissional/meus-atendimentos/{id}/editar", method = RequestMethod.GET)
    public ModelAndView getEditarAtendimento(@PathVariable Long id, HttpSession session) {
        ModelAndView mv = null;
        try {
            Profissional profissionalSession = (Profissional) session.getAttribute("usuarioLogado");
            AtendimentoService s = new AtendimentoService();
            Atendimento atendimento = s.readById(id);
            if (atendimento.getProfissional().getId() == profissionalSession.getId()) {
                mv = new ModelAndView("profissional/editar-atendimento");
                mv.addObject("idAtendimento", atendimento.getId());
                int lenght = 400 - (atendimento.getApontamento().length());
                mv.addObject("lenght", lenght);
            } else {
                //redirect para uma página como permissão negada
                mv = new ModelAndView("profissional/permissão-negada");
            }
        } catch (Exception e) {
            mv = new ModelAndView("erro/error");
            mv.addObject("erro", e.getMessage());
        }
        return mv;
    }

    @RequestMapping(value = "/profissional/meus-atendimentos/{id}/editar", method = RequestMethod.POST)
    public ModelAndView postEditarAtendimento(@PathVariable Long id, HttpSession session, String apontamentoComp) {
        ModelAndView mv = null;
        try {
            AtendimentoService s = new AtendimentoService();
            Atendimento atendimento = s.readById(id);
            atendimento.setApontamento(
                    ComplementaApontamento.complementarApontamento(atendimento.getApontamento(), atendimento.getDataApontamento(), apontamentoComp)
            );
            s.update(atendimento);
            mv = new ModelAndView("redirect:/profissional/meus-atendimentos");
        } catch (Exception e) {
            mv = new ModelAndView("erro/error");
            mv.addObject("erro", e.getMessage());
        }

        return mv;
    }

    @RequestMapping(value = "/profissional/meus-dados", method = RequestMethod.GET)
    public ModelAndView getEditarMeusDados() {
        ModelAndView mv = null;
        try {
            mv = new ModelAndView("profissional/meus-dados");
        } catch (Exception e) {
            mv = new ModelAndView("erro/error");
            mv.addObject("erro", e.getMessage());
        }
        return mv;
    }

    @RequestMapping(value = "/profissional/alterar-senha", method = RequestMethod.GET)
    public ModelAndView getAlterarSenha() {
        ModelAndView mv = null;
        try {
            mv = new ModelAndView("profissional/alterar-senha");
        } catch (Exception e) {
            mv = new ModelAndView("erro/error");
            mv.addObject("erro", e.getMessage());
        }
        return mv;
    }

    @RequestMapping(value = "/profissional/permissao-negada", method = RequestMethod.GET)
    public ModelAndView getPermissaoNegada() {
        ModelAndView mv = new ModelAndView("profissional/permissao-negada");
        return mv;
    }
}
