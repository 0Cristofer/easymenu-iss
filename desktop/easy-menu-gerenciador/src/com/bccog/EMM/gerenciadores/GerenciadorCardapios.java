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
 * @author Diogo Almeida
 * @since 12/10/2017
 */
public class GerenciadorCardapios {
    private static Gerenciador<Cardapio> gerenciador_ = new Gerenciador<>(Cardapio.class, "/estabelecimento/"
            + EMM.getInstance().getUsuarioAtual().getId() + "/" + Cardapio.class.getSimpleName().toLowerCase());

    /**
     * Carrega a lista de cardápios
     * @return Todos os cardapios cadastrados
     */
    public static List<Cardapio> getCardapios(){
        return gerenciador_.get();
    }

    /**
     * Cria um cardápio
     * @param nome do cardapio a ser criado
     * @throws NoConnectionException Sem internet
     * @throws NotImplementedErrorExcpetion Erro não implementado
     * @throws NotAuthorizedException Não auorizado
     * @throws InternalServerErrorException Erro interno do servidor
     * @throws ForbiddenException Não encontrado
     * @throws BadRequestException Request errado
     * @throws NotFoundException Não encontrado
     */
    public static void criarCardapio(String nome) throws NoConnectionException, ForbiddenException, BadRequestException,
            NotImplementedErrorExcpetion, InternalServerErrorException, NotFoundException, NotAuthorizedException {
        Usuario usuario = EMM.getInstance().getUsuarioAtual();

        Cardapio cardapio = new Cardapio(nome);
        gerenciador_.criar(cardapio);

        usuario.getEstabelecimento().getCardapios().add(cardapio);
    }

    /**
     * Atualiza um cardápio no BD
     * @param cardapio O cardápio a ser atualizado
     * @throws NoConnectionException Sem internet
     * @throws NotImplementedErrorExcpetion Erro não implementado
     * @throws NotAuthorizedException Não auorizado
     * @throws InternalServerErrorException Erro interno do servidor
     * @throws ForbiddenException Não encontrado
     * @throws BadRequestException Request errado
     * @throws NotFoundException Não encontrado
     */
    public static void atualizaCardapio(Cardapio cardapio) throws NoConnectionException, ForbiddenException,
            BadRequestException, NotImplementedErrorExcpetion, InternalServerErrorException, NotFoundException,
            NotAuthorizedException {
        gerenciador_.atualizar(cardapio);
    }

    /**
     * Deleta um cardápio do BD
     * @param cardapio O cardápio a ser deletado
     * @throws NoConnectionException Sem internet
     * @throws NotImplementedErrorExcpetion Erro não implementado
     * @throws NotAuthorizedException Não auorizado
     * @throws InternalServerErrorException Erro interno do servidor
     * @throws ForbiddenException Não encontrado
     * @throws BadRequestException Request errado
     * @throws NotFoundException Não encontrado
     */
    public static void deletaCardapio(Cardapio cardapio) throws NoConnectionException, ForbiddenException,
            BadRequestException, NotImplementedErrorExcpetion, InternalServerErrorException, NotFoundException,
            NotAuthorizedException {
        Usuario usuario = EMM.getInstance().getUsuarioAtual();

        gerenciador_.deleta(cardapio);

        usuario.getEstabelecimento().getCardapios().remove(cardapio);
    }

    /**
     * Adiciona uma categoria a um cardápio
     * @param cardapio O cardápio a ser modificado
     * @param categoria A categoria a ser adicionada
     * @throws NoConnectionException Sem internet
     * @throws NotImplementedErrorExcpetion Erro não implementado
     * @throws NotAuthorizedException Não auorizado
     * @throws InternalServerErrorException Erro interno do servidor
     * @throws ForbiddenException Não encontrado
     * @throws BadRequestException Request errado
     * @throws NotFoundException Não encontrado
     */
    public static void adicionaCategoriaCardapio(Cardapio cardapio, Categoria categoria) throws NoConnectionException,
            ForbiddenException, BadRequestException, NotImplementedErrorExcpetion, InternalServerErrorException,
            NotFoundException, NotAuthorizedException {
        if(cardapio.getCategorias().contains(categoria)) return;
        cardapio.addCategoria(categoria);
        gerenciador_.relaciona(cardapio, categoria);
    }

    /**
     * Deleta uma categoria de um cardápio
     * @param cardapio O cardápio a ser modificado
     * @param categoria A categoria a ser adicionada
     * @throws NoConnectionException Sem internet
     * @throws NotImplementedErrorExcpetion Erro não implementado
     * @throws NotAuthorizedException Não auorizado
     * @throws InternalServerErrorException Erro interno do servidor
     * @throws ForbiddenException Não encontrado
     * @throws BadRequestException Request errado
     * @throws NotFoundException Não encontrado
     */
    public static void removeCategoriaCardapio(Cardapio cardapio, Categoria categoria) throws NoConnectionException,
            ForbiddenException, BadRequestException, NotImplementedErrorExcpetion, InternalServerErrorException,
            NotFoundException, NotAuthorizedException {
        cardapio.getCategorias().remove(categoria);
        gerenciador_.desrelaciona(cardapio, categoria);
    }

    /**
     * Inicia um stream com a entidade Cardapio ligado ao estabelecimento
     * @param time O tempo de duração do stream
     */
    public static void startStream(int time, ProgressCallback<StreamingEvent> progress){
        gerenciador_.startStream(progress, time);
    }

    /**
     * Para o stream com a entidade Cardapio
     */
    public static void stopStream(){
        gerenciador_.stopStream();
    }
}
