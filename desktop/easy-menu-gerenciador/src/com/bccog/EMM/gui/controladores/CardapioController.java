package com.bccog.EMM.gui.controladores;

import com.bccog.EMM.bd.entidades.cardapio.Cardapio;
import com.bccog.EMM.bd.entidades.categoria.Categoria;
import com.bccog.FXController.BaseController;
import com.bccog.FXController.ScreenController;
import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;

/**
 * Classe controladora de tela de visualização de cardápio
 * @author Diogo Almeida
 * @since 04/10/2017
 */

public class CardapioController implements BaseController {
    private ScreenController controller_;
    private Cardapio cardapio_;

    @FXML
    public Accordion acc_categorias_;
    public Label lbl_ultima_alt_;
    public Label lbl_nome_cardapio_;
    public Label lbl_total_produtos_;
    public JFXComboBox<Categoria> cbox_categorias_;
    public JFXButton btn_add_categoria_;
    public JFXButton btn_del_categoria_;
    public JFXButton btn_excluir_;
    public JFXButton btn_voltar_;
    public JFXButton btn_confirmar_;


    public Cardapio getCardapio() {
        return cardapio_;
    }

    public void setCardapio(Cardapio cardapio) {
        this.cardapio_ = cardapio;
    }

    public void iniciarSessao(ActionEvent event) {
        controller_.setVisibleScreen("work_screen");
    }

    @FXML public void produtos(){
        controller_.setVisibleScreen("produtos");
    }

    @FXML public void cardapios(){ /*Esta nesta tela*/}

    @FXML public void inicio(){
        controller_.setVisibleScreen("main");
    }

    public void dadosEstabelecimento(){ controller_.setVisibleScreen("info_estabelecimento");}

    public void categorias(){ controller_.setVisibleScreen("categorias");}

    @FXML public void voltaseleciona() {controller_.setVisibleScreen("seleciona_cardapio");}

    @FXML public void sair(){
        Platform.exit();
    }


    public void removeCategoria(){
    }

    public void confirmar(){

    }

    public void addCategoria(){
        cbox_categorias_.setVisible(true);
        btn_confirmar_.setVisible(true);

    }

    public void excluirCardapio(){
    }

    @Override
    public void atualizar() {
    }

    @Override
    public void init() {
        cbox_categorias_.setPromptText("Selecione um categoria");
        cbox_categorias_.setVisible(false);
        btn_confirmar_.setVisible(false);
    }

    @Override
    public void setMainController(ScreenController controller) {
        controller_ = controller;
    }

    @Override
    public double getWidth() {
        return 0;
    }

    @Override
    public double getHeight() {
        return 0;
    }
}
