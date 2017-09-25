package com.bccog.EMM.gerenciadores;

import com.bccog.EMM.EMM;
import com.bccog.EMM.bd.entidades.pedido.Pedido;

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
