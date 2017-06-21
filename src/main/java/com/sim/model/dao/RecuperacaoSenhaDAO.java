package com.sim.model.dao;

import com.sim.model.base.BaseDAO;
import com.sim.model.entity.Instituicao;
import com.sim.model.entity.Master;
import com.sim.model.entity.Profissional;
import com.sim.model.entity.RecuperacaoSenha;
import com.sim.model.entity.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Joao Pedro
 */
public class RecuperacaoSenhaDAO implements BaseDAO<RecuperacaoSenha> {

    @Override
    public void create(Connection conn, RecuperacaoSenha entity) throws Exception {
        String sql = "INSERT INTO recuperacao_senha(hash, usuario_fk, ativo) VALUES (?, ?, ?) RETURNING id";

        PreparedStatement ps = conn.prepareStatement(sql);
        int i = 0;
        ps.setString(++i, entity.getHash());
        ps.setLong(++i, entity.getUsuario().getId());
        ps.setBoolean(++i, entity.getAtivo());

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            entity.setId(rs.getLong("id"));
        }

        rs.close();
        ps.close();

    }

    @Override
    public RecuperacaoSenha readById(Connection conn, Long id) throws Exception {
        String sql = "select recuperacao_senha.*, usuario.id usu_id, usuario.email usu_email, usuario.nome usu_nome, instituicao.usuario_fk instituicao, master.usuario_fk master, profissional.usuario_fk profissional from recuperacao_senha \n"
                + " left join usuario on recuperacao_senha.usuario_fk = usuario.id\n"
                + " left join profissional on profissional.usuario_fk = usuario.id\n"
                + " left join instituicao on instituicao.usuario_fk = usuario.id\n"
                + " left join master on master.usuario_fk = usuario.id where recuperacao_senha.id = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setLong(1, id);
        RecuperacaoSenha recuperacaoSenha = null;
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            recuperacaoSenha = new RecuperacaoSenha();
            recuperacaoSenha.setId(rs.getLong("id"));
            recuperacaoSenha.setAtivo(rs.getBoolean("ativo"));
            recuperacaoSenha.setHash(rs.getString("hash"));

            Usuario usuario = null;
            if (usuario == null) {
                if (rs.getLong("instituicao") > 0) {
                    usuario = new Instituicao();
                } else if ((rs.getLong("profissional") > 0)) {
                    usuario = new Profissional();
                } else {
                    usuario = new Master();
                }
            }
            usuario.setId(rs.getLong("usu_id"));
            usuario.setNome(rs.getString("usu_nome"));
            usuario.setEmail(rs.getString("usu_email"));
            
            recuperacaoSenha.setUsuario(usuario);
        }

        rs.close();
        ps.close();

        return recuperacaoSenha;
    }

    @Override
    public List<RecuperacaoSenha> readByCriteria(Connection conn, Map<Long, Object> criteria, Long limit, Long offset) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Connection conn, RecuperacaoSenha entity) throws Exception {
        String sql = "UPDATE recuperacao_senha SET hash=?, usuario_fk=?, ativo=? WHERE id=?;";

        PreparedStatement ps = conn.prepareStatement(sql);
        int i = 0;
        ps.setString(++i, entity.getHash());
        ps.setLong(++i, entity.getUsuario().getId());
        ps.setBoolean(++i, entity.getAtivo());
        ps.setLong(++i, entity.getId());

        ps.execute();
        ps.close();
    }

    @Override
    public void delete(Connection conn, Long id) throws Exception {
        String sql = "DELETE FROM recuperacao_senha WHERE id = ?;";

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
    public RecuperacaoSenha parser(ResultSet rs) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public RecuperacaoSenha readByHashCode(Connection conn, String codigo) throws Exception {
        String sql = "select recuperacao_senha.*, usuario.id usu_id, usuario.email usu_email, usuario.nome usu_nome, instituicao.usuario_fk instituicao, master.usuario_fk master, profissional.usuario_fk profissional from recuperacao_senha \n"
                + " left join usuario on recuperacao_senha.usuario_fk = usuario.id\n"
                + " left join profissional on profissional.usuario_fk = usuario.id\n"
                + " left join instituicao on instituicao.usuario_fk = usuario.id\n"
                + " left join master on master.usuario_fk = usuario.id where recuperacao_senha.hash = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, codigo);
        RecuperacaoSenha recuperacaoSenha = null;
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            recuperacaoSenha = new RecuperacaoSenha();
            recuperacaoSenha.setId(rs.getLong("id"));
            recuperacaoSenha.setAtivo(rs.getBoolean("ativo"));
            recuperacaoSenha.setHash(rs.getString("hash"));

            Usuario usuario = null;
            if (usuario == null) {
                if (rs.getLong("instituicao") > 0) {
                    usuario = new Instituicao();
                } else if ((rs.getLong("profissional") > 0)) {
                    usuario = new Profissional();
                } else {
                    usuario = new Master();
                }
            }
            usuario.setId(rs.getLong("usu_id"));
            usuario.setNome(rs.getString("usu_nome"));
            usuario.setEmail(rs.getString("usu_email"));
            
            recuperacaoSenha.setUsuario(usuario);
        }

        rs.close();
        ps.close();

        return recuperacaoSenha;
    }

}
