package com.sim.model.base.service;

import com.sim.model.base.BaseCRUDService;
import com.sim.model.entity.Mensagem;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Joao Pedro
 */
public interface BaseMensagemService extends BaseCRUDService<Mensagem>{
    
    public List<Mensagem> readMensagensRecebidas(Map<Long, Object> criteria, Long limit, Long offset, Long idProfSession) throws Exception;
    public List<Mensagem> readMensagensEnviadas(Map<Long, Object> criteria, Long limit, Long offset, Long idProfSession) throws Exception;
}
