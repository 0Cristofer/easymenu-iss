package com.bccog.EMM.gui.controladores;

import com.bccog.EMM.bd.entidades.pedido.Pedido;
import com.bccog.EMM.gui.subEntidades.PedidoView;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Controlador da tela de mostragem de produtos
 * @author Bruno Cesar
 * @since 31/05/2017
 */
public class HistoricoController implements BaseController {
    private ScreenController controller_;
    private PedidoView selected_pedido_;

    @FXML
    public BorderPane main_pane_;
    public JFXButton btn_produtos_;
    public JFXTreeTableView<PedidoView> pedidos_view_;
    public JFXTextField txtf_busca_;
    public JFXButton btn_add_categoria_;
    public JFXButton btn_del_prod_;

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

        JFXTreeTableColumn<PedidoView, String> idCol = new JFXTreeTableColumn<>("ID");
        idCol.setPrefWidth(50);
        idCol.setCellValueFactory(param -> param.getValue().getValue().id_Property());

        JFXTreeTableColumn<PedidoView, String> horarioCol = new JFXTreeTableColumn<>("H. Recebido");
        horarioCol.setPrefWidth(150);
        horarioCol.setCellValueFactory(param -> param.getValue().getValue().horario_recebido_Property());

        JFXTreeTableColumn<PedidoView, String> horarioFCol = new JFXTreeTableColumn<>("H. Finalizado");
        horarioFCol.setPrefWidth(150);
        horarioFCol.setCellValueFactory(param -> param.getValue().getValue().horario_finalizado_Property());

        JFXTreeTableColumn<PedidoView, String> nomeCol = new JFXTreeTableColumn<>("Cliente");
        nomeCol.setPrefWidth(300);
        nomeCol.setCellValueFactory(param -> param.getValue().getValue().cliente_nome_Property());

        JFXTreeTableColumn<PedidoView, String> valorCol = new JFXTreeTableColumn<>("Valor");
        valorCol.setPrefWidth(150);
        valorCol.setCellValueFactory(param -> param.getValue().getValue().valor_Property());


        //List<Pedido> pedidos = EMM.getInstance().getUsuarioAtual().getEstabelecimento().getPedidos(); //todo
        List<Pedido> pedidos = new ArrayList<>();
        ObservableList<PedidoView> pedidosv = FXCollections.observableArrayList();

        for (Pedido p : pedidos) {
            pedidosv.add(new PedidoView(p));
        }

        final TreeItem<PedidoView> root = new RecursiveTreeItem<>(pedidosv, RecursiveTreeObject::getChildren);
        pedidos_view_.getColumns().setAll(idCol, nomeCol, valorCol, horarioCol, horarioFCol);
        pedidos_view_.setRoot(root);
        pedidos_view_.setShowRoot(false);

        // Adiciona um listener ao treeTableView :
        // Se um pedido e atualizado deve atualizar a exibiçao
        pedidos_view_.getSelectionModel().selectedItemProperty().
                addListener((obs, oldSelection, newSelection)  -> {
                if (newSelection != null){
                    selected_pedido_ = newSelection.getValue();
                    display(selected_pedido_);
                } else {
                    selected_pedido_ = null;
                }
        });

        /*txtf_busca_.textProperty().addListener((observable, oldValue, newValue) -> pedidos_view_
                .setPredicate(produtoViewTreeItem -> produtoViewTreeItem.getValue().nomeProperty()
                        .getValue().contains(newValue)));*/
    }


    public void display(PedidoView pv){
        Pedido p = pv.getPedido_();

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
