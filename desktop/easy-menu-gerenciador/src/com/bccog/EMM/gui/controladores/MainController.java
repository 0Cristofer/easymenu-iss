package com.bccog.EMM.gui.controladores;

import com.bccog.EMM.EMM;
import com.bccog.EMM.gui.subEntidades.ProdutoView;
import com.bccog.FXController.BaseController;
import com.bccog.FXController.ScreenController;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

/**
 * Controlador da tela principal
 * @author Bruno Cesar
 * @since 24/05/2017
 */
public class MainController implements BaseController {
    public Label lbl_bemvindo_;
    private ScreenController controller_;

    public BorderPane main_pane_;
    public Button btn_produtos_;
    public Label lbl_categoria_vend;
    public Label lbl_produto_vend;
    public Label lbl_total_produtos;
    public Label lbl_lucro;

    public void produtos(){
        controller_.setVisibleScreen("produtos");
    }

    public void cardapios(){ controller_.setVisibleScreen("seleciona_cardapio");}

    public void dadosEstabelecimento(){ controller_.setVisibleScreen("info_estabelecimento");}

    public void categorias(){ controller_.setVisibleScreen("categorias");}

    public void historico(){ controller_.setVisibleScreen("historico");}

    public void sair(){
        Platform.exit();
    }

    @Override
    public void atualizar() {
        lbl_bemvindo_.setText("Bem vindo, " + EMM.getInstance().getUsuarioAtual().getEstabelecimento().getNome());
    }

    @Override
    public void init() {
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
        return main_pane_.getPrefHeight();
    }

    public void iniciarSessao(){
        controller_.setVisibleScreen("sessao_trabalho");}
}