package com.bccog.EMM.gerenciadores;

import com.bccog.EMM.EMM;
import com.bccog.EMM.bd.entidades.cupons.Cupons;
import com.bccog.EMM.bd.entidades.usuario.Usuario;
import com.bccog.EMM.bd.exceptions.*;
import com.bccog.EMM.gerenciadores.exceptions.MissingPriceException;
import com.bccog.EMM.gerenciadores.exceptions.NegativePriceException;
import com.bccog.EMM.bd.entidades.categoria.Categoria;

import java.util.List;
import java.util.Set;


/**
 * Gerenciador de categorias
 * @author Guilherme Quental
 *
 * @since 23/10/2017
 */


public class GerenciadorCupons {
    private static Gerenciador<Cupons> gerenciador_= new Gerenciador<>(Cupons.class,
            "/estabelecimento/" + EMM.getInstance().getUsuarioAtual().getId()
                    + "/" + Cupons.class.getSimpleName().toLowerCase());


    public static void cadastrarCupons(String nome, long timestamp, float valor, Categoria categoria) throws ForbiddenException, BadRequestException,
            NotImplementedErrorExcpetion, InternalServerErrorException, NotFoundException, NotAuthorizedException,
            NegativePriceException, MissingPriceException, NoConnectionException {
        Usuario usuario = EMM.getInstance().getUsuarioAtual();

        if(valor < 0){
            throw new NegativePriceException();
        }
        if(valor == 0){
            throw new MissingPriceException();
        }

        Cupons cupons = new Cupons(nome, timestamp, valor, categoria);
        gerenciador_.criar(cupons);
        gerenciador_.relaciona(cupons,categoria);
        usuario.getEstabelecimento().getCupons().add(cupons);
    }

    public static Set<String> getCategoriaId(Cupons c){
        return gerenciador_.getKeys(c.getClass().getSimpleName().toLowerCase() + "/" + c.getId() + "/" +
                Categoria.class.getSimpleName().toLowerCase() + "/");
    }


    public static List<Cupons> getCupons(){
        return gerenciador_.get();
    }

}