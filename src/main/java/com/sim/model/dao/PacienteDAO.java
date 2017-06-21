package com.sim.model.dao;

import com.sim.model.base.BaseDAO;
import com.sim.model.criteria.PacienteCriteria;
import com.sim.model.entity.Instituicao;
import com.sim.model.entity.Paciente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Joao Pedro
 */
public class PacienteDAO implements BaseDAO<Paciente> {

    @Override
    public void create(Connection conn, Paciente entity) throws Exception {
        String sql = "INSERT INTO paciente(nome, nascimento, cpf, rg, nome_pai, nome_mae, endereco, numero, bairro, cidade, uf, cep, telefone, obs, prontuario, instituicao_fk)VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id;";

        PreparedStatement ps = conn.prepareStatement(sql);
        int i = 0;
        ps.setString(++i, entity.getNome());
        if (entity.getNascimento() != null) {
            java.sql.Date dataSql = new java.sql.Date(entity.getNascimento().getTime());
            ps.setDate(++i, dataSql);
        } else {
            ps.setNull(++i, Types.DATE);
        }
        ps.setString(++i, entity.getCpf());
        ps.setString(++i, entity.getRg());
        ps.setString(++i, entity.getNomePai());
        ps.setString(++i, entity.getNomeMae());
        ps.setString(++i, entity.getEndereco());
        ps.setString(++i, entity.getNumero());
        ps.setString(++i, entity.getBairro());
        ps.setString(++i, entity.getCidade());
        ps.setString(++i, entity.getUf());
        ps.setString(++i, entity.getCep());
        ps.setString(++i, entity.getTelefone());
        ps.setString(++i, entity.getObs());
        ps.setString(++i, entity.getProntuario());
        ps.setLong(++i, entity.getInstituicao().getId());

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            entity.setId(rs.getLong("id"));
        }

