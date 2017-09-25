package com.bccog.EMM.bd.entidades.pedido;

import com.google.gson.annotations.SerializedName;

/**
 * Status possíveis de um pedido
 * @author Cristofer Oswald
 * @since 06/06/2017
 */
public enum Status {
    ENVIADO("enviado"), RECEBIDO("recebido"), PREPARANDO("preparando"), PRONTO("pronto"), ENTREGUE("entregue"), CANCELADO("cancelado");

    @SerializedName("status")
    private String status_;

    Status(String status){
        this.status_ = status;
    }

    public String getStatus(){
        return status_;
    }
}
