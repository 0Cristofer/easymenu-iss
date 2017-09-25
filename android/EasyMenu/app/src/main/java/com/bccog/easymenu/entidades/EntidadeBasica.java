package com.bccog.easymenu.entidades;

import com.google.firebase.database.Exclude;

/**
 * Entidade básica, todas as entidades são filhas dela
 * @author Cristofer Oswald
 * @since 06/07/17
 */
public abstract class EntidadeBasica {
    private String id_;

    public EntidadeBasica(){

    }

    @Exclude
    public String getId() {
        return id_;
    }

    public void setId(String id) {
        this.id_ = id;
    }
}
