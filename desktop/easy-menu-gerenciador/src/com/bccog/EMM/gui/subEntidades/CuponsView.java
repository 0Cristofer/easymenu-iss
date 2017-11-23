package com.bccog.EMM.gui.subEntidades;

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

    private StringProperty id_;
    private StringProperty cliente_nome_;
    private StringProperty valor_;
    private StringProperty horario_recebido_;
    private StringProperty horario_finalizado_;

}








