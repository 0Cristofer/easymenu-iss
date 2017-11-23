package com.bccog.EMM.gui.controladores;

import com.bccog.EMM.bd.entidades.categoria.Categoria;
import com.bccog.FXController.BaseController;
import com.bccog.FXController.ScreenController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;


/**
 * Classe controladora de tela de cupons
 * @author Guilherme Quental
 * @since 23/11/2017
 */

public class CuponsController implements BaseController{

    private ScreenController controller_;
    private Categoria selected_cat;


    public void iniciarSessao(ActionEvent event) {
        controller_.setVisibleScreen("work_screen");
    }

    @FXML
    public void produtos(){ controller_.setVisibleScreen("produtos");}

    @FXML public void cardapios(){ controller_.setVisibleScreen("seleciona_cardapio");}

    @FXML public void inicio(){
        controller_.setVisibleScreen("main");
    }

    @FXML public void voltainicio(){
        controller_.setVisibleScreen("main");
    }
    @FXML public void sair(){
        Platform.exit();
    }

    @Override
    public void atualizar() {

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
        return controller_.getStage().getWidth();
    }

    @Override
    public double getHeight() {
        return controller_.getStage().getHeight();
    }


}
