package com.bccog.EMM.bd.entidades.produto;

import com.google.gson.annotations.SerializedName;

/**
 * Enum tags
 * @author Bruno Cesar
 * @since 01/06/2017
 */
public enum Tag {
    HAMBURGUER("Hamburguer","/hamburguer"), BACON("Bacon","/b"),
    CHOCOLATE("Chocolate","/c"), PIMENTA("Pimenta","/p");

    private String nome_;
    @SerializedName("imgUrl")
    private String img_url_;

    Tag(String nome, String url) {
        this.nome_ = nome;
        this.img_url_ = url;
    }

    public String getNome() {
        return nome_;
    }

    public String getImgUrl() {
        return img_url_;
    }
}
