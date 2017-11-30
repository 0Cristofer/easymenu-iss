package com.bccog.EMM.gui.controladores;

import com.bccog.EMM.EMM;
import com.bccog.EMM.bd.entidades.categoria.Categoria;
import com.bccog.EMM.bd.entidades.cupons.Cupons;
import com.bccog.EMM.bd.exceptions.*;
import com.bccog.EMM.gerenciadores.GerenciadorCupons;
import com.bccog.EMM.gerenciadores.exceptions.MissingPriceException;
import com.bccog.EMM.gerenciadores.exceptions.NegativePriceException;
import com.bccog.FXController.BaseController;
import com.bccog.FXController.ScreenController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTreeTableView;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.joda.time.DateTime;

import java.util.List;


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
    public Label lbl_aviso;

    public void gerar(){
        if(checarCampos()){
            try{
                DateTime timestamp = new DateTime(datep_expira.getValue().getYear(), datep_expira.getValue().getMonth().getValue(), datep_expira.getValue().getDayOfMonth(), 0, 0, 0 );
                System.out.println(txtf_codigo.getText());
                System.out.println(timestamp);
                System.out.println(txtf_desconto.getText());

                Categoria categoria_selecionada = null;
                if(cbox_categorias.getValue() != null){
                    categoria_selecionada = cbox_categorias.getValue();
                }

                GerenciadorCupons.cadastrarCupons(txtf_codigo.getText(), timestamp.getMillis(), Float.parseFloat(txtf_desconto.getText()), categoria_selecionada);

                lbl_aviso.setVisible(false);

            } catch (NoConnectionException | ForbiddenException | BadRequestException |
                    NotImplementedErrorExcpetion | InternalServerErrorException | NotAuthorizedException |
                    NotFoundException e) {
                e.printStackTrace();
            } catch (MissingPriceException e) {
                e.printStackTrace();
            } catch (NegativePriceException e) {
                e.printStackTrace();
            }

        } else{



        }
    }

    public boolean checarCampos(){
        //Nome
        if(txtf_codigo.getText().equals("")){
            lbl_aviso.setText("Codigo invalido ou vazio.");
            lbl_aviso.setVisible(true);
            return false;
        }

        if(Float.valueOf(txtf_desconto.getText()) < 1.0 || Float.valueOf(txtf_desconto.getText()) > 100.0){
            lbl_aviso.setText("Desconto invalido!\n(Deve ser entre 1.0 e 100.0)");
            lbl_aviso.setVisible(true);
            return false;
        }

        if(cbox_categorias.getValue() == null){
            lbl_aviso.setText("Categoria nao selecionada");
            lbl_aviso.setVisible(true);
            return false;
        }

        if(datep_expira.getValue() == null){
            lbl_aviso.setText("Data nao selecionada");
            lbl_aviso.setVisible(true);
            return false;
        }

        lbl_aviso.setVisible(false);
        return true;
    }

    public void cancelar(){
        lbl_aviso.setVisible(false);
        txtf_desconto.setText("");
        txtf_codigo.setText("");
        controller_.setVisibleScreen("main"); }


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
        List<Categoria> categorias = EMM.getInstance().getUsuarioAtual().getEstabelecimento().getCategorias();
        if(categorias == null) return;
        cbox_categorias.getItems().clear();
        cbox_categorias.getItems().addAll(categorias);

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
