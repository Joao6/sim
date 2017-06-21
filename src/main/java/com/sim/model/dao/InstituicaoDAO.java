package com.sim.model.dao;

import com.sim.model.base.BaseDAO;
import com.sim.model.entity.Instituicao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Joao Pedro
 */
public class InstituicaoDAO implements BaseDAO<Instituicao> {

    @Override
    public void create(Connection conn, Instituicao entity) throws Exception {
        String sql = "INSERT INTO instituicao(nome, conf_versao, telefone, endereco, numero, bairro, cidade, cep) VALUES (?, ?, ?, ?, ?, ?, ?, ?) RETURNING id;";
        PreparedStatement ps = conn.prepareStatement(sql);
        int i = 0;
        ps.setString(++i, entity.getNome());
        if (entity.getConfVersao() != null) {
            ps.setString(++i, entity.getConfVersao());
        } else {
            ps.setString(++i, "free");
        }
        ps.setString(++i, entity.getTelefone());
        ps.setString(++i, entity.getEndereco());
        ps.setString(++i, entity.getNumero());
        ps.setString(++i, entity.getBairro());
        ps.setString(++i, entity.getCidade());
        ps.setString(++i, entity.getCep());

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            entity.setId(rs.getLong("id"));
        }

        rs.close();
        ps.close();
    }

    @Override
    public Instituicao readById(Connection conn, Long id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Instituicao> readByCriteria(Connection conn, Map<Long, Object> criteria, Long limit, Long offset) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Connection conn, Instituicao entity) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Connection conn, Long id) throws Exception {
        String sql = "DELETE FROM instituicao WHERE id=? ";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setLong(1, id);
        ps.execute();
        ps.close();
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
    public Instituicao parser(ResultSet rs) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
