package com.sim.model.dao;

import com.sim.model.base.BaseDAO;
import com.sim.model.criteria.MensagemCriteria;
import com.sim.model.entity.Mensagem;
import com.sim.model.entity.Profissional;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Joao Pedro
 */
public class MensagemDAO implements BaseDAO<Mensagem> {

    @Override
    public void create(Connection conn, Mensagem entity) throws Exception {
        String sql = "INSERT INTO mensagem(destinatario_fk, remetente_fk, conteudo, data_hora_envio)VALUES (?, ?, ?, ?) RETURNING id";

        PreparedStatement ps = conn.prepareStatement(sql);

        int i = 0;
        ps.setLong(++i, entity.getDestinatario().getId());
        ps.setLong(++i, entity.getRemetente().getId());
        ps.setString(++i, entity.getConteudo());
        ps.setTimestamp(++i, new Timestamp(entity.getDataEnvio().getTime()));
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            entity.setId(rs.getLong("id"));
        }
        rs.close();
        ps.close();
    }

    @Override
    public Mensagem readById(Connection conn, Long id) throws Exception {
        String sql = "SELECT mensagem.*, item.id item_id, item.nome item_nome FROM mensagem left join item on item.id = mensagem.item_fk WHERE mensagem.id=?";

        Mensagem mensagem = null;
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setLong(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            mensagem = new Mensagem();

            mensagem.setId(rs.getLong("id"));
            mensagem.setConteudo(rs.getString("conteudo"));

            mensagem.setDestinatario(new Profissional(rs.getLong("destinatario_fk")));
            mensagem.setRemetente(new Profissional(rs.getLong("remetente_fk")));
            mensagem.setIsLida(rs.getBoolean("is_lida"));

            mensagem.setDataEnvio(new Date(rs.getTimestamp("data_hora_envio").getTime()));
            if (rs.getObject("data_hora_leitura") != null) {
                mensagem.setDataLeitura(new Date(rs.getTimestamp("data_hora_leitura").getTime()));
            }
        }

        rs.close();
        ps.close();

        return mensagem;
    }

    @Override
    public List<Mensagem> readByCriteria(Connection conn, Map<Long, Object> criteria, Long limit, Long offset) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Mensagem> readMensagensRecebidas(Connection conn, Map<Long, Object> criteria, Long limit, Long offset, Long idProfSession) throws Exception {
        String sql = "select usuario.*, profissional.*, m.id msg_id, m.data_hora_envio msg_data_hora_envio, m.data_hora_leitura msg_data_hora_leitura, m.conteudo msg_conteudo, m.is_lida msg_is_lida from mensagem m \n"
                + "left join usuario on usuario.id = m.remetente_fk\n"
                + "left join profissional on profissional.usuario_fk = m.remetente_fk\n"
                + "where m.destinatario_fk = ? ";

        if (criteria != null) {
            sql += applyCriteria(criteria);
        }

        sql += "ORDER BY msg_data_hora_envio DESC ";

        if (limit != null && limit > 0) {
            sql += " limit " + limit;
        }
        if (offset != null && offset >= 0) {
            sql += " offset " + offset;
        }

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setLong(1, idProfSession);
        ResultSet rs = ps.executeQuery();
        List<Mensagem> mensagemList = new ArrayList<>();
        while (rs.next()) {
            Mensagem mensagem = new Mensagem();

            mensagem.setConteudo(rs.getString("msg_conteudo"));
            mensagem.setId(rs.getLong("msg_id"));
            mensagem.setDataEnvio(rs.getTimestamp("msg_data_hora_envio"));
            mensagem.setDataLeitura(rs.getTimestamp("msg_data_hora_leitura"));
            mensagem.setIsLida(rs.getBoolean("msg_is_lida"));
            
            Profissional remetente = new Profissional();
            remetente.setId(rs.getLong("id"));
            remetente.setNome(rs.getString("nome"));
            remetente.setEmail(rs.getString("email"));
            remetente.setProfissao(rs.getString("profissao"));
            
            mensagem.setRemetente(remetente);            
            
            mensagemList.add(mensagem);
        }
        rs.close();
        ps.close();

        return mensagemList;
    }

    public List<Mensagem> readMensagensEnviadas(Connection conn, Map<Long, Object> criteria, Long limit, Long offset, Long idProfSession) throws Exception {
        String sql = "select usuario.*, profissional.*, m.id msg_id, m.data_hora_envio msg_data_hora_envio, m.data_hora_leitura msg_data_hora_leitura, m.conteudo msg_conteudo, m.is_lida msg_is_lida from mensagem m \n"
                + "left join usuario on usuario.id = m.destinatario_fk\n"
                + "left join profissional on profissional.usuario_fk = m.destinatario_fk\n"
                + "where m.remetente_fk = ? ";

        if (criteria != null) {
            sql += applyCriteria(criteria);
        }

        sql += "ORDER BY msg_data_hora_envio DESC ";

        if (limit != null && limit > 0) {
            sql += " limit " + limit;
        }
        if (offset != null && offset >= 0) {
            sql += " offset " + offset;
        }

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setLong(1, idProfSession);
        ResultSet rs = ps.executeQuery();
        List<Mensagem> mensagemList = new ArrayList<>();
        while (rs.next()) {
            Mensagem mensagem = new Mensagem();

            mensagem.setConteudo(rs.getString("msg_conteudo"));
            mensagem.setId(rs.getLong("msg_id"));
            mensagem.setDataEnvio(rs.getTimestamp("msg_data_hora_envio"));
            mensagem.setDataLeitura(rs.getTimestamp("msg_data_hora_leitura"));
            mensagem.setIsLida(rs.getBoolean("msg_is_lida"));
            
            Profissional destinatario = new Profissional();
            destinatario.setId(rs.getLong("id"));
            destinatario.setNome(rs.getString("nome"));
            destinatario.setEmail(rs.getString("email"));
            destinatario.setProfissao(rs.getString("profissao"));
            
            mensagem.setDestinatario(destinatario);            
            
            mensagemList.add(mensagem);
        }
        rs.close();
        ps.close();

        return mensagemList;
    }
    
    public Integer verificaMensagem(Profissional profissional, Connection conn) throws Exception {
        String sql = "select count(*) count from mensagem where destinatario_fk=? and data_hora_leitura is null ";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setLong(1, profissional.getId());

        ResultSet rs = ps.executeQuery();
        Integer quantidade = null;
        if (rs.next()) {
            quantidade = rs.getInt("count");
        }
        rs.close();
        ps.close();

        return quantidade;
    }

    @Override
    public void update(Connection conn, Mensagem entity) throws Exception {
        String sql = "UPDATE mensagem SET data_hora_leitura=?, is_lida=? WHERE id=?";

        PreparedStatement ps = conn.prepareStatement(sql);

        int i = 0;
        ps.setTimestamp(++i, new Timestamp(entity.getDataLeitura().getTime()));
        ps.setBoolean(++i, entity.getIsLida());

        ps.setLong(++i, entity.getId());

        ps.execute();
        ps.close();
    }

    @Override
    public void delete(Connection conn, Long id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Long countByCriteria(Connection conn, Map<Long, Object> criteria, Long limit, Long offset) throws Exception {
         String sql = "SELECT count(*) count FROM mensagem WHERE 1=1 ";
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
        
        Long idProfSession = (Long) criteria.get(MensagemCriteria.USUARIO_SESSAO_ID);
        String nomeILike = (String) criteria.get(MensagemCriteria.NOME_PROF_ILIKE);
        if (nomeILike != null && !nomeILike.isEmpty()) {
            sql += " AND nome ILIKE '%" + nomeILike + "%'";
        }
        
        Boolean enviadas = (Boolean) criteria.get(MensagemCriteria.ENVIADAS);
        if(enviadas != null && enviadas){
            sql += " AND remetente_fk=" + idProfSession;
        }
        
        Boolean recebidas = (Boolean) criteria.get(MensagemCriteria.RECEBIDAS);
        if(recebidas != null && recebidas){
            sql += " AND destinatario_fk=" + idProfSession;
        }
        return sql;
    }

    @Override
    public Mensagem parser(ResultSet rs) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
