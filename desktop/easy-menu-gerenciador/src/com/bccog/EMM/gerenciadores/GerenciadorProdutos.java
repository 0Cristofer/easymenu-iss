package com.bccog.EMM.gerenciadores;

import com.bccog.EMM.EMM;
import com.bccog.EMM.bd.entidades.categoria.Categoria;
import com.bccog.EMM.bd.entidades.produto.Produto;
import com.bccog.EMM.bd.entidades.produto.ProdutoComTamanho;
import com.bccog.EMM.bd.entidades.produto.ProdutoPrecoUnico;
import com.bccog.EMM.bd.entidades.produto.Tag;
import com.bccog.EMM.bd.entidades.usuario.Usuario;
import com.bccog.EMM.bd.exceptions.*;
import com.bccog.EMM.gerenciadores.exceptions.MissingPriceException;
import com.bccog.EMM.gerenciadores.exceptions.NegativePriceException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Classe gerenciadora de produtos
 * @author Bruno Cesar
 * @author Cristofer Oswald
 * @since 03/06/2017
 */
public class GerenciadorProdutos {
    private static Gerenciador<ProdutoComTamanho> gerenciador_pt = new Gerenciador<>(ProdutoComTamanho.class,
            "/estabelecimento/" + EMM.getInstance().getUsuarioAtual().getId()
                    + "/" + ProdutoComTamanho.class.getSimpleName().toLowerCase());
    private static Gerenciador<ProdutoPrecoUnico> gerenciador_pu = new Gerenciador<>(ProdutoPrecoUnico.class,
            "/estabelecimento/" + EMM.getInstance().getUsuarioAtual().getId()
                    + "/" + ProdutoPrecoUnico.class.getSimpleName().toLowerCase());

    /**
     * Busca a lista com todos os produtos deste estabelecimento
     * @return A lista com os produtos
     */
    public static List<Produto> getProdutos(){
        List<Produto> produtos = new ArrayList<>();
        produtos.addAll(gerenciador_pt.get());
        produtos.addAll(gerenciador_pu.get());

        return produtos;
    }

    static Set<String> getProdutosCategoria(Categoria c){
        Set<String> keys;

        keys = gerenciador_pu.getKeys(c.getClass().getSimpleName().toLowerCase() + "/" +
                c.getId() + "/" + ProdutoPrecoUnico.class.getSimpleName().toLowerCase() + "/");

        keys.addAll(gerenciador_pt.getKeys(c.getClass().getSimpleName().toLowerCase() + "/" +
                c.getId() + "/" + ProdutoComTamanho.class.getSimpleName().toLowerCase() + "/"));

        return keys;
    }

    static ProdutoComTamanho getProdutoTamanho(String key){
        return gerenciador_pt.getByKey(key);
    }

    static ProdutoPrecoUnico getProdutoUnico(String key){
        return gerenciador_pu.getByKey(key);
    }

    /**
     * Cadastra um ProdutoPrecoUnico, que tem apenas um único preço
     * @param nome Nome do produto
     * @param desc Descrição breve
     * @param tags Lista com as tags do produto
     * @param img_url Caminho para a foto do produto
     * @param preco Preço único
     * @throws NoConnectionException Sem internet
     * @throws NotImplementedErrorExcpetion Erro não implementado
     * @throws NotAuthorizedException Não auorizado
     * @throws InternalServerErrorException Erro interno do servidor
     * @throws ForbiddenException Não encontrado
     * @throws BadRequestException Request errado
     * @throws NotFoundException Não encontrado
     * @throws NegativePriceException Preço rcebido é negativo
     * @throws MissingPriceException Preço não pode ser 0
     */
    public static void cadastrarProduto(String nome, String desc, Tag[] tags,
                                        String img_url, float preco) throws ForbiddenException, BadRequestException,
            NotImplementedErrorExcpetion, InternalServerErrorException, NotFoundException, NotAuthorizedException,
            NegativePriceException, MissingPriceException, NoConnectionException {
        Usuario usuario = EMM.getInstance().getUsuarioAtual();

        if(preco < 0){
            throw new NegativePriceException();
        }
        if(preco == 0){
            throw new MissingPriceException();
        }

        ProdutoPrecoUnico produto = new ProdutoPrecoUnico(nome, desc, tags,img_url, preco);

        gerenciador_pu.criar(produto);

        usuario.getEstabelecimento().getProdutos().add(produto);
    }

