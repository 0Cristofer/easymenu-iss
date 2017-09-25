package com.bccog.EMM.gerenciadores;

import com.bccog.EMM.EMM;
import com.bccog.EMM.bd.entidades.categoria.Categoria;

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
