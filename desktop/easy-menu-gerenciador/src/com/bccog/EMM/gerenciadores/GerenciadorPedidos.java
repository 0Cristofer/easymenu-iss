package com.bccog.EMM.gerenciadores;

import com.bccog.EMM.EMM;
import com.bccog.EMM.bd.BancoDeDados;
import com.bccog.EMM.bd.entidades.pedido.Pedido;
import com.bccog.EMM.bd.entidades.produto.ProdutoComTamanho;
import com.bccog.EMM.bd.entidades.produto.ProdutoPedido;
import com.bccog.EMM.bd.entidades.produto.ProdutoPrecoUnico;
import com.bccog.EMM.bd.entidades.usuario.Usuario;
import com.bccog.EMM.bd.exceptions.*;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.collections.ObservableList;
import org.jdeferred.ProgressCallback;
import org.restonfire.responses.StreamingEvent;

import java.util.List;
import java.util.Map;

/**
 * Gerenciador de pedidos
 * @author Cristofer Oswald
 * @since 25/07/17
 */
public class GerenciadorPedidos {
    private static Gerenciador<Pedido> gerenciador = new Gerenciador<>(Pedido.class,
            "/estabelecimento/" + EMM.getInstance().getUsuarioAtual().getId()
                    + "/" + Pedido.class.getSimpleName().toLowerCase());
}
