/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sim.web.rest.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sim.model.criteria.MensagemCriteria;
import com.sim.model.criteria.ProfissionalCriteria;
import com.sim.model.entity.Instituicao;
import com.sim.model.entity.Mensagem;
import com.sim.model.entity.Paciente;
import com.sim.model.entity.Profissional;
import com.sim.model.service.MensagemService;
import com.sim.model.service.ProfissionalService;
import com.sim.web.constraints.AppConstraints;
import com.sim.web.utils.PaginaRetorno;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
@RequestMapping(value = "/profissional/rest/")
public class MensagemRestController {
    
    private final MensagemService mensagemService = new MensagemService();
    private final ProfissionalService profissionalService = new ProfissionalService();
    
    @RequestMapping(value = "mensagem/recebidas", method = RequestMethod.GET)
    @ResponseBody
    public String getMensagensRecebidas(HttpSession session, HttpServletResponse response,
            @RequestParam(value = "nomeProfissional", required = false) String nomeProfissional,
            @RequestParam(value = "offset", required = false) Long offset) {

        String mensagens = null;
        try {
            Profissional profissionalSession = (Profissional) session.getAttribute("usuarioLogado");
            List<Mensagem> mensagemList = new ArrayList<>();
            Map<Long, Object> criteria = new HashMap<>();
            if (nomeProfissional != null && !nomeProfissional.isEmpty()) {
                criteria.put(MensagemCriteria.NOME_PROF_ILIKE, nomeProfissional);
            }

            mensagemList = mensagemService.readMensagensRecebidas(criteria, AppConstraints.LIMIT_DEFAULT, offset, profissionalSession.getId());
            criteria.clear();
            criteria.put(MensagemCriteria.RECEBIDAS, true);
            criteria.put(MensagemCriteria.USUARIO_SESSAO_ID, profissionalSession.getId());
            Long count = mensagemService.countByCriteria(criteria, AppConstraints.LIMIT_DEFAULT, offset);
            PaginaRetorno pagina = new PaginaRetorno();
            pagina.setDados(mensagemList);
            pagina.setCount(count);

            Gson g = new GsonBuilder().setDateFormat("dd/MM/yyyy hh:mm").create();
            mensagens = g.toJson(pagina);
            response.setStatus(200);
        } catch (Exception e) {
            response.setStatus(500);
            response.setHeader("erro", e.getMessage());
        }

        return mensagens;
    }
    
    @RequestMapping(value = "mensagem/enviadas", method = RequestMethod.GET)
    @ResponseBody
    public String getMensagensEnviadas(HttpSession session, HttpServletResponse response,
            @RequestParam(value = "nomeProfissional", required = false) String nomeProfissional,
            @RequestParam(value = "offset", required = false) Long offset) {

        String mensagens = null;
        try {
            Profissional profissionalSession = (Profissional) session.getAttribute("usuarioLogado");
            List<Mensagem> mensagemList = new ArrayList<>();
            Map<Long, Object> criteria = new HashMap<>();
            if (nomeProfissional != null && !nomeProfissional.isEmpty()) {
                criteria.put(MensagemCriteria.NOME_PROF_ILIKE, nomeProfissional);
            }

            mensagemList = mensagemService.readMensagensEnviadas(criteria, AppConstraints.LIMIT_DEFAULT, offset, profissionalSession.getId());
            criteria.clear();
            criteria.put(MensagemCriteria.ENVIADAS, true);
            criteria.put(MensagemCriteria.USUARIO_SESSAO_ID, profissionalSession.getId());
            Long count = mensagemService.countByCriteria(criteria, AppConstraints.LIMIT_DEFAULT, offset);
            PaginaRetorno pagina = new PaginaRetorno();
            pagina.setDados(mensagemList);
            pagina.setCount(count);

            Gson g = new GsonBuilder().setDateFormat("dd/MM/yyyy hh:mm").create();
            mensagens = g.toJson(pagina);
            response.setStatus(200);
        } catch (Exception e) {
            response.setStatus(500);
            response.setHeader("erro", e.getMessage());
        }

        return mensagens;
    }
    
    @RequestMapping(value = "list-profissionais", method = RequestMethod.GET)
    @ResponseBody
    public String getProfissionalList(HttpSession session, HttpServletResponse response) {

        String profissionais = null;
        try {
            Profissional profissionalSession = (Profissional) session.getAttribute("usuarioLogado");
            List<Profissional> profissionalList = new ArrayList<>();
            Map<Long, Object> criteria = new HashMap<>();
            criteria.put(ProfissionalCriteria.INSTITUICAO_ID, profissionalSession.getInstituicao().getId());
            profissionalList = profissionalService.readByCriteria(criteria, null, null);
            List<Profissional> profissionalListReturn = new ArrayList<>();
            for (Profissional profissional : profissionalList) {
                if(profissional.getId() != profissionalSession.getId()){
                    profissionalListReturn.add(profissional);
                }
            }

            Gson g = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            profissionais = g.toJson(profissionalListReturn);
            response.setStatus(200);
        } catch (Exception e) {
            response.setStatus(500);
            response.setHeader("erro", e.getMessage());
        }

        return profissionais;
    }
    
    @RequestMapping(value = "mensagem/nova", method = RequestMethod.POST)
    @ResponseBody
    public void novaMensagem(HttpServletResponse response,
            @RequestBody String mensagem, HttpSession session) {
        try {
            Type type = new TypeToken<Mensagem>() {
            }.getType();
            Gson g = new GsonBuilder().setDateFormat("dd/MM/yyyy hh:mm:ss").create();
            Mensagem msg = g.fromJson(mensagem, type);
            Profissional profissionalSession = (Profissional) session.getAttribute("usuarioLogado");
            msg.setRemetente(profissionalSession);
            mensagemService.create(msg);
            response.setStatus(200);
        } catch (Exception e) {
            response.setStatus(500);
            response.setHeader("erro", e.getMessage());
        }
    }
    
    @RequestMapping(value = "mensagem/lida", method = RequestMethod.PUT)
    @ResponseBody
    public void mensagemLida(HttpServletResponse response,
            @RequestBody String mensagem, HttpSession session) {
        try {
            Type type = new TypeToken<Mensagem>() {
            }.getType();
            Gson g = new GsonBuilder().setDateFormat("dd/MM/yyyy hh:mm").create();
            Mensagem msg = g.fromJson(mensagem, type);
            mensagemService.update(msg);
            response.setStatus(200);
        } catch (Exception e) {
            response.setStatus(500);
            response.setHeader("erro", e.getMessage());
        }
    }
    
    @RequestMapping(value = "mensagem/verifica", method = RequestMethod.GET)
    public @ResponseBody
    Integer verificaMensagem(HttpSession session) throws Exception {
        Profissional profissional = (Profissional) session.getAttribute("usuarioLogado");
        Integer quantidade = mensagemService.verificaMensagem(profissional);
        return quantidade;
    }
    
}
