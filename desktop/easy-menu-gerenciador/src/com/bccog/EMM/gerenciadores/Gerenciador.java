package com.bccog.EMM.gerenciadores;

import com.bccog.EMM.EMM;
import com.bccog.EMM.bd.BancoDeDados;
import com.bccog.EMM.bd.EntidadeBasica;
import com.bccog.EMM.bd.FailCall;
import com.bccog.EMM.bd.entidades.usuario.Usuario;
import com.bccog.EMM.bd.exceptions.*;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jdeferred.ProgressCallback;
import org.restonfire.responses.StreamingEvent;

import java.util.*;

/**
 * Gerenciador genérico, usado para manipular entidades básicas
 * @author Cristofer Oswald
 * @since 06/07/17
 */
public class Gerenciador<T extends EntidadeBasica> {
    private Class<T> clazz_;
    private String clazz_name_;
    private BancoDeDados.Stream stream_ = null;
    private Timer timer_;
    private String path_;


    public Gerenciador(Class<T> clazz, String path){
        this.clazz_ = clazz;
        this.path_ = path;

        clazz_name_ = clazz.getSimpleName().toLowerCase();
    }

    /**
     * Carrega a lista de dados T do estabelecimento
     * @param path Caminho para as chaves dos dados
     * @return A lista de dados
     */
    public List<T> get(String path){
        List<T> objects = new ArrayList<>();

        for(String key : getKeys(path)){
            objects.add(getByKey(key));
        }

        return objects;
    }

    /**
     * Carrega a lista de dados T do estabelecimento
     * @return A lista de dados
     */
    public List<T> get(){
        return get("/estabelecimento/" + EMM.getInstance().getUsuarioAtual().getId() + "/" + clazz_name_);
    }

    /**
     * Lê um objeto dado sua key
     * @param key A key do objeto no BD
     * @return A instância do objeto
     */
    T getByKey(String key){
        Usuario usuario = EMM.getInstance().getUsuarioAtual();
        JsonElement element;
        Gson gson = new Gson();
        T object;

        try {
            element = BancoDeDados.getInstance().getData(usuario.getToken(), "/" + clazz_name_ + "/" + key);

            if(element.isJsonNull()){
                return null;
            }

            object = gson.fromJson(element.toString(), clazz_);
            object.setId(key);

            return object;

        } catch (ForbiddenException | BadRequestException | NotAuthorizedException | NotImplementedErrorExcpetion |
                InternalServerErrorException | NotFoundException | NoConnectionException e) {
            e.printStackTrace();
        }

        return null;
    }

    Set<String> getKeys(String path){
        Set<String> keys = new HashSet<>();
        JsonElement element;

        try {
            element = BancoDeDados.getInstance().getData(EMM.getInstance().getUsuarioAtual().getToken(),
                    path);

            if(element.isJsonNull()){
                return keys;
            }

            for(Map.Entry<String, JsonElement> set : element.getAsJsonObject().entrySet()){
                keys.add(set.getKey());
            }

        } catch (ForbiddenException | InternalServerErrorException | NotFoundException | BadRequestException |
                NotImplementedErrorExcpetion | NotAuthorizedException | NoConnectionException e) {
            e.printStackTrace();
        }

        return keys;
    }

    /**
     * Cria um objeto no BD
     * @param object O objeto a ser criado
     * @throws NoConnectionException Sem internet
     * @throws NotImplementedErrorExcpetion Erro não implementado
     * @throws NotAuthorizedException Não auorizado
     * @throws InternalServerErrorException Erro interno do servidor
     * @throws ForbiddenException Não encontrado
     * @throws BadRequestException Request errado
     * @throws NotFoundException Não encontrado
     */
    void criar(T object) throws NoConnectionException, ForbiddenException, BadRequestException,
            NotImplementedErrorExcpetion, InternalServerErrorException, NotFoundException, NotAuthorizedException {
        Usuario usuario = EMM.getInstance().getUsuarioAtual();

        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(new Gson().toJson(object, clazz_));

        String key = BancoDeDados.getInstance().postData(usuario.getToken(), clazz_name_, element);

        element = new JsonObject();
        element.getAsJsonObject().addProperty(key, true);
        BancoDeDados.getInstance().patchData(usuario.getToken(),
                "estabelecimento/" + usuario.getId() + "/" + clazz_name_,  element);

        object.setId(key);
    }

