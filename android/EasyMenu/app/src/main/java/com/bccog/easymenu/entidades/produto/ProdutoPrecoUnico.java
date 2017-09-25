package com.bccog.easymenu.entidades.produto;

/**
 * Classe produtoPrecoUnico representa um produto com apenas um preço
 * @author Bruno Cesar
 * @since 03/06/2017
 */
public class ProdutoPrecoUnico extends Produto {
    private float preco_;

    public ProdutoPrecoUnico(){
        super();
    }

    public ProdutoPrecoUnico(String nome, String descricao, Tag[] tags, String foto, float preco) {
        super(nome, descricao, tags, foto);
        this.preco_ = preco;
    }

    public float getPreco() {
        return preco_;
    }

    public void setPreco(float preco) {
        this.preco_ = preco;
    }

    public String toString(){
        String textTags = "";

        if (getTags() != null) {
            for (Tag t : getTags()) {
                textTags = textTags.concat(t.getNome() + ", ");
            }
        }

        return getNome() + " : " + textTags + "\nValor = " + getPreco() +
                "\nimage_url: " + getFotos() + "\nDescricao: " + getDescricao();
    }
}
