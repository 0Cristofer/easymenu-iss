package com.bccog.easymenu.entidades.produto;

import com.bccog.easymenu.entidades.EntidadeBasica;

/**
 * Representa um produto básico
 * @author Cristofer Oswald
 * @author Bruno Cesar
 * @since 01/06/2017
 */
public abstract class Produto extends EntidadeBasica {
    private String nome_;
    private String descricao_;
    private Tag[] tags_;
    private String foto_;

    public Produto(){

    }

    protected Produto(String nome, String descricao, Tag[] tags, String foto){
        setNome(nome);
        setDescricao(descricao);
        setTags(tags);
        setFotos(foto);
    }

    public String getNome() {
        return nome_;
    }

    public String getDescricao() {
        return descricao_;
    }

    public Tag[] getTags() {
        return tags_;
    }

    public String getFotos() {
        return foto_;
    }

    public void setNome(String nome) {
        this.nome_ = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao_ = descricao;
    }

    public void setTags(Tag[] tags) {
        this.tags_ = tags;
    }

    public void setFotos(String foto) {
        this.foto_ = foto;
    }
}