    /**
     * Atualiza um dado no BD
     * @param object O dado a ser atualizada
     * @throws NoConnectionException Sem internet
     * @throws NotImplementedErrorExcpetion Erro não implementado
     * @throws NotAuthorizedException Não auorizado
     * @throws InternalServerErrorException Erro interno do servidor
     * @throws ForbiddenException Não encontrado
     * @throws BadRequestException Request errado
     * @throws NotFoundException Não encontrado
     */
    void atualizar(T object) throws NoConnectionException, ForbiddenException,
            BadRequestException, NotImplementedErrorExcpetion, InternalServerErrorException, NotFoundException,
            NotAuthorizedException {
        Usuario usuario = EMM.getInstance().getUsuarioAtual();

        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(new Gson().toJson(object, clazz_));

        BancoDeDados.getInstance().putData(usuario.getToken(), clazz_name_ + "/" + object.getId(), element);
    }

    /**
     * Deleta um dado do BD
     * @param object O dado a ser deletado
     * @throws NoConnectionException Sem internet
     * @throws NotImplementedErrorExcpetion Erro não implementado
     * @throws NotAuthorizedException Não auorizado
     * @throws InternalServerErrorException Erro interno do servidor
     * @throws ForbiddenException Não encontrado
     * @throws BadRequestException Request errado
     * @throws NotFoundException Não encontrado
     */
    void deleta(T object) throws NoConnectionException, ForbiddenException,
            BadRequestException, NotImplementedErrorExcpetion, InternalServerErrorException, NotFoundException,
            NotAuthorizedException {
        Usuario usuario = EMM.getInstance().getUsuarioAtual();

        BancoDeDados.getInstance().deleteData(usuario.getToken(), clazz_name_ + "/"+ object.getId());
        BancoDeDados.getInstance().deleteData(usuario.getToken(),
                "estabelecimento/" + usuario.getEstabelecimento().getId() + "/" + clazz_name_ + "/" + object.getId());
    }

    /**
     * Relaciona um objeto S a um objeto T
     * @param pai O objeto base
     * @param filho O objeto a ser relacionado
     * @throws NoConnectionException Sem internet
     * @throws NotImplementedErrorExcpetion Erro não implementado
     * @throws NotAuthorizedException Não auorizado
     * @throws InternalServerErrorException Erro interno do servidor
     * @throws ForbiddenException Não permitido
     * @throws BadRequestException Request errado
     * @throws NotFoundException Não encontrado
     */
    <S extends EntidadeBasica> void relaciona(T pai, S filho) throws NoConnectionException,
            ForbiddenException, BadRequestException, NotImplementedErrorExcpetion, InternalServerErrorException,
            NotFoundException, NotAuthorizedException {
        Usuario usuario = EMM.getInstance().getUsuarioAtual();

        JsonElement element = new JsonObject();

        element.getAsJsonObject().addProperty(filho.getId(), true);
        BancoDeDados.getInstance().patchData(usuario.getToken(), clazz_name_ + "/" + pai.getId()
                + "/" + filho.getClass().getSimpleName().toLowerCase(), element);
    }

    /**
     * Desrelaciona um objeto S a um objeto T
     * @param pai O objeto base
     * @param filho O objeto a ser relacionado
     * @throws NoConnectionException Sem internet
     * @throws NotImplementedErrorExcpetion Erro não implementado
     * @throws NotAuthorizedException Não auorizado
     * @throws InternalServerErrorException Erro interno do servidor
     * @throws ForbiddenException Não permitido
     * @throws BadRequestException Request errado
     * @throws NotFoundException Não encontrado
     */
    <S extends EntidadeBasica> void desrelaciona(T pai, S filho) throws NoConnectionException,
            ForbiddenException, BadRequestException, NotImplementedErrorExcpetion, InternalServerErrorException,
            NotFoundException, NotAuthorizedException {
        Usuario usuario = EMM.getInstance().getUsuarioAtual();


        BancoDeDados.getInstance().deleteData(usuario.getToken(), clazz_name_ + "/" + pai.getId()
                + "/" + filho.getClass().getSimpleName().toLowerCase() + "/" + filho.getId());
    }

    /**
     * Inicia um stream com a entidade dada no gerenciador
     * @param progress A função chamada a cada atualização
     * @param time O tempo, em milissegundos, de duração do stream
     */
    void startStream(ProgressCallback<StreamingEvent> progress, long time){
        if(stream_ != null){
            stopStream();
        }

        BancoDeDados.getInstance().startStream(EMM.getInstance().getUsuarioAtual().getToken(),
                path_, progress, new FailCall(this, time), time);

        timer_ = new Timer();
        timer_.schedule(new TimerTask() {
            @Override
            public void run() {
                stopStream();
            }
        }, new Date(System.currentTimeMillis() + time - 5));
    }

    /**
     * Encerra qualquer stream iniciado
     */
    public void stopStream(){
        if(stream_ != null){
            stream_.stopStream();
            stream_ = null;
        }
        if(timer_ != null){
            timer_.cancel();
            timer_ = null;
        }
    }

    /**
     * Atualiza o stream desse gerenciador
     * @param stream O novo stream
     */
    public void setStream(BancoDeDados.Stream stream){
        this.stream_ = stream;
    }
}
