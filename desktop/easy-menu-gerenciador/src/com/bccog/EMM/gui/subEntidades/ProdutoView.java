package com.bccog.EMM.gui.subEntidades;

import com.bccog.EMM.EMM;
import com.bccog.EMM.bd.entidades.categoria.Categoria;
import com.bccog.EMM.bd.entidades.produto.Produto;
import com.bccog.EMM.bd.entidades.produto.ProdutoComTamanho;
import com.bccog.EMM.bd.entidades.produto.ProdutoPrecoUnico;
import com.bccog.EMM.bd.entidades.produto.Tag;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author Bruno Cesar
 * @since 01/06/17
 */
public class ProdutoView extends RecursiveTreeObject<ProdutoView> {
    private StringProperty nome_;
    private StringProperty preco_;
    private StringProperty tags_text_;
    private StringProperty categorias_;

    private Produto produto_;


    public ProdutoView(Produto produto) {
        produto_ = produto;

        this.nome_ = new SimpleStringProperty(produto_.getNome());

        String tags = "";
        String categorias = "";

        if(produto.getTags() != null){
            for (Tag t : produto_.getTags()) {
                tags.concat(t.getNome() + " ");
            }
        }

        for (Categoria c: EMM.getInstance().getUsuarioAtual().getEstabelecimento().getCategorias()) {
            if (c.getProdutos().contains(produto_)){
                categorias = categorias + c.getNome() + " ";
            }
        }


        this.tags_text_ = new SimpleStringProperty(tags);
        this.categorias_ = new SimpleStringProperty(categorias);


        if(produto_ instanceof ProdutoComTamanho){
            String precos = ("P: " + ((ProdutoComTamanho) produto).getPrecoP() + "$ | " +
                             "M: " + ((ProdutoComTamanho) produto).getPrecoM() + "$ | " +
                             "G: " + ((ProdutoComTamanho) produto).getPrecoG() + "$");

            this.preco_ = new SimpleStringProperty(precos);

        } else {
            this.preco_ = new SimpleStringProperty(Float.toString(
                    ((ProdutoPrecoUnico)produto_).getPreco()));
        }
    }


    public Produto getProduto_() {
        return produto_;
    }

    public void setProduto_(Produto produto_) {
        this.produto_ = produto_;
    }

    public StringProperty nomeProperty() {
        return nome_;
    }


    public StringProperty precoProperty() {
        return preco_;
    }


    public StringProperty tagsTextProperty() {
        return tags_text_;
    }


    public StringProperty cardapioProperty() {
        return categorias_;
    }
}
