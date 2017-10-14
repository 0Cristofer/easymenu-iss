package com.bccog.EMM.gui.controladores;

import com.bccog.EMM.EMM;
import com.bccog.EMM.bd.entidades.categoria.Categoria;
import com.bccog.EMM.bd.entidades.produto.Produto;
import com.bccog.EMM.bd.exceptions.*;
import com.bccog.EMM.gerenciadores.GerenciadorCategoria;
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

import java.util.List;

/**
 * Classe controladora de tela de categorias
 * @author Guilherme Quental
 * @since 06/10/2017
 */
public class CategoriaController implements BaseController{
    private ScreenController controller_;
    private ProdutoView selected_prod;
    private Categoria selected_cat;

    @FXML
    public JFXTreeTableView<ProdutoView> produto_view_;
    public JFXComboBox<Categoria> cbox_categoria_;
    public JFXButton btn_new_categoria_;
    public JFXButton btn_del_categoria_;
    public JFXButton btn_confirmar_;
    public JFXButton btn_cancelar_;
    public JFXButton btn_del_produto_;
    public JFXTextField txt_nome_;

    public void iniciarSessao(ActionEvent event) {
        controller_.setVisibleScreen("work_screen");
    }

    @FXML public void produtos(){ controller_.setVisibleScreen("produtos");}

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

    public void dadosEstabelecimento(){ controller_.setVisibleScreen("info_estabelecimento");}

    public void removeProduto(){
        Produto p = selected_prod.getProduto_();
        try {
            GerenciadorCategoria.removeProdutoCategoria(selected_cat, p);
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

        selected_cat = null;
        btn_del_produto_.setDisable(true);
        atualizar();
        selecionaCategoria();
    }

    @FXML
    public void confirmar(){
        if(!txt_nome_.getText().equals("")){
            try {
                GerenciadorCategoria.criaCategoria(txt_nome_.getText());
            } catch (NoConnectionException | ForbiddenException | BadRequestException |
                    NotImplementedErrorExcpetion | InternalServerErrorException | NotAuthorizedException |
                    NotFoundException e) {
                e.printStackTrace();
            }

            txt_nome_.setText("");
            txt_nome_.setVisible(false);
            btn_cancelar_.setVisible(false);
            btn_confirmar_.setVisible(false);
            btn_del_categoria_.setDisable(false);
            btn_new_categoria_.setDisable(false);
            atualizar();
        } else{
            System.out.println("sem nome");
        }
    }

    @FXML
    public void cancelar(){
        txt_nome_.setText("");
        txt_nome_.setVisible(false);
        btn_cancelar_.setVisible(false);
        btn_confirmar_.setVisible(false);
        btn_new_categoria_.setDisable(false);
        btn_del_categoria_.setDisable(false);
    }

    @FXML
    public void selecionaCategoria(){
        if (selected_cat == null) return;
        List<Produto> produtos = selected_cat.getProdutos();
        ObservableList<ProdutoView> produtoViews = FXCollections.observableArrayList();

        for (Produto p: produtos) {
            produtoViews.add(new ProdutoView(p));
        }

        JFXTreeTableColumn<ProdutoView, String> nomeCol = new JFXTreeTableColumn<>("Nome");
        nomeCol.setPrefWidth(200);
        nomeCol.setCellValueFactory(param -> param.getValue().getValue().nomeProperty());

        JFXTreeTableColumn<ProdutoView, String> precoCol = new JFXTreeTableColumn<>("Preço");
        precoCol.setPrefWidth(250);
        precoCol.setCellValueFactory(param -> param.getValue().getValue().precoProperty());

        JFXTreeTableColumn<ProdutoView, String> tagsCol = new JFXTreeTableColumn<>("Tags");
        tagsCol.setPrefWidth(200);
        tagsCol.setCellValueFactory(param -> param.getValue().getValue().tagsTextProperty());


        final TreeItem<ProdutoView> root = new RecursiveTreeItem<>(produtoViews, RecursiveTreeObject::getChildren);
        produto_view_.getColumns().setAll(nomeCol, precoCol, tagsCol);
        produto_view_.setRoot(root);
        produto_view_.setShowRoot(false);

        // Adiciona um listener ao treeTableView :
        // Se um produto é selecionado selected_prod é atualizado
        produto_view_.getSelectionModel().selectedItemProperty().
                addListener((obs, oldSelection, newSelection)  -> {
                    if (newSelection != null){
                        selected_prod = newSelection.getValue();
                        btn_del_produto_.setDisable(false);
                    } else {
                        btn_del_produto_.setDisable(true);
                    }
                });
    }

    @FXML
    public void criarCategoria(){
        btn_del_categoria_.setDisable(true);
        btn_new_categoria_.setDisable(true);

        btn_confirmar_.setVisible(true);
        btn_cancelar_.setVisible(true);

        txt_nome_.setVisible(true);
    }

    @FXML
    public void deletarCategoria(){
        if(cbox_categoria_.getSelectionModel().getSelectedItem() == null){
            return;
        }

        List<Categoria> categorias = EMM.getInstance().getUsuarioAtual().
                getEstabelecimento().getCategorias();
        String nome = (cbox_categoria_.getSelectionModel().getSelectedItem().toString());

        for (Categoria c: categorias) {
            if(c.getNome().equals(nome)){

                try {
                    GerenciadorCategoria.deletaCategoria(c);
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
                return;
            }
        }

    }

    @Override
    public void atualizar() {
        List<Categoria> categorias = EMM.getInstance().getUsuarioAtual().getEstabelecimento().getCategorias();
        if(categorias == null) return;
        cbox_categoria_.getItems().clear();
        cbox_categoria_.getItems().addAll(categorias);
    }

    @Override
    public void init() {
        cbox_categoria_.setOnAction(event -> {
            selected_cat = cbox_categoria_.getValue();
            selecionaCategoria();
        });

        btn_del_produto_.setDisable(true);
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
