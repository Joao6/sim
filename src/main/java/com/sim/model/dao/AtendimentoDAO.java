package com.sim.model.dao;

import com.sim.model.base.BaseDAO;
import com.sim.model.criteria.AtendimentoCriteria;
import com.sim.model.entity.Atendimento;
import com.sim.model.entity.Instituicao;
import com.sim.model.entity.Paciente;
import com.sim.model.entity.Profissional;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Joao Pedro
 */
public class AtendimentoDAO implements BaseDAO<Atendimento> {

    @Override
    public void create(Connection conn, Atendimento entity) throws Exception {
        String sql = "INSERT INTO atendimento(paciente_fk, profissional_fk, data_apontamento, apontamento)VALUES (?, ?, ?, ?) RETURNING id;";
        PreparedStatement ps = conn.prepareStatement(sql);
        int i = 0;
        ps.setLong(++i, entity.getPaciente().getId());
        ps.setLong(++i, entity.getProfissional().getId());

        if (entity.getDataApontamento() != null) {
            java.sql.Date dataSql = new java.sql.Date(entity.getDataApontamento().getTime());
            ps.setDate(++i, dataSql);
        } else {
            Date data = new Date();
            java.sql.Date dataSql = new java.sql.Date(data.getTime());
            ps.setDate(++i, dataSql);
        }
        ps.setString(++i, entity.getApontamento());

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            entity.setId(rs.getLong("id"));
        }
        rs.close();
        ps.close();
    }

    @Override
    public Atendimento readById(Connection conn, Long id) throws Exception {
        String sql = "SELECT atendimento.*, paciente.id paciente_id, paciente.instituicao_fk paciente_instituicao, paciente.nome paciente_nome, paciente.nascimento paciente_nascimento, paciente.cpf paciente_cpf, paciente.rg paciente_rg, paciente.nome_pai paciente_nome_pai, paciente.nome_mae paciente_nome_mae, paciente.endereco paciente_endereco, paciente.numero paciente_numero, paciente.bairro paciente_bairro, paciente.cidade paciente_cidade, paciente.uf paciente_uf, paciente.cep paciente_cep, paciente.telefone paciente_telefone, paciente.obs paciente_obs,\n"
                + "profissional.usuario_fk prof_id, usuario.nome prof_nome, usuario.email prof_email, profissional.profissao prof_profissao, profissional.registro_de_conselho prof_reg_conselho,\n"
                + "profissional.cpf prof_cpf, profissional.rg prof_rg, profissional.endereco prof_endereco, profissional.numero prof_numero, profissional.bairro prof_bairro,\n"
                + "profissional.cidade prof_cidade, profissional.uf prof_uf, profissional.cep prof_cep, profissional.telefone prof_telefone, profissional.instituicao_fk prof_instituicao \n"
                + "FROM atendimento left join paciente on paciente.id = atendimento.paciente_fk left join profissional on profissional.usuario_fk = atendimento.profissional_fk left join usuario on usuario.id = profissional.usuario_fk where atendimento.id = ?";
        Atendimento atendimento = null;
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setLong(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            atendimento = this.parser(rs);
        }
        rs.close();
        ps.close();
        return atendimento;
    }

    @Override
    public List<Atendimento> readByCriteria(Connection conn, Map<Long, Object> criteria, Long limit, Long offset) throws Exception {
        String sql = "SELECT atendimento.*, paciente.id paciente_id, paciente.instituicao_fk paciente_instituicao, paciente.nome paciente_nome, paciente.nascimento paciente_nascimento, paciente.cpf paciente_cpf, paciente.rg paciente_rg, paciente.nome_pai paciente_nome_pai, paciente.nome_mae paciente_nome_mae, paciente.endereco paciente_endereco, paciente.numero paciente_numero, paciente.bairro paciente_bairro, paciente.cidade paciente_cidade, paciente.uf paciente_uf, paciente.cep paciente_cep, paciente.telefone paciente_telefone, paciente.obs paciente_obs,\n"
                + "profissional.usuario_fk prof_id, usuario.nome prof_nome, usuario.email prof_email, profissional.profissao prof_profissao, profissional.registro_de_conselho prof_reg_conselho,\n"
                + "profissional.cpf prof_cpf, profissional.rg prof_rg, profissional.endereco prof_endereco, profissional.numero prof_numero, profissional.bairro prof_bairro,\n"
                + "profissional.cidade prof_cidade, profissional.uf prof_uf, profissional.cep prof_cep, profissional.telefone prof_telefone, profissional.instituicao_fk prof_instituicao \n"
                + "FROM atendimento left join paciente on paciente.id = atendimento.paciente_fk left join profissional on profissional.usuario_fk = atendimento.profissional_fk left join usuario on usuario.id = profissional.usuario_fk where 1=1 ";

        if (criteria != null) {
            sql += applyCriteria(criteria);
        }

        sql += " ORDER BY data_apontamento DESC";

        if (limit != null && limit > 0) {
            sql += " limit " + limit;
        }
        if (offset != null && offset >= 0) {
            sql += " offset " + offset;
        }

        Statement s = conn.createStatement();
        ResultSet rs = s.executeQuery(sql);
        List<Atendimento> atendimentoList = new ArrayList<>();
        while (rs.next()) {
            Atendimento atendimento = this.parser(rs);
            atendimentoList.add(atendimento);
        }
        return atendimentoList;
    }

    @Override
    public void update(Connection conn, Atendimento entity) throws Exception {
        String sql = "UPDATE atendimento SET paciente_fk=?, profissional_fk=?, data_apontamento=?, apontamento=? WHERE id = ?;";
        PreparedStatement ps = conn.prepareStatement(sql);
        int i = 0;
        ps.setLong(++i, entity.getPaciente().getId());
        ps.setLong(++i, entity.getProfissional().getId());
        if (entity.getDataApontamento() != null) {
            java.sql.Date dataSql = new java.sql.Date(entity.getDataApontamento().getTime());
            ps.setDate(++i, dataSql);
        } else {
            ps.setNull(++i, Types.DATE);
        }
        ps.setString(++i, entity.getApontamento());
        ps.setLong(++i, entity.getId());
        ps.execute();
        ps.close();
    }

    @Override
    public void delete(Connection conn, Long id) throws Exception {
        String sql = "DELETE FROM atendimento WHERE id=? ";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setLong(1, id);
        ps.execute();
        ps.close();
    }

    @Override
    public Long countByCriteria(Connection conn, Map<Long, Object> criteria, Long limit, Long offset) throws Exception {
        String sql = "SELECT count(*) count FROM atendimento LEFT JOIN paciente ON atendimento.paciente_fk = paciente.id LEFT JOIN profissional ON atendimento.profissional_fk = profissional.usuario_fk LEFT JOIN usuario ON usuario.id = profissional.usuario_fk WHERE 1=1 ";
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

        String nomePacienteILike = (String) criteria.get(AtendimentoCriteria.PACIENTE_NOME);
        if (nomePacienteILike != null && !nomePacienteILike.isEmpty()) {
            sql += " AND paciente.nome ILIKE '%" + nomePacienteILike + "%'";
        }

        String nomeProfissionalILike = (String) criteria.get(AtendimentoCriteria.PROFISSIONAL_NOME);
        if (nomeProfissionalILike != null && !nomeProfissionalILike.isEmpty()) {
            sql += " AND usuario.nome ILIKE '%" + nomeProfissionalILike + "%'";
        }

        Long profissionalId = (Long) criteria.get(AtendimentoCriteria.PROFISSIONAL_ID);
        if (profissionalId != null && profissionalId > 0) {
            sql += " AND atendimento.profissional_fk =" + profissionalId;
        }

        String dataAtendimento = (String) criteria.get(AtendimentoCriteria.DATA);
        if (dataAtendimento != null && !dataAtendimento.isEmpty()) {
            sql += " AND atendimento.data_apontamento = '" + dataAtendimento + "'";
        }

        Long instituicao = (Long) criteria.get(AtendimentoCriteria.INSTITUICAO_ID);
        if (instituicao != null && instituicao > 0) {
            sql += " AND profissional.instituicao_fk = '" + instituicao + "'";
        }
        
        Boolean associado = (Boolean) criteria.get(AtendimentoCriteria.PACIENTE_ASSOCIADO);
        if(associado != null && associado == true){
            sql += " AND paciente_fk in (select paciente_fk from profissional_paciente where profissional_fk = 29)";
        }

        return sql;
    }

    @Override
    public Atendimento parser(ResultSet rs) throws Exception {
        Atendimento atendimento = new Atendimento();
        atendimento.setId(rs.getLong("id"));
        atendimento.setApontamento(rs.getString("apontamento"));

        atendimento.setDataApontamento(rs.getDate("data_apontamento"));

        Paciente paciente = new Paciente();
        paciente.setId(rs.getLong("paciente_id"));
        paciente.setNome(rs.getString("paciente_nome"));
        paciente.setNascimento(rs.getDate("paciente_nascimento"));
        paciente.setCpf(rs.getString("paciente_cpf"));
        paciente.setRg(rs.getString("paciente_rg"));
        paciente.setNomePai(rs.getString("paciente_nome_pai"));
        paciente.setNomeMae(rs.getString("paciente_nome_mae"));
        paciente.setEndereco(rs.getString("paciente_endereco"));
        paciente.setNumero(rs.getString("paciente_numero"));
        paciente.setBairro(rs.getString("paciente_bairro"));
        paciente.setCidade(rs.getString("paciente_cidade"));
        paciente.setUf(rs.getString("paciente_uf"));
        paciente.setCep(rs.getString("paciente_cep"));
        paciente.setTelefone(rs.getString("paciente_telefone"));
        paciente.setObs(rs.getString("paciente_obs"));

        atendimento.setPaciente(paciente);

        Profissional profissional = new Profissional();
        profissional.setId(rs.getLong("prof_id"));
        profissional.setNome(rs.getString("prof_nome"));
        profissional.setEmail(rs.getString("prof_email"));
        profissional.setProfissao(rs.getString("prof_profissao"));
        profissional.setRegistroDeConselho(rs.getString("prof_reg_conselho"));
        profissional.setCpf(rs.getString("prof_cpf"));
        profissional.setRg(rs.getString("prof_rg"));
        profissional.setEndereco(rs.getString("prof_endereco"));
        profissional.setNumero(rs.getString("prof_numero"));
        profissional.setBairro(rs.getString("prof_bairro"));
        profissional.setCidade(rs.getString("prof_cidade"));
        profissional.setUf(rs.getString("prof_uf"));
        profissional.setTelefone(rs.getString("prof_telefone"));

        Instituicao instituicao = new Instituicao();
        instituicao.setId(rs.getLong("prof_instituicao"));

        profissional.setInstituicao(instituicao);

        atendimento.setProfissional(profissional);

        return atendimento;
    }

    public List<Atendimento> getAtendimentosPacienteAssociado(Connection conn,
            Map<Long, Object> criteria,
            Long idInstituicao,
            Long idProfissional,
            Long limit,
            Long offset) throws Exception {
        String sql = "select atendimento.*, paciente.id paciente_id, paciente.instituicao_fk paciente_instituicao, \n"
                + "paciente.nome paciente_nome, paciente.nascimento paciente_nascimento, \n"
                + "paciente.cpf paciente_cpf, paciente.rg paciente_rg, paciente.nome_pai paciente_nome_pai, \n"
                + "paciente.nome_mae paciente_nome_mae, paciente.endereco paciente_endereco, \n"
                + "paciente.numero paciente_numero, paciente.bairro paciente_bairro, \n"
                + "paciente.cidade paciente_cidade, paciente.uf paciente_uf, \n"
                + "paciente.cep paciente_cep, paciente.telefone paciente_telefone, \n"
                + "paciente.obs paciente_obs, profissional.usuario_fk prof_id, \n"
                + "usuario.nome prof_nome, usuario.email prof_email, \n"
                + "profissional.profissao prof_profissao, profissional.registro_de_conselho prof_reg_conselho,\n"
                + "profissional.cpf prof_cpf, profissional.rg prof_rg, profissional.endereco prof_endereco, \n"
                + "profissional.numero prof_numero, profissional.bairro prof_bairro, profissional.cidade prof_cidade, \n"
                + "profissional.uf prof_uf, profissional.cep prof_cep, profissional.telefone prof_telefone, \n"
                + "profissional.instituicao_fk prof_instituicao from atendimento \n"
                + "left join profissional on profissional.usuario_fk = atendimento.profissional_fk\n"
                + "left join instituicao on instituicao.usuario_fk = profissional.instituicao_fk\n"
                + "left join paciente on paciente.id = atendimento.paciente_fk\n"
                + "left join usuario on usuario.id = profissional.usuario_fk\n"
                + "where instituicao.usuario_fk = ? and paciente_fk in\n"
                + "(select paciente_fk from profissional_paciente where profissional_fk = ?) ";

        if (criteria != null) {
            sql += applyCriteria(criteria);
        }

        sql += " ORDER BY data_apontamento DESC";

        if (limit != null && limit > 0) {
            sql += " limit " + limit;
        }
        if (offset != null && offset >= 0) {
            sql += " offset " + offset;
        }

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setLong(1, idInstituicao);
        ps.setLong(2, idProfissional);

        List<Atendimento> atendimentoList = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Atendimento atendimento = this.parser(rs);
            atendimentoList.add(atendimento);
        }
        return atendimentoList;
    }

}
