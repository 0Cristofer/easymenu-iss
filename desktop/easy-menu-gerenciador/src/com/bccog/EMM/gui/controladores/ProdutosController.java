package com.bccog.EMM.gui.controladores;

import com.bccog.EMM.EMM;
import com.bccog.EMM.bd.entidades.categoria.Categoria;
import com.bccog.EMM.bd.entidades.produto.Produto;
import com.bccog.EMM.bd.exceptions.*;
import com.bccog.EMM.gerenciadores.GerenciadorProdutos;
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
import javafx.scene.control.TreeItem;
import javafx.scene.layout.BorderPane;

import java.util.List;

/**
 * Controlador da tela de mostragem de produtos
 * @author Bruno Cesar
 * @since 31/05/2017
 */
public class ProdutosController implements BaseController {
    private ScreenController controller_;
    private ProdutoView selected_prod;

    @FXML
    public BorderPane main_pane_;
    public JFXButton btn_produtos_;
    public JFXTreeTableView<ProdutoView> produto_view_;
    public JFXButton btn_cadastrar_prod_;
    public JFXTextField txtf_busca_;
    public JFXButton btn_add_categoria_;
    public JFXButton btn_del_prod_;
    public JFXComboBox<Categoria> cbox_categorias_;

    public void iniciarSessao(ActionEvent event) {
    }

    public void dadosEstabelecimento(){ controller_.setVisibleScreen("info_estabelecimento");}

    public void categorias(){
    }

    @FXML
    public void cadastrarProduto(){
        controller_.setVisibleScreen("cadastro_produto");
    }

    @FXML public void addCategoria(){

    }

    @FXML public void produtos(){
        //Esta nesta tela
    }

    @FXML public void cardapios(){ controller_.setVisibleScreen("seleciona_cardapio");}


    @FXML public void inicio(){
        controller_.setVisibleScreen("main");
    }

    @FXML public void sair(){
        Platform.exit();
    }



    @FXML public void deletarProduto(){
        Produto p = selected_prod.getProduto_();
        try {
            GerenciadorProdutos.deletaProduto(p);
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

        atualizar();
        btn_del_prod_.setDisable(true);
    }

    @Override
    public void atualizar() {
        List<Categoria> categorias = EMM.getInstance().getUsuarioAtual().getEstabelecimento().getCategorias();
        if(categorias == null) return;
        cbox_categorias_.getItems().clear();
        cbox_categorias_.getItems().addAll(categorias);


        JFXTreeTableColumn<ProdutoView, String> nomeCol = new JFXTreeTableColumn<>("Nome");
        nomeCol.setPrefWidth(200);
        nomeCol.setCellValueFactory(param -> param.getValue().getValue().nomeProperty());

        JFXTreeTableColumn<ProdutoView, String> precoCol = new JFXTreeTableColumn<>("Preço");
        precoCol.setPrefWidth(250);
        precoCol.setCellValueFactory(param -> param.getValue().getValue().precoProperty());

        JFXTreeTableColumn<ProdutoView, String> tagsCol = new JFXTreeTableColumn<>("Tags");
        tagsCol.setPrefWidth(200);
        tagsCol.setCellValueFactory(param -> param.getValue().getValue().tagsTextProperty());

        JFXTreeTableColumn<ProdutoView, String> cardapioCol = new JFXTreeTableColumn<>("Cardapios");
        cardapioCol.setPrefWidth(150);
        cardapioCol.setCellValueFactory(param -> param.getValue().getValue().cardapioProperty());

        List<Produto> produtos = EMM.getInstance().getUsuarioAtual().getEstabelecimento().getProdutos();
        ObservableList<ProdutoView> produtosv = FXCollections.observableArrayList();

        for (Produto p : produtos) {
            produtosv.add(new ProdutoView(p));
        }

        final TreeItem<ProdutoView> root = new RecursiveTreeItem<>(produtosv, RecursiveTreeObject::getChildren);
        produto_view_.getColumns().setAll(nomeCol, precoCol, tagsCol, cardapioCol);
        produto_view_.setRoot(root);
        produto_view_.setShowRoot(false);

        // Adiciona um listener ao treeTableView :
        // Se um produto é selecionado selected_prod é atualizado
        produto_view_.getSelectionModel().selectedItemProperty().
                addListener((obs, oldSelection, newSelection)  -> {
                if (newSelection != null){
                    selected_prod = newSelection.getValue();
                    btn_del_prod_.setDisable(false);
                    btn_add_categoria_.setDisable(false);
                } else {
                    btn_del_prod_.setDisable(true);
                    btn_add_categoria_.setDisable(true);
                }
        });

        txtf_busca_.textProperty().addListener((observable, oldValue, newValue) -> produto_view_
                .setPredicate(produtoViewTreeItem -> produtoViewTreeItem.getValue().nomeProperty()
                        .getValue().contains(newValue)));
    }


    @Override
    public void init() {
        btn_add_categoria_.setDisable(true);
        btn_del_prod_.setDisable(true);
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
