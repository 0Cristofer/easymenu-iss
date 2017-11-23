package com.bccog.EMM.gui.controladores;

import com.bccog.EMM.EMM;
import com.bccog.EMM.bd.entidades.pedido.Pedido;
import com.bccog.EMM.gui.subEntidades.PedidoView;
import com.bccog.FXController.BaseController;
import com.bccog.FXController.ScreenController;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.BorderPane;
import org.joda.time.DateTime;

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
    public Label lbl_id;
    public Label lbl_data_recebido;
    public Label lbl_cliente_nome;
    public Label lbl_finalizado;
    public Label lbl_valor;
    public TextArea txtA_desc;

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

        JFXTreeTableColumn<PedidoView, String> idCol = new JFXTreeTableColumn<>("Data");
        idCol.setPrefWidth(150);
        idCol.setCellValueFactory(param -> param.getValue().getValue().data_Property());

        JFXTreeTableColumn<PedidoView, String> horarioCol = new JFXTreeTableColumn<>("H. Recebido");
        horarioCol.setPrefWidth(150);
        horarioCol.setCellValueFactory(param -> param.getValue().getValue().horario_recebido_Property());

        JFXTreeTableColumn<PedidoView, String> horarioFCol = new JFXTreeTableColumn<>("H. Finalizado");
        horarioFCol.setPrefWidth(150);
        horarioFCol.setCellValueFactory(param -> param.getValue().getValue().horario_finalizado_Property());

        JFXTreeTableColumn<PedidoView, String> nomeCol = new JFXTreeTableColumn<>("Cliente");
        nomeCol.setPrefWidth(250);
        nomeCol.setCellValueFactory(param -> param.getValue().getValue().cliente_nome_Property());

        JFXTreeTableColumn<PedidoView, String> valorCol = new JFXTreeTableColumn<>("Valor");
        valorCol.setPrefWidth(150);
        valorCol.setCellValueFactory(param -> param.getValue().getValue().valor_Property());


        List<Pedido> pedidos = EMM.getInstance().getUsuarioAtual().getEstabelecimento().getPedidos();
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
    }


    public void display(PedidoView pv){
        Pedido p = pv.getPedido_();

        /**
         *   public Label lbl_id;
         public Label lbl_data_recebido;
         public Label lbl_cliente_nome;
         public Label lbl_finalizado;
         public Label lbl_valor;
         public TextArea txtA_desc;
         */

        lbl_id.setText("Pedido: " + p.getId());
        lbl_cliente_nome.setText("Cliente: " + p.getCliente().getNome());
        lbl_valor.setText("Valor total R$ " + Float.toString(p.getValor()));
        lbl_finalizado.setText("Finalizado as " + pv.getDt_finalizado().getHourOfDay() + ":" + pv.getDt_finalizado().getMinuteOfHour());

        lbl_data_recebido.setText(pv.getDt_recebido().getDayOfMonth() + "/" +
                                  pv.getDt_recebido().getMonthOfYear() + "/" +
                                  pv.getDt_recebido().getYear() + " as " +
                                  pv.getDt_recebido().getHourOfDay() + ":" +
                                  pv.getDt_recebido().getMinuteOfHour());

        txtA_desc.setText(p.produtosToString());
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
