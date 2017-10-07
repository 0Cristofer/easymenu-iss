package com.bccog.EMM.gui.controladores;


import com.bccog.FXController.BaseController;
import com.bccog.FXController.ScreenController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;

/**
 * Controlador da tela de seleção de cardápio
 * @author Diogo Almeida
 * @since 04/10/2017
 */

public class SelecionaCardapioController implements BaseController {

    private ScreenController controller_;

    @FXML
    private BorderPane main_pane_;
    public Label lbl_titulo_;
    public ScrollPane scroll_pane_;
    public Label lbl_nome_;
    public JFXButton btn_cancelar_;
    public JFXButton btn_confirmar_;
    public JFXButton btn_cadastrar_;
    public JFXTextField txtf_nome_;
    public JFXButton btn_lista_categorias_;

    @FXML public void verCategorias(){
    }

    public void categorias(){
    }

    public void iniciarSessao(){ controller_.setVisibleScreen("sessao_trabalho");}

    public void dadosEstabelecimento(){ controller_.setVisibleScreen("info_estabelecimento");}

    @FXML public void cardapios(){ controller_.setVisibleScreen("seleciona_cardapio");}

    @FXML public void produtos(){
        controller_.setVisibleScreen("produtos");
    }

    @FXML public void inicio(){
        controller_.setVisibleScreen("main");
    }

    @FXML public void sair(){
        Platform.exit();
    }


    @FXML public void cadastrarCardapio(){
        lbl_nome_.setVisible(true);
        btn_confirmar_.setVisible(true);
        txtf_nome_.setVisible(true);
        btn_cancelar_.setVisible(true);

    }

    @FXML public void cancelar(){
        lbl_nome_.setVisible(false);
        btn_confirmar_.setVisible(false);
        txtf_nome_.setVisible(false);
        btn_cancelar_.setVisible(false);
        txtf_nome_.setText("");
    }

    @FXML public void confirmar(){
    }


    @Override
    public void atualizar() {
    }

    @Override
    public void init() {

    }

    @Override
    public void setMainController(ScreenController controller) { controller_ = controller;
    }

    @Override
    public double getWidth() {
        return main_pane_.getPrefWidth();
    }

    @Override
    public double getHeight() {
        return main_pane_.getPrefWidth();
    }
}
