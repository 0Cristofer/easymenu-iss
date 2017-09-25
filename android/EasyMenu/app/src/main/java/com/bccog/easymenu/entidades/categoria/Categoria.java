package com.bccog.easymenu.entidades.categoria;

import com.bccog.easymenu.entidades.EntidadeBasica;
import com.bccog.easymenu.entidades.produto.Produto;
import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe Categoria organiza produtos nos cardápios, cada categoria inclui um conjunto de adicionais
 * @author Bruno Cesar
 * @since 03/06/2017
 */
public class Categoria extends EntidadeBasica {
    private List<Adicional> adiconais_;
    private List<Produto> produtos_;

    private String nome_;
    private boolean adiconais_tipo_;

    public Categoria(String nome, boolean adiconais_tipo) {
        this.nome_ = nome;
        this.adiconais_tipo_ = adiconais_tipo;
        this.produtos_ = new ArrayList<>();
    }

    public String getNome() {
        return nome_;
    }

    @Exclude
    public List<Adicional> getAdiconais() {
        return adiconais_;
    }

    public boolean isAdiconaisTipo() {
        return adiconais_tipo_;
    }

    @Exclude
    public List<Produto> getProdutos() {
        return produtos_;
    }

    public void setNome(String nome) {
        this.nome_ = nome;
    }

    public void setAdiconaisTipo(boolean adiconais_tipo) {
        this.adiconais_tipo_ = adiconais_tipo;
    }

    public void addProduto(Produto produto){
        produtos_.add(produto);
    }

    public void addAdicional(Adicional adicional){
        adiconais_.add(adicional);
    }

    public void setProdutos(List<Produto> produtos){
        produtos_ = produtos;
    }

    @Override
    public String toString() {
        return "Categoria: " + getNome();
    }
}
