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
    private StringProperty data_;
    private StringProperty cliente_nome_;
    private StringProperty valor_;
    private StringProperty horario_recebido_;
    private StringProperty horario_finalizado_;
    DateTime dt_recebido;
    DateTime dt_finalizado;

    private Pedido pedido_;


    public PedidoView(Pedido pedido) {
        pedido_ = pedido;

        dt_recebido = new DateTime(pedido_.getTimestamp());
        dt_finalizado = new DateTime(pedido_.getTimestamp_final_());

        data_ = new SimpleStringProperty( Integer.toString(dt_recebido.getDayOfMonth()) + "/" + Integer.toString(dt_recebido.getMonthOfYear()));

        cliente_nome_ = new SimpleStringProperty(pedido_.getCliente().getNome());

        valor_ = new SimpleStringProperty(Float.toString(pedido_.getValor()));

        horario_recebido_ = new SimpleStringProperty(Integer.toString(dt_recebido.getHourOfDay()) + ":" + Integer.toString(dt_recebido.getMinuteOfHour()));
        horario_finalizado_ = new SimpleStringProperty(Integer.toString(dt_finalizado.getHourOfDay()) + ":" + Integer.toString(dt_finalizado.getMinuteOfHour()));
    }


    public DateTime getDt_recebido() {
        return dt_recebido;
    }

    public DateTime getDt_finalizado() {
        return dt_finalizado;
    }

    public Pedido getPedido_() {
        return pedido_;
    }

    public void setPedido_(Pedido pedido) {
        pedido_ = pedido;
    }


    public String getData_() {
        return data_.get();
    }

    public StringProperty data_Property() {
        return data_;
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
