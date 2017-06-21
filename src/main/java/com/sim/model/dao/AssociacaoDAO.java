package com.sim.model.dao;

import com.sim.model.base.BaseDAO;
import com.sim.model.entity.Associacao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Joao Pedro
 */
public class AssociacaoDAO implements BaseDAO<Associacao> {

    @Override
    public void create(Connection conn, Associacao entity) throws Exception {
        String sql = "DELETE FROM profissional_paciente WHERE profissional_fk=?;"; //delete o registro anterior;
        PreparedStatement ps = conn.prepareStatement(sql);
        int i = 0;
        ps.setLong(++i, entity.getIdProfissional());
        ps.execute();
        ps.close();
        
        sql = "INSERT INTO profissional_paciente(profissional_fk, paciente_fk)VALUES (?, ?);";
        ps = conn.prepareStatement(sql);

        i = 0;
        ps.setLong(++i, entity.getIdProfissional());

        for (Long idPaciente : entity.getPacienteList()) {
            i = 1;
            ps.setLong(++i, idPaciente);
            ps.execute();
        }
        ps.close();
    }        

    @Override
    public Associacao readById(Connection conn, Long id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Associacao> readByCriteria(Connection conn, Map<Long, Object> criteria, Long limit, Long offset) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Connection conn, Associacao entity) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Connection conn, Long id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Long countByCriteria(Connection conn, Map<Long, Object> criteria, Long limit, Long offset) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String applyCriteria(Map<Long, Object> criteria) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Associacao parser(ResultSet rs) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
