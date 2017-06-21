package com.sim.web.rest.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sim.model.criteria.AtendimentoCriteria;
import com.sim.model.criteria.PacienteCriteria;
import com.sim.model.entity.Atendimento;
import com.sim.model.entity.Paciente;
import com.sim.model.entity.Profissional;
import com.sim.model.service.AtendimentoService;
import com.sim.model.service.PacienteService;
import com.sim.model.service.ProfissionalService;
import com.sim.web.constraints.AppConstraints;
import com.sim.web.utils.PaginaRetorno;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Joao Pedro
 */
@RestController
public class ProfissionalRestController {

    private final AtendimentoService atendimentoService = new AtendimentoService();
    private final PacienteService pacienteService = new PacienteService();
    private final ProfissionalService profissionalService = new ProfissionalService();

    @RequestMapping(value = "/profissional/rest/atendimentos/instituicao", method = RequestMethod.GET)
    @ResponseBody
    public String getAtendimentosInstituicao(HttpServletResponse response, HttpSession session,
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "nomePaciente", required = false) String nomePaciente,
            @RequestParam(value = "nomeProfissional", required = false) String nomeProfissional,
            @RequestParam(value = "dataAtendimento", required = false) String dataAtendimento) throws Exception {
        String atendimentos = null;
        try {
            Profissional profissional = (Profissional) session.getAttribute("usuarioLogado");
            Map<Long, Object> criteria = new HashMap<>();
            criteria.put(AtendimentoCriteria.INSTITUICAO_ID, profissional.getInstituicao().getId());
            if (nomePaciente != null && !nomePaciente.isEmpty()) {
                criteria.put(AtendimentoCriteria.PACIENTE_NOME, nomePaciente);
            }
            if (nomeProfissional != null && !nomeProfissional.isEmpty()) {
                criteria.put(AtendimentoCriteria.PROFISSIONAL_NOME, nomeProfissional);
            }
            if (dataAtendimento != null && !dataAtendimento.isEmpty()) {
                criteria.put(AtendimentoCriteria.DATA, dataAtendimento);
            }
            List<Atendimento> atendimentoList = atendimentoService.readByCriteria(criteria, AppConstraints.LIMIT_DEFAULT, offset);
            Long count = atendimentoService.countByCriteria(criteria, AppConstraints.LIMIT_DEFAULT, offset);
            PaginaRetorno pagina = new PaginaRetorno();
            pagina.setDados(atendimentoList);
            pagina.setCount(count);

            Gson g = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            atendimentos = g.toJson(pagina);
            response.setStatus(200);
        } catch (Exception e) {
            response.setStatus(500);
            response.setHeader("erro", e.getMessage());
        }
        return atendimentos;
    }
    
    @RequestMapping(value = "/profissional/rest/atendimentos/paciente-associado", method = RequestMethod.GET)
    @ResponseBody
    public String getAtendimentosPacienteAssociado(HttpServletResponse response, HttpSession session,
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "nomePaciente", required = false) String nomePaciente,
            @RequestParam(value = "nomeProfissional", required = false) String nomeProfissional,
            @RequestParam(value = "dataAtendimento", required = false) String dataAtendimento) throws Exception {
        String atendimentos = null;
        try {
            Profissional profissional = (Profissional) session.getAttribute("usuarioLogado");
            Map<Long, Object> criteria = new HashMap<>();
            if (nomePaciente != null && !nomePaciente.isEmpty()) {
                criteria.put(AtendimentoCriteria.PACIENTE_NOME, nomePaciente);
            }
            if (nomeProfissional != null && !nomeProfissional.isEmpty()) {
                criteria.put(AtendimentoCriteria.PROFISSIONAL_NOME, nomeProfissional);
            }
            if (dataAtendimento != null && !dataAtendimento.isEmpty()) {
                criteria.put(AtendimentoCriteria.DATA, dataAtendimento);
            }
            List<Atendimento> atendimentoList = 
                    atendimentoService.getAtendimentosPacienteAssociado(criteria, 
                            profissional.getInstituicao().getId(), profissional.getId(), 
                            AppConstraints.LIMIT_DEFAULT, offset);
            criteria.put(AtendimentoCriteria.PACIENTE_ASSOCIADO, true);
            Long count = atendimentoService.countByCriteria(criteria, AppConstraints.LIMIT_DEFAULT, offset);
            PaginaRetorno pagina = new PaginaRetorno();
            pagina.setDados(atendimentoList);
            pagina.setCount(count);

            Gson g = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            atendimentos = g.toJson(pagina);
            response.setStatus(200);
        } catch (Exception e) {
            response.setStatus(500);
            response.setHeader("erro", e.getMessage());
        }
        return atendimentos;
    }

    @RequestMapping(value = "/profissional/rest/atendimentos", method = RequestMethod.GET)
    @ResponseBody
    public String getAtendimentosProfissional(HttpServletResponse response, HttpSession session,
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "nomePaciente", required = false) String nomePaciente,
            @RequestParam(value = "dataAtendimento", required = false) String dataAtendimento) throws Exception {
        String atendimentos = null;
        try {
            Profissional profissional = (Profissional) session.getAttribute("usuarioLogado");
            Map<Long, Object> criteria = new HashMap<>();
            criteria.put(AtendimentoCriteria.PROFISSIONAL_ID, profissional.getId());
            if (nomePaciente != null && !nomePaciente.isEmpty()) {
                criteria.put(AtendimentoCriteria.PACIENTE_NOME, nomePaciente);
            }
            if (dataAtendimento != null && !dataAtendimento.isEmpty()) {
                criteria.put(AtendimentoCriteria.DATA, dataAtendimento);
            }
            List<Atendimento> atendimentoList = atendimentoService.readByCriteria(criteria, AppConstraints.LIMIT_DEFAULT, offset);
            Long count = atendimentoService.countByCriteria(criteria, AppConstraints.LIMIT_DEFAULT, offset);
            PaginaRetorno pagina = new PaginaRetorno();
            pagina.setDados(atendimentoList);
            pagina.setCount(count);

            Gson g = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            atendimentos = g.toJson(pagina);
            response.setStatus(200);
        } catch (Exception e) {
            response.setStatus(500);
            response.setHeader("erro", e.getMessage());
        }
        return atendimentos;
    }

    @RequestMapping(value = "/profissional/rest/atendimento/{idAtendimento}", method = RequestMethod.GET)
    @ResponseBody
    public String getAtendimentosProfissional(@PathVariable Long idAtendimento, HttpServletResponse response, HttpSession session) throws Exception {
        String atendimento = null;
        try {
            if (idAtendimento != null) {
                Atendimento a = atendimentoService.readById(idAtendimento);
                if (a != null) {
                    Gson g = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                    atendimento = g.toJson(a);
                    response.setStatus(200);
                }
            } else {
                response.setStatus(400);
                response.setHeader("erro", "Não foi possível localizar!");
            }
        } catch (Exception e) {
            response.setStatus(500);
            response.setHeader("erro", e.getMessage());
        }
        return atendimento;
    }

    @RequestMapping(value = "/profissional/rest/atendimento/", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public void updateAtendimento(HttpServletResponse response, HttpSession session,
            @RequestBody String atendimento) throws Exception {
        try {
            Gson g = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
            Atendimento atend = g.fromJson(atendimento, Atendimento.class);
            Profissional profSession = (Profissional) session.getAttribute("usuarioLogado");
            if (profSession.getId() == atend.getProfissional().getId()) {
                atendimentoService.update(atend);
                response.setStatus(200);
            } else {
                response.setStatus(401);
                response.setHeader("erro", "Você não possui permissão para alterar este atendimento!");
            }
        } catch (Exception e) {
            response.setStatus(500);
            response.setHeader("erro", e.getMessage());
        }
    }

    @RequestMapping(value = "/profissional/rest/paciente-associado", method = RequestMethod.GET)
    @ResponseBody
    public String getPacientesAssociados(HttpServletResponse response, HttpSession session) throws Exception {
        String pacientes = null;
        try {
            Profissional profSession = (Profissional) session.getAttribute("usuarioLogado");
            List<Paciente> pacienteList = new ArrayList<>();
            Map<Long, Object> criteria = new HashMap<>();
            criteria.put(PacienteCriteria.INSTITUICAO, profSession.getInstituicao().getId());
            criteria.put(PacienteCriteria.PROFISSIONAL_ASSOCIADO, profSession.getId());
            //pegar somente os pacientes que estão associados ao profissional.
            pacienteList = pacienteService.readPacienteAssociado(criteria);
            Gson g = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            pacientes = g.toJson(pacienteList);
            response.setStatus(200);
        } catch (Exception e) {
            response.setStatus(500);
            response.setHeader("erro", e.getMessage());
        }
        return pacientes;
    }

    @RequestMapping(value = "/profissional/rest/profissional", method = RequestMethod.GET)
    @ResponseBody
    public String getProfissionalSession(HttpServletResponse response, HttpSession session
    ) throws Exception {
        String profissional = null;
        try {
            Profissional profSession = (Profissional) session.getAttribute("usuarioLogado");
            Profissional prof = profissionalService.readById(profSession.getId());
            Gson g = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            profissional = g.toJson(prof);
            response.setStatus(200);
        } catch (Exception e) {
            response.setStatus(500);
            response.setHeader("erro", e.getMessage());
        }
        return profissional;
    }

    @RequestMapping(value = "/profissional/rest/cadastrar-atendimento", method = RequestMethod.POST)
    @ResponseBody
    public void postCadastrarAtendimento(HttpServletResponse response, HttpSession session,
            @RequestBody String atendimento) throws Exception {
        try {
            Profissional profSession = (Profissional) session.getAttribute("usuarioLogado");
            Gson g = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
            Atendimento atend = g.fromJson(atendimento, Atendimento.class);
            atend.setProfissional(profSession);
            atendimentoService.create(atend);
            response.setStatus(200);
        } catch (Exception e) {
            response.setStatus(500);
            response.setHeader("erro", e.getMessage());
        }
    }
    
    @RequestMapping(value = "/profissional/rest/editar/meus-dados", method = RequestMethod.PUT)
    @ResponseBody
    public void putEditarMeusDados(HttpServletResponse response, HttpSession session,
            @RequestBody String profissional) throws Exception {
        try {
            Gson g = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
            Profissional prof = g.fromJson(profissional, Profissional.class);            
            Map<String, Object> fields = new HashMap<>();
            Map<String, String> errors = new HashMap<>();
            fields.put("id", prof.getId());
            fields.put("nome", prof.getNome());
            fields.put("email", prof.getEmail());
            fields.put("senha", prof.getSenha());
            fields.put("profissao", prof.getProfissao());
            fields.put("telefone", prof.getTelefone());
            errors = profissionalService.validate(fields);
            if (errors.isEmpty()) {
                profissionalService.update(prof);
                session.setAttribute("usuarioLogado", prof);
                response.setStatus(200);
            } else {
                response.setStatus(401);
                response.setHeader("error", errors.toString());
            }
        } catch (Exception e) {
            response.setStatus(500);
            response.setHeader("erro", e.getMessage());
        }
    }
}
