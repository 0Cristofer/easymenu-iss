package com.bccog.EMM.gerenciadores;

import com.bccog.EMM.EMM;
import com.bccog.EMM.bd.entidades.cardapio.Cardapio;

/**
 * Classe controladora de Cardapios
 * @author Cristofer Oswald
 * @author Bruno Cesar
 * @since 06/06/2017
 */
public class GerenciadorCardapios {
    private static Gerenciador<Cardapio> gerenciador_ = new Gerenciador<>(Cardapio.class, "/estabelecimento/"
            + EMM.getInstance().getUsuarioAtual().getId() + "/" + Cardapio.class.getSimpleName().toLowerCase());

}
