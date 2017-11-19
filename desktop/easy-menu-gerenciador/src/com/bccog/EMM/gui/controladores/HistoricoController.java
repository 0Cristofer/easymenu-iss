package com.bccog.EMM.gui.controladores;

import com.bccog.EMM.EMM;
import com.bccog.EMM.bd.entidades.categoria.Categoria;
import com.bccog.EMM.bd.entidades.pedido.Pedido;
import com.bccog.EMM.bd.entidades.produto.Produto;
import com.bccog.EMM.bd.exceptions.*;
import com.bccog.EMM.gerenciadores.GerenciadorCategoria;
import com.bccog.EMM.gerenciadores.GerenciadorProdutos;
import com.bccog.EMM.gui.subEntidades.ProdutoView;
import com.bccog.FXController.BaseController;
import com.bccog.FXController.ScreenController;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.BorderPane;

import java.util.List;

/**
 * Controlador da tela de mostragem de produtos
 * @author Bruno Cesar
 * @since 31/05/2017
 */
public class HistoricoController implements BaseController {
    private ScreenController controller_;
    private ProdutoView selected_prod;

    @FXML
    public BorderPane main_pane_;
    public JFXButton btn_produtos_;
    public JFXTreeTableView<ProdutoView> pedidos_view_;
    public JFXButton btn_cadastrar_prod_;
    public JFXTextField txtf_busca_;
    public JFXButton btn_add_categoria_;
    public JFXButton btn_del_prod_;
    public JFXComboBox<Categoria> cbox_categorias_;

    public void dadosEstabelecimento(){ controller_.setVisibleScreen("info_estabelecimento");}

    public void categorias(){ controller_.setVisibleScreen("categorias");}

    public void iniciarSessao(){ controller_.setVisibleScreen("sessao_trabalho");}

    @FXML public void produtos(){
        controller_.setVisibleScreen("produtos");
    }

    @FXML public void cardapios(){ controller_.setVisibleScreen("seleciona_cardapio");}

    @FXML public void historico(){
        //Ja esta na tela
    }

    @FXML public void inicio(){
        controller_.setVisibleScreen("main");
    }

    @FXML public void sair(){
        Platform.exit();
    }


    @Override
    public void atualizar() {

        /*JFXTreeTableColumn<ProdutoView, String> horarioCol = new JFXTreeTableColumn<>("Horario");
        horarioCol.setPrefWidth(250);
        horarioCol.setCellValueFactory(param -> param.getValue().getValue().horarioProperty());

        JFXTreeTableColumn<ProdutoView, String> idCol = new JFXTreeTableColumn<>("ID");
        idCol.setPrefWidth(250);
        idCol.setCellValueFactory(param -> param.getValue().getValue().idProperty());

        JFXTreeTableColumn<ProdutoView, String> nomeCol = new JFXTreeTableColumn<>("Cliente");
        nomeCol.setPrefWidth(200);
        nomeCol.setCellValueFactory(param -> param.getValue().getValue().nomeProperty());

        JFXTreeTableColumn<ProdutoView, String> valorCol = new JFXTreeTableColumn<>("Valor");
        valorCol.setPrefWidth(250);
        valorCol.setCellValueFactory(param -> param.getValue().getValue().valorProperty());*/



        /*JFXTreeTableColumn<ProdutoView, String> tagsCol = new JFXTreeTableColumn<>("Tags");
        tagsCol.setPrefWidth(200);
        tagsCol.setCellValueFactory(param -> param.getValue().getValue().tagsTextProperty());*/

        /*List<Pedido> pedidos = EMM.getInstance().getUsuarioAtual().getEstabelecimento().getPedidos(); //todo
        ObservableList<ProdutoView> produtosv = FXCollections.observableArrayList();

        for (Pedido p : pedidos) {
            produtosv.add(new ProdutoView(p));
        }

        final TreeItem<ProdutoView> root = new RecursiveTreeItem<>(produtosv, RecursiveTreeObject::getChildren);
        pedidos_view_.getColumns().setAll(idCol, nomeCol, horarioCol, valorCol);
        pedidos_view_.setRoot(root);
        pedidos_view_.setShowRoot(false);

        // Adiciona um listener ao treeTableView :
        // Se um produto é selecionado selected_prod é atualizado
        pedidos_view_.getSelectionModel().selectedItemProperty().
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

        txtf_busca_.textProperty().addListener((observable, oldValue, newValue) -> pedidos_view_
                .setPredicate(produtoViewTreeItem -> produtoViewTreeItem.getValue().nomeProperty()
                        .getValue().contains(newValue)));*/
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
        return main_pane_.getPrefWidth();
    }

    @Override
    public double getHeight() {
        return main_pane_.getPrefHeight();
    }
}
