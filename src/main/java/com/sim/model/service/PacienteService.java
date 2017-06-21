package com.sim.model.service;

import com.sim.model.ConnectionManager;
import com.sim.model.base.service.BasePacienteService;
import com.sim.model.dao.PacienteDAO;
import com.sim.model.entity.Paciente;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Joao Pedro
 */
public class PacienteService implements BasePacienteService{

    @Override
    public void create(Paciente entity) throws Exception {
        Connection conn = ConnectionManager.getInstance().getConnection();
        try {
            PacienteDAO dao = new PacienteDAO();
            dao.create(conn, entity);
            conn.commit();
            conn.close();
        } catch (Exception e) {            
            conn.rollback();
            conn.close();
        }
    }

    @Override
    public Paciente readById(Long id) throws Exception {
        Connection conn = ConnectionManager.getInstance().getConnection();
        Paciente paciente = null;
        try {
            PacienteDAO dao = new PacienteDAO();
            paciente = dao.readById(conn, id);
            conn.close();
        } catch (Exception e) {
            conn.close();
        }
        return paciente;
    }

    @Override
    public List<Paciente> readByCriteria(Map<Long, Object> criteria, Long limit, Long offset) throws Exception {
        Connection conn = ConnectionManager.getInstance().getConnection();
        List<Paciente> pacienteList = null;
        try {
            PacienteDAO dao = new PacienteDAO();
            pacienteList = dao.readByCriteria(conn, criteria, limit, offset);
            conn.close();
        } catch (Exception e) {
            conn.close();
        }
        return pacienteList;
    }

    @Override
    public void update(Paciente entity) throws Exception {
        Connection conn = ConnectionManager.getInstance().getConnection();
        try {
            PacienteDAO dao = new PacienteDAO();
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
            PacienteDAO dao = new PacienteDAO();
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
            PacienteDAO dao = new PacienteDAO();
            count = dao.countByCriteria(conn, criteria, limit, offset);
            conn.commit();
            conn.close();
        } catch (Exception e) {
            conn.rollback();
            conn.close();
            throw e;
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
        String data = (String) fields.get("data");
        if(data == null || data.trim().isEmpty()){
            errors.put("Data nascimento", "Este Campo é obrigatório!");
        } 
        return errors;
    }
    
    public List<Paciente> readPacienteAssociado(Map<Long, Object> criteria) throws Exception{
        Connection conn = ConnectionManager.getInstance().getConnection();
        List<Paciente> pacienteList = null;
        try {
            PacienteDAO dao = new PacienteDAO();
            pacienteList = dao.readPacienteAssociado(conn, criteria);
            conn.close();
        } catch (Exception e) {
            conn.close();
        }
        return pacienteList;
    }
    
}
