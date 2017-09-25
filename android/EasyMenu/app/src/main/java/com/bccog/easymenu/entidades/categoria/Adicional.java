package com.bccog.easymenu.entidades.categoria;

/**
 * Classe adicionais representa os adicionais disponíveis para alguma categoria de produtos
 * Exemplo: Objeto "bordas" é um adicional para a categoria "Pizza"
 * @author Bruno Cesar
 * @since 03/06/2017
 */
public class Adicional {
    private String nome_;
    private boolean status_;
    private float preco_;

    public Adicional(String nome, float preco) {
        this.nome_ = nome;
        this.preco_ = preco;
    }

    public String getNome() {
        return nome_;
    }

    public void setNome(String nome) {
        this.nome_ = nome;
    }

    public boolean isStatus() {
        return status_;
    }

    public void setStatus(boolean status) {
        this.status_ = status;
    }

    public float getPreco() {
        return preco_;
    }

    public void setPreco(float preco) {
        this.preco_ = preco;
    }
}