        rs.close();
        ps.close();
    }

    @Override
    public Paciente readById(Connection conn, Long id) throws Exception {
        String sql = "select paciente.*, usuario.nome inst_nome, usuario.email inst_email, instituicao.conf_versao inst_conf_versao, instituicao.telefone inst_telefone, instituicao.endereco inst_endereco, instituicao.numero inst_numero, instituicao.bairro inst_bairro, \n"
                + " instituicao.cidade inst_cidade, instituicao.uf inst_uf, instituicao.cep inst_cep\n"
                + " from paciente left join instituicao on paciente.instituicao_fk = instituicao.usuario_fk\n"
                + " left join usuario on usuario.id = instituicao.usuario_fk where paciente.id = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setLong(1, id);
        ResultSet rs = ps.executeQuery();
        Paciente paciente = null;
        if (rs.next()) {
            paciente = this.parser(rs);
        }
        return paciente;
    }

    @Override
    public List<Paciente> readByCriteria(Connection conn, Map<Long, Object> criteria, Long limit, Long offset) throws Exception {
        String sql = "select paciente.*, usuario.nome inst_nome, usuario.email inst_email, instituicao.conf_versao inst_conf_versao, instituicao.telefone inst_telefone, instituicao.endereco inst_endereco, instituicao.numero inst_numero, instituicao.bairro inst_bairro, \n"
                + " instituicao.cidade inst_cidade, instituicao.uf inst_uf, instituicao.cep inst_cep\n"
                + " from paciente left join instituicao on paciente.instituicao_fk = instituicao.usuario_fk\n"
                + " left join usuario on usuario.id = instituicao.usuario_fk where 1=1 ";

        if (criteria != null) {
            sql += applyCriteria(criteria);
        }

        sql += "ORDER BY paciente.nome ";

        if (limit != null && limit > 0) {
            sql += " limit " + limit;
        }
        if (offset != null && offset >= 0) {
            sql += " offset " + offset;
        }

        Statement s = conn.createStatement();
        ResultSet rs = s.executeQuery(sql);
        List<Paciente> pacienteList = new ArrayList<>();
        while (rs.next()) {
            Paciente paciente = this.parser(rs);

            pacienteList.add(paciente);
        }
        rs.close();
        s.close();

        return pacienteList;
    }

    @Override
    public void update(Connection conn, Paciente entity) throws Exception {
        String sql = "UPDATE paciente SET instituicao_fk=?, nome=?, nascimento=?, cpf=?, rg=?, nome_pai=?, nome_mae=?, endereco=?, numero=?, bairro=?, cidade=?, uf=?, cep=?, telefone=?, obs=?, prontuario=? WHERE id = ?;";

        PreparedStatement ps = conn.prepareStatement(sql);
        int i = 0;
        ps.setLong(++i, entity.getInstituicao().getId());
        ps.setString(++i, entity.getNome());
        if (entity.getNascimento() != null) {
            java.sql.Date dataSql = new java.sql.Date(entity.getNascimento().getTime());
            ps.setDate(++i, dataSql);
        } else {
            ps.setNull(++i, Types.DATE);
        }
        ps.setString(++i, entity.getCpf());
        ps.setString(++i, entity.getRg());
        ps.setString(++i, entity.getNomePai());
        ps.setString(++i, entity.getNomeMae());
        ps.setString(++i, entity.getEndereco());
        ps.setString(++i, entity.getNumero());
        ps.setString(++i, entity.getBairro());
        ps.setString(++i, entity.getCidade());
        ps.setString(++i, entity.getUf());
        ps.setString(++i, entity.getCep());
        ps.setString(++i, entity.getTelefone());
        ps.setString(++i, entity.getObs());
        ps.setString(++i, entity.getProntuario());
        ps.setLong(++i, entity.getId());

        ps.execute();
        ps.close();
    }

    @Override
    public void delete(Connection conn, Long id) throws Exception {
        String sql = "DELETE FROM paciente WHERE id=? ";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setLong(1, id);
        ps.execute();
        ps.close();
    }

    @Override
    public Long countByCriteria(Connection conn, Map<Long, Object> criteria, Long limit, Long offset) throws Exception {
        String sql = "SELECT count(*) count FROM paciente WHERE 1=1 ";
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

    public List<Paciente> readPacienteAssociado(Connection conn, Map<Long, Object> criteria) throws Exception {
        String sql = "select paciente.* from profissional_paciente left join paciente on paciente.id = profissional_paciente.paciente_fk where profissional_fk = ?;";

        PreparedStatement ps = conn.prepareStatement(sql);
        Long idProfissional = (Long) criteria.get(PacienteCriteria.PROFISSIONAL_ASSOCIADO);
        ps.setLong(1, idProfissional);

        ResultSet rs = ps.executeQuery();
        List<Paciente> pacienteList = new ArrayList<>();
        while (rs.next()) {
            Paciente paciente = new Paciente();
            paciente.setId(rs.getLong("id"));
            paciente.setNome(rs.getString("nome"));
            paciente.setNomeMae(rs.getString("nome_mae"));
            paciente.setNomePai(rs.getString("nome_pai"));
            paciente.setNumero(rs.getString("numero"));
            paciente.setNascimento(rs.getDate("nascimento"));
            paciente.setObs(rs.getString("obs"));
            paciente.setProntuario(rs.getString("prontuario"));
            paciente.setTelefone(rs.getString("telefone"));
            paciente.setRg(rs.getString("rg"));
            paciente.setCpf(rs.getString("cpf"));
            paciente.setEndereco(rs.getString("endereco"));
            paciente.setBairro(rs.getString("bairro"));
            paciente.setCidade(rs.getString("cidade"));
            paciente.setCep(rs.getString("cep"));
            paciente.setNumero(rs.getString("numero"));
            paciente.setUf(rs.getString("uf"));

            Instituicao instituicao = new Instituicao();
            instituicao.setId(rs.getLong("instituicao_fk"));
            paciente.setInstituicao(instituicao);
            pacienteList.add(paciente);
        }
        rs.close();
        ps.close();

        return pacienteList;
    }

    @Override
    public String applyCriteria(Map<Long, Object> criteria) throws Exception {
        String sql = "";

        Long instituicao_eq = (Long) criteria.get(PacienteCriteria.INSTITUICAO);
        if (instituicao_eq != null && instituicao_eq > 0) {
            sql += " AND paciente.instituicao_fk =" + instituicao_eq;
        }

        String nomeILike = (String) criteria.get(PacienteCriteria.NOME_ILIKE);
        if (nomeILike != null && !nomeILike.isEmpty()) {
            sql += " AND paciente.nome ILIKE '%" + nomeILike + "%'";
        }

        String nomeEQ = (String) criteria.get(PacienteCriteria.NOME_EQ);
        if (nomeEQ != null && !nomeEQ.isEmpty()) {
            sql += " AND paciente.nome = '" + nomeEQ + "'";
        }

        return sql;
    }

    @Override
    public Paciente parser(ResultSet rs) throws Exception {
        Paciente paciente = new Paciente();
        paciente.setId(rs.getLong("id"));
        paciente.setNome(rs.getString("nome"));
        paciente.setNomeMae(rs.getString("nome_mae"));
        paciente.setNomePai(rs.getString("nome_pai"));
        paciente.setNumero(rs.getString("numero"));
        paciente.setNascimento(rs.getDate("nascimento"));
        paciente.setObs(rs.getString("obs"));
        paciente.setProntuario(rs.getString("prontuario"));
        paciente.setTelefone(rs.getString("telefone"));
        paciente.setRg(rs.getString("rg"));
        paciente.setCpf(rs.getString("cpf"));
        paciente.setEndereco(rs.getString("endereco"));
        paciente.setBairro(rs.getString("bairro"));
        paciente.setCidade(rs.getString("cidade"));
        paciente.setCep(rs.getString("cep"));
        paciente.setNumero(rs.getString("numero"));
        paciente.setUf(rs.getString("uf"));

        Instituicao instituicao = new Instituicao();
        instituicao.setId(rs.getLong("instituicao_fk"));
        instituicao.setNome(rs.getString("inst_nome"));
        instituicao.setEmail(rs.getString("inst_email"));
        instituicao.setTelefone(rs.getString("inst_telefone"));
        instituicao.setEndereco(rs.getString("inst_endereco"));
        instituicao.setBairro(rs.getString("inst_bairro"));
        instituicao.setCep(rs.getString("inst_cep"));
        instituicao.setCidade(rs.getString("inst_cidade"));
        instituicao.setConfVersao(rs.getString("inst_conf_versao"));
        instituicao.setNumero(rs.getString("inst_numero"));
        instituicao.setUf(rs.getString("inst_uf"));

        paciente.setInstituicao(instituicao);

        return paciente;
    }

}
