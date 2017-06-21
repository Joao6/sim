package com.sim.model.entity;

import java.security.MessageDigest;
import com.sim.model.base.BaseEntity;

/**
 *
 * @author Joao Pedro
 */
public abstract class Usuario extends BaseEntity {

    private static String SALT_PREFIX_SENHA = "W216hgyDJS%$#bn009!!!@#";
    private static String SALT_SUFIX_SENHA = "$%j7U9E123DFssd782";

    private String nome;
    private String email;
    private String senha;
    
    public Usuario() {
    }

    public Usuario(Long id) {
        super(id);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setSenhaAsPlainText(String senhaAsPlainText) throws Exception {
        this.senha = encodeSenha(senhaAsPlainText);
    }

    public static String encodeSenha(String senhaAsPlainText) throws Exception {
        MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
        byte messageDigest[] = algorithm.digest((SALT_PREFIX_SENHA + senhaAsPlainText + SALT_SUFIX_SENHA).getBytes("UTF-8"));
        StringBuilder hexString = new StringBuilder();
        for (byte b : messageDigest) {
            hexString.append(String.format("%02X", 0xFF & b));
        }
        return hexString.toString();
    }
}
