package com.bccog.EMM.bd.entidades.cupons;
    import com.bccog.EMM.bd.EntidadeBasica;
    import com.bccog.EMM.bd.entidades.categoria.Categoria;
    import com.google.gson.annotations.SerializedName;


/**
 * Classe Cupons
 * @author Guilherme Quental
 * @since 23/11/2017
 */

public class Cupons extends EntidadeBasica{

    @SerializedName("nome")
    private String nome_;
    @SerializedName("timestamp")
    private long timestamp_;
    @SerializedName("valor")
    private float valor_;
    private transient Categoria categoria_;

    public Cupons (String nome, long timestamp, float valor, Categoria categoria){
        this.nome_ = nome;
        this.timestamp_ = timestamp;
        this.valor_ = valor;
        this.categoria_ = categoria;

    }

    public void setNome_(String nome_) {
        this.nome_ = nome_;
    }

    public void setTimestamp_(long timestamp_) {
        this.timestamp_ = timestamp_;
    }

    public void setValor_(float valor_) {
        this.valor_ = valor_;
    }

    public String getNome_() {
        return nome_;
    }

    public long getTimestamp_() {
        return timestamp_;
    }

    public float getValor_() {
        return valor_;
    }

    public Categoria getCategoria_() {
        return categoria_;
    }

    public void setCategoria_(Categoria categoria_) {
        this.categoria_ = categoria_;
    }
}