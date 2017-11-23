package com.bccog.EMM.bd.entidades.cupons;
    import com.bccog.EMM.bd.EntidadeBasica;
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

    public Cupons (String nome, long timestamp, float valor){
        this.nome_ = nome;
        this.timestamp_ = timestamp;
        this.valor_ = valor;

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
}