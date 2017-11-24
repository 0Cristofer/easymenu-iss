package com.bccog.EMM.gui.controladores;

import com.bccog.EMM.EMM;
import com.bccog.EMM.bd.entidades.pedido.Pedido;
import com.bccog.EMM.bd.entidades.produto.Produto;
import com.bccog.EMM.bd.entidades.produto.ProdutoPedido;
import com.bccog.EMM.gui.subEntidades.ProdutoView;
import com.bccog.FXController.BaseController;
import com.bccog.FXController.ScreenController;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;
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

    List<Pedido> pedidos;
    int total_Categorias;
    int total_Produtos;
    private float lucro;
    private int totalProdutos;
    private int categoriaVendida;
    private int produtoVendido;

    public void produtos(){
        controller_.setVisibleScreen("produtos");
    }

    public void cardapios(){ controller_.setVisibleScreen("seleciona_cardapio");}

    public void dadosEstabelecimento(){ controller_.setVisibleScreen("info_estabelecimento");}

    public void categorias(){ controller_.setVisibleScreen("categorias");}

    public void historico(){ controller_.setVisibleScreen("historico");}

    public void gerenciador_cupons(){ controller_.setVisibleScreen("cupons"); }

    public void sair(){
        Platform.exit();
    }

    @Override
    public void atualizar() {
        pedidos = EMM.getInstance().getUsuarioAtual().getEstabelecimento().getPedidos();

        lbl_bemvindo_.setText("Bem vindo, " + EMM.getInstance().getUsuarioAtual().getEstabelecimento().getNome());

        total_Categorias = EMM.getInstance().getUsuarioAtual().getEstabelecimento().numeroCategorias();
        total_Produtos = EMM.getInstance().getUsuarioAtual().getEstabelecimento().numeroProdutos();
//        lucro = 0;
//        total_Produtos = 0;
//
//        for (Pedido p: pedidos){
//            lucro += p.getValor();
//        }
//        System.out.println(lucro);
//        lbl_lucro.setText(String.valueOf("R$ " + lucro));
//        lbl_total_produtos.setText(String.valueOf(totalProdutos));
    }


    @Override
    public void init(){
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

    public void iniciarSessao(){ controller_.setVisibleScreen("sessao_trabalho");}
}