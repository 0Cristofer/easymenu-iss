package com.bccog.EMM.bd;

/**
 * Entidade básica, todas as entidades são filhas dela
 * @author Cristofer Oswald
 * @since 06/07/17
 */
public abstract class EntidadeBasica {
    private transient String id_;

    public String getId() {
        return id_;
    }

    public void setId(String id) {
        this.id_ = id;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof EntidadeBasica) && (((EntidadeBasica) obj).getId().equals(getId()));
    }
}
