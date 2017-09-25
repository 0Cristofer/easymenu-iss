package com.bccog.easymenu.entidades.embutido;

/**
 * Classe embutida que contém os dados necssários para o delivery
 * @author Cristofer Oswald
 * @since 06/06/2017
 */
public class Delivery {
    private float preco_min_;
    private float taxa_entrega_;
    private float frete_gratis_;
    private String horario_entrega_;
    private String media_demora_;

    public Delivery(){

    }

    public Delivery(float preco_min, float taxa_entrega, float frete_gratis,
                    String horario_entrega, String media_demora) {
        setPrecoMin(preco_min);
        setTaxaEntrega(taxa_entrega);
        setFreteGratis(frete_gratis);
        setHorarioEntrega(horario_entrega);
        setMediaDemora(media_demora);
    }

    public float getPrecoMin() {
        return preco_min_;
    }

    public void setPrecoMin(float preco_min) {
        this.preco_min_ = preco_min;
    }

    public float getTaxaEntrega() {
        return taxa_entrega_;
    }

    public void setTaxaEntrega(float taxa_entrega) {
        this.taxa_entrega_ = taxa_entrega;
    }

    public float getFreteGratis() {
        return frete_gratis_;
    }

    public void setFreteGratis(float frete_gratis) {
        this.frete_gratis_ = frete_gratis;
    }

    public String getHorarioEntrega() {
        return horario_entrega_;
    }

    public void setHorarioEntrega(String horario_entrega) {
        this.horario_entrega_ = horario_entrega;
    }

    public String getMediaDemora() {
        return media_demora_;
    }

    public void setMediaDemora(String media_demora) {
        this.media_demora_ = media_demora;
    }

    @Override
    public String toString() {
        return "Delivery:\n" + "preço min: " + preco_min_ + "\ntaxa entrega: " + taxa_entrega_ + "\nfrete gratis: " + frete_gratis_
                + "\nhorario entrega: " + horario_entrega_ + "\nmedia demora: " + media_demora_;
    }
}
