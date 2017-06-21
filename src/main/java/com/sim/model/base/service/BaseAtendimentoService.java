package com.sim.model.base.service;

import com.sim.model.base.BaseCRUDService;
import com.sim.model.entity.Atendimento;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Joao Pedro
 */
public interface BaseAtendimentoService extends BaseCRUDService<Atendimento>{
    
    public List<Atendimento> getAtendimentosPacienteAssociado(Map<Long, Object> criteria, Long idInstituicao, Long idProfissional, Long limit, Long offset) throws Exception;
    
}
