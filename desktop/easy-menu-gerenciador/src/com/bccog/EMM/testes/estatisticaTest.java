package com.bccog.EMM.testes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.bccog.EMM.bd.entidades.pedido.Pedido;
import com.bccog.EMM.bd.entidades.produto.Produto;
import com.bccog.EMM.bd.entidades.produto.ProdutoPedido;
import com.bccog.EMM.bd.entidades.produto.ProdutoPrecoUnico;
import com.bccog.EMM.gui.controladores.MainController;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class estatisticaTest {

    @Test
    public void totalProdutosVendidos_valorTotal(){
        MainController testeMainController = new MainController();
        List<Pedido> listaDePedidos = new ArrayList<>();
        Produto p1 = new ProdutoPrecoUnico("coxinha", "coxinha gostosa", 50.00f);
        Produto p2 = new ProdutoPrecoUnico("coca", "coca gostosa", 50.00f);
        List<ProdutoPedido> pp = new ArrayList<>();
        Pedido pedido1 = new Pedido(50.00f, pp);
        Pedido pedido2 = new Pedido(50.00f, pp);

        listaDePedidos.add(pedido1);
        listaDePedidos.add(pedido2);
        assertEquals(100.00f, testeMainController.totalProdutosVendidos_valorTotal(listaDePedidos), "Espera-se o valor total de 100.00");
    }
}
