package com.bccog.EMM.gui.controladores;

import com.bccog.EMM.EMM;
import com.bccog.EMM.bd.entidades.categoria.Categoria;
import com.bccog.EMM.bd.entidades.cupons.Cupons;
import com.bccog.EMM.bd.entidades.pedido.Pedido;
import com.bccog.EMM.bd.entidades.produto.Produto;
import com.bccog.EMM.bd.entidades.produto.ProdutoPedido;
import com.bccog.EMM.gui.subEntidades.CuponsView;
import com.bccog.EMM.gui.subEntidades.ProdutoView;
import com.bccog.FXController.BaseController;
import com.bccog.FXController.ScreenController;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Controlador da tela principal
 * @author Bruno Cesar e Diogo Almeida
 * @since 24/05/2017
 */

@SuppressWarnings("ALL")
public class MainController implements BaseController {
    private ScreenController controller_;

    public Label lbl_bemvindo_;
    public BorderPane main_pane_;
    public Button btn_produtos_;
    public Button btn_filtrar;
    public Label lbl_categoria_vend;
    public Label lbl_produto_vend;
    public Label lbl_total_produtos;
    public Label lbl_valor_vendido;
    public Label lbl_erro;
    public Button btn_gerenciador_cupons;
    public JFXTreeTableView<CuponsView> cupom_view;
    public DatePicker date_inicial;
    public DatePicker date_final;

    List<Pedido> pedidos;
    private float valor_vendido;
    private int totalProdutosVendidos;
    int data_inicial = -1;
    int data_final = -1;

    public void iniciarSessao(){ controller_.setVisibleScreen("sessao_trabalho");}

    public void produtos() {
        controller_.setVisibleScreen("produtos");
    }

    public void cardapios() {
        controller_.setVisibleScreen("seleciona_cardapio");
    }

    public void dadosEstabelecimento() {
        controller_.setVisibleScreen("info_estabelecimento");
    }

    public void categorias() {
        controller_.setVisibleScreen("categorias");
    }

    public void historico() {
        controller_.setVisibleScreen("historico");
    }

    public void gerenciador_cupons() {
        controller_.setVisibleScreen("cupons");
    }

    public void sair() {
        Platform.exit();
    }

    @Override
    public void init(){ }

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

    @Override
    public void atualizar() {
        pedidos = EMM.getInstance().getUsuarioAtual().getEstabelecimento().getPedidos();
        lbl_bemvindo_.setText("Bem vindo, " + EMM.getInstance().getUsuarioAtual().getEstabelecimento().getNome());
        produtosVendidos_valorVendido(pedidos, data_inicial, data_final);
        produtoMaisVendido(pedidos, data_inicial, data_final);
        categoriaMaisVendida(pedidos, data_inicial, data_final);


        JFXTreeTableColumn<CuponsView, String> codCol = new JFXTreeTableColumn<>("Codigo");
        codCol.setPrefWidth(120);
        codCol.setCellValueFactory(param -> param.getValue().getValue().nome_Property());


        JFXTreeTableColumn<CuponsView, String> valCol = new JFXTreeTableColumn<>("Desconto");
        valCol.setPrefWidth(100);
        valCol.setCellValueFactory(param -> param.getValue().getValue().valorProperty());

        JFXTreeTableColumn<CuponsView, String> expiraCol = new JFXTreeTableColumn<>("Data");
        expiraCol.setPrefWidth(100);
        expiraCol.setCellValueFactory(param -> param.getValue().getValue().timestampProperty());


        List<Cupons> cupons = EMM.getInstance().getUsuarioAtual().getEstabelecimento().getCupons();
        List<Cupons> cupons_a = new ArrayList<>();
        for(Cupons c : cupons){
            if(c.getTimestamp_() >= DateTime.now().getMillis()){
                cupons_a.add(c);
            }
        }
        ObservableList<CuponsView> cuponsv = FXCollections.observableArrayList();

        for (Cupons c : cupons_a) {
            cuponsv.add(new CuponsView(c));
        }

        final TreeItem<CuponsView> root = new RecursiveTreeItem<>(cuponsv, RecursiveTreeObject::getChildren);
        cupom_view.getColumns().setAll(codCol, valCol, expiraCol);
        cupom_view.setRoot(root);
        cupom_view.setShowRoot(false);
    }

