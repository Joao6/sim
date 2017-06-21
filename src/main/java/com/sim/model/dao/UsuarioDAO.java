package com.sim.model.dao;

import com.sim.model.base.BaseDAO;
import com.sim.model.criteria.InstituicaoCriteria;
import com.sim.model.criteria.PacienteCriteria;
import com.sim.model.criteria.ProfissionalCriteria;
import com.sim.model.criteria.UsuarioCriteria;
import com.sim.model.entity.Instituicao;
import com.sim.model.entity.Master;
import com.sim.model.entity.Profissional;
import com.sim.model.entity.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Joao Pedro
 */
public class UsuarioDAO implements BaseDAO<Usuario> {

    @Override
    public void create(Connection conn, Usuario entity) throws Exception {
        String sql = "INSERT INTO usuario(nome, email, senha)VALUES (?, ?, ?) RETURNING id;";
        PreparedStatement ps = conn.prepareStatement(sql);

        int i = 0;
        ps.setString(++i, entity.getNome());
        ps.setString(++i, entity.getEmail());
        ps.setString(++i, entity.getSenha());
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            entity.setId(rs.getLong("id"));
        }

        rs.close();
        ps.close();

        if (entity instanceof Master) {
            this.createMaster(conn, (Master) entity);
        } else if (entity instanceof Instituicao) {
            this.createInstituicao(conn, (Instituicao) entity);
        } else {
            this.createProfissional(conn, (Profissional) entity);
        }
    }

    private void createMaster(Connection conn, Master entity) throws Exception {
        String sql = "INSERT INTO master(usuario_fk)VALUES (?);";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setLong(1, entity.getId());
        ps.execute();
        ps.close();
    }

    private void createInstituicao(Connection conn, Instituicao entity) throws Exception {
        String sql = "INSERT INTO instituicao(usuario_fk, conf_versao, telefone, endereco, numero, bairro, cidade, uf, cep)VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement ps = conn.prepareStatement(sql);
        int i = 0;
        ps.setLong(++i, entity.getId());
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
        ps.setString(++i, entity.getUf());
        ps.setString(++i, entity.getCep());
        ps.execute();
        ps.close();
    }

    private void createProfissional(Connection conn, Profissional entity) throws Exception {
        String sql = "INSERT INTO profissional(usuario_fk, instituicao_fk, profissao, registro_de_conselho, cpf, rg, endereco, numero, bairro, cidade, uf, cep, telefone)VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement ps = conn.prepareStatement(sql);
        int i = 0;
        ps.setLong(++i, entity.getId());
        ps.setLong(++i, entity.getInstituicao().getId());

        ps.setString(++i, entity.getProfissao());
        ps.setString(++i, entity.getRegistroDeConselho());
        ps.setString(++i, entity.getCpf());
        ps.setString(++i, entity.getRg());
        ps.setString(++i, entity.getEndereco());
        ps.setString(++i, entity.getNumero());
        ps.setString(++i, entity.getBairro());
        ps.setString(++i, entity.getCidade());
        ps.setString(++i, entity.getUf());
        ps.setString(++i, entity.getCep());
        ps.setString(++i, entity.getTelefone());
        ps.execute();
        ps.close();
    }

    @Override
    public Usuario readById(Connection conn, Long id) throws Exception {
        String sql = "select usuario.*, instituicao.usuario_fk instituicao, master.usuario_fk master, profissional.usuario_fk profissional, instituicao.conf_versao inst_conf_versao, instituicao.telefone inst_telefone, instituicao.endereco inst_endereco, instituicao.numero inst_numero, instituicao.bairro inst_bairro, \n"
                + "instituicao.cidade inst_cidade, instituicao.uf inst_uf, instituicao.cep inst_cep, profissional.instituicao_fk prof_instituicao_fk,\n"
                + "profissional.profissao prof_profissao, profissional.registro_de_conselho prof_reg_conselho, profissional.cpf prof_cpf, profissional.rg prof_rg,\n"
                + "profissional.endereco prof_endereco, profissional.numero prof_numero, profissional.bairro prof_bairro, profissional.cidade prof_cidade,\n"
                + "profissional.uf prof_uf, profissional.cep prof_cep, profissional.telefone prof_telefone from usuario \n"
                + "left join profissional on profissional.usuario_fk = usuario.id\n"
                + "left join instituicao on instituicao.usuario_fk = usuario.id\n"
                + "left join master on master.usuario_fk = usuario.id where usuario.id = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setLong(1, id);
        ResultSet rs = ps.executeQuery();
        Usuario usuario = null;
        if (rs.next()) {
            if (usuario == null) {
                if (rs.getLong("instituicao") > 0) {
                    usuario = new Instituicao();
                } else if ((rs.getLong("profissional") > 0)) {
                    usuario = new Profissional();
                } else {
                    usuario = new Master();
                }
            }
            usuario.setId(rs.getLong("id"));
            usuario.setNome(rs.getString("nome"));
            usuario.setEmail(rs.getString("email"));
            usuario.setSenha(rs.getString("senha"));

            if (usuario instanceof Profissional) {
                Profissional profissional = (Profissional) usuario;
                profissional.setBairro(rs.getString("prof_bairro"));
                profissional.setProfissao(rs.getString("prof_profissao"));
                profissional.setCep(rs.getString("prof_cep"));
                profissional.setCidade(rs.getString("prof_cidade"));
                profissional.setCpf(rs.getString("prof_cpf"));
                profissional.setEndereco(rs.getString("prof_endereco"));
                profissional.setRegistroDeConselho(rs.getString("prof_reg_conselho"));
                profissional.setNumero(rs.getString("prof_numero"));
                profissional.setTelefone(rs.getString("prof_telefone"));
                profissional.setUf(rs.getString("prof_uf"));
                profissional.setRg(rs.getString("prof_rg"));

                Instituicao instituicao = new Instituicao();
                instituicao.setId(rs.getLong("prof_instituicao_fk"));

                profissional.setInstituicao(instituicao);

            } else if (usuario instanceof Instituicao) {
                Instituicao instituicao = (Instituicao) usuario;
                instituicao.setConfVersao(rs.getString("inst_conf_versao"));
                instituicao.setBairro(rs.getString("inst_bairro"));
                instituicao.setCidade(rs.getString("inst_cidade"));
                instituicao.setUf(rs.getString("inst_uf"));
                instituicao.setCep(rs.getString("inst_cep"));
                instituicao.setTelefone(rs.getString("inst_telefone"));
                instituicao.setEndereco(rs.getString("inst_endereco"));
                instituicao.setNumero(rs.getString("inst_numero"));
            }
        }

        return usuario;
    }

    @Override
    public List<Usuario> readByCriteria(Connection conn, Map<Long, Object> criteria, Long limit, Long offset) throws Exception {
        String sql = "select usuario.*, instituicao.usuario_fk instituicao, master.usuario_fk master, profissional.usuario_fk profissional, instituicao.conf_versao inst_conf_versao, instituicao.telefone inst_telefone, instituicao.endereco inst_endereco, instituicao.numero inst_numero, instituicao.bairro inst_bairro, \n"
                + "instituicao.cidade inst_cidade, instituicao.uf inst_uf, instituicao.cep inst_cep, profissional.instituicao_fk prof_instituicao_fk,\n"
                + "profissional.profissao prof_profissao, profissional.registro_de_conselho prof_reg_conselho, profissional.cpf prof_cpf, profissional.rg prof_rg,\n"
                + "profissional.endereco prof_endereco, profissional.numero prof_numero, profissional.bairro prof_bairro, profissional.cidade prof_cidade,\n"
                + "profissional.uf prof_uf, profissional.cep prof_cep, profissional.telefone prof_telefone from usuario \n"
                + "left join profissional on profissional.usuario_fk = usuario.id\n"
                + "left join instituicao on instituicao.usuario_fk = usuario.id\n"
                + "left join master on master.usuario_fk = usuario.id where 1 = 1 ";
        if (criteria != null) {
            sql += applyCriteria(criteria);
        }

        sql += " ORDER BY usuario.nome ";

        if (limit != null && limit > 0) {
            sql += " limit " + limit;
        }
        if (offset != null && offset >= 0) {
            sql += " offset " + offset;
        }

        Statement s = conn.createStatement();
        ResultSet rs = s.executeQuery(sql);
        List<Usuario> usuarioList = new ArrayList<>();
        while (rs.next()) {
            Usuario usuario = null;
            if (rs.getLong("master") > 0) {
                usuario = new Master();
            } else if (rs.getLong("instituicao") > 0) {
                usuario = new Instituicao();
            } else {
                usuario = new Profissional();
            }

            usuario.setId(rs.getLong("id"));
            usuario.setNome(rs.getString("nome"));
            usuario.setEmail(rs.getString("email"));
            usuario.setSenha(rs.getString("senha"));

            if (usuario instanceof Profissional) {
                Profissional profissional = (Profissional) usuario;
                profissional.setBairro(rs.getString("prof_bairro"));
                profissional.setProfissao(rs.getString("prof_profissao"));
                profissional.setCep(rs.getString("prof_cep"));
                profissional.setCidade(rs.getString("prof_cidade"));
                profissional.setCpf(rs.getString("prof_cpf"));
                profissional.setEndereco(rs.getString("prof_endereco"));
                profissional.setRegistroDeConselho(rs.getString("prof_reg_conselho"));
                profissional.setNumero(rs.getString("prof_numero"));
                profissional.setTelefone(rs.getString("prof_telefone"));
                profissional.setUf(rs.getString("prof_uf"));
                profissional.setRg(rs.getString("prof_rg"));

                Instituicao instituicao = new Instituicao();
                instituicao.setId(rs.getLong("prof_instituicao_fk"));

                profissional.setInstituicao(instituicao);

            } else if (usuario instanceof Instituicao) {
                Instituicao instituicao = (Instituicao) usuario;
                instituicao.setConfVersao(rs.getString("inst_conf_versao"));
                instituicao.setCidade(rs.getString("inst_cidade"));
                instituicao.setUf(rs.getString("inst_uf"));
                instituicao.setCep(rs.getString("inst_cep"));
                instituicao.setTelefone(rs.getString("inst_telefone"));
                instituicao.setEndereco(rs.getString("inst_endereco"));
                instituicao.setNumero(rs.getString("inst_numero"));
            }

            usuarioList.add(usuario);

        }

        return usuarioList;
    }

    @Override
    public void update(Connection conn, Usuario entity) throws Exception {
        String sql = "UPDATE usuario SET nome=?, email=?, senha=? WHERE id = ?;";

        PreparedStatement ps = conn.prepareStatement(sql);
        int i = 0;
        ps.setString(++i, entity.getNome());
        ps.setString(++i, entity.getEmail());
        ps.setString(++i, entity.getSenha());
        ps.setLong(++i, entity.getId());
        ps.execute();

        if (entity instanceof Instituicao) {
            sql = "UPDATE instituicao SET conf_versao=?, telefone=?, endereco=?, numero=?, bairro=?, cidade=?, uf=?, cep=? WHERE usuario_fk = ?;";
            ps = conn.prepareStatement(sql);
            i = 0;
            ps.setString(++i, ((Instituicao) entity).getConfVersao());
            ps.setString(++i, ((Instituicao) entity).getTelefone());
            ps.setString(++i, ((Instituicao) entity).getEndereco());
            ps.setString(++i, ((Instituicao) entity).getNumero());
            ps.setString(++i, ((Instituicao) entity).getBairro());
            ps.setString(++i, ((Instituicao) entity).getCidade());
            ps.setString(++i, ((Instituicao) entity).getUf());
            ps.setString(++i, ((Instituicao) entity).getCep());
            ps.setLong(++i, entity.getId());
            ps.execute();
        } else if (entity instanceof Profissional) {
            sql = "UPDATE profissional SET profissao=?, registro_de_conselho=?, cpf=?, rg=?, endereco=?, numero=?, bairro=?, cidade=?, uf=?, cep=?, telefone=? WHERE usuario_fk = ?;";
            ps = conn.prepareStatement(sql);
            i = 0;
            ps.setString(++i, ((Profissional) entity).getProfissao());
            ps.setString(++i, ((Profissional) entity).getRegistroDeConselho());
            ps.setString(++i, ((Profissional) entity).getCpf());
            ps.setString(++i, ((Profissional) entity).getRg());
            ps.setString(++i, ((Profissional) entity).getEndereco());
            ps.setString(++i, ((Profissional) entity).getNumero());
            ps.setString(++i, ((Profissional) entity).getBairro());
            ps.setString(++i, ((Profissional) entity).getCidade());
            ps.setString(++i, ((Profissional) entity).getUf());
            ps.setString(++i, ((Profissional) entity).getCep());
            ps.setString(++i, ((Profissional) entity).getTelefone());
            ps.setLong(++i, entity.getId());
            ps.execute();
        }
        ps.close();
    }

    @Override
    public void delete(Connection conn, Long id) throws Exception {
        String sql = "DELETE FROM usuario WHERE id=? ";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setLong(1, id);
        ps.execute();
        ps.close();
    }

    @Override
    public Long countByCriteria(Connection conn, Map<Long, Object> criteria, Long limit, Long offset) throws Exception {
        String sql = "select count(*) from usuario \n"
                + "                left join profissional on profissional.usuario_fk = usuario.id\n"
                + "                left join instituicao on instituicao.usuario_fk = usuario.id\n"
                + "                left join master on master.usuario_fk = usuario.id where 1 = 1 ";
        if (criteria != null) {
            sql += applyCriteria(criteria);
        }

        Statement s = conn.createStatement();
        ResultSet rs = s.executeQuery(sql);
        Long count = 0L;
        if (rs.next()) {
            count = rs.getLong("count");
        }
        rs.close();
        s.close();
        return count;
    }

    @Override
    public String applyCriteria(Map<Long, Object> criteria) throws Exception {
        String sql = "";

        Boolean master = (Boolean) criteria.get(UsuarioCriteria.MASTER);
        if (master != null) {
            if (master) {
                sql += " and master.usuario_fk is not null";
            } else {
                sql += " and master.usuario_fk is null";
            }
        }

        Boolean instituicao = (Boolean) criteria.get(UsuarioCriteria.INSTITUICAO);
        if (instituicao != null) {
            if (instituicao) {
                sql += " and instituicao.usuario_fk is not null";
            } else {
                sql += " and instituicao.usuario_fk is null";
            }
        }

        Boolean profissional = (Boolean) criteria.get(UsuarioCriteria.PROFISSIONAL);
        if (profissional != null) {
            if (profissional) {
                sql += " and profissional.usuario_fk is not null";
            } else {
                sql += " and profissional.usuario_fk is null";
            }
        }

        String email = (String) criteria.get(UsuarioCriteria.EMAIL_EQ);
        if (email != null) {
            sql += " and email ='" + email + "'";
        }

        String senha = (String) criteria.get(UsuarioCriteria.SENHA_EQ);
        if (senha != null) {
            sql += " and senha ='" + senha + "'";
        }

        String nome = (String) criteria.get(UsuarioCriteria.NOME_ILIKE);
        if (nome != null) {
            sql += " and nome ILIKE '%" + nome + "%'";
        }

        String confVersao = (String) criteria.get(InstituicaoCriteria.CONF_VERSAO);
        if (confVersao != null) {
            sql += " and instituicao.conf_versao ='" + confVersao + "'";
        }

        Long idInstituicaoProfissional = (Long) criteria.get(ProfissionalCriteria.INSTITUICAO_ID);
        if (idInstituicaoProfissional != null) {
            sql += " and profissional.instituicao_fk =" + idInstituicaoProfissional;
        }

        Long idInstituicaoPaciente = (Long) criteria.get(PacienteCriteria.INSTITUICAO);
        if (idInstituicaoPaciente != null) {
            sql += " and paciente.instituicao_fk =" + idInstituicaoPaciente;
        }
        return sql;
    }

    @Override
    public Usuario parser(ResultSet rs) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
