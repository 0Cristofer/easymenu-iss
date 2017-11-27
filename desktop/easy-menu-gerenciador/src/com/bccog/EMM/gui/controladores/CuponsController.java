package com.bccog.EMM.gui.controladores;

import com.bccog.EMM.bd.entidades.categoria.Categoria;
import com.bccog.EMM.bd.entidades.cupons.Cupons;
import com.bccog.FXController.BaseController;
import com.bccog.FXController.ScreenController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;


/**
 * Classe controladora de tela de cupons
 * @author Guilherme Quental
 * @since 23/11/2017
 */

public class CuponsController implements BaseController{

    private ScreenController controller_;

    private Categoria selected_cat;
    public JFXComboBox<Categoria> cbox_categorias;
    public TextField txtf_desconto;
    public JFXDatePicker datep_expira;
    public TextField txtf_codigo;
    public JFXButton btn_gerar;
    public JFXButton btn_cancelar;
    public JFXTreeTableView ttv_cupons;

    public void gerar(){
        String nome = txtf_codigo.getText();
        long stamp = datep_expira.getValue().toEpochDay();
        float valor = Float.parseFloat(txtf_desconto.getText());

        Cupons cupom = new Cupons(nome, stamp, valor);

    }

    public void cancelar(){

    }


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


    public void categorias(ActionEvent actionEvent) {
        
    }

    public void dadosEstabelecimento(ActionEvent actionEvent) {
    }
}
