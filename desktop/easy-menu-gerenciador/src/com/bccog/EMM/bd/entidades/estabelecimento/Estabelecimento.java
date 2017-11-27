package com.bccog.EMM.bd.entidades.estabelecimento;

import com.bccog.EMM.bd.EntidadeBasica;
import com.bccog.EMM.bd.entidades.cardapio.Cardapio;
import com.bccog.EMM.bd.entidades.categoria.Categoria;
import com.bccog.EMM.bd.entidades.embutido.Delivery;
import com.bccog.EMM.bd.entidades.embutido.Endereco;
import com.bccog.EMM.bd.entidades.embutido.HorarioAtendimento;
import com.bccog.EMM.bd.entidades.pedido.Pedido;
import com.bccog.EMM.bd.entidades.produto.Produto;
import com.bccog.EMM.bd.entidades.review.Review;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstrata que contém os dados básicos de um estabelecimento
 * @author Cristofer Oswald
 * @since 25/05/2017
 */
public abstract class Estabelecimento extends EntidadeBasica {
    private transient List<Review> reviews_;
    private transient List<Cardapio> cardapios_;
    private transient List<Categoria> categorias_;
    private transient List<Produto> produtos_;
    private transient List<Pedido> pedidos_;

    @SerializedName("nome")
    private String nome_;
    @SerializedName("endereco")
    private Endereco endereco_;
    @SerializedName("horarioAtendimento")
    private HorarioAtendimento horario_atendimento_;
    @SerializedName("nota")
    private float nota_;
    @SerializedName("fotos")
    private String[] fotos_;
    @SerializedName("delivery")
    private Delivery delivery_;
    @SerializedName("soDelivery")
    private boolean so_delivery_;

    public Estabelecimento(String nome, Endereco endereco, HorarioAtendimento horario_atendimento, float nota) {
        setNome(nome);
        setEndereco(endereco);
        setHorarioAtendimento(horario_atendimento);
        setNota(nota);
        setFotos(null);
        setDelivery(null);
        setSoDelivery(false);
    }

    public Estabelecimento(String nome, Endereco endereco, HorarioAtendimento horario_atendimento,
                           float nota, Delivery delivery, boolean so_delivery) {
        setNome(nome);
        setEndereco(endereco);
        setHorarioAtendimento(horario_atendimento);
        setNota(nota);
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

    public List<Pedido> getPedidos() {
        return pedidos_;
    }

    public void setPedidos(List<Pedido> pedidos_) {
        this.pedidos_ = pedidos_;
    }

    public List<Categoria> getCategorias(){
        if(categorias_ == null){
            categorias_ = new ArrayList<>();
        }
        return categorias_;
    }

    public List<Cardapio> getCardapios(){
        if(cardapios_ == null){
            cardapios_ = new ArrayList<>();
        }
        return cardapios_;
    }

    public List<Produto> getProdutos(){
        if(produtos_ == null){
            produtos_ = new ArrayList<>();
        }
        return produtos_;
    }

    public void addReview(Review review) {
        reviews_.add(review);
    }

    public int numeroCategorias(){
        return categorias_.size();
    }

    public int numeroProdutos(){
        return produtos_.size();
    }


    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Estabelecimento) && super.equals(obj);
    }
}
