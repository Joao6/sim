package com.sim.web.rest.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sim.model.criteria.InstituicaoCriteria;
import com.sim.model.criteria.PacienteCriteria;
import com.sim.model.criteria.UsuarioCriteria;
import com.sim.model.entity.Instituicao;
import com.sim.model.entity.Master;
import com.sim.model.entity.Usuario;
import com.sim.model.service.InstituicaoService;
import com.sim.model.service.MasterService;
import com.sim.model.service.PacienteService;
import com.sim.model.service.UsuarioService;
import com.sim.web.constraints.AppConstraints;
import com.sim.web.utils.PaginaRetorno;
import com.sim.web.utils.responseForm.InstituicaoResponseForm;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Joao Pedro
 */
@RestController
@RequestMapping(value = "/master/rest/")
public class MasterRestController {

    InstituicaoService instituicaoService = new InstituicaoService();
    MasterService masterService = new MasterService();
    PacienteService pacienteService = new PacienteService();

    @RequestMapping(value = "instituicao-list", method = RequestMethod.GET)
    public String getInstituicoes(HttpServletResponse response,
            @RequestParam(value = "nomeInstituicao", required = false) String nomeInstituicao,
            @RequestParam(value = "confVersao", required = false) String confVersao,
            @RequestParam(value = "offset", required = false) Long offset) {
        String instituicoes = null;
        try {
            Map<Long, Object> criteria = new HashMap<>();
            criteria.put(UsuarioCriteria.INSTITUICAO, true);
            if (nomeInstituicao != null && !nomeInstituicao.isEmpty()) {
                criteria.put(UsuarioCriteria.NOME_ILIKE, nomeInstituicao);
            }
            if (confVersao != null && !confVersao.isEmpty()) {
                criteria.put(InstituicaoCriteria.CONF_VERSAO, confVersao);
            }

            List<InstituicaoResponseForm> instituicaoResponseList = new ArrayList<>();
            List<Instituicao> instituicaoList = instituicaoService.readByCriteria(criteria, AppConstraints.LIMIT_DEFAULT, offset);
            Long count = instituicaoService.countByCriteria(criteria, AppConstraints.LIMIT_DEFAULT, offset);

            Map<Long, Object> criteriaQtdPaciente = new HashMap<>();
            for (Instituicao instituicao : instituicaoList) {
                criteriaQtdPaciente.put(PacienteCriteria.INSTITUICAO, instituicao.getId());
                Long countQtd = pacienteService.countByCriteria(criteriaQtdPaciente, AppConstraints.LIMIT_DEFAULT, offset);
                InstituicaoResponseForm instituicaoResponse = 
                        new InstituicaoResponseForm(instituicao.getId(), instituicao.getNome(), instituicao.getEmail(),
                                                     instituicao.getTelefone(), instituicao.getConfVersao(), countQtd);
                instituicaoResponseList.add(instituicaoResponse);
                criteriaQtdPaciente.clear();
            }
            
            PaginaRetorno<InstituicaoResponseForm> responseForm = new PaginaRetorno<>();
            responseForm.setCount(count);
            responseForm.setDados(instituicaoResponseList);

            Gson g = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            instituicoes = g.toJson(responseForm);
            response.setStatus(200);
        } catch (Exception e) {
            response.setStatus(500);
            response.setHeader("erro", e.getMessage());
        }
        return instituicoes;
    }

    @RequestMapping(value = "cadastrar-instituicao", method = RequestMethod.POST)
    @ResponseBody
    public void salvarInstituicao(HttpServletResponse response,
            @RequestBody String instituicao, HttpSession session) throws Exception {
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
                instituicaoService.create(inst);
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

    @RequestMapping(value = "editar-instituicao", method = RequestMethod.PUT)
    @ResponseBody
    public void editarInstituicao(HttpServletResponse response,
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
                response.setStatus(200);
            } else {
                response.setStatus(401);
                response.setHeader("error", errors.toString());
            }
        } catch (Exception e) {
            response.setStatus(500);
            response.setHeader("error", e.getMessage());
        }
    }

    @RequestMapping(value = "master", method = RequestMethod.GET)
    @ResponseBody
    public String getMaster(HttpServletResponse response, HttpSession session) {
        String master = null;
        try {
            Master masterSession = (Master) session.getAttribute("usuarioLogado");
            masterSession = masterService.readById(masterSession.getId());
            Gson g = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            master = g.toJson(masterSession);
            response.setStatus(200);
        } catch (Exception e) {
            response.setStatus(500);
            response.setHeader("error", e.getMessage());
        }
        return master;
    }

    @RequestMapping(value = "instituicao/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getInstituicao(HttpServletResponse response, @PathVariable Long id) {
        String instituicao = null;
        try {
            Instituicao inst = instituicaoService.readById(id);
            Gson g = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            instituicao = g.toJson(inst);
            response.setStatus(200);
        } catch (Exception e) {
            response.setStatus(500);
            response.setHeader("error", e.getMessage());
        }
        return instituicao;
    }

    @RequestMapping(value = "editar-master", method = RequestMethod.PUT)
    @ResponseBody
    public void editarDadosMaster(HttpServletResponse response,
            @RequestBody String master, HttpSession session) {
        try {
            Type type = new TypeToken<Master>() {
            }.getType();
            Gson g = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
            Master mas = g.fromJson(master, type);
            Map<String, Object> fields = new HashMap<>();
            Map<String, String> errors = new HashMap<>();
            fields.put("nome", mas.getNome());
            fields.put("email", mas.getEmail());
            fields.put("id", mas.getId());
            errors = masterService.validate(fields);
            if (errors.isEmpty()) {
                masterService.update(mas);
                session.setAttribute("usuarioLogado", mas);
                response.setStatus(200);
            } else {
                response.setStatus(401);
                response.setHeader("error", errors.toString());
            }
        } catch (Exception e) {
            response.setStatus(500);
            response.setHeader("error", e.getMessage());
        }
    }

    @RequestMapping(value = "valida-email", method = RequestMethod.GET)
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