    /**
     * Cadastra um ProdutoComTamanho, o qual pode ter até 3 preços
     * @param nome Nome do produto
     * @param desc Descrição
     * @param tags Lista de tags
     * @param img_url Caminho para a foto
     * @param preco_p Preço pequeno
     * @param preco_m Preço médio
     * @param preco_g Preço grande
     * @throws NoConnectionException Sem internet
     * @throws NotImplementedErrorExcpetion Erro não implementado
     * @throws NotAuthorizedException Não auorizado
     * @throws InternalServerErrorException Erro interno do servidor
     * @throws ForbiddenException Não encontrado
     * @throws BadRequestException Request errado
     * @throws NotFoundException Não encontrado
     * @throws NegativePriceException Algum dos preços é negativo
     * @throws MissingPriceException Todos os preços estão nulos
     */
    public static void cadastrarProduto(String nome, String desc, Tag[] tags,
                                        String img_url, float preco_p,
                                        float preco_m, float preco_g) throws ForbiddenException, BadRequestException,
            NotImplementedErrorExcpetion, InternalServerErrorException, NotFoundException, NotAuthorizedException,
            NegativePriceException, MissingPriceException, NoConnectionException {
        Usuario usuario = EMM.getInstance().getUsuarioAtual();

        if((preco_p < 0) || (preco_m < 0) || (preco_g < 0)){
            throw new NegativePriceException();
        }
        if((preco_p == 0) && (preco_m == 0) && (preco_g == 0)){
            throw new MissingPriceException();
        }

        ProdutoComTamanho produto = new ProdutoComTamanho(nome, desc, tags,
                img_url, preco_p, preco_m, preco_g);

        gerenciador_pt.criar(produto);

        usuario.getEstabelecimento().getProdutos().add(produto);
    }

    /**
     * Atualiza um produto no BD
     * @param produto O produto a ser atualizado
     * @throws NoConnectionException Sem internet
     * @throws NotImplementedErrorExcpetion Erro não implementado
     * @throws NotAuthorizedException Não auorizado
     * @throws InternalServerErrorException Erro interno do servidor
     * @throws ForbiddenException Não encontrado
     * @throws BadRequestException Request errado
     * @throws NotFoundException Não encontrado
     */
    public static void atualizaProduto(Produto produto) throws NoConnectionException, ForbiddenException,
            BadRequestException, NotImplementedErrorExcpetion, InternalServerErrorException, NotFoundException,
            NotAuthorizedException {
        if(produto instanceof ProdutoComTamanho){
            gerenciador_pt.atualizar((ProdutoComTamanho)produto);
        }
        else{
            gerenciador_pu.atualizar((ProdutoPrecoUnico)produto);
        }
    }

    /**
     * Deleta um cardápio do BD
     * @param produto O produto a ser deletado
     * @throws NoConnectionException Sem internet
     * @throws NotImplementedErrorExcpetion Erro não implementado
     * @throws NotAuthorizedException Não auorizado
     * @throws InternalServerErrorException Erro interno do servidor
     * @throws ForbiddenException Não encontrado
     * @throws BadRequestException Request errado
     * @throws NotFoundException Não encontrado
     */
    public static void deletaProduto(Produto produto) throws NoConnectionException, ForbiddenException,
            BadRequestException, NotImplementedErrorExcpetion, InternalServerErrorException, NotFoundException,
            NotAuthorizedException {
        Usuario usuario = EMM.getInstance().getUsuarioAtual();

        if(produto instanceof ProdutoComTamanho){
            gerenciador_pt.deleta((ProdutoComTamanho)produto);
        }
        else{
            gerenciador_pu.deleta((ProdutoPrecoUnico)produto);
        }

        usuario.getEstabelecimento().getProdutos().remove(produto);
    }

}
