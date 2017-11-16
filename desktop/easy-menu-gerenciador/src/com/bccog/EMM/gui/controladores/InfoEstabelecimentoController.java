package com.bccog.EMM.gui.controladores;

import com.bccog.EMM.EMM;
import com.bccog.EMM.bd.entidades.estabelecimento.Estabelecimento;
import com.bccog.FXController.BaseController;
import com.bccog.FXController.ScreenController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;

public class InfoEstabelecimentoController implements BaseController{

    private ScreenController controller_;
    public JFXTextField txt_nome_est;
    public JFXButton btn_excluir_;
    public JFXTextField txt_complemento_;
    public JFXTextField txt_cep_;
    public JFXTextField txt_referencia_;
    public JFXTextField txt_rua_;
    public JFXTextField txt_numero_;
    public JFXTextField txt_hora_fim_;
    public JFXTextField txt_hora_abertura_;
    public JFXCheckBox check_delivery_;
    public JFXTextField txt_taxa_gratis_;
    public JFXTextField txt_preco_min_;
    public JFXTextField txt_horario_entrega_;
    public JFXTextField txt_tempo_medio_;
    public JFXTextField txt_taxa_entrega_;
    public ToggleGroup tipo_estabelecimento;
    public JFXRadioButton radio_balcao_;
    public JFXRadioButton radio_mesa_;
    public JFXRadioButton radio_delivery_;

    public BorderPane main_pane_;

    @Override
    public void atualizar() {
        Estabelecimento estabelecimento = EMM.getInstance().getUsuarioAtual().getEstabelecimento();

        txt_nome_est.setText(estabelecimento.getNome());


        txt_cep_.setText(estabelecimento.getEndereco().getCep());
        txt_rua_.setText(estabelecimento.getEndereco().getRua());
        txt_complemento_.setText(estabelecimento.getEndereco().getComplemento());
        txt_referencia_.setText( estabelecimento.getEndereco().getReferencia());
        txt_numero_.setText(estabelecimento.getEndereco().getNumero());
        txt_hora_fim_.setText(estabelecimento.getHorarioAtendimento().getHoraFim());
        txt_hora_abertura_.setText(estabelecimento.getHorarioAtendimento().getHoraAbertura());
        if(estabelecimento.getDelivery() != null){
            txt_taxa_entrega_.setText("Taxa de entrega: " + Float.toString(estabelecimento.getDelivery().getTaxaEntrega()));
            txt_preco_min_.setText("Preço mínimo: " + Float.toString(estabelecimento.getDelivery().getPrecoMin()));
            txt_horario_entrega_.setText("Horário de entrega: " + estabelecimento.getDelivery().getHorarioEntrega());
            txt_tempo_medio_.setText("Tempo médio: " +estabelecimento.getDelivery().getMediaDemora());
        }
    }
    @FXML
    public void inicio(){
        controller_.setVisibleScreen("main");
    }
    public void iniciarSessao() {
    }

    public void sair(){
        Platform.exit();
    }

    public void produtos(){
        controller_.setVisibleScreen("produtos");
    }

    public void cardapios(){ controller_.setVisibleScreen("seleciona_cardapio");}

    public void verDados(){
        controller_.setVisibleScreen("dados_estabelecimento");
    }

    public void dadosEstabelecimento(){
        controller_.setVisibleScreen("info_estabelecimento");
    }

    public void historico(){ controller_.setVisibleScreen("historico");}

    public void categorias(){

    }

    public void editar(){
        txt_nome_est.setDisable(false);
        txt_cep_.setDisable(false);
        txt_rua_.setDisable(false);
        txt_complemento_.setDisable(false);
        txt_referencia_.setDisable(false);
        txt_numero_.setDisable(false);
        txt_hora_fim_.setDisable(false);
        txt_hora_abertura_.setDisable(false);

    }

    @Override
    public void init() {

    }

    @Override
    public void setMainController(ScreenController controller) {
        controller_= controller;

    }

    @Override
    public double getWidth() {
        return main_pane_.getPrefWidth();
    }

    @Override
    public double getHeight() {
        return main_pane_.getPrefHeight();
    }



}

