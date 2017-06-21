package com.sim.model.service;

import com.sim.model.ConnectionManager;
import com.sim.model.base.service.BaseAtendimentoService;
import com.sim.model.dao.AtendimentoDAO;
import com.sim.model.entity.Atendimento;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Joao Pedro
 */
public class AtendimentoService implements BaseAtendimentoService{

    @Override
    public void create(Atendimento entity) throws Exception {
        Connection conn = ConnectionManager.getInstance().getConnection();
        try {
            AtendimentoDAO dao = new AtendimentoDAO();
            dao.create(conn, entity);
            conn.commit();
            conn.close();
        } catch (Exception e) {            
            conn.rollback();
            conn.close();
        }
    }

    @Override
    public Atendimento readById(Long id) throws Exception {
        Connection conn = ConnectionManager.getInstance().getConnection();
        Atendimento atendimento = null;
        try {
            AtendimentoDAO dao = new AtendimentoDAO();
            atendimento = dao.readById(conn, id);
            conn.close();
        } catch (Exception e) {
            conn.close();
        }
        return atendimento;
    }

    @Override
    public List<Atendimento> readByCriteria(Map<Long, Object> criteria, Long limit, Long offset) throws Exception {
        Connection conn = ConnectionManager.getInstance().getConnection();
        List<Atendimento> atendimentoList = null;
        try {
            AtendimentoDAO dao = new AtendimentoDAO();
            atendimentoList = dao.readByCriteria(conn, criteria, limit, offset);
            conn.close();
        } catch (Exception e) {
            conn.close();
        }
        return atendimentoList;
    }

    @Override
    public void update(Atendimento entity) throws Exception {
        Connection conn = ConnectionManager.getInstance().getConnection();
        try {
            AtendimentoDAO dao = new AtendimentoDAO();
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
            AtendimentoDAO dao = new AtendimentoDAO();
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
            AtendimentoDAO dao = new AtendimentoDAO();
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Atendimento> getAtendimentosPacienteAssociado(Map<Long, Object> criteria, 
            Long idInstituicao, Long idProfissional, Long limit, Long offset) throws Exception{
        Connection conn = ConnectionManager.getInstance().getConnection();
        List<Atendimento> atendimentoList = null;
        try {
            AtendimentoDAO dao = new AtendimentoDAO();
            atendimentoList = dao.getAtendimentosPacienteAssociado(conn, criteria, idInstituicao, idProfissional, limit, offset);
            conn.close();
        } catch (Exception e) {
            conn.close();
        }
        return atendimentoList;
    }
    
}
