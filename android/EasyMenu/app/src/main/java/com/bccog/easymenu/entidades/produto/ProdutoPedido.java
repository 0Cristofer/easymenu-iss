package com.bccog.easymenu.entidades.produto;

import com.bccog.easymenu.entidades.EntidadeBasica;
import com.google.firebase.database.Exclude;

/**
 * Produto que é pedido pelo cliente
 * @author Cristofer Oswald
 * @since 24/07/17
 */
public class ProdutoPedido extends EntidadeBasica {
    private String pedido_;
    private Produto produto_o_;
    private String produto;
    private TamanhoProduto tamanho_;

    public ProdutoPedido(){
        super();
    }

    public ProdutoPedido(String pedido, Produto produto, TamanhoProduto tamanho){
        this.pedido_ = pedido;
        this.produto_o_ = produto;
        this.tamanho_ = tamanho;
        this.produto = produto.getId();
    }

    public String getPedido() {
        return pedido_;
    }

    public void setPedido(String pedido) {
        this.pedido_ = pedido;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    @Exclude
    public Produto getProdutoO() {
        return produto_o_;
    }

    public void setProdutoO(Produto produto) {
        this.produto_o_ = produto;
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
