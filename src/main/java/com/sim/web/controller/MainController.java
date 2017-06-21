package com.sim.web.controller;

import com.sim.model.entity.Instituicao;
import com.sim.model.entity.Master;
import com.sim.model.entity.Usuario;
import com.sim.model.service.SenhaService;
import com.sim.model.service.UsuarioService;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Joao Pedro
 */
@Controller
public class MainController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView getLogin(HttpSession session) {
        session.invalidate();
        ModelAndView mv = new ModelAndView("login/form-login");
        return mv;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login(String email, String senha, HttpSession session) throws Exception {
        ModelAndView mv = null;
        try {
            UsuarioService service = new UsuarioService();
            SenhaService ss = new SenhaService();
            senha = ss.convertPasswordToMD5(senha);
            Usuario usuario = service.login(email, senha);
            if (usuario != null) {
                session.setAttribute("usuarioLogado", usuario);
                if (usuario instanceof Master) {
                    mv = new ModelAndView("redirect:/master/home");
                    mv.addObject("usuarioLogado", usuario);
                } else if (usuario instanceof Instituicao) {
                    mv = new ModelAndView("redirect:/instituicao/home");
                    mv.addObject("usuarioLogado", usuario);
                } else {
                    mv = new ModelAndView("redirect:/profissional/home");
                    mv.addObject("usuarioLogado", usuario);
                }
            } else {
                String errors = "Dados incorretos!";
                mv = new ModelAndView("login/form-login");
                mv.addObject("errors", errors);
            }
        } catch (Exception e) {
            mv = new ModelAndView("erro/error");
            mv.addObject("erro", e.getMessage());
        }
        return mv;
    }

    @RequestMapping(value = "/usuario/logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpSession session) {
        session.removeAttribute("usuarioLogado");
        ModelAndView mv = new ModelAndView("redirect:/login");
        return mv;
    }

}
