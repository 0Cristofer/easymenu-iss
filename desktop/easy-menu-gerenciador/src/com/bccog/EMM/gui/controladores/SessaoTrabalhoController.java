package com.bccog.EMM.gui.controladores;

import com.bccog.EMM.EMM;
import com.bccog.EMM.bd.entidades.pedido.Pedido;
import com.bccog.EMM.bd.entidades.pedido.Status;
import com.bccog.EMM.bd.exceptions.*;
import com.bccog.EMM.gerenciadores.GerenciadorPedidos;
import com.bccog.EMM.gui.subEntidades.PedidoCard;
import com.bccog.FXController.BaseController;
import com.bccog.FXController.ScreenController;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;


public class SessaoTrabalhoController implements BaseController {
    private ScreenController controller_;
    private List<Pedido> pedidos_;
    private ObservableList<Pedido> pedidos_observable_;

    @FXML
    public BorderPane main_pane_;
    public Label lbl_username_;
    public Label lbl_status_;
    public Label lbl_titulo_;
    public JFXButton btn_add_;
    public JFXButton btn_mesas_;
    public JFXButton btn_historico_;
    public JFXButton btn_cancelados_;
    public JFXButton btn_ajuda_;
    public JFXButton btn_bug_;
    public JFXButton btn_configuracoes_;
    public JFXButton btn_sair_;

    /* Vertical Boxes de pedidosCard */
    public VBox box_recebido;
    public VBox box_preparo;
    public VBox box_pronto;
    public VBox box_finalizado;


    @FXML
    private void add(){
    }

    @FXML
    private void encerrarSessao() {
        controller_.setVisibleScreen("main");
    }

    @Override
    public void atualizar() {
        lbl_username_.setText("Bem vindo, " + EMM.getInstance().getUsuarioAtual().getEstabelecimento().getNome());
        lbl_status_.setText("Conectado");
        lbl_titulo_.setText("Sessão de trabalho aberta a pedidos");

    }

    @Override
    public void init() {
        pedidos_ = new ArrayList<>();
        pedidos_observable_ = FXCollections.observableList(pedidos_);

        //Apenas teste da interface. Na versão final deverá ocorrer via conexão com BD na nuvem
        Pedido pedidoTeste = new Pedido();
        PedidoCard cardTeste = new PedidoCard(pedidoTeste,this);
        box_recebido.getChildren().addAll(cardTeste);
    }

    @Override
    public void setMainController(ScreenController controller) {
        controller_ = controller;
    }

    @Override
    public double getWidth() {
        return main_pane_.getPrefWidth();
    }

    @Override
    public double getHeight() {
        return main_pane_.getPrefHeight() ;
    }
}
