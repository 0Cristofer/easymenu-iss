package com.bccog.EMM.gui.controladores;

import com.bccog.EMM.bd.entidades.produto.Tag;
import com.bccog.EMM.bd.exceptions.*;
import com.bccog.EMM.gerenciadores.GerenciadorProdutos;
import com.bccog.EMM.gerenciadores.exceptions.MissingPriceException;
import com.bccog.EMM.gerenciadores.exceptions.NegativePriceException;
import com.bccog.FXController.BaseController;
import com.bccog.FXController.ScreenController;
import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

/**
 * Controlador do cadastro do produto
 * @author Bruno Cesar
 * @since 27/05/2017
 */
public class CadastroProdutoController implements BaseController {
    private ScreenController controller_;

    public BorderPane main_pane_;
    public ImageView img_prod_;
    public JFXTextField txtf_nome_;
    public JFXTextField txtf_preco_;
    public JFXButton btn_cadastrar_;
    public JFXButton btn_cancelar_;
    public TextArea txta_desc_;
    public JFXCheckBox cb_tamanhos_;
    public JFXCheckBox cb_tam_p_;
    public JFXCheckBox cb_tam_m_;
    public JFXCheckBox cb_tam_g_;
    public JFXTextField txtf_tam_p_;
    public JFXTextField txtf_tam_m_;
    public JFXTextField txtf_tam_g_;
    public JFXTextField txtf_qtd_p_;
    public JFXTextField txtf_qtd_m_;
    public JFXTextField txtf_qtd_g_;
    public JFXComboBox<Tag> cb_tag_1_;
    public JFXComboBox<Tag> cb_tag_2_;
    public JFXComboBox<Tag> cb_tag_3_;
    public JFXComboBox<Tag> cb_tag_4_;
    public JFXComboBox<Tag> cb_tag_5_;
    public JFXComboBox<Tag> cb_tag_6_;

    public void cancelar(){
        //TODO prompt "você tem certeza?" ??
        clean();
        controller_.setVisibleScreen("produtos");
    }

    public void produtos(){
        //sub-tela de produtos
    }

    public void cardapios(){
    }

    public void inicio(){
        controller_.setVisibleScreen("main");
    }

    public void sair(){
        Platform.exit();
    }


    public void cadastrar(){

        try{
            if(cb_tamanhos_.isSelected()){
                GerenciadorProdutos.cadastrarProduto(txtf_nome_.getText(),
                        txta_desc_.getText(),
                        null,"url_imagem_teste",
                        Float.valueOf(txtf_tam_p_.getText()),
                        Float.valueOf(txtf_tam_m_.getText()),
                        Float.valueOf(txtf_tam_g_.getText()));
            }
            else{
                GerenciadorProdutos.cadastrarProduto(txtf_nome_.getText(),
                        txta_desc_.getText(),
                        null,"url_imagem_teste",
                        Float.valueOf(txtf_preco_.getText()));
            }
        }
        catch (ForbiddenException | BadRequestException | NotImplementedErrorExcpetion |
                NotAuthorizedException | NotFoundException | InternalServerErrorException e) {
            e.printStackTrace();
        }
        catch (NumberFormatException e){
            System.out.println("preco mal formatado");
        } catch (MissingPriceException e) {
            System.out.println("falta algum preco");
        } catch (NegativePriceException e) {
            System.out.println("preço negativo");
        } catch (NoConnectionException e) {
            System.out.println("sem internet");
        }

        /*
        JFXDialogLayout conteudo = new JFXDialogLayout();
        conteudo.setHeading(new Text("Aviso!"));
        conteudo.setBody(new Text("Cadastro realizado com sucesso!"));

        JFXDialog pop_up = new JFXDialog(stackPane, conteudo, JFXDialog.DialogTransition.CENTER);
        JFXButton botao = new JFXButton("Ok");

        botao.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                pop_up.close();
            }
        });

        conteudo.setActions(botao);

        pop_up.show();
        */
        controller_.setVisibleScreen("produtos");
    }

    /**
     * Função que configura os checkBox do tamanho.
     * @param b Boolean que define se habilita ou desabilita (false : desabilita)
     */
    private void configTamanho(Boolean b){
        cb_tam_p_.setDisable(!b);
        cb_tam_m_.setDisable(!b);
        cb_tam_g_.setDisable(!b);
        txtf_tam_p_.setDisable(!b);
        txtf_tam_m_.setDisable(!b);
        txtf_tam_g_.setDisable(!b);
        txtf_qtd_p_.setDisable(!b);
        txtf_qtd_m_.setDisable(!b);
        txtf_qtd_g_.setDisable(!b);

        cb_tam_p_.setSelected(false);
        cb_tam_m_.setSelected(false);
        cb_tam_g_.setSelected(false);
        txtf_qtd_p_.setText("");
        txtf_qtd_m_.setText("");
        txtf_qtd_g_.setText("");
        txtf_tam_p_.setText("0.0");
        txtf_tam_m_.setText("0.0");
        txtf_tam_g_.setText("0.0");
    }

    public void habilitaTamanhos(){
        configTamanho(cb_tamanhos_.isSelected());
    }

    @Override
    public void atualizar() {

    }

    /**
     * Limpa todos os campos na tela
     */
    public void clean(){
        txtf_nome_.textProperty().setValue("");
        txtf_preco_.textProperty().setValue("");
        txta_desc_.textProperty().setValue("");

        cb_tamanhos_.setSelected(false);
        configTamanho(false);
    }

    /**
     * Configura a tela de cadastro com as tags e mais
     */
    @Override
    public void init() {
        cb_tag_1_.getItems().addAll(Tag.values());
        cb_tag_2_.getItems().addAll(Tag.values());
        cb_tag_3_.getItems().addAll(Tag.values());
        cb_tag_4_.getItems().addAll(Tag.values());
        cb_tag_5_.getItems().addAll(Tag.values());
        cb_tag_6_.getItems().addAll(Tag.values());

        cb_tamanhos_.setIndeterminate(false);

        configTamanho(false);
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
}