    public void filtrar(){
        if (date_inicial.getValue() == null && date_final.getValue() == null){
            data_final = -1;
            data_inicial = -1;
        } else if(date_inicial.getValue() == null && date_final.getValue() != null) {
            data_inicial = -1;
            data_final = date_final.getValue().getDayOfYear();
        } else if (date_inicial.getValue() != null && date_final.getValue() == null){
            data_inicial = date_inicial.getValue().getDayOfYear();
            data_final = DateTime.now().dayOfYear().get();
        } else {
            data_inicial = date_inicial.getValue().getDayOfYear();
            data_final = date_final.getValue().getDayOfYear();
        }
        lbl_erro.setText("");
        if (data_inicial > data_final) {
            lbl_categoria_vend.setText("");
            lbl_produto_vend.setText("");
            lbl_total_produtos.setText("");
            lbl_valor_vendido.setText("");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erro ao definir as datas!!");
            alert.setHeaderText(null);
            alert.setContentText("Data inicial > Data final\n" + "Por favor, selecione uma data inicial < data final.");
            alert.showAndWait();
            alert.getOnCloseRequest();
        } else {
            produtosVendidos_valorVendido(pedidos, data_inicial, data_final);
            produtoMaisVendido(pedidos, data_inicial, data_final);
            categoriaMaisVendida(pedidos, data_inicial, data_final);
        }
    }

    public void produtosVendidos_valorVendido(List<Pedido> p, int data_inicial, int data_final) {
        totalProdutosVendidos = 0;
        valor_vendido = 0;
        if (!p.isEmpty()) {
            if(data_inicial == -1 && data_final == -1) {
                for (Pedido pedido : p) {
                    valor_vendido += pedido.getValor();
                    totalProdutosVendidos += pedido.getProdutosNoPedido().size();
                }
                lbl_valor_vendido.setText(String.valueOf("R$ " + valor_vendido));
                lbl_total_produtos.setText(String.valueOf(totalProdutosVendidos + " produtos"));
            }
            else if (data_inicial == -1 && data_final != -1){
                for (Pedido pedido : p) {
                    if(new org.joda.time.DateTime(pedido.getTimestamp()).getDayOfYear() <= data_final){
                        valor_vendido += pedido.getValor();
                        totalProdutosVendidos += pedido.getProdutosNoPedido().size();
                    }
                }
                if(valor_vendido == 0 && totalProdutosVendidos == 0){
                    lbl_valor_vendido.setText("Nao ha pedidos!");
                    lbl_total_produtos.setText("Nao ha pedidos!");
                }
                else {
                    lbl_valor_vendido.setText(String.valueOf("R$ " + valor_vendido));
                    lbl_total_produtos.setText(String.valueOf(totalProdutosVendidos + " produtos"));
                }
            }
            else if (data_inicial == data_final){
                for (Pedido pedido : p) {
                    if(new org.joda.time.DateTime(pedido.getTimestamp()).getDayOfYear() == data_inicial){
                        valor_vendido += pedido.getValor();
                        totalProdutosVendidos += pedido.getProdutosNoPedido().size();
                    }
                }
                if(valor_vendido == 0 && totalProdutosVendidos == 0){
                    lbl_valor_vendido.setText("Nao ha pedidos!");
                    lbl_total_produtos.setText("Nao ha pedidos!");
                }
                else {
                    lbl_valor_vendido.setText(String.valueOf("R$ " + valor_vendido));
                    lbl_total_produtos.setText(String.valueOf(totalProdutosVendidos + " produtos"));
                }
            }
            else if (data_inicial != -1 && data_final == -1){
                for (Pedido pedido : p) {
                    if(new org.joda.time.DateTime(pedido.getTimestamp()).getDayOfYear() >=data_inicial){
                        valor_vendido += pedido.getValor();
                        totalProdutosVendidos += pedido.getProdutosNoPedido().size();
                    }
                }

                if(valor_vendido == 0 && totalProdutosVendidos == 0){
                    lbl_valor_vendido.setText("Nao ha pedidos!");
                    lbl_total_produtos.setText("Nao ha pedidos!");
                }
                else {
                    lbl_valor_vendido.setText(String.valueOf("R$ " + valor_vendido));
                    lbl_total_produtos.setText(String.valueOf(totalProdutosVendidos + " produtos"));
                }
            }
            else if(data_inicial != 1 && data_final !=1){
                for (Pedido pedido : p) {
                    if(new org.joda.time.DateTime(pedido.getTimestamp()).getDayOfYear() >=data_inicial && new org.joda.time.DateTime(pedido.getTimestamp()).getDayOfYear() <= data_final){
                        valor_vendido += pedido.getValor();
                        totalProdutosVendidos += pedido.getProdutosNoPedido().size();
                    }
                }

                if(valor_vendido == 0 && totalProdutosVendidos == 0){
                    lbl_valor_vendido.setText("Nao ha pedidos!");
                    lbl_total_produtos.setText("Nao ha pedidos!");
                }
                else {
                    lbl_valor_vendido.setText(String.valueOf("R$ " + valor_vendido));
                    lbl_total_produtos.setText(String.valueOf(totalProdutosVendidos + " produtos"));
                }
            }
        }
        else {
            lbl_valor_vendido.setText("Nao ha pedidos!");
            lbl_total_produtos.setText("Nao ha pedidos!");
        }
    }

