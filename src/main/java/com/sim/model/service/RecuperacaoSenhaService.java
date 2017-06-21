package com.sim.model.service;

import com.sim.model.ConnectionManager;
import com.sim.model.base.service.BaseRecuperacaoSenhaService;
import com.sim.model.dao.RecuperacaoSenhaDAO;
import com.sim.model.entity.RecuperacaoSenha;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Joao Pedro
 */
public class RecuperacaoSenhaService implements BaseRecuperacaoSenhaService {

    @Override
    public void create(RecuperacaoSenha entity) throws Exception {
        Connection conn = ConnectionManager.getInstance().getConnection();
        try {
            RecuperacaoSenhaDAO dao = new RecuperacaoSenhaDAO();
            dao.create(conn, entity);

            EmailService es = new EmailService();
            String assunto = "Recuperação de senha SIM.";
            String texto = "Olá, " + entity.getUsuario().getNome()
                    + ". Para a redefinir sua senha no sistema, acesse o link: "
                    + "https://app-sim.herokuapp.com/recuperar-senha/" + entity.getHash() + "/" + entity.getUsuario().getId();
            es.sendEmail(entity.getUsuario().getEmail(), assunto, texto);

            conn.commit();
            conn.close();
        } catch (Exception e) {
            conn.rollback();
            conn.close();
            throw e;
        }
    }

    @Override
    public RecuperacaoSenha readById(Long id) throws Exception {
        Connection conn = ConnectionManager.getInstance().getConnection();
        RecuperacaoSenha recuperacaoSenha = null;
        try {
            RecuperacaoSenhaDAO dao = new RecuperacaoSenhaDAO();
            recuperacaoSenha = dao.readById(conn, id);
            conn.close();
        } catch (Exception e) {
            conn.close();
            throw e;
        }
        return recuperacaoSenha;
    }

    @Override
    public List<RecuperacaoSenha> readByCriteria(Map<Long, Object> criteria, Long limit, Long offset) throws Exception {
        Connection conn = ConnectionManager.getInstance().getConnection();
        List<RecuperacaoSenha> recuperacaoSenha = null;
        try {
            RecuperacaoSenhaDAO dao = new RecuperacaoSenhaDAO();
            recuperacaoSenha = dao.readByCriteria(conn, criteria, limit, offset);
            conn.close();
        } catch (Exception e) {
            conn.close();
            throw e;
        }
        return recuperacaoSenha;
    }

    public RecuperacaoSenha readByHashCode(String codigo) throws Exception {
        Connection conn = ConnectionManager.getInstance().getConnection();
        RecuperacaoSenha recuperacaoSenha = null;
        try {
            RecuperacaoSenhaDAO dao = new RecuperacaoSenhaDAO();
            recuperacaoSenha = dao.readByHashCode(conn, codigo);
            conn.close();
        } catch (Exception e) {
            conn.close();
            throw e;
        }
        return recuperacaoSenha;
    }

    @Override
    public void update(RecuperacaoSenha entity) throws Exception {
        Connection conn = ConnectionManager.getInstance().getConnection();
        try {
            RecuperacaoSenhaDAO dao = new RecuperacaoSenhaDAO();
            dao.update(conn, entity);
            conn.commit();
            conn.close();
        } catch (Exception e) {
            conn.rollback();
            conn.close();
            throw e;
        }
    }

    @Override
    public void delete(Long id) throws Exception {
        Connection conn = ConnectionManager.getInstance().getConnection();
        try {
            RecuperacaoSenhaDAO dao = new RecuperacaoSenhaDAO();
            dao.delete(conn, id);
            conn.commit();
            conn.close();
        } catch (Exception e) {
            conn.rollback();
            conn.close();
            throw e;
        }
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
