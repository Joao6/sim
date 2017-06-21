package com.sim.model;

import com.sim.model.dao.UsuarioDAO;
import com.sim.model.entity.Usuario;
import java.sql.Connection;

/**
 *
 * @author Joao Pedro
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
//        AtendimentoService s = new AtendimentoService();
//
//        List<Atendimento> atendimentoList = s.readByCriteria(null, null, null);
//
//        for (Atendimento atendimento : atendimentoList) {
//            System.out.println(atendimento.getPaciente().getNome());
//
//        }
//
//        atendimentoList.forEach(a -> System.out.println(a.getPaciente().getNome()));
//
//        List<Atendimento> diegoList = atendimentoList.stream()
//                .filter(a -> "Diego".equals(a.getPaciente().getNome()))
//                .collect(Collectors.toList());
//
//        diegoList.forEach(a -> System.out.println(a.getPaciente().getNome()));

        UsuarioDAO dao = new UsuarioDAO();
        Connection conn = ConnectionManager.getInstance().getConnection();
        Usuario usuario = dao.readById(conn, 11L);
    }

}
