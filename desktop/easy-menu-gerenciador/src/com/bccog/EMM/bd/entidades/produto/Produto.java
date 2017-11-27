package com.bccog.EMM.bd.entidades.produto;

import com.bccog.EMM.bd.EntidadeBasica;
import com.google.gson.annotations.SerializedName;

/**
 * Representa um produto básico
 * @author Cristofer Oswald
 * @author Bruno Cesar
 * @since 01/06/2017
 */
public abstract class Produto extends EntidadeBasica {
    @SerializedName("nome")
    private String nome_;
    @SerializedName("descricao")
    private String descricao_;
    @SerializedName("tags")
    private Tag[] tags_;
    @SerializedName("fotos")
    private String foto_;

    protected Produto(String nome, String descricao, Tag[] tags, String foto){
        setNome(nome);
        setDescricao(descricao);
        setTags(tags);
        setFoto(foto);
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

    public String getFoto() {
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

    public void setFoto(String foto) {
        this.foto_ = foto;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Produto) && (this.getId().equals(((Produto) obj).getId()));
    }
}
