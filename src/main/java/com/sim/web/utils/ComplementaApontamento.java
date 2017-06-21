package com.sim.web.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Joao Pedro
 */
public class ComplementaApontamento {

    public static String complementarApontamento(String apontamentoAnterior, Date dataAnterior, String apontamentoComp) {
        String novoApontamento;
        Date dataAtual = new Date();
        SimpleDateFormat df;
        df = new SimpleDateFormat("dd/MM/yyyy");
        String dataFormat = df.format(dataAtual);
        df = new SimpleDateFormat("yyyy-MM-dd");
        String verificaApontamento = df.format(dataAnterior);
        if(apontamentoAnterior.contains(verificaApontamento)){
            novoApontamento = apontamentoAnterior + "\n" + dataFormat + " : " + apontamentoComp;
        }else{
            novoApontamento = dataAnterior + " : " + apontamentoAnterior + "\n" + dataFormat + " : " + apontamentoComp;
        }
        
        return novoApontamento;
    }
}
