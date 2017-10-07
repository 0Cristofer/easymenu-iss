package com.bccog.EMM.gui.subEntidades;

import com.bccog.EMM.bd.entidades.pedido.Pedido;
import com.bccog.EMM.bd.entidades.produto.Produto;
import com.bccog.EMM.bd.entidades.produto.ProdutoPedido;
import com.bccog.EMM.gui.GUIController;
import com.bccog.EMM.gui.controladores.SessaoTrabalhoController;
import com.bccog.EMM.gui.controladores.SessaoTrabalhoController;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Classe View para a interface de sessão de trabalho
 * @author Bruno Cesar
 * @since 23/07/17
 */

public class PedidoCard extends GridPane {
    private Pedido pedido_;
    private SessaoTrabalhoController controller_;

    private Label lbl_horario_ = new Label();
    private Label lbl_ID_      = new Label();
    private Label lbl_nome_    = new Label();
    private Label lbl_A_       = new Label(); //Tipo de entrega: Local/Entrega/Reserva
    private Label lbl_preco_       = new Label(); //Tipo de pagamento caso seja entrega. Ambos trocar por IMGview com icones

    private Button btn_aceitar_;
    private Button btn_recusar_;

    private TextArea pedido_desc_ = new TextArea();

    private ColumnConstraints col1 = new ColumnConstraints();
    private ColumnConstraints col2 = new ColumnConstraints();
    private ColumnConstraints col3 = new ColumnConstraints();
    private RowConstraints row1 = new RowConstraints();
    private RowConstraints row2 = new RowConstraints();
    private RowConstraints row3 = new RowConstraints();

    public PedidoCard(Pedido pedido, SessaoTrabalhoController controller) {
        this.controller_ = controller;
        this.pedido_ = pedido;

        String css = GUIController.class.getResource("/stylesheet.css").toExternalForm();



        //lbl_ID_.setText(pedido_.getId());

        lbl_nome_.setText("Teste da silva");
        lbl_nome_.setFont(new Font(20));

        lbl_horario_.setText("21:34");
        lbl_horario_.setFont(new Font(18));

        lbl_A_.setText("");
        lbl_preco_.setText("15.00 R$");

        pedido_desc_.setText("Cachorro-simples (Adicional de Calabresa), Guaraná Lata.");
        pedido_desc_.setFont(new Font(18));
        pedido_desc_.setWrapText(true);

        ImageView icone_aceitar = new ImageView(new Image("/icons/aceitar.png"));
        btn_aceitar_ = new JFXButton("",icone_aceitar);
        btn_aceitar_.setContentDisplay(ContentDisplay.CENTER);
        btn_aceitar_.getStylesheets().add(css);
        btn_aceitar_.getStyleClass().add("btn_aceitar_");

        btn_aceitar_.setOnAction(event -> System.out.println("Aceitou o pedido!"));

        ImageView icone_recusar = new ImageView(new Image("/icons/recusar.png"));
        btn_recusar_ = new JFXButton("", icone_recusar);
        btn_recusar_.setContentDisplay(ContentDisplay.CENTER);
        btn_recusar_.getStylesheets().add(css);
        btn_recusar_.getStyleClass().add("btn_recusar_");

        btn_recusar_.setOnAction(event -> System.out.println("Recusou o pedido!"));


        col1.setHalignment(HPos.LEFT);
        col1.setHgrow(Priority.NEVER);
        col1.setMinWidth(10.0);
        col1.setPercentWidth(15.0);
        col1.setPrefWidth(30.0);

        col2.setHalignment(HPos.CENTER);
        col2.setHgrow(Priority.NEVER);
        col2.setMinWidth(10.0);
        col2.setPercentWidth(65.0);
        col2.setPrefWidth(30.0);

        col3.setHalignment(HPos.CENTER);
        col3.setHgrow(Priority.NEVER);
        col3.setMinWidth(10.0);
        col3.setPercentWidth(20.0);
        col3.setPrefWidth(30.0);

        getColumnConstraints().addAll(col1,col2,col3);

        row1.setMinHeight(10.0);
        row1.setVgrow(Priority.NEVER);
        row1.setPercentHeight(20.0);
        row1.setPrefHeight(30.0);
        row1.setVgrow(Priority.SOMETIMES);

        row2.setMinHeight(10.0);
        row2.setVgrow(Priority.NEVER);
        row2.setPercentHeight(50.0);
        row2.setPrefHeight(30.0);
        row2.setVgrow(Priority.SOMETIMES);

        row3.setMinHeight(10.0);
        row3.setVgrow(Priority.NEVER);
        row3.setPercentHeight(20.0);
        row3.setPrefHeight(30.0);
        row3.setVgrow(Priority.SOMETIMES);

        getRowConstraints().addAll(row1, row2, row3);


        lbl_ID_.setPadding(new Insets(0,0,0,5));
        lbl_nome_.setPadding(new Insets(0,0,10,0));
        lbl_horario_.setPadding(new Insets(0,10,5,0));
        lbl_A_.setPadding(new Insets(0,0,0,5));
        btn_recusar_.setPadding(new Insets(0,0,5,5));
        btn_aceitar_.setPadding(new Insets(0,5,5,0));

        add(lbl_ID_,0,0);
        add(lbl_nome_,1,0);
        add(lbl_horario_,2,0);
        add(lbl_A_,0,1);
        add(lbl_preco_,2,1);
        add(pedido_desc_,1,1,1,REMAINING);
        add(btn_recusar_,0,2);
        add(btn_aceitar_,2,2);

        this.setAlignment(Pos.CENTER);
        this.setPrefHeight(200);
        this.setPrefWidth(200);
        this.setMaxHeight(USE_COMPUTED_SIZE);
        this.setMaxWidth(USE_COMPUTED_SIZE);

        getStylesheets().add(css);
        getStyleClass().add("card");
    }

    public Pedido getPedido() {
        return pedido_;
    }

    public void setPedido(Pedido pedido_) {
        this.pedido_ = pedido_;
    }

    public void removeCancelar(){
        btn_recusar_.setDisable(true);
    }
}