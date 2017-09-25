package com.bccog.easymenu.entidades.produto;

import com.bccog.easymenu.entidades.EntidadeBasica;

/**
 * Produto que é pedido pelo cliente
 * @author Cristofer Oswald
 * @since 24/07/17
 */
public class ProdutoPedido extends EntidadeBasica {
    private String pedido_;
    private Produto produto_;
    private TamanhoProduto tamanho_;

    public ProdutoPedido(){
        super();
    }

    public ProdutoPedido(String pedido, Produto produto, TamanhoProduto tamanho){
        this.pedido_ = pedido;
        this.produto_ = produto;
        this.tamanho_ = tamanho;
    }

    public String getPedido() {
        return pedido_;
    }

    public void setPedido(String pedido) {
        this.pedido_ = pedido;
    }

    public Produto getProduto() {
        return produto_;
    }

    public void setProduto(Produto produto) {
        this.produto_ = produto;
    }

    public TamanhoProduto getTamanho() {
        return tamanho_;
    }

    public void setTamanho(TamanhoProduto tamanho) {
        this.tamanho_ = tamanho;
    }

    public enum TamanhoProduto{
        UNICO,
        PEQUENO,
        MEDIO,
        GRANDE
    }
}
