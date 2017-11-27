package com.bccog.EMM.bd.entidades.categoria;

import com.bccog.EMM.bd.EntidadeBasica;
import com.bccog.EMM.bd.entidades.produto.Produto;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe Categoria organiza produtos nos cardápios
 * @author Guilherme Quental
 * @since 06/10/2017
 */
public class Categoria extends EntidadeBasica{
    private transient List<Adicional> adicionais_;
    private transient List<Produto> produtos_;

    @SerializedName("nome")
    private String nome_;
    @SerializedName("adicionaisTipo")
    private boolean adicionais_tipo_;

    public Categoria(String nome, boolean adiconais_tipo) {
        this.nome_ = nome;
        this.adicionais_tipo_ = adiconais_tipo;
        this.produtos_ = new ArrayList<>();
    }

    public String getNome() {
        return nome_;
    }

    public List<Adicional> getAdiconais() {
        return adicionais_;
    }

    public boolean isAdicionaisTipo() {
        return adicionais_tipo_;
    }

    public List<Produto> getProdutos() {
        if(produtos_ == null){
            produtos_ = new ArrayList<>();
        }
        return produtos_;
    }

    public void setNome(String nome) {
        this.nome_ = nome;
    }

    public void setAdicionaisTipo(boolean adicionais_tipo) {
        this.adicionais_tipo_ = adicionais_tipo;
    }

    public void addProduto(Produto produto){
        if(produtos_ == null){
            produtos_ = new ArrayList<>();
        }
        produtos_.add(produto);
    }

    public void addAdicional(Adicional adicional){
        if(adicionais_ == null){
            adicionais_ = new ArrayList<>();
        }
        adicionais_.add(adicional);
    }

    public void setProdutos(List<Produto> produtos){
        produtos_ = produtos;
    }

    @Override
    public String toString() {
        return getNome();
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Categoria) && (this.getId().equals(((Categoria) obj).getId()));
    }
}
