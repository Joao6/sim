package com.sim.model.service;

import com.sim.model.ConnectionManager;
import com.sim.model.base.service.BaseUsuarioService;
import com.sim.model.criteria.UsuarioCriteria;
import com.sim.model.dao.UsuarioDAO;
import com.sim.model.entity.Usuario;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Joao Pedro
 */
public class UsuarioService implements BaseUsuarioService {

    @Override
    public Usuario login(String email, String senha) throws Exception {
//        senha = Usuario.encodeSenha(senha);
        Connection conn = ConnectionManager.getInstance().getConnection();
        Usuario usuario = null;
        Map<Long, Object> criteria = new HashMap<>();
        criteria.put(UsuarioCriteria.EMAIL_EQ, email);
        criteria.put(UsuarioCriteria.SENHA_EQ, senha);
        UsuarioDAO dao = new UsuarioDAO();
        List<Usuario> usuarioList = dao.readByCriteria(conn, criteria, 0L, 0L);
        if (usuarioList.size() == 1) {
            Usuario aux = usuarioList.get(0);
            if (aux.getEmail().equals(email) && aux.getSenha().equals(senha)) {
                usuario = aux;
            }
        }
        conn.commit();
        conn.close();
        return usuario;
    }

    @Override
    public Usuario validaEmail(String email) throws Exception {
        Connection conn = ConnectionManager.getInstance().getConnection();
        Usuario usuario = null;
        try {
            Map<Long, Object> criteria = new HashMap<>();
            criteria.put(UsuarioCriteria.EMAIL_EQ, email);
            UsuarioDAO dao = new UsuarioDAO();
            List<Usuario> usuarioList = dao.readByCriteria(conn, criteria, 0L, 0L);
            if (usuarioList.size() == 1) {
                Usuario aux = usuarioList.get(0);
                if (aux.getEmail().equals(email)) {
                    usuario = aux;
                }
            }
            conn.commit();
            conn.close();
        } catch (Exception e) {
            conn.close();
            throw e;
        }

        return usuario;
    }

    public Usuario readById(Long id) throws Exception {
        Connection conn = ConnectionManager.getInstance().getConnection();
        Usuario usuario = null;
        try {
            UsuarioDAO dao = new UsuarioDAO();
            usuario = dao.readById(conn, id);
            conn.close();
        } catch (Exception e) {
            conn.close();
            throw e;
        }
        return usuario;
    }
}
