package com.sim.web.utils;

import java.util.List;

/**
 *
 * @author Joao Pedro
 */
public class PaginaRetorno<E> {

    private List<E> dados;
    private Long count;

    public List<E> getDados() {
        return dados;
    }

    public void setDados(List<E> dados) {
        this.dados = dados;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
