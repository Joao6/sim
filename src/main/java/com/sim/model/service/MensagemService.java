package com.sim.model.service;

import com.sim.model.ConnectionManager;
import com.sim.model.base.service.BaseMensagemService;
import com.sim.model.dao.MensagemDAO;
import com.sim.model.entity.Mensagem;
import com.sim.model.entity.Profissional;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Joao Pedro
 */
public class MensagemService implements BaseMensagemService {

    @Override
    public void create(Mensagem entity) throws Exception {
        Connection conn = ConnectionManager.getInstance().getConnection();
        try {
            MensagemDAO dao = new MensagemDAO();
            dao.create(conn, entity);
            conn.commit();
            conn.close();
        } catch (Exception e) {
            conn.rollback();
            conn.close();
        }
    }

    @Override
    public Mensagem readById(Long id) throws Exception {
        Connection conn = ConnectionManager.getInstance().getConnection();
        Mensagem mensagem = null;
        try {
            MensagemDAO dao = new MensagemDAO();
            mensagem = dao.readById(conn, id);
            conn.close();
        } catch (Exception e) {
            conn.close();
        }
        return mensagem;
    }

    @Override
    public List<Mensagem> readByCriteria(Map<Long, Object> criteria, Long limit, Long offset) throws Exception {
        Connection conn = ConnectionManager.getInstance().getConnection();
        List<Mensagem> mensagemList = null;
        try {
            MensagemDAO dao = new MensagemDAO();
            mensagemList = dao.readByCriteria(conn, criteria, null, null);
            conn.close();
        } catch (Exception e) {
            conn.close();
        }
        return mensagemList;
    }

    @Override
    public List<Mensagem> readMensagensRecebidas(Map<Long, Object> criteria, Long limit, Long offset, Long idProfSession) throws Exception {
        Connection conn = ConnectionManager.getInstance().getConnection();
        List<Mensagem> mensagemList = null;
        try {
            MensagemDAO dao = new MensagemDAO();
            mensagemList = dao.readMensagensRecebidas(conn, criteria, limit, offset, idProfSession);
            conn.close();
        } catch (Exception e) {
            conn.close();
        }
        return mensagemList;
    }

    @Override
    public List<Mensagem> readMensagensEnviadas(Map<Long, Object> criteria, Long limit, Long offset, Long idProfSession) throws Exception {
        Connection conn = ConnectionManager.getInstance().getConnection();
        List<Mensagem> mensagemList = null;
        try {
            MensagemDAO dao = new MensagemDAO();
            mensagemList = dao.readMensagensEnviadas(conn, criteria, limit, offset, idProfSession);
            conn.close();
        } catch (Exception e) {
            conn.close();
        }
        return mensagemList;
    }

    @Override
    public void update(Mensagem entity) throws Exception {
        Connection conn = ConnectionManager.getInstance().getConnection();
        try {
            MensagemDAO dao = new MensagemDAO();
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
            MensagemDAO dao = new MensagemDAO();
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
            MensagemDAO dao = new MensagemDAO();
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
    
    public Integer verificaMensagem(Profissional profissional) throws Exception {
        Connection conn = ConnectionManager.getInstance().getConnection();
        Integer quantidade = null;
        try {
            MensagemDAO dao = new MensagemDAO();
            quantidade = dao.verificaMensagem(profissional, conn);
            conn.close();
        } catch (Exception e) {
            conn.rollback();
            conn.close();
            throw e;
        }
        return quantidade;
    }

    @Override
    public Map<String, String> validate(Map<String, Object> fields) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
