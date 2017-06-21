package com.sim.web.controller;

import com.sim.model.entity.RecuperacaoSenha;
import com.sim.model.service.RecuperacaoSenhaService;
import java.util.Objects;
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
public class RecuperacaoSenhaController {

    private static final RecuperacaoSenhaService recuperacaoSenhaService = new RecuperacaoSenhaService();
    
    @RequestMapping(value = "/recuperar-senha", method = RequestMethod.GET)
    public ModelAndView getFormRecuperarSenha() {
        ModelAndView mv = new ModelAndView("usuario/envia-email-recuperar-senha");
        return mv;
    }

    @RequestMapping(value = "/recuperar-senha/{codigo}/{idUsuario}", method = RequestMethod.GET)
    public ModelAndView getRecuperarSenha(@PathVariable String codigo, @PathVariable Long idUsuario) {
        ModelAndView mv = null;
        try {
            RecuperacaoSenha recuperacaoSenha = recuperacaoSenhaService.readByHashCode(codigo);
            if (recuperacaoSenha.getAtivo()) {
                if (Objects.equals(recuperacaoSenha.getUsuario().getId(), idUsuario)) {
                    //ok - permitido alterar a senha
                    mv = new ModelAndView("usuario/recuperar-senha");
                    mv.addObject("idUsuario", idUsuario);
                    mv.addObject("idRecuperacao", recuperacaoSenha.getId());
                } else {
                    //erro
                    mv = new ModelAndView("usuario/erro-codigo-invalido");
                }
            } else {
                //erro
                mv = new ModelAndView("usuario/erro-codigo-invalido");
            }
        } catch (Exception e) {
            mv = new ModelAndView("usuario/erro-codigo-invalido");
            mv.addObject("error", e.getMessage());
        }

        return mv;
    }
}
