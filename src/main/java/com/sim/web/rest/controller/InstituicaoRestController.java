/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sim.web.rest.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sim.model.criteria.AtendimentoCriteria;
import com.sim.model.criteria.PacienteCriteria;
import com.sim.model.criteria.ProfissionalCriteria;
import com.sim.model.criteria.UsuarioCriteria;
import com.sim.model.entity.Atendimento;
import com.sim.model.entity.Instituicao;
import com.sim.model.entity.Paciente;
import com.sim.model.entity.Profissional;
import com.sim.model.entity.Usuario;
import com.sim.model.service.AtendimentoService;
import com.sim.model.service.InstituicaoService;
import com.sim.model.service.PacienteService;
import com.sim.model.service.ProfissionalService;
import com.sim.model.service.UsuarioService;
import com.sim.web.constraints.AppConstraints;
import com.sim.web.utils.PaginaRetorno;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
public class InstituicaoRestController {

    private final AtendimentoService serviceAtendimento = new AtendimentoService();
    private final InstituicaoService instituicaoService = new InstituicaoService();
    private final PacienteService pacienteService = new PacienteService();
    private final ProfissionalService profissionalService = new ProfissionalService();

    @RequestMapping(value = "/instituicao/rest/atendimentos", method = RequestMethod.GET)
    @ResponseBody
    public String getAtendimentoList(HttpSession session, HttpServletResponse response,
            @RequestParam("nomePaciente") String nomePaciente,
            @RequestParam("nomeProfissional") String nomeProfissional,
            @RequestParam("dataAtendimento") String dataAtendimento,
            @RequestParam("offset") Long offset
    ) {

        String atendimentos = null;
        try {
            Instituicao instituicaoSession = (Instituicao) session.getAttribute("usuarioLogado");
            List<Atendimento> atendimentoList = new ArrayList<>();
            Map<Long, Object> criteria = new HashMap<>();
            criteria.put(AtendimentoCriteria.INSTITUICAO_ID, instituicaoSession.getId());

            if (nomeProfissional != null && !nomeProfissional.isEmpty()) {
                criteria.put(AtendimentoCriteria.PROFISSIONAL_NOME, nomeProfissional);
            }
            if (nomePaciente != null && !nomePaciente.isEmpty()) {
                criteria.put(AtendimentoCriteria.PACIENTE_NOME, nomePaciente);
            }
            if (dataAtendimento != null && !dataAtendimento.isEmpty()) {
                criteria.put(AtendimentoCriteria.DATA, dataAtendimento);
            }

            atendimentoList = serviceAtendimento.readByCriteria(criteria, AppConstraints.LIMIT_DEFAULT, offset);
            Long count = serviceAtendimento.countByCriteria(criteria, AppConstraints.LIMIT_DEFAULT, offset);
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

    @RequestMapping(value = "/instituicao/rest/profissional/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getProfisional(HttpServletResponse response, @PathVariable Long id) {
        String profissional = null;
        try {
            Profissional prof = profissionalService.readById(id);
            Gson g = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            profissional = g.toJson(prof);
            response.setStatus(200);
        } catch (Exception e) {
            response.setStatus(500);
        }
        return profissional;
    }

    @RequestMapping(value = "/instituicao/rest/profissionais/editar", method = RequestMethod.PUT)
    @ResponseBody
    public void putEditarProfissional(HttpServletResponse response,
            @RequestBody String profissional) {
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
                response.setStatus(200);
            } else {
                response.setStatus(401);
                response.setHeader("error", errors.toString());
            }
        } catch (Exception e) {
            response.setStatus(500);
            response.setStatus(500);
            response.setHeader("erro", e.getMessage());
        }
    }

    @RequestMapping(value = "/instituicao/rest/paciente/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getPaciente(HttpServletResponse response, @PathVariable Long id) {
        String paciente = null;
        try {
            Paciente pacie = pacienteService.readById(id);
            Gson g = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            paciente = g.toJson(pacie);
            response.setStatus(200);
        } catch (Exception e) {
            response.setStatus(500);
            response.setHeader("erro", e.getMessage());
        }
        return paciente;
    }

