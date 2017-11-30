package com.bccog.EMM.gui.subEntidades;

    import com.bccog.EMM.bd.entidades.cupons.Cupons;
    import com.bccog.EMM.bd.entidades.pedido.Pedido;
    import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
    import javafx.beans.property.SimpleStringProperty;
    import javafx.beans.property.StringProperty;
    import org.joda.time.DateTime;

/**
 * Classe view de cupons
 * @author Guilherme Quental
 * @since 23/11/17
 */

public class CuponsView extends RecursiveTreeObject<CuponsView>{

    private StringProperty nome_;
    private StringProperty timestamp;
    private StringProperty valor;


    private Cupons cupons;

    public CuponsView(Cupons cupons) {
        this.cupons = cupons;
        this.nome_ = new SimpleStringProperty(cupons.getNome_());
        this.timestamp = new SimpleStringProperty();
        this.valor = new SimpleStringProperty(String.valueOf(cupons.getValor_()));
    }

    public String getNome_() {
        return nome_.get();
    }

    public StringProperty nome_Property() {
        return nome_;
    }

    public String getTimestamp() {
        return timestamp.get();
    }

    public StringProperty timestampProperty() {
        return timestamp;
    }

    public String getValor() {
        return valor.get();
    }

    public StringProperty valorProperty() {
        return valor;
    }

    public Cupons getCupons() {
        return cupons;
    }
}








