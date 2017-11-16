package com.bccog.EMM.gui.controladores;

import com.bccog.EMM.EMM;
import com.bccog.EMM.bd.entidades.cardapio.Cardapio;
import com.bccog.EMM.bd.exceptions.*;
import com.bccog.EMM.gui.subEntidades.CardapioCard;
import com.bccog.FXController.BaseController;
import com.bccog.FXController.ScreenController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.util.List;

import static com.bccog.EMM.gerenciadores.GerenciadorCardapios.criarCardapio;

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

    @FXML
    public void iniciarSessao() {
        controller_.setVisibleScreen("work_screen");
    }

    @FXML public void verCategorias(){
        controller_.setVisibleScreen("categorias");
    }

    public void categorias(){ controller_.setVisibleScreen("categorias");
    }

    public void dadosEstabelecimento(){ controller_.setVisibleScreen("info_estabelecimento");}

    @FXML public void cardapios(){ /*Esta nesta tela*/ }

    @FXML public void produtos(){
        controller_.setVisibleScreen("produtos");
    }

    public void historico(){ controller_.setVisibleScreen("historico");}

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
        if(!txtf_nome_.getText().equals("")){
            try {
                criarCardapio(txtf_nome_.getText());
            } catch (NoConnectionException | ForbiddenException | BadRequestException | InternalServerErrorException |
                    NotImplementedErrorExcpetion | NotAuthorizedException | NotFoundException e) {
                e.printStackTrace();
            }
            //TODO pop-up cadastrado com sucesso!
            cancelar();
            atualizar();
        }
    }


    @Override
    public void atualizar() {
        List<Cardapio> cardapios = EMM.getInstance().getUsuarioAtual().
                getEstabelecimento().getCardapios();

        GridPane grid_ = new GridPane();

        if(cardapios == null) return;

        int i = 0;
        boolean b = true;
        for (Cardapio c : cardapios) {
            if(b){
                grid_.add(new CardapioCard(c),0,i);
            } else {
                grid_.add(new CardapioCard(c),1,i);
                i++;
            }

            b = !b;
        }

        scroll_pane_.setContent(grid_);
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
