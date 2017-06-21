package com.sim.model.service;

import com.sim.model.ConnectionManager;
import com.sim.model.base.service.BaseMasterService;
import com.sim.model.dao.UsuarioDAO;
import com.sim.model.entity.Master;
import com.sim.model.entity.Usuario;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Joao Pedro
 */
public class MasterService implements BaseMasterService{

    @Override
    public void create(Master entity) throws Exception {
        Connection conn = ConnectionManager.getInstance().getConnection();
        try {
            UsuarioDAO dao = new UsuarioDAO();
            dao.create(conn, entity);
            conn.commit();
            conn.close();
        } catch (Exception e) {            
            conn.rollback();
            conn.close();
        }
    }

    @Override
    public Master readById(Long id) throws Exception {
        Connection conn = ConnectionManager.getInstance().getConnection();
        Master master = null;
        try {
            UsuarioDAO dao = new UsuarioDAO();
            master = (Master)dao.readById(conn, id);
            conn.close();
        } catch (Exception e) {
            conn.close();
        }
        return master;
    }

    @Override
    public List<Master> readByCriteria(Map<Long, Object> criteria, Long limit, Long offset) throws Exception {
        Connection conn = ConnectionManager.getInstance().getConnection();
        List<Master> masterList = null;
        try {
            UsuarioDAO dao = new UsuarioDAO();
            masterList = (List)dao.readByCriteria(conn, criteria, null, null);
            conn.close();
        } catch (Exception e) {
            conn.close();
        }
        return masterList;
    }

    @Override
    public void update(Master entity) throws Exception {
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
        
        String nome = (String) fields.get("nome");
        if(nome == null || nome.trim().isEmpty()){
            errors.put("Nome", "Este Campo é obrigatório!");
        }
        
        String email = (String) fields.get("email");
        if(email == null || email.trim().isEmpty()){
            errors.put("Email", "Este Campo é obrigatório!");
        }
        
        Long id = (Long) fields.get("id");          
        UsuarioService us = new UsuarioService();
        Usuario usuario = us.validaEmail(email);
        if(usuario != null && id != usuario.getId()){            
            errors.put("Email", "Este email já está cadastrado!");
        }
        return errors;
    }
    
}
