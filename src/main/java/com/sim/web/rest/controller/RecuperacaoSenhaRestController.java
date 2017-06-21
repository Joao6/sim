package com.sim.web.rest.controller;

import com.sim.model.entity.Instituicao;
import com.sim.model.entity.Master;
import com.sim.model.entity.Profissional;
import com.sim.model.entity.RecuperacaoSenha;
import com.sim.model.entity.Usuario;
import com.sim.model.service.InstituicaoService;
import com.sim.model.service.MasterService;
import com.sim.model.service.ProfissionalService;
import com.sim.model.service.RecuperacaoSenhaService;
import com.sim.model.service.UsuarioService;
import com.sim.web.utils.ObjetoRecuperacao;
import com.sim.web.utils.StringUtils;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Joao Pedro
 */
@RestController
public class RecuperacaoSenhaRestController {
    
    @RequestMapping(value = "/recuperar-senha/rest/", method = RequestMethod.GET)
    public void enviarEmailRecuperacao(HttpServletResponse response,
            @RequestParam(value = "email") String email) throws Exception {
        
        try {
            UsuarioService usuarioService = new UsuarioService();
            Usuario usuario = usuarioService.validaEmail(email);
            if (usuario != null) {
                RecuperacaoSenha recuperacaoSenha = new RecuperacaoSenha();
                recuperacaoSenha.setAtivo(true);
                recuperacaoSenha.setHash(StringUtils.gerarCodigoDeRecuperacao());
                recuperacaoSenha.setUsuario(usuario);
                
                RecuperacaoSenhaService rs = new RecuperacaoSenhaService();
                rs.create(recuperacaoSenha);                
                response.setStatus(200);
            }
        } catch (Exception e) {
            response.setStatus(500);
            response.setHeader("error", e.getMessage());
        }
    }
    
    @RequestMapping(value = "/recuperar-senha/rest/", method = RequestMethod.PUT)
    public void alterarSenha(HttpServletResponse response,
            @RequestBody ObjetoRecuperacao objeto) throws Exception {
        
        try {
            UsuarioService usuarioService = new UsuarioService();
            Usuario usuario = usuarioService.readById(objeto.getIdUsuario());
            if (usuario != null) {
                if (usuario instanceof Master) {
                    Master master = new Master();
                    MasterService ms = new MasterService();
                    master = ms.readById(usuario.getId());                    
                    master.setSenha(objeto.getSenha());
                    ms.update(master);
                } else if (usuario instanceof Instituicao) {
                    Instituicao instituicao = new Instituicao();
                    InstituicaoService es = new InstituicaoService();
                    instituicao = es.readById(usuario.getId());
                    instituicao.setSenha(objeto.getSenha());
                    es.update(instituicao);
                } else {//profissional
                    Profissional profissional = new Profissional();
                    ProfissionalService ps = new ProfissionalService();
                    profissional = ps.readById(usuario.getId());
                    profissional.setSenha(objeto.getSenha());
                    ps.update(profissional);
                }
            }
            //setar o código de verificação como false
            RecuperacaoSenhaService rs = new RecuperacaoSenhaService();
            RecuperacaoSenha recuperacaoSenha = rs.readById(objeto.getIdRecuperacao());
            recuperacaoSenha.setAtivo(false);
            rs.update(recuperacaoSenha);
            response.setStatus(200);
        } catch (Exception e) {
            response.setStatus(500);
            response.setHeader("error", e.getMessage());
        }
    }
    
}