    public void produtoMaisVendido( List<Pedido> p, int data_inicial, int data_final) {
        if (!p.isEmpty()) {
            HashMap<Produto, Integer> produtoMap = new HashMap<>();
            int maiorP = 0;
            Produto maiorProduto = null;
            if (data_inicial == -1 && data_final == -1) {
                for (Pedido pedido : p) {
                    for (ProdutoPedido pp : pedido.getProdutosNoPedido()) {
                        if (produtoMap.get(pp.getProduto()) == null) {
                            if (maiorP == 0) {
                                maiorP = 1;
                                maiorProduto = pp.getProduto();
                            }
                            produtoMap.put(pp.getProduto(), 1);
                        } else {
                            int novoValor = produtoMap.get(pp.getProduto()) + 1;
                            if (novoValor > maiorP) {
                                maiorP = novoValor;
                                maiorProduto = pp.getProduto();
                            }
                            produtoMap.put(pp.getProduto(), novoValor);
                        }
                    }
                }
                if (maiorProduto == null) lbl_produto_vend.setText("Nao ha pedidos!");
                else lbl_produto_vend.setText(maiorProduto.getNome());
            } else if (data_inicial == -1 && data_final != -1) {
                for (Pedido pedido : p) {
                    if (new org.joda.time.DateTime(pedido.getTimestamp()).getDayOfYear() <= data_final) {
                        for (ProdutoPedido pp : pedido.getProdutosNoPedido()) {
                            if (produtoMap.get(pp.getProduto()) == null) {
                                if (maiorP == 0) {
                                    maiorP = 1;
                                    maiorProduto = pp.getProduto();
                                }
                                produtoMap.put(pp.getProduto(), 1);
                            } else {
                                int novoValor = produtoMap.get(pp.getProduto()) + 1;
                                if (novoValor > maiorP) {
                                    maiorP = novoValor;
                                    maiorProduto = pp.getProduto();
                                }
                                produtoMap.put(pp.getProduto(), novoValor);
                            }
                        }

                    }
                }
                if (maiorProduto == null) lbl_produto_vend.setText("Nao ha pedidos!");
                else lbl_produto_vend.setText(maiorProduto.getNome());
            } else if (data_inicial == data_final) {
                for (Pedido pedido : p) {
                    if (new org.joda.time.DateTime(pedido.getTimestamp()).getDayOfYear() == data_inicial) {
                        for (ProdutoPedido pp : pedido.getProdutosNoPedido()) {
                            if (produtoMap.get(pp.getProduto()) == null) {
                                if (maiorP == 0) {
                                    maiorP = 1;
                                    maiorProduto = pp.getProduto();
                                }
                                produtoMap.put(pp.getProduto(), 1);
                            } else {
                                int novoValor = produtoMap.get(pp.getProduto()) + 1;
                                if (novoValor > maiorP) {
                                    maiorP = novoValor;
                                    maiorProduto = pp.getProduto();
                                }
                                produtoMap.put(pp.getProduto(), novoValor);
                            }
                        }
                    }
                }
                if (maiorProduto == null) lbl_produto_vend.setText("Nao ha pedidos!");
                else lbl_produto_vend.setText(maiorProduto.getNome());
            } else if (data_inicial != -1 && data_final == -1) {
                for (Pedido pedido : p) {
                    if (new org.joda.time.DateTime(pedido.getTimestamp()).getDayOfYear() >= data_inicial) {
                        for (ProdutoPedido pp : pedido.getProdutosNoPedido()) {
                            if (produtoMap.get(pp.getProduto()) == null) {
                                if (maiorP == 0) {
                                    maiorP = 1;
                                    maiorProduto = pp.getProduto();
                                }
                                produtoMap.put(pp.getProduto(), 1);
                            } else {
                                int novoValor = produtoMap.get(pp.getProduto()) + 1;
                                if (novoValor > maiorP) {
                                    maiorP = novoValor;
                                    maiorProduto = pp.getProduto();
                                }
                                produtoMap.put(pp.getProduto(), novoValor);
                            }
                        }
                    }
                }
                if (maiorProduto == null) lbl_produto_vend.setText("Nao ha pedidos!");
                else lbl_produto_vend.setText(maiorProduto.getNome());
            } else if(data_inicial != 1 && data_final !=1){
                for (Pedido pedido : p) {
                    if(new org.joda.time.DateTime(pedido.getTimestamp()).getDayOfYear() >=data_inicial && new org.joda.time.DateTime(pedido.getTimestamp()).getDayOfYear() <= data_final){
                        for (ProdutoPedido pp : pedido.getProdutosNoPedido()) {
                            if (produtoMap.get(pp.getProduto()) == null) {
                                if (maiorP == 0) {
                                    maiorP = 1;
                                    maiorProduto = pp.getProduto();
                                }
                                produtoMap.put(pp.getProduto(), 1);
                            } else {
                                int novoValor = produtoMap.get(pp.getProduto()) + 1;
                                if (novoValor > maiorP) {
                                    maiorP = novoValor;
                                    maiorProduto = pp.getProduto();
                                }
                                produtoMap.put(pp.getProduto(), novoValor);
                            }
                        }
                    }
                }
                if (maiorProduto == null) lbl_produto_vend.setText("Nao ha pedidos!");
                else lbl_produto_vend.setText(maiorProduto.getNome());
            }
        } else lbl_produto_vend.setText("Nao ha pedidos!");
    }

