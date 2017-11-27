package com.bccog.EMM.bd.entidades.produto;

import com.bccog.EMM.bd.EntidadeBasica;
import com.google.gson.annotations.SerializedName;

/**
 * Produto que é pedido pelo cliente
 * @author Cristofer Oswald
 * @since 24/07/17
 */
public class ProdutoPedido extends EntidadeBasica {
    @SerializedName("pedido")
    private String pedido_;
    private transient Produto produto_;
    @SerializedName("produto")
    private String produto_id_;
    @SerializedName("tamanho")
    private TamanhoProduto tamanho_;

    public ProdutoPedido(){

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

    public String getProdutoId() {
        return produto_id_;
    }

    public void setProdutoId(String produto_id_) {
        this.produto_id_ = produto_id_;
    }

    public TamanhoProduto getTamanho() {
        return tamanho_;
    }

    public void setTamanho(TamanhoProduto tamanho) {
        this.tamanho_ = tamanho;
    }

    public enum TamanhoProduto{
        UNICO("unico"),
        PEQUENO("pequeno"),
        MEDIO("medio"),
        GRANDE("grade");

        @SerializedName("tamanho")
        private String tamanho_;

        TamanhoProduto(String tamanho){
            this.tamanho_ = tamanho;
        }

        public String getTamanho(){
            return tamanho_;
        }
    }
}
