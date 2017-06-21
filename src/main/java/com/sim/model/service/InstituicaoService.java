package com.sim.model.service;

import com.sim.model.ConnectionManager;
import com.sim.model.base.service.BaseInstituicaoService;
import com.sim.model.dao.UsuarioDAO;
import com.sim.model.entity.Instituicao;
import com.sim.model.entity.Usuario;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Joao Pedro
 */
public class InstituicaoService implements BaseInstituicaoService{

    @Override
    public void create(Instituicao entity) throws Exception {
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
    public Instituicao readById(Long id) throws Exception {
        Connection conn = ConnectionManager.getInstance().getConnection();
        Instituicao instituicao = null;
        try {
            UsuarioDAO dao = new UsuarioDAO();
            instituicao = (Instituicao)dao.readById(conn, id);
            conn.close();
        } catch (Exception e) {
            conn.close();
        }
        return instituicao;
    }

    @Override
    public List<Instituicao> readByCriteria(Map<Long, Object> criteria, Long limit, Long offset) throws Exception {
        Connection conn = ConnectionManager.getInstance().getConnection();
        List<Instituicao> instituicaoList = null;
        try {
            UsuarioDAO dao = new UsuarioDAO();
            instituicaoList = (List)dao.readByCriteria(conn, criteria, null, null);
            conn.close();
        } catch (Exception e) {
            conn.close();
        }
        return instituicaoList;
    }

    @Override
    public void update(Instituicao entity) throws Exception {
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
        String versao = (String) fields.get("versao");
        if(versao == null || versao.trim().isEmpty()){
            errors.put("Versão", "Este Campo é obrigatório!");
        } 
        String telefone = (String) fields.get("telefone");
        if(telefone == null || telefone.trim().isEmpty()){
            errors.put("Telefone", "Este Campo é obrigatório!");
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
