package com.sim.model.entity;

/**
 *
 * @author Joao Pedro
 */
public class Instituicao extends Usuario{
    
    private String confVersao;
    private String telefone;
    private String endereco;
    private String numero;
    private String bairro;
    private String cidade;
    private String cep;
    private String uf;    


    public String getConfVersao() {
        return confVersao;
    }

    public void setConfVersao(String confVersao) {
        this.confVersao = confVersao;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }   

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }
}
