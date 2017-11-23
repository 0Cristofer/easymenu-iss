package com.bccog.EMM.gui.subEntidades;

import com.bccog.EMM.bd.entidades.pedido.Pedido;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.joda.time.DateTime;

/**
 * Classe view de pedido_ para exibiçao no historico
 * @author Bruno Cesar
 * @since 21/11/17
 */
public class PedidoView extends RecursiveTreeObject<PedidoView> {
    private StringProperty id_;
    private StringProperty cliente_nome_;
    private StringProperty valor_;
    private StringProperty horario_recebido_;
    private StringProperty horario_finalizado_;

    private Pedido pedido_;


    public PedidoView(Pedido pedido) {
        pedido_ = pedido;

        id_ = new SimpleStringProperty(pedido_.getId());

        cliente_nome_ = new SimpleStringProperty(pedido_.getCliente().getNome());

        valor_ = new SimpleStringProperty(Float.toString(pedido_.getValor()));

        DateTime time_recebido = new DateTime(pedido_.getTimestamp());
        DateTime time_finalizado = new DateTime(pedido_.getTimestamp_final_());

        horario_recebido_ = new SimpleStringProperty(time_recebido.toString());
        horario_finalizado_ = new SimpleStringProperty(time_finalizado.toString());
    }


    public Pedido getPedido_() {
        return pedido_;
    }

    public void setPedido_(Pedido pedido) {
        pedido_ = pedido;
    }


    public String getId_() {
        return id_.get();
    }

    public StringProperty id_Property() {
        return id_;
    }

    public String getCliente_nome_() {
        return cliente_nome_.get();
    }

    public StringProperty cliente_nome_Property() {
        return cliente_nome_;
    }

    public String getValor_() {
        return valor_.get();
    }

    public StringProperty valor_Property() {
        return valor_;
    }

    public String getHorario_recebido_() {
        return horario_recebido_.get();
    }

    public StringProperty horario_recebido_Property() {
        return horario_recebido_;
    }

    public String getHorario_finalizado_() {
        return horario_finalizado_.get();
    }

    public StringProperty horario_finalizado_Property() {
        return horario_finalizado_;
    }
}
