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
 * @author Guilherme Quental
 *
 * @since 13/10/2017
 */
public class GerenciadorCategoria {
    private static Gerenciador<Categoria> gerenciador_ = new Gerenciador<>(Categoria.class,
            "/estabelecimento/" + EMM.getInstance().getUsuarioAtual().getId() + "/"
                    + Categoria.class.getSimpleName().toLowerCase());

    /**
     * Carrega a lista de categorias do usuário
     * @return A lista de categorias
     */
    public static List<Categoria> getCategorias(){
        return gerenciador_.get();
    }

    static Set<String> getCategoriasCardapio(Cardapio cardapio){
        return gerenciador_.getKeys(cardapio.getClass().getSimpleName().toLowerCase() + "/" +
                cardapio.getId() + "/" + Categoria.class.getSimpleName().toLowerCase() + "/");
    }

    /**
     * Cria uma categoria
     * @param nome Nome da categoria a ser criada
     * @throws NoConnectionException Sem internet
     * @throws NotImplementedErrorExcpetion Erro não implementado
     * @throws NotAuthorizedException Não auorizado
     * @throws InternalServerErrorException Erro interno do servidor
     * @throws ForbiddenException Não encontrado
     * @throws BadRequestException Request errado
     * @throws NotFoundException Não encontrado
     */
    public static void criaCategoria(String nome) throws NoConnectionException, ForbiddenException, BadRequestException,
            NotImplementedErrorExcpetion, InternalServerErrorException, NotFoundException, NotAuthorizedException {
        Usuario usuario = EMM.getInstance().getUsuarioAtual();

        Categoria categoria = new Categoria(nome, true);
        gerenciador_.criar(categoria);

        usuario.getEstabelecimento().getCategorias().add(categoria);
    }

    /**
     * Atualiza uma categoria no BD
     * @param categoria A categoria a ser atualizada
     * @throws NoConnectionException Sem internet
     * @throws NotImplementedErrorExcpetion Erro não implementado
     * @throws NotAuthorizedException Não auorizado
     * @throws InternalServerErrorException Erro interno do servidor
     * @throws ForbiddenException Não encontrado
     * @throws BadRequestException Request errado
     * @throws NotFoundException Não encontrado
     */
    public static void atualizaCategoria(Categoria categoria) throws NoConnectionException, ForbiddenException,
            BadRequestException, NotImplementedErrorExcpetion, InternalServerErrorException, NotFoundException,
            NotAuthorizedException {
        gerenciador_.atualizar(categoria);
    }

    /**
     * Deleta uma categoria do BD
     * @param categoria O cardápio a ser deletado
     * @throws NoConnectionException Sem internet
     * @throws NotImplementedErrorExcpetion Erro não implementado
     * @throws NotAuthorizedException Não auorizado
     * @throws InternalServerErrorException Erro interno do servidor
     * @throws ForbiddenException Não encontrado
     * @throws BadRequestException Request errado
     * @throws NotFoundException Não encontrado
     */

    /**
    public static void deletaCategoria(Categoria categoria) throws NoConnectionException, ForbiddenException,
            BadRequestException, NotImplementedErrorExcpetion, InternalServerErrorException, NotFoundException,
            NotAuthorizedException {
        Usuario usuario = EMM.getInstance().getUsuarioAtual();

        for(Cardapio c :usuario.getEstabelecimento().getCardapios()){
            if(c.getCategorias().remove(categoria)){
                GerenciadorCardapios.removeCategoriaCardapio(c, categoria);
            }
        }

        gerenciador_.deleta(categoria);

        usuario.getEstabelecimento().getCategorias().remove(categoria);
    }
/**
    /**
     * Adiciona um produto a uma categora
     * @param categoria A categoria que será modificada
     * @param produto O produto a ser adicionado
     * @throws NoConnectionException Sem internet
     * @throws NotImplementedErrorExcpetion Erro não implementado
     * @throws NotAuthorizedException Não auorizado
     * @throws InternalServerErrorException Erro interno do servidor
     * @throws ForbiddenException Não encontrado
     * @throws BadRequestException Request errado
     * @throws NotFoundException Não encontrado
     */
    public static void adicionaProdutoCategoria(Categoria categoria, Produto produto) throws NoConnectionException,
            ForbiddenException, BadRequestException, NotImplementedErrorExcpetion, InternalServerErrorException,
            NotFoundException, NotAuthorizedException {
        if(categoria.getProdutos().contains(produto)) return;
        categoria.addProduto(produto);
        gerenciador_.relaciona(categoria, produto);
    }

    /**
     * Deleta um produto de uma categoria
     * @param categoria A categoria que será modificada
     * @param produto O produto a ser adicionado
     * @throws NoConnectionException Sem internet
     * @throws NotImplementedErrorExcpetion Erro não implementado
     * @throws NotAuthorizedException Não auorizado
     * @throws InternalServerErrorException Erro interno do servidor
     * @throws ForbiddenException Não encontrado
     * @throws BadRequestException Request errado
     * @throws NotFoundException Não encontrado
     */
    public static void removeProdutoCategoria(Categoria categoria, Produto produto) throws NoConnectionException,
            ForbiddenException, BadRequestException, NotImplementedErrorExcpetion, InternalServerErrorException,
            NotFoundException, NotAuthorizedException {
        categoria.getProdutos().remove(produto);
        gerenciador_.desrelaciona(categoria, produto);
    }

    /**
     * Inicia um stream com a entidade Categoria ligado ao estabelecimento
     * @param time O tempo de duração do stream
     */
    public static void startStream(ProgressCallback<StreamingEvent> progress, int time){
        gerenciador_.startStream(progress, time);
    }

    /**
     * Para o stream com a entidade Categoria
     */
    public static void stopStream(){
        gerenciador_.stopStream();
    }
}
