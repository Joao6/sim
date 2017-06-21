package com.sim.model.service;

import com.sim.model.ConnectionManager;
import com.sim.model.base.service.BaseProfissionalService;
import com.sim.model.criteria.UsuarioCriteria;
import com.sim.model.dao.UsuarioDAO;
import com.sim.model.entity.Profissional;
import com.sim.model.entity.Usuario;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Joao Pedro
 */
public class ProfissionalService implements BaseProfissionalService {

    @Override
    public void create(Profissional entity) throws Exception {
        Connection conn = ConnectionManager.getInstance().getConnection();
        try {
            UsuarioDAO dao = new UsuarioDAO();
            dao.create(conn, entity);
            conn.commit();
            conn.close();
        } catch (Exception e) {
            conn.rollback();
            conn.close();
            throw e;
        }
    }

    @Override
    public Profissional readById(Long id) throws Exception {
        Connection conn = ConnectionManager.getInstance().getConnection();
        Profissional profissional = null;
        try {
            UsuarioDAO dao = new UsuarioDAO();
            profissional = (Profissional) dao.readById(conn, id);
            conn.close();
        } catch (Exception e) {
            conn.close();
        }
        return profissional;
    }

    @Override
    public List<Profissional> readByCriteria(Map<Long, Object> criteria, Long limit, Long offset) throws Exception {
        Connection conn = ConnectionManager.getInstance().getConnection();
        List<Profissional> profissionalList = null;
        try {
            UsuarioDAO dao = new UsuarioDAO();
            profissionalList = (List) dao.readByCriteria(conn, criteria, limit, offset);
            conn.close();
        } catch (Exception e) {
            conn.close();
        }
        return profissionalList;
    }

    @Override
    public void update(Profissional entity) throws Exception {
        Connection conn = ConnectionManager.getInstance().getConnection();
        try {
            UsuarioDAO dao = new UsuarioDAO();
            dao.update(conn, entity);
            conn.commit();
            conn.close();
        } catch (Exception e) {
            conn.rollback();
            conn.close();
        }
    }

    @Override
    public void delete(Long id) throws Exception {
        Connection conn = ConnectionManager.getInstance().getConnection();
        try {
            UsuarioDAO dao = new UsuarioDAO();
            dao.delete(conn, id);
            conn.commit();
            conn.close();
        } catch (Exception e) {
            conn.rollback();
            conn.close();
        }
    }

    @Override
    public Long countByCriteria(Map<Long, Object> criteria, Long limit, Long offset) throws Exception {
        Connection conn = ConnectionManager.getInstance().getConnection();
        Long count = null;
        try {
            UsuarioDAO dao = new UsuarioDAO();
            count = dao.countByCriteria(conn, criteria, limit, offset);
            conn.commit();
            conn.close();
        } catch (Exception e) {
            conn.rollback();
            conn.close();
        }
        return count;
    }

    @Override
    public Map<String, String> validate(Map<String, Object> fields) throws Exception {
        Map<String, String> errors = new HashMap<>();

        String email = (String) fields.get("email");
        if (email == null || email.trim().isEmpty()) {
            errors.put("Email", "O email é obrigatório!");
        }

        String senha = (String) fields.get("senha");
        if (senha == null || senha.trim().isEmpty()) {
            errors.put("Senha", "Este campo é obrigatório!");
        }

        String nome = (String) fields.get("nome");
        if (nome == null || nome.trim().isEmpty()) {
            errors.put("Nome", "Este campo é obrigatório!");
        }

        String profissao = (String) fields.get("profissao");
        if (profissao == null || profissao.trim().isEmpty()) {
            errors.put("Profissao", "Este campo é obrigatório!");
        }

        String telefone = (String) fields.get("telefone");
        if (telefone == null || telefone.trim().isEmpty()) {
            errors.put("Telefone", "Este campo é obrigatório!");
        }
        Long id = (Long) fields.get("id");
        UsuarioService us = new UsuarioService();
        Usuario usuario = us.validaEmail(email);
        if (usuario != null && id != usuario.getId()) {
            errors.put("Email", "Este email já está cadastrado!");
        }
        return errors;
    }

}
