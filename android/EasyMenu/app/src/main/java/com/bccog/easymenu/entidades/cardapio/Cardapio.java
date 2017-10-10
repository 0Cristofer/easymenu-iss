package com.bccog.easymenu.entidades.cardapio;

import com.bccog.easymenu.entidades.EntidadeBasica;
import com.bccog.easymenu.entidades.categoria.Categoria;
import com.google.firebase.database.Exclude;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe cardapio agrupa um conjunto de categorias, promoções e outros atributos
 * @author Diogo Almeida
 * @since 06/10/2017
 */
public class Cardapio extends EntidadeBasica {
    private List<Categoria> categorias_;

    private String nome_;
    private CardapioStatus status_;
    private long time_;

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

    @Exclude
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
    public String categoriasToString() {
        return "";
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
