package com.bccog.EMM.gui.controladores;

import com.bccog.EMM.EMM;
import com.bccog.EMM.bd.entidades.cardapio.Cardapio;
import com.bccog.EMM.bd.entidades.categoria.Categoria;
import com.bccog.EMM.bd.entidades.produto.Produto;
import com.bccog.EMM.bd.exceptions.*;
import com.bccog.EMM.gerenciadores.GerenciadorCardapios;
import com.bccog.EMM.gui.subEntidades.ProdutoView;
import com.bccog.FXController.BaseController;
import com.bccog.FXController.ScreenController;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;

import java.util.List;

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

    public void categorias(){ controller_.setVisibleScreen("categorias");
    }

    @FXML public void voltaseleciona() {controller_.setVisibleScreen("seleciona_cardapio");}

    @FXML public void sair(){
        Platform.exit();
    }


    public void removeCategoria(){
        String id = acc_categorias_.getExpandedPane().getId();
        for (Categoria c: cardapio_.getCategorias()) {
            if(id.equals(c.getId())){
                try {
                    GerenciadorCardapios.removeCategoriaCardapio(cardapio_, c);
                } catch (NoConnectionException e) {
                    e.printStackTrace();
                } catch (ForbiddenException e) {
                    e.printStackTrace();
                } catch (BadRequestException e) {
                    e.printStackTrace();
                } catch (NotImplementedErrorExcpetion notImplementedErrorExcpetion) {
                    notImplementedErrorExcpetion.printStackTrace();
                } catch (InternalServerErrorException e) {
                    e.printStackTrace();
                } catch (NotFoundException e) {
                    e.printStackTrace();
                } catch (NotAuthorizedException e) {
                    e.printStackTrace();
                }
                acc_categorias_.getPanes().remove(acc_categorias_.getExpandedPane());
                break;
            }
        }
    }

    public void confirmar(){
        if (cbox_categorias_.getValue() == null) return;

        try {
            GerenciadorCardapios.adicionaCategoriaCardapio(cardapio_, cbox_categorias_.getValue());
        } catch (NoConnectionException e) {
            e.printStackTrace();
        } catch (ForbiddenException e) {
            e.printStackTrace();
        } catch (BadRequestException e) {
            e.printStackTrace();
        } catch (NotImplementedErrorExcpetion notImplementedErrorExcpetion) {
            notImplementedErrorExcpetion.printStackTrace();
        } catch (InternalServerErrorException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (NotAuthorizedException e) {
            e.printStackTrace();
        }
        cbox_categorias_.setVisible(false);
        btn_confirmar_.setVisible(false);

        atualizar();
    }

    public void addCategoria(){
        cbox_categorias_.setVisible(true);
        btn_confirmar_.setVisible(true);

    }

    public void excluirCardapio(){
        try {
            GerenciadorCardapios.deletaCardapio(cardapio_);
        } catch (NoConnectionException e) {
            e.printStackTrace();
        } catch (ForbiddenException e) {
            e.printStackTrace();
        } catch (BadRequestException e) {
            e.printStackTrace();
        } catch (NotImplementedErrorExcpetion notImplementedErrorExcpetion) {
            notImplementedErrorExcpetion.printStackTrace();
        } catch (InternalServerErrorException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (NotAuthorizedException e) {
            e.printStackTrace();
        }
        controller_.setVisibleScreen("seleciona_cardapio");
    }

    @Override
    public void atualizar() {
        lbl_nome_cardapio_.setText("Nome do cardápio: " +cardapio_.getNome());
        lbl_ultima_alt_.setText("Última alteração: " + cardapio_.getDateTime().toString());
        lbl_total_produtos_.setText("Total de produtos: " + cardapio_.getCategorias().size());

        cbox_categorias_.getItems().clear();
        cbox_categorias_.getItems().addAll(EMM.getInstance().getUsuarioAtual().getEstabelecimento().getCategorias());
        List<Categoria> categorias = cardapio_.getCategorias();

        acc_categorias_.getPanes().clear();
        for (Categoria c: categorias) {
            TitledPane tp = new TitledPane();
            tp.setText(c.getNome());

            JFXTreeTableView<ProdutoView> produto_view_ = new JFXTreeTableView<>();

            JFXTreeTableColumn<ProdutoView, String> nomeCol = new JFXTreeTableColumn<>("Nome");
            nomeCol.setPrefWidth(200);
            nomeCol.setCellValueFactory(param -> param.getValue().getValue().nomeProperty());

            JFXTreeTableColumn<ProdutoView, String> precoCol = new JFXTreeTableColumn<>("Preço");
            precoCol.setPrefWidth(250);
            precoCol.setCellValueFactory(param -> param.getValue().getValue().precoProperty());

            JFXTreeTableColumn<ProdutoView, String> tagsCol = new JFXTreeTableColumn<>("Tags");
            tagsCol.setPrefWidth(200);
            tagsCol.setCellValueFactory(param -> param.getValue().getValue().tagsTextProperty());

            ObservableList<ProdutoView> produtosv = FXCollections.observableArrayList();

            if(c.getProdutos() != null) {
                for (Produto p : c.getProdutos()) {
                    produtosv.add(new ProdutoView(p));
                }
            }

            final TreeItem<ProdutoView> root = new RecursiveTreeItem<>(produtosv, RecursiveTreeObject::getChildren);
            produto_view_.getColumns().setAll(nomeCol, precoCol, tagsCol);
            produto_view_.setRoot(root);
            produto_view_.setShowRoot(false);

            tp.setContent(produto_view_);
            tp.setId(c.getId());
            acc_categorias_.getPanes().add(tp);
        }
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
