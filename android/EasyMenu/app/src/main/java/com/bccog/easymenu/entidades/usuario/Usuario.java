package com.bccog.easymenu.entidades.usuario;

import com.bccog.easymenu.entidades.cliente.Cliente;

/**
 * Entidade utilizada a fins de autenticação
 * @author Cristofer Oswald
 * @since 03/06/2017
 */
public class Usuario {
    private String id_;
    private Cliente cliente_;

    public Usuario(){
        super();
    }

    /**
     * Construtor que configura o id e token do usuário logado
     * @param id Id do usuário
     * @param token Token de autenticação
     */
    public Usuario(String id){
        setId(id);
    }

    /**
     * @return O ID único do usuário
     */
    public String getId() {
        return id_;
    }

    /**
     * @return Estabelecimento ligado a esse usuário
     */
    public Cliente getCliente() {
        return cliente_;
    }

    public void setId(String id) {
        this.id_ = id;
    }

    public void setCliente(Cliente cliente) {
        this.cliente_ = cliente;
    }
}
