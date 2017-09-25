package com.bccog.EMM.gerenciadores;

import com.bccog.EMM.EMM;
import com.bccog.EMM.bd.entidades.cardapio.Cardapio;
import com.bccog.EMM.bd.entidades.categoria.Categoria;
import com.bccog.EMM.bd.entidades.produto.Produto;
import com.bccog.EMM.bd.entidades.usuario.Usuario;
import com.bccog.EMM.bd.exceptions.*;
import org.jdeferred.ProgressCallback;
import org.restonfire.responses.StreamingEvent;

import java.util.List;
import java.util.Set;

/**
 * Gerenciador de categorias
 * @author Cristofer Oswald
 *
 * @since 07/06/2017
 */
public class GerenciadorCategoria {
    private static Gerenciador<Categoria> gerenciador_ = new Gerenciador<>(Categoria.class,
            "/estabelecimento/" + EMM.getInstance().getUsuarioAtual().getId() + "/"
                    + Categoria.class.getSimpleName().toLowerCase());
}