    @RequestMapping(value = "/instituicao/rest/pacientes/editar", method = RequestMethod.PUT)
    @ResponseBody
    public void putEditarPaciente(HttpServletResponse response,
            @RequestBody String paciente) {
        try {
            Gson g = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
            Paciente pac = g.fromJson(paciente, Paciente.class);
            Map<String, Object> fields = new HashMap<>();
            Map<String, String> errors = new HashMap<>();
            fields.put("nome", pac.getNome());
            fields.put("data", pac.getNascimento().toString());
            errors = pacienteService.validate(fields);
            if (errors.isEmpty()) {
                pacienteService.update(pac);
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

    @RequestMapping(value = "/instituicao/rest/", method = RequestMethod.GET)
    @ResponseBody
    public String getInstituicao(HttpSession session, HttpServletResponse response) {
        String instituicao = null;
        try {
            Instituicao instituicaoSession = (Instituicao) session.getAttribute("usuarioLogado");
            Instituicao inst = instituicaoService.readById(instituicaoSession.getId());
            Gson g = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            instituicao = g.toJson(inst);
            response.setStatus(200);
        } catch (Exception e) {
            response.setStatus(500);
            response.setHeader("erro", e.getMessage());
        }
        return instituicao;
    }

    @RequestMapping(value = "/instituicao/rest/editar", method = RequestMethod.PUT)
    @ResponseBody
    public void putEditarInstituicao(HttpServletResponse response,
            @RequestBody String instituicao, HttpSession session) {
        try {
            Type type = new TypeToken<Instituicao>() {
            }.getType();
            Gson g = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
            Instituicao inst = g.fromJson(instituicao, type);
            Map<String, Object> fields = new HashMap<>();
            Map<String, String> errors = new HashMap<>();
            fields.put("id", inst.getId());
            fields.put("nome", inst.getNome());
            fields.put("email", inst.getEmail());
            fields.put("versao", inst.getConfVersao());
            fields.put("telefone", inst.getTelefone());
            errors = instituicaoService.validate(fields);
            if (errors.isEmpty()) {
                instituicaoService.update(inst);
                session.setAttribute("usuarioLogado", inst);
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

    @RequestMapping(value = "/instituicao/rest/pacientes/salvar", method = RequestMethod.POST)
    @ResponseBody
    public void salvarPaciente(HttpServletResponse response,
            @RequestBody String paciente, HttpSession session) {
        try {
            Type type = new TypeToken<Paciente>() {
            }.getType();
            Gson g = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
            Paciente p = g.fromJson(paciente, type);
            Instituicao inst = (Instituicao) session.getAttribute("usuarioLogado");
            p.setInstituicao(inst);
            Map<String, Object> fields = new HashMap<>();
            Map<String, String> errors = new HashMap<>();
            fields.put("nome", p.getNome());
            fields.put("data", p.getNascimento().toString());
            errors = pacienteService.validate(fields);
            if (errors.isEmpty()) {
                pacienteService.create(p);
                response.setStatus(200);
            } else {
                response.setStatus(401);
                response.setHeader("error", errors.toString());
            }
        } catch (Exception e) {
            response.setStatus(500);
        }
    }

    @RequestMapping(value = "/instituicao/rest/profissionais/salvar", method = RequestMethod.POST)
    @ResponseBody
    public void salvarProfissional(HttpServletResponse response,
            @RequestBody String profissional, HttpSession session) {
        try {
            Type type = new TypeToken<Profissional>() {
            }.getType();
            Gson g = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
            Profissional p = g.fromJson(profissional, type);
            Instituicao inst = (Instituicao) session.getAttribute("usuarioLogado");
            p.setInstituicao(inst);
            Map<String, Object> fields = new HashMap<>();
            Map<String, String> errors = new HashMap<>();
            fields.put("id", p.getId());
            fields.put("nome", p.getNome());
            fields.put("email", p.getEmail());
            fields.put("senha", p.getSenha());
            fields.put("profissao", p.getProfissao());
            fields.put("telefone", p.getTelefone());
            errors = profissionalService.validate(fields);
            if (errors.isEmpty()) {
                profissionalService.create(p);
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

    @RequestMapping(value = "/instituicao/rest/profissionalList", method = RequestMethod.GET)
    @ResponseBody
    public String getProfissionalList(HttpSession session, HttpServletResponse response,
            @RequestParam("offset") Long offset,
            @RequestParam("nomeProfissional") String nomeProfissional) {
        List<Profissional> profissionalList = new ArrayList<>();
        String profissionais = null;
        try {
            Instituicao instituicao = (Instituicao) session.getAttribute("usuarioLogado");
            Map<Long, Object> criteria = new HashMap<>();
            criteria.put(ProfissionalCriteria.INSTITUICAO_ID, instituicao.getId());
            criteria.put(UsuarioCriteria.PROFISSIONAL, true);

            if (nomeProfissional != null && !nomeProfissional.isEmpty()) {
                criteria.put(UsuarioCriteria.NOME_ILIKE, nomeProfissional);
            }
            profissionalList = profissionalService.readByCriteria(criteria, AppConstraints.LIMIT_DEFAULT, offset);
            Long count = profissionalService.countByCriteria(criteria, AppConstraints.LIMIT_DEFAULT, offset);
            PaginaRetorno pagina = new PaginaRetorno();
            pagina.setDados(profissionalList);
            pagina.setCount(count);
            Gson g = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            profissionais = g.toJson(pagina);
            response.setStatus(200);
        } catch (Exception e) {
            response.setStatus(500);
            response.setHeader("erro", e.getMessage());
        }

        return profissionais;
    }

    @RequestMapping(value = "/instituicao/rest/pacienteList", method = RequestMethod.GET)
    @ResponseBody
    public String getPacienteList(HttpSession session, HttpServletResponse response,
            @RequestParam("offset") Long offset,
            @RequestParam("nomePaciente") String nomePaciente) {
        List<Paciente> pacienteList = new ArrayList<>();
        String pacientes = null;
        try {
            Instituicao instituicao = (Instituicao) session.getAttribute("usuarioLogado");
            Map<Long, Object> criteria = new HashMap<>();
            criteria.put(PacienteCriteria.INSTITUICAO, instituicao.getId());
            if (nomePaciente != null && !nomePaciente.isEmpty()) {
                criteria.put(PacienteCriteria.NOME_ILIKE, nomePaciente);
            }
            Long count = pacienteService.countByCriteria(criteria, AppConstraints.LIMIT_DEFAULT, offset);
            pacienteList = pacienteService.readByCriteria(criteria, AppConstraints.LIMIT_DEFAULT, offset);
            PaginaRetorno pagina = new PaginaRetorno();
            pagina.setDados(pacienteList);
            pagina.setCount(count);
            Gson g = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            pacientes = g.toJson(pagina);
            response.setStatus(200);
        } catch (Exception e) {
            response.setStatus(500);
            response.setHeader("erro", e.getMessage());
        }
        return pacientes;
    }

    @RequestMapping(value = "/instituicao/rest/excluir/paciente/{idPaciente}/", method = RequestMethod.DELETE)
    @ResponseBody
    public void deletePaciente(@PathVariable Long idPaciente, HttpSession session, HttpServletResponse response) {
        try {
            Instituicao instituicao = (Instituicao) session.getAttribute("usuarioLogado");
            Paciente paciente = pacienteService.readById(idPaciente);
            if (Objects.equals(paciente.getInstituicao().getId(), instituicao.getId())) {
                pacienteService.delete(idPaciente);
                response.setStatus(200);
            } else {
                response.setStatus(401);
                response.setHeader("error", "Você não possui autorização para realizar esta ação!");
            }
        } catch (Exception e) {
            response.setStatus(500);
            response.setHeader("erro", e.getMessage());
        }
    }

    @RequestMapping(value = "/instituicao/rest/excluir/profissional/{idProfissional}/", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteProfissional(@PathVariable Long idProfissional, HttpSession session, HttpServletResponse response) {
        try {
            Instituicao instituicao = (Instituicao) session.getAttribute("usuarioLogado");
            Profissional profissional = profissionalService.readById(idProfissional);
            if (Objects.equals(profissional.getInstituicao().getId(), instituicao.getId())) {
                profissionalService.delete(idProfissional);
                response.setStatus(200);
            } else {
                response.setStatus(401);
                response.setHeader("error", "Você não possui autorização para realizar esta ação!");
            }
        } catch (Exception e) {
            response.setStatus(500);
            response.setHeader("erro", e.getMessage());
        }
    }
    
    @RequestMapping(value = "/instituicao/rest/valida-email", method = RequestMethod.GET)
    @ResponseBody
    public Boolean validaEmail(HttpServletResponse response, @RequestParam(value = "email", required = true) String email) {
        Boolean isEmailOk = false;
        try {
            UsuarioService s = new UsuarioService();
            Usuario usuario = s.validaEmail(email);
            if (usuario != null) {
                isEmailOk = false;
            } else {
                isEmailOk = true;
            }
            response.setStatus(200);
        } catch (Exception e) {
            response.setStatus(500);
            response.setHeader("erro", e.getMessage());
        }
        return isEmailOk;
    }
}
