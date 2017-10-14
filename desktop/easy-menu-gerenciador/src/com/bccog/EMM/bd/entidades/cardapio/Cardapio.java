package com.bccog.EMM.bd.entidades.cardapio;

import com.bccog.EMM.bd.EntidadeBasica;
import com.bccog.EMM.bd.entidades.categoria.Categoria;
import com.google.gson.annotations.SerializedName;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe cardapio agrupa um conjunto de categorias, promoções e outros atributos
 * @author Diogo Almeida
 * @since 06/10/2017
 */
public class Cardapio extends EntidadeBasica {
    private transient List<Categoria> categorias_;

    @SerializedName("nome")
    private String nome_;
    @SerializedName("status")
    private CardapioStatus status_;
    @SerializedName("time")
    private long time_;
    //TODO promoção

    public Cardapio(String nome) {
        this.nome_ = nome;
        status_ = CardapioStatus.RECUSADO;
        categorias_ = new ArrayList<>();
        time_ = DateTime.now().getMillis();
    }

    public String getNome() {
        return nome_;
    }

    public void setNome(String nome){
        this.nome_ = nome;
    }

    public CardapioStatus getStatus() {
        return status_;
    }

    public List<Categoria> getCategorias() {
        if(categorias_ == null){
            categorias_ = new ArrayList<>();
        }
        return categorias_;
    }

    public long getTime() {
        return time_;
    }

    public void addCategoria(Categoria categoria){
        if(categorias_ == null){
            categorias_ = new ArrayList<>();
        }
        categorias_.add(categoria);
    }

    public void setCategorias(List<Categoria> categorias){
        categorias_ = categorias;
    }

    /**
     * @return Uma string com o nome de todas as categorias no cardápio
     */
    public String categoriasToString(){
        String text = "";

        if(categorias_ != null) {
            for (Categoria c : categorias_) {
                text = text.concat(c.getNome() + " ");
            }
        }

        return text;
    }

    public DateTime getDateTime(){
        return new DateTime(time_);
    }

    /**
     * @return Uma string resumo do cardápio
     */
    @Override
    public String toString(){
        String text = nome_ + " : " + status_ + "\n";
        text = text.concat("Categorias: ") + categoriasToString();
        text = text.concat("\n Ultma alteração em: " + getDateTime().toString());
        return text;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Cardapio) && super.equals(obj);
    }
}
