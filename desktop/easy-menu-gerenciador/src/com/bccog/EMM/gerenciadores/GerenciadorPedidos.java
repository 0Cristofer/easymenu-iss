package com.bccog.EMM.gerenciadores;

import com.bccog.EMM.EMM;
import com.bccog.EMM.bd.BancoDeDados;
import com.bccog.EMM.bd.entidades.estabelecimento.Estabelecimento;
import com.bccog.EMM.bd.entidades.pedido.Pedido;
import com.bccog.EMM.bd.entidades.produto.Produto;
import com.bccog.EMM.bd.entidades.produto.ProdutoComTamanho;
import com.bccog.EMM.bd.entidades.produto.ProdutoPedido;
import com.bccog.EMM.bd.entidades.produto.ProdutoPrecoUnico;
import com.bccog.EMM.bd.entidades.usuario.Usuario;
import com.bccog.EMM.bd.exceptions.*;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.collections.ObservableList;
import org.jdeferred.ProgressCallback;
import org.restonfire.responses.StreamingEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Gerenciador de pedidos
 * @author Cristofer Oswald
 * @author Bruno Cesar
 * @since 25/07/17
 */
public class GerenciadorPedidos {
    private static Gerenciador<Pedido> gerenciador = new Gerenciador<>(Pedido.class,
            "/estabelecimento/" + EMM.getInstance().getUsuarioAtual().getId()
                    + "/" + Pedido.class.getSimpleName().toLowerCase());

    /**
     * Inicia um stream com o firebase, atualizando uma lista observável de pedidos
     * @param pedidos A lista que será atualizada a cada novo pedido
     * @param time O tempo em milissegundos de duração do stream
     */
    public static void startStream(ObservableList<Pedido> pedidos, long time){
        gerenciador.startStream(new ProgressCallback<StreamingEvent>() {
            private int change = -1;

            private JsonParser parser = new JsonParser();

            @Override
            public void onProgress(StreamingEvent streamingEvent) {
                change = change + 1;
                if(change == 0){
                    return;
                }

                JsonElement element = parser.parse(streamingEvent.getSerialzedEventData());
                if(!element.isJsonNull()){
                    JsonObject object = element.getAsJsonObject().get("data").getAsJsonObject();
                    String data = "";
                    for(Map.Entry<String, JsonElement> e : object.entrySet()){
                        data = e.getKey();
                    }
                    Pedido pedido = getPedido(data);

                    pedido.setCliente(GerenciadorUsuarios.carregaCliente(pedido.getClienteId()));
                    pedidos.add(pedido);
                }

            }
        }, time);
    }

    /**
     * Lê um pedido dado sua key
     * @param key A key do pedido
     * @return A instância do pedido
     */
    public static Pedido getPedido(String key){
        Usuario usuario = EMM.getInstance().getUsuarioAtual();
        JsonElement element;
        Gson gson = new Gson();
        Pedido object;

        try {
            element = BancoDeDados.getInstance().getData(usuario.getToken(), "/" +
                    Pedido.class.getSimpleName().toLowerCase() + "/" + key);

            if(element.isJsonNull()){
                return null;
            }

            object = gson.fromJson(element.toString(), Pedido.class);
            object.setId(key);
            object.setCliente(GerenciadorUsuarios.carregaCliente(object.getClienteId()));

            int i = 0;
            List<ProdutoPedido> ProdutosNoPedido = object.getProdutosNoPedido();

            if(ProdutosNoPedido != null) {

                for (ProdutoPedido p : ProdutosNoPedido) {
                    if (p.getTamanho() == ProdutoPedido.TamanhoProduto.UNICO) {
                        p.setProduto(gson.fromJson(element.getAsJsonObject().
                                get("produtosNoPedido").getAsJsonArray().get(i).
                                getAsJsonObject().get("produto").toString(), ProdutoPrecoUnico.class));
                    } else {
                        p.setProduto(gson.fromJson(element.getAsJsonObject().
                                get("produtosNoPedido").getAsJsonArray().get(i).
                                getAsJsonObject().get("produto").toString(), ProdutoComTamanho.class));
                    }
                    i = i + 1;
                }
            }

            //object.setProdutosNoPedido();

            return object;

        } catch (ForbiddenException | BadRequestException | NotAuthorizedException | NotImplementedErrorExcpetion |
                InternalServerErrorException | NotFoundException | NoConnectionException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Carrega a lista de pedidos existente no BD
     * @return A lista de pedidos
     */
    public static List<Pedido> getPedidos(){
        List<Pedido> pedidos = new ArrayList<>();

        Set<String> keys = gerenciador.getKeys(Estabelecimento.class.getSimpleName().toLowerCase() + "/"
                + EMM.getInstance().getUsuarioAtual().getEstabelecimento().getId() + "/" + Pedido.class.getSimpleName().toLowerCase() + "/");

        System.out.println("Pedidos Keys size = " + keys.size());

        for (String key: keys) {
            pedidos.add(getPedido(key));
        }

        return pedidos;
    }

    /**
     * Atualiza um pedido
     * @param pedido A instância do pedido
     * @throws NoConnectionException Sem internet
     * @throws NotImplementedErrorExcpetion Erro não implementado
     * @throws NotAuthorizedException Não auorizado
     * @throws InternalServerErrorException Erro interno do servidor
     * @throws ForbiddenException Não permitido
     * @throws BadRequestException Request errado
     * @throws NotFoundException Não encontrado
     */
    public static void atualizarPedido(Pedido pedido) throws ForbiddenException, BadRequestException,
            NotImplementedErrorExcpetion, NotAuthorizedException, InternalServerErrorException,
            NotFoundException, NoConnectionException {
        gerenciador.atualizar(pedido);
    }

    public static void atualizarStatusPedido(Pedido pedido) throws NoConnectionException, ForbiddenException,
            BadRequestException, NotImplementedErrorExcpetion, InternalServerErrorException,
            NotFoundException, NotAuthorizedException {
        JsonElement e = new JsonObject();
        e.getAsJsonObject().addProperty("status", pedido.getStatus().getStatus().toUpperCase());
        BancoDeDados.getInstance().patchData(EMM.getInstance().getUsuarioAtual().getToken(),
                Pedido.class.getSimpleName().toLowerCase() + "/" + pedido.getId(),
                e);
    }

    /**
     * Para o stream atual (se existir)
     */
    public static void stopStream(){
        gerenciador.stopStream();
    }
}
