package com.bccog.easymenu.entidades.estabelecimento;

import com.bccog.easymenu.entidades.EntidadeBasica;
import com.bccog.easymenu.entidades.cardapio.Cardapio;
import com.bccog.easymenu.entidades.categoria.Categoria;
import com.bccog.easymenu.entidades.embutido.Delivery;
import com.bccog.easymenu.entidades.embutido.Endereco;
import com.bccog.easymenu.entidades.embutido.HorarioAtendimento;
import com.bccog.easymenu.entidades.produto.Produto;
import com.bccog.easymenu.entidades.review.Review;
import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstrata que contém os dados básicos de um estabelecimento
 * @author Cristofer Oswald
 * @since 25/05/2017
 */
public abstract class Estabelecimento extends EntidadeBasica {
    private List<Review> reviews_;
    private List<Cardapio> cardapios_;
    private List<Categoria> categorias_;
    private List<Produto> produtos_;

    private String nome_;
    private Endereco endereco_;
    private HorarioAtendimento horario_atendimento_;
    private float nota_;
    private String[] fotos_;
    private Delivery delivery_;
    private boolean so_delivery_;

    public Estabelecimento(){

    }

    public Estabelecimento(String nome, Endereco endereco, HorarioAtendimento horario_atendimento, float nota) {
        cardapios_ = new ArrayList<>();
        categorias_ = new ArrayList<>();
        produtos_ = new ArrayList<>();
        setNome(nome);
        setEndereco(endereco);
        setHorarioAtendimento(horario_atendimento);
        setNota(nota);
        setReviews(new ArrayList<Review>());
        setFotos(null);
        setDelivery(null);
        setSoDelivery(false);
    }

    public Estabelecimento(String nome, Endereco endereco, HorarioAtendimento horario_atendimento,
                           float nota, Delivery delivery, boolean so_delivery) {
        cardapios_ = new ArrayList<>();
        categorias_ = new ArrayList<>();
        produtos_ = new ArrayList<>();
        setNome(nome);
        setEndereco(endereco);
        setHorarioAtendimento(horario_atendimento);
        setNota(nota);
        setReviews(new ArrayList<Review>());
        setFotos(null);
        setDelivery(delivery);
        setSoDelivery(so_delivery);
    }

    public String getNome() {
        return nome_;
    }

    public void setNome(String nome) {
        this.nome_ = nome;
    }

    public Endereco getEndereco() {
        return endereco_;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco_ = endereco;
    }

    public HorarioAtendimento getHorarioAtendimento() {
        return horario_atendimento_;
    }

    public void setHorarioAtendimento(HorarioAtendimento horario_atendimento) {
        this.horario_atendimento_ = horario_atendimento;
    }

    public float getNota() {
        return nota_;
    }

    public void setNota(float nota) {
        this.nota_ = nota;
    }

    @Exclude
    public List<Review> getReviews() {
        return reviews_;
    }

    protected void setReviews(List<Review> reviews){
        this.reviews_ = reviews;
    }

    public String[] getFotos() {
        return fotos_;
    }

    public void setFotos(String[] fotos) {
        this.fotos_ = fotos;
    }

    public Delivery getDelivery() {
        return delivery_;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery_ = delivery;
    }

    public boolean isSoDelivery() {
        return so_delivery_;
    }

    public void setSoDelivery(boolean so_delivery) {
        this.so_delivery_ = so_delivery;
    }

    public void setCardapios(List<Cardapio> cardapios) {
        this.cardapios_ = cardapios;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias_ = categorias;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos_ = produtos;
    }

    public void addCategoria(Categoria categoria){
        categorias_.add(categoria);
    }

    public void addCardapio(Cardapio cardapio){
        cardapios_.add(cardapio);
    }

    public void addProduto(Produto produto){
        produtos_.add(produto);
    }

    @Exclude
    public List<Categoria> getCategorias(){
        return categorias_;
    }

    @Exclude
    public List<Cardapio> getCardapios(){
        return cardapios_;
    }

    @Exclude
    public List<Produto> getProdutos(){
        return produtos_;
    }

    public void addReview(Review review) {
        reviews_.add(review);
    }
}
