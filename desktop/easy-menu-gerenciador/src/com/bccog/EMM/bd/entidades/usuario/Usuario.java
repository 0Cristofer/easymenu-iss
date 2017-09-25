package com.bccog.EMM.bd.entidades.usuario;

import com.bccog.EMM.bd.entidades.estabelecimento.Estabelecimento;

/**
 * Entidade utilizada a fins de autenticação
 * @author Cristofer Oswald
 * @since 03/06/2017
 */
public class Usuario {
    private String id_;
    private String token_;
    private Estabelecimento estabelecimento_;

    /**
     * Construtor que configura o id e token do usuário logado
     * @param id Id do usuário
     * @param token Token de autenticação
     */
    public Usuario(String id, String token){
        setId(id);
        setToken(token);
    }

    /**
     * @return O ID único do usuário
     */
    public String getId() {
        return id_;
    }

    /**
     * @return Token gerado na autenticação de requisições
     */
    public String getToken() {
        return token_;
    }

    /**
     * @return Estabelecimento ligado a esse usuário
     */
    public Estabelecimento getEstabelecimento() {
        return estabelecimento_;
    }

    public void setId(String id) {
        this.id_ = id;
    }

    public void setToken(String token) {
        this.token_ = token;
    }

    public void setEstabelecimento(Estabelecimento estabelecimento) {
        this.estabelecimento_ = estabelecimento;
    }
}
