package com.bccog.EMM.gerenciadores;

import com.bccog.EMM.EMM;
import com.bccog.EMM.bd.BancoDeDados;
import com.bccog.EMM.bd.entidades.cardapio.Cardapio;
import com.bccog.EMM.bd.entidades.categoria.Categoria;
import com.bccog.EMM.bd.entidades.cliente.Cliente;
import com.bccog.EMM.bd.entidades.cupons.Cupons;
import com.bccog.EMM.bd.entidades.produto.Produto;
import com.bccog.EMM.bd.entidades.usuario.Usuario;
import com.bccog.EMM.bd.exceptions.*;
import com.bccog.EMM.bd.responses.SingInResponse;
import com.bccog.EMM.bd.responses.SingUpResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Classe gerenciadora da entidade usuários
 * @author Cristofer Oswald
 * @since 04/06/2017
 */
public class GerenciadorUsuarios {
    private static Gerenciador<Cliente> gerenciador_ = new Gerenciador<>(Cliente.class, "");

    /**
     * Loga o usuário dado o login e a senha. Automaticamente configura este usuário como o usuário atual
     * na instância do EMM
     * @param email Email a ser verificado
     * @param senha Senha a ser verificada
     * @return Se o login teve sucesso
     * @throws NoConnectionException Sem conexão com a internet
     * @throws EmailNotFoundException Email não encontrado
     * @throws InvalidEmailException Email inválido
     * @throws InvalidPasswordException Senha inválida
     * @throws UserDisabledException Usuário desabilitado
     * @throws NotImplementedErrorExcpetion Erro não implementado
     * @throws MissingPasswordException Falta senha
     * @throws EmailExistsException Email já cadastrado
     * @throws OpNotAllowedException Operação não permitida
     * @throws TooManyAttempsException Muitas requisições, tente mais tarde
     */
    public static boolean logarUsuario(String email, String senha) throws NoConnectionException,
            InvalidEmailException, NotImplementedErrorExcpetion, InvalidPasswordException,
            UserDisabledException, EmailNotFoundException, MissingPasswordException,
            TooManyAttempsException, OpNotAllowedException, EmailExistsException {

        SingInResponse response = BancoDeDados.getInstance().loginEmailSenha(email, senha);
        if (response == null){
            return false;
        }

        Usuario usuario = new Usuario(response.getLocalId(), response.getIdToken());

        try {
            EMM.getInstance().setUsuarioAtual(usuario);

            usuario.setEstabelecimento(GerenciadorEstabelecimentos.getEstabelecimento());
            carregaDados(usuario);
        } catch (ForbiddenException | InternalServerErrorException |
                NotFoundException | NotAuthorizedException | BadRequestException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * Cria um novo usuário no banco e dados.
     * Automaticamente configura este usuário como o usuário atual na instância do EMM
     * @param email Email do novo usuário
     * @param senha Senha do novo usuário
     * @return Se a criação teve sucesso
     * @throws NoConnectionException Sem conexão com a internet
     * @throws EmailNotFoundException Email não encontrado
     * @throws InvalidEmailException Email inválido
     * @throws InvalidPasswordException Senha inválida
     * @throws UserDisabledException Usuário desabilitado
     * @throws NotImplementedErrorExcpetion Erro não implementado
     * @throws MissingPasswordException Falta senha
     * @throws EmailExistsException Email já cadastrado
     * @throws OpNotAllowedException Operação não permitida
     * @throws TooManyAttempsException Muitas requisições, tente mais tarde
     */
    public static boolean criarUsuario(String email, String senha) throws NoConnectionException,
            InvalidEmailException, EmailNotFoundException, InvalidPasswordException,
            UserDisabledException, NotImplementedErrorExcpetion, MissingPasswordException,
            TooManyAttempsException, OpNotAllowedException, EmailExistsException {
        SingUpResponse response = BancoDeDados.getInstance().singUpUsuario(email, senha);
        if(response == null){
            return false;
        }

        Usuario usuario = new Usuario(response.getLocalId(), response.getIdToken());

        EMM.getInstance().setUsuarioAtual(usuario);
        return true;
    }

    /**
     * Lê um cliente pela key
     * @param key A key do cliente
     * @return A instância do cliente
     */
    public static Cliente carregaCliente(String key){
        return gerenciador_.getByKey(key);
    }
    /**
     * Carrega todos os dados do usuário (estabelecimento)
     * @param usuario O usuário logado
     */
    private static void carregaDados(Usuario usuario) throws NoConnectionException, ForbiddenException,
            BadRequestException, NotImplementedErrorExcpetion, InternalServerErrorException,
            NotFoundException, NotAuthorizedException {
        List<Cardapio> cardapios = GerenciadorCardapios.getCardapios();
        List<Categoria> categorias = GerenciadorCategoria.getCategorias();
        List<Produto> produtos = GerenciadorProdutos.getProdutos();
        List<Cupons> cupons = GerenciadorCupons.getCupons();

        HashMap<String, Categoria> categoria_hash = new HashMap<>();
        HashMap<String, Produto> produto_hash = new HashMap<>();

        for(Categoria c : categorias){
            categoria_hash.put(c.getId(), c);
        }

        for(Produto p : produtos){
            produto_hash.put(p.getId(), p);
        }

        for(Cardapio c : cardapios){
            for(String key : GerenciadorCategoria.getCategoriasCardapio(c)){
                c.addCategoria(categoria_hash.get(key));
            }
        }

        for(Categoria c : categorias){
            for(String key : GerenciadorProdutos.getProdutosCategoria(c)){
                c.addProduto(produto_hash.get(key));
            }
        }

        usuario.getEstabelecimento().setCardapios(cardapios);
        usuario.getEstabelecimento().setCategorias(categorias);
        usuario.getEstabelecimento().setProdutos(produtos);
        usuario.getEstabelecimento().setPedidos(GerenciadorPedidos.getPedidos());
        usuario.getEstabelecimento().setCupons(cupons);
    }
}