    public void categoriaMaisVendida (List<Pedido> p, int data_inicial, int data_final){
        if(!p.isEmpty()){
            HashMap<Categoria, Integer> categoriaMap = new HashMap<>();
            int maiorC = 0;
            Categoria maiorCategoria = null;
            if (data_inicial == -1 && data_final == -1){
                for (Pedido pedido : p) {
                    for (ProdutoPedido pp : pedido.getProdutosNoPedido()) {
                        for (Categoria categoria : EMM.getInstance().getUsuarioAtual().getEstabelecimento().getCategorias()) {
                            if (categoria.getProdutos().contains(pp.getProduto())) {
                                if (categoriaMap.get(categoria) == null) {
                                    if (maiorC == 0) {
                                        maiorC = 1;
                                        maiorCategoria = categoria;
                                    }
                                    categoriaMap.put(categoria, 1);
                                }
                                else {
                                    int novoValor = categoriaMap.get(categoria) + 1;
                                    if (novoValor > maiorC) {
                                        maiorC = novoValor;
                                        maiorCategoria = categoria;
                                    }
                                    categoriaMap.put(categoria, novoValor);
                                }
                            }
                        }
                    }
                }
                if (maiorCategoria == null)lbl_categoria_vend.setText("Nao ha pedidos!");
                else lbl_categoria_vend.setText(maiorCategoria.getNome());
            }
            else if (data_inicial == -1 && data_final != -1) {
                for (Pedido pedido : p) {
                    if (new org.joda.time.DateTime(pedido.getTimestamp()).getDayOfYear() <= data_final) {
                        for (ProdutoPedido pp : pedido.getProdutosNoPedido()) {
                            for (Categoria categoria : EMM.getInstance().getUsuarioAtual().getEstabelecimento().getCategorias()) {
                                if (categoria.getProdutos().contains(pp.getProduto())) {
                                    if (categoriaMap.get(categoria) == null) {
                                        if (maiorC == 0) {
                                            maiorC = 1;
                                            maiorCategoria = categoria;
                                        }
                                        categoriaMap.put(categoria, 1);
                                    }
                                    else {
                                        int novoValor = categoriaMap.get(categoria) + 1;
                                        if (novoValor > maiorC) {
                                            maiorC = novoValor;
                                            maiorCategoria = categoria;
                                        }
                                        categoriaMap.put(categoria, novoValor);
                                    }
                                }
                            }
                        }
                    }
                    if (maiorCategoria == null)lbl_categoria_vend.setText("Nao ha pedidos!");
                    else lbl_categoria_vend.setText(maiorCategoria.getNome());
                }
            }
            else if (data_inicial == data_final) {
                for (Pedido pedido : p) {
                    if (new org.joda.time.DateTime(pedido.getTimestamp()).getDayOfYear() == data_inicial) {
                        for (ProdutoPedido pp : pedido.getProdutosNoPedido()) {
                            for (Categoria categoria : EMM.getInstance().getUsuarioAtual().getEstabelecimento().getCategorias()) {
                                if (categoria.getProdutos().contains(pp.getProduto())) {
                                    if (categoriaMap.get(categoria) == null) {
                                        if (maiorC == 0) {
                                            maiorC = 1;
                                            maiorCategoria = categoria;
                                        }
                                        categoriaMap.put(categoria, 1);
                                    }
                                    else {
                                        int novoValor = categoriaMap.get(categoria) + 1;
                                        if (novoValor > maiorC) {
                                            maiorC = novoValor;
                                            maiorCategoria = categoria;
                                        }
                                        categoriaMap.put(categoria, novoValor);
                                    }
                                }
                            }
                        }
                    }
                }
                if (maiorCategoria == null)lbl_categoria_vend.setText("Nao ha pedidos!");
                else lbl_categoria_vend.setText(maiorCategoria.getNome());
            } else if (data_inicial != -1 && data_final == -1) {
                for (Pedido pedido : p) {
                    if (new org.joda.time.DateTime(pedido.getTimestamp()).getDayOfYear() >= data_inicial) {
                        for (ProdutoPedido pp : pedido.getProdutosNoPedido()) {
                            for (Categoria categoria : EMM.getInstance().getUsuarioAtual().getEstabelecimento().getCategorias()) {
                                if (categoria.getProdutos().contains(pp.getProduto())) {
                                    if (categoriaMap.get(categoria) == null) {
                                        if (maiorC == 0) {
                                            maiorC = 1;
                                            maiorCategoria = categoria;
                                        }
                                        categoriaMap.put(categoria, 1);
                                    }
                                    else {
                                        int novoValor = categoriaMap.get(categoria) + 1;
                                        if (novoValor > maiorC) {
                                            maiorC = novoValor;
                                            maiorCategoria = categoria;
                                        }
                                        categoriaMap.put(categoria, novoValor);
                                    }
                                }
                            }
                        }
                    }
                }
                if (maiorCategoria == null)lbl_categoria_vend.setText("Nao ha pedidos!");
                else lbl_categoria_vend.setText(maiorCategoria.getNome());
            } else if(data_inicial != 1 && data_final !=1) {
                for (Pedido pedido : p) {
                    if (new org.joda.time.DateTime(pedido.getTimestamp()).getDayOfYear() >= data_inicial && new org.joda.time.DateTime(pedido.getTimestamp()).getDayOfYear() <= data_final) {
                        for (ProdutoPedido pp : pedido.getProdutosNoPedido()) {
                            for (Categoria categoria : EMM.getInstance().getUsuarioAtual().getEstabelecimento().getCategorias()) {
                                if (categoria.getProdutos().contains(pp.getProduto())) {
                                    if (categoriaMap.get(categoria) == null) {
                                        if (maiorC == 0) {
                                            maiorC = 1;
                                            maiorCategoria = categoria;
                                        }
                                        categoriaMap.put(categoria, 1);
                                    } else {
                                        int novoValor = categoriaMap.get(categoria) + 1;
                                        if (novoValor > maiorC) {
                                            maiorC = novoValor;
                                            maiorCategoria = categoria;
                                        }
                                        categoriaMap.put(categoria, novoValor);
                                    }
                                }
                            }
                        }
                    }
                }

                if (maiorCategoria == null) lbl_categoria_vend.setText("Nao ha pedidos!");
                else lbl_categoria_vend.setText(maiorCategoria.getNome());
            }
            else {
            lbl_categoria_vend.setText("Nao ha pedidos!");
            }
        }
    }

    public void totalProdutosVendidos_valorTotal(List<Pedido> listaDePedidos) {
        totalProdutosVendidos = 0;
        valor_vendido = 0;
        if (!listaDePedidos.isEmpty()){
            for (Pedido pedido : listaDePedidos) {
                valor_vendido += pedido.getValor();
                totalProdutosVendidos += pedido.getProdutosNoPedido().size();
            }
            lbl_valor_vendido.setText(String.valueOf("R$ " + valor_vendido));
            lbl_total_produtos.setText(String.valueOf(totalProdutosVendidos + " produtos"));
        }
        else {
            lbl_valor_vendido.setText("Nao ha pedidos!");
            lbl_total_produtos.setText("Nao ha pedidos!");
        }
    }

}