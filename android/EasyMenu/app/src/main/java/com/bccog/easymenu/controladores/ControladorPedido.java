package com.bccog.easymenu.controladores;

import android.widget.TextView;

import com.bccog.easymenu.entidades.pedido.Pedido;
import com.bccog.easymenu.entidades.pedido.Status;
import com.bccog.easymenu.entidades.produto.Produto;
import com.bccog.easymenu.entidades.produto.ProdutoComTamanho;
import com.bccog.easymenu.entidades.produto.ProdutoPedido;
import com.bccog.easymenu.entidades.produto.ProdutoPrecoUnico;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by crisofer on 26/11/17.
 */

public class ControladorPedido {
    private static Pedido pedido = null;
    private static List<List<ProdutoPedido>> produtos_pedidos = null;
    private static int total = 0;
    private static float valor_total = 0f;
    private static TextView txt_total;

    public static void setTxtTotal(TextView total){
        txt_total = total;
    }

    public static void adicionarProdutoPedido(Produto p, int quantidade, ProdutoPedido.TamanhoProduto tamanho){
        total = total + quantidade;
        switch (tamanho){
            case PEQUENO:
                valor_total = valor_total + ((ProdutoComTamanho)p).getPrecoP()*quantidade;
                break;
            case MEDIO:
                valor_total = valor_total + ((ProdutoComTamanho)p).getPrecoM()*quantidade;
                break;
            case GRANDE:
                valor_total = valor_total + ((ProdutoComTamanho)p).getPrecoG()*quantidade;
                break;
            case UNICO:
                valor_total = valor_total + ((ProdutoPrecoUnico)p).getPreco()*quantidade;
                break;
        }
        txt_total.setText(String.valueOf(total));

        List<ProdutoPedido> pedido_atual = new ArrayList<>();
        if(pedido == null){
            pedido = new Pedido(ControladorCliente.getClienteAtual(), DateTime.now().getMillis(), false,
                    "", null, "", Status.RECEBIDO, new ArrayList<ProdutoPedido>(),
                    ControladorEstabelecimento.getAtual().getId());
        }

        if(produtos_pedidos == null){
            produtos_pedidos = new ArrayList<>();
        }

        produtos_pedidos.add(pedido_atual);
        ProdutoPedido pp;
        for(int i = 0; i < quantidade; i++){
            pp = new ProdutoPedido(p.getNome(), p, tamanho);
            pedido_atual.add(pp);
            pedido.adicionarProdutoPedido(pp);
        }
    }

    public static List<List<ProdutoPedido>> getPedidos(){
        return produtos_pedidos;
    }

    public static void removerPedido(List<ProdutoPedido> produtos){
        total = total - produtos.size();
        switch (produtos.get(0).getTamanho()){
            case PEQUENO:
                valor_total = valor_total - ((ProdutoComTamanho)produtos.get(0).getProdutoO()).getPrecoP()*produtos.size();
                break;
            case MEDIO:
                valor_total = valor_total - ((ProdutoComTamanho)produtos.get(0).getProdutoO()).getPrecoM()*produtos.size();
                break;
            case GRANDE:
                valor_total = valor_total - ((ProdutoComTamanho)produtos.get(0).getProdutoO()).getPrecoG()*produtos.size();
                break;
            case UNICO:
                valor_total = valor_total - ((ProdutoPrecoUnico)produtos.get(0).getProdutoO()).getPreco()*produtos.size();
                break;
        }
        txt_total.setText(String.valueOf(total));

        for(ProdutoPedido p : produtos){
            pedido.getProdutosNoPedido().remove(p);
        }
        produtos_pedidos.remove(produtos);
    }

    public static void fecharPedido(){
        DatabaseReference datapedido_reference = FirebaseDatabase.getInstance().getReference("/" + Pedido.class.getSimpleName().toLowerCase());
        DatabaseReference pedido_e_reference = FirebaseDatabase.getInstance().
                getReference("/estabelecimento/"+ControladorEstabelecimento.getAtual().getId()).child(Pedido.class.getSimpleName().toLowerCase());
        DatabaseReference pedido_c_reference = FirebaseDatabase.getInstance()
                .getReference("/cliente/" + ControladorCliente.getClienteAtual().getId()).child(Pedido.class.getSimpleName().toLowerCase());

        pedido.calcularPreco();

        DatabaseReference new_datapedido_reference = datapedido_reference.push();
        String key = new_datapedido_reference.getKey();
        new_datapedido_reference.setValue(pedido);
        Map<String, Object> map = new HashMap<>();
        map.put(key, true);
        pedido_e_reference.updateChildren(map);
        pedido_c_reference.updateChildren(map);

        total = 0;
        valor_total = 0f;
        txt_total.setText("0");
        pedido = null;
        produtos_pedidos.clear();
    }

    public static int getTotal() {
        return total;
    }

    public static float getValorTotal() {
        return valor_total;
    }
}
