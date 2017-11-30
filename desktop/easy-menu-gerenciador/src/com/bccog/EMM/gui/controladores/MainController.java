package com.bccog.EMM.gui.controladores;

import com.bccog.EMM.EMM;
import com.bccog.EMM.bd.entidades.categoria.Categoria;
import com.bccog.EMM.bd.entidades.pedido.Pedido;
import com.bccog.EMM.bd.entidades.produto.Produto;
import com.bccog.EMM.bd.entidades.produto.ProdutoPedido;
import com.bccog.EMM.gui.subEntidades.ProdutoView;
import com.bccog.FXController.BaseController;
import com.bccog.FXController.ScreenController;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import java.util.HashMap;
import java.util.List;

/**
 * Controlador da tela principal
 * @author Bruno Cesar e Diogo Almeida
 * @since 24/05/2017
 */

public class MainController implements BaseController {
    private ScreenController controller_;

    public Label lbl_bemvindo_;
    public BorderPane main_pane_;
    public Button btn_produtos_;
    public Label lbl_categoria_vend;
    public Label lbl_produto_vend;
    public Label lbl_total_produtos;
    public Label lbl_lucro;
    public Button btn_gerenciador_cupons;
    public JFXTreeTableView<ProdutoView> cupom_view;
    public DatePicker date_inicial;
    public DatePicker date_final;

    List<Pedido> pedidos;
    private float lucro;
    private int totalProdutosVendidos;

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
        totalProdutosVendidos_Lucro(pedidos);
        produtoMaisVendido(pedidos);
        categoriaMaisVendida(pedidos);
    }

    public void totalProdutosVendidos_Lucro(List<Pedido> p) {
        totalProdutosVendidos = 0;
        lucro = 0;
        if (!p.isEmpty()) {
            for (Pedido pedido : p) {
                lucro += pedido.getValor();
                totalProdutosVendidos += pedido.getProdutosNoPedido().size();
            }
            lbl_lucro.setText(String.valueOf("R$ " + lucro));
            lbl_total_produtos.setText(String.valueOf(totalProdutosVendidos + " produtos"));
        }
        else {
            lbl_lucro.setText("Nao ha pedidos!");
            lbl_total_produtos.setText("Nao ha pedidos!");
        }
    }

    public void produtoMaisVendido( List<Pedido> p){
        if(!p.isEmpty()){
            HashMap<Produto, Integer> produtoMap = new HashMap<>();
            int maiorP = 0;
            Produto maiorProduto = null;
            for (Pedido pedido : p) {
                for (ProdutoPedido pp : pedido.getProdutosNoPedido()) {
                    if (produtoMap.get(pp.getProduto()) == null) {
                        if (maiorP == 0) {
                            maiorP = 1;
                            maiorProduto = pp.getProduto();
                        }
                        produtoMap.put(pp.getProduto(), 1);
                    }
                    else {
                        int novoValor = produtoMap.get(pp.getProduto()) + 1;
                        if (novoValor > maiorP) {
                            maiorP = novoValor;
                            maiorProduto = pp.getProduto();
                        }
                        produtoMap.put(pp.getProduto(), novoValor);
                    }
                }
            }
            lbl_produto_vend.setText(maiorProduto.getNome());
        }
        else {
            lbl_produto_vend.setText("Nao ha pedidos!");
        }
    }

    public void categoriaMaisVendida (List<Pedido> p){
        if(!p.isEmpty()){
            HashMap<Categoria, Integer> categoriaMap = new HashMap<>();
            int maiorC = 0;
            Categoria maiorCategoria = null;

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
            lbl_categoria_vend.setText(maiorCategoria.getNome());

        }
        else {
            lbl_categoria_vend.setText("Nao ha pedidos!");
        }
    }
}

