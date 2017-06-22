package com.sim.web.interceptor;

import com.sim.model.entity.Instituicao;
import com.sim.model.entity.Master;
import com.sim.model.entity.Profissional;
import com.sim.model.entity.Usuario;
import com.sim.model.service.InstituicaoService;
import com.sim.model.service.MasterService;
import com.sim.model.service.ProfissionalService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Joao
 */
public class Interceptor implements HandlerInterceptor {
//    
//    @Autowired
//    private UsuarioService usuarioService;

    @Override
    public void afterCompletion(HttpServletRequest req, HttpServletResponse res, Object arg2, Exception arg3) throws Exception {
    }

    @Override
    public void postHandle(HttpServletRequest req, HttpServletResponse res, Object arg2, ModelAndView modelAndView) throws Exception {

        String url = req.getRequestURL().toString();

        if (url.endsWith("/master/home") || url.endsWith("/profissional/home") || url.endsWith("/instituicao/home")) {
            if (req.getSession().getAttribute("usuarioLogado") != null) {
                String urlDesejada = (String) req.getSession().getAttribute("urlDesejada");
                if (urlDesejada != null) {
                    req.getSession().setAttribute("urlDesejada", null);
                    res.sendRedirect(urlDesejada);
                }
            }
        }

    }

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object arg2) throws Exception {

        String url = req.getRequestURL().toString();
        String uri = req.getRequestURI();        

        if (url.contains("css") || url.contains("fonts") || url.contains("img") || url.contains("js") || url.contains("less") || url.contains("woff2")) {
            return true;
        }

        if (url.endsWith("/")) {
            if (req.getSession().getAttribute("usuarioLogado") == null) {
                res.sendRedirect("/login");
                return false;
            }
        }
        

        //URL's que não necessitam de login são tratadas aqui
        if (!url.endsWith("/login") && !url.endsWith("/recuperar-senha") && !url.endsWith("/recuperar-senha/rest/") && !url.contains("/recuperar-senha/")) {
            if (req.getSession().getAttribute("usuarioLogado") == null) {
                req.getSession().setAttribute("urlDesejada", url);
                res.sendRedirect("/login");
                return false;
            }
        }       

        if (req.getSession().getAttribute("usuarioLogado") != null) {
            Usuario usuario = (Usuario) req.getSession().getAttribute("usuarioLogado");
            if (usuario instanceof Master) {
                MasterService s = new MasterService();
                s.readById(usuario.getId());
            } else if (usuario instanceof Instituicao) {
                InstituicaoService s = new InstituicaoService();
                s.readById(usuario.getId());
            } else if(usuario instanceof Profissional){
                ProfissionalService s = new ProfissionalService();
                s.readById(usuario.getId());
            }
           
            req.getSession().setAttribute("usuarioLogado", usuario);            
            if (usuario instanceof Master) {
                if (uri.startsWith("/instituicao/") || uri.startsWith("/profissional/")) {
                    res.sendRedirect("/master/permissao-negada");
                    return false;
                }
            }
            if (usuario instanceof Instituicao) {
                if (uri.startsWith("/master/") || uri.startsWith("/profissional/")) {
                    res.sendRedirect("/instituicao/permissao-negada");
                    return false;
                }
            }
            if (usuario instanceof Profissional) {
                if (uri.startsWith("/master/") || uri.startsWith("/instituicao/")) {
                    res.sendRedirect("/profissional/permissao-negada");
                    return false;
                }
            }
        } 
        
        return true;
    }

}
