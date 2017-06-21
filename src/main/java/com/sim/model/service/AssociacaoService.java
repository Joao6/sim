package com.sim.model.service;

import com.sim.model.ConnectionManager;
import com.sim.model.base.service.BaseAssociacaoService;
import com.sim.model.dao.AssociacaoDAO;
import com.sim.model.entity.Associacao;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Joao Pedro
 */
public class AssociacaoService implements BaseAssociacaoService {

    @Override
    public void create(Associacao entity) throws Exception {
        Connection conn = ConnectionManager.getInstance().getConnection();
        try {
            AssociacaoDAO dao = new AssociacaoDAO();
            dao.create(conn, entity);
            conn.commit();
            conn.close();
        } catch (Exception e) {
            conn.rollback();
            conn.close();
        }
    }

    @Override
    public Associacao readById(Long id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Associacao> readByCriteria(Map<Long, Object> criteria, Long limit, Long offset) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Associacao entity) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Long id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Long countByCriteria(Map<Long, Object> criteria, Long limit, Long offset) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<String, String> validate(Map<String, Object> fields) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
