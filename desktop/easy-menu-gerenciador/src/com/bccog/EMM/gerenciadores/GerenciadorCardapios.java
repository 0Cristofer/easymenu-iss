package com.bccog.EMM.gerenciadores;

import com.bccog.EMM.EMM;
import com.bccog.EMM.bd.entidades.cardapio.Cardapio;
import com.bccog.EMM.bd.entidades.categoria.Categoria;
import com.bccog.EMM.bd.entidades.usuario.Usuario;
import com.bccog.EMM.bd.exceptions.*;
import org.jdeferred.ProgressCallback;
import org.restonfire.responses.StreamingEvent;

import java.util.List;

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
