package com.bccog.easymenu.entidades.review;

import com.bccog.easymenu.entidades.EntidadeBasica;
import com.bccog.easymenu.entidades.pedido.Pedido;
import com.google.firebase.database.Exclude;

/**
 * Um review é uma nota com um comentário que um usuário pode deixar após utilizar um produto (sem uso no momento)
 * @author Cristofer Oswald
 * @since 06/06/2017
 */
public class Review extends EntidadeBasica {
    private Pedido pedido_;

    private float nota_;
    private String foto_;
    private String comentario_;

    public Review(){
        super();
    }

    public Review(Pedido pedido, float nota, String foto, String comentario) {
        setPedido(pedido);
        setNota(nota);
        setFoto(foto);
        setComentario(comentario);
    }

    @Exclude
    public Pedido getPedido(){
        return pedido_;
    }

    public void setPedido(Pedido pedido){
        this.pedido_ = pedido;
    }

    public float getNota() {
        return nota_;
    }

    public void setNota(float nota) {
        this.nota_ = nota;
    }

    public String getFoto() {
        return foto_;
    }

    public void setFoto(String foto) {
        this.foto_ = foto;
    }

    public String getComentario() {
        return comentario_;
    }

    public void setComentario(String comentario) {
        this.comentario_ = comentario;
    }
}
