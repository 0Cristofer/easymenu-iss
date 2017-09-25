package com.bccog.EMM.bd;

import com.bccog.EMM.bd.exceptions.*;
import com.bccog.EMM.bd.responses.PostResponse;
import com.bccog.EMM.bd.responses.SingInResponse;
import com.bccog.EMM.bd.responses.SingUpResponse;
import com.bccog.EMM.bd.responses.errors.ErrorResponse;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClientConfig;
import org.jdeferred.ProgressCallback;
import org.jdeferred.Promise;
import org.restonfire.BaseFirebaseRestDatabaseFactory;
import org.restonfire.FirebaseRestDatabase;
import org.restonfire.FirebaseRestEventStream;
import org.restonfire.exceptions.FirebaseRuntimeException;
import org.restonfire.responses.StreamingEvent;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Singleton responsável por realizar as operações com o banco de dados (Firebase)
 * @author Cristofer Oswald
 * @since 25/05/17
 */
public class BancoDeDados{
    private static BancoDeDados instance_ = null;

    //Key do projeto no firebase para acesso aos métodos
    private static final String FIREBASE_KEY = "AIzaSyBKLoc6dK_Agu81Le8vGEDVmuwkw12cb_s";

    //URLs utilizadas para a conexão
    private static final String BD_URL = "https://testproject-c4fcf.firebaseio.com/";
    //private static final String CUSTOM_TOKEN_URL = "https://www.googleapis.com/identitytoolkit/v3/relyingparty/verifyCustomToken";
    //private static final String REFRESH_TOKEN_URL = "https://securetoken.googleapis.com/v1/token";
    private static final String SING_UP_URL = "https://www.googleapis.com/identitytoolkit/v3/relyingparty/signupNewUser";
    private static final String SING_IN_URL = "https://www.googleapis.com/identitytoolkit/v3/relyingparty/verifyPassword";
    //private static final String RESET_PASS_URL = "https://www.googleapis.com/identitytoolkit/v3/relyingparty/getOobConfirmationCode";
    private static final String DELETE_ACC_URL = "https://www.googleapis.com/identitytoolkit/v3/relyingparty/deleteAccount";

    private WebTarget root_target_;
    private Gson gson_;
    private JsonParser json_parser_;

    public static BancoDeDados getInstance(){
        if(instance_ == null){
            instance_ = new BancoDeDados();
        }
        return instance_;
    }

    private BancoDeDados(){
        gson_ = new Gson();
        json_parser_ = new JsonParser();
        root_target_ = ClientBuilder.newClient().target("").queryParam("key", FIREBASE_KEY);
    }

    /**
     * Autentica um usuário com email e senha
     * @param email O email do usuário a ser logado
     * @param senha A senha do usuário
     * @return A resposta do firebase com os dados necessários
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
    public SingInResponse loginEmailSenha(String email, String senha) throws NoConnectionException,
            EmailNotFoundException, InvalidEmailException, InvalidPasswordException,
            UserDisabledException, NotImplementedErrorExcpetion, MissingPasswordException,
            EmailExistsException, OpNotAllowedException, TooManyAttempsException {
        WebTarget target = root_target_.path(SING_IN_URL);
        JsonObject usuario = new JsonObject();
        Response response;

        usuario.addProperty("email", email);
        usuario.addProperty("password", senha);
        usuario.addProperty("returnSecureToken", true);
        try{
            response = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(usuario.toString(),
                    MediaType.APPLICATION_JSON_TYPE));

        }
        catch (ProcessingException e){
            throw new NoConnectionException();
        }

        if(response.getStatusInfo().equals(Response.Status.OK)){
            return gson_.fromJson(response.readEntity(String.class), SingInResponse.class);

        }
        else{
            String res = response.readEntity(String.class);
            parseAuthError(gson_.fromJson(res, ErrorResponse.class), res);
        }

        return null;
    }

    /**
     * Cria um usuário juntamente com seu respectivo estabelecimento
     * @param email O email usuário a ser criado
     * @param senha A senha do novo usuário
     * @return A resposta do firebase com os dados
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
    public SingUpResponse singUpUsuario(String email, String senha) throws NoConnectionException,
            InvalidEmailException, NotImplementedErrorExcpetion, InvalidPasswordException,
            UserDisabledException, EmailNotFoundException, MissingPasswordException, EmailExistsException,
            OpNotAllowedException, TooManyAttempsException {
        WebTarget target = root_target_.path(SING_UP_URL);
        JsonObject usuario = new JsonObject();
        Response response;

        usuario.addProperty("email", email);
        usuario.addProperty("password", senha);
        usuario.addProperty("returnSecureToken", true);
        try{
            response = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(usuario.toString(),
                            MediaType.APPLICATION_JSON_TYPE));
        }
        catch (ProcessingException e){
            throw new NoConnectionException();
        }

        if(response.getStatusInfo().equals(Response.Status.OK)){
            return gson_.fromJson(response.readEntity(String.class), SingUpResponse.class);

        }
        else{
            String res = response.readEntity(String.class);
            parseAuthError(gson_.fromJson(res, ErrorResponse.class), res);
        }

        return null;
    }

    /**
     * Remove um usuário do sistema de autenticação
     * @param token O token do usuário a ser removido
     * @throws NoConnectionException Sem internet
     */
    public void deletarUsuario(String token) throws NoConnectionException {
        WebTarget target = root_target_.path(DELETE_ACC_URL);
        JsonObject user = new JsonObject();

        user.addProperty("idToken", token);
        try{
            target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(user.toString(),
                    MediaType.APPLICATION_JSON_TYPE));
        }
        catch (ProcessingException e){
            throw new NoConnectionException();
        }
    }

    /**
     * Lê um dado no BD e retorna o JSON equivalente
     * @param user_token O token usuário logado na seção atual
     * @param path Caminho para o dado
     * @return O nó inicial da árvore JSON do dado
     * @throws NoConnectionException Sem internet
     * @throws ForbiddenException Request violou as regras do BD
     * @throws InternalServerErrorException Erro do servidor
     * @throws NotFoundException Caminho não encontrado
     * @throws BadRequestException Chamada de servidor inválida (veja documentação firebase)
     * @throws NotAuthorizedException Não autorizado para o token dado
     * @throws NotImplementedErrorExcpetion Erro não implementado
     */
    public JsonElement getData(String user_token, String path) throws ForbiddenException,
            InternalServerErrorException, NotFoundException, BadRequestException,
            NotAuthorizedException, NotImplementedErrorExcpetion, NoConnectionException {
        WebTarget target = root_target_.path(BD_URL).path(path + ".json").
                queryParam("auth", user_token);
        Response response;

        try{
            response = target.request(MediaType.APPLICATION_JSON_TYPE).get();
        }
        catch (ProcessingException e){
            throw new NoConnectionException();
        }

        if(response.getStatusInfo().getStatusCode() == Response.Status.OK.getStatusCode()){
            return json_parser_.parse(response.readEntity(String.class));
        }
        else{
            parseBDError(response);
        }

        return null;
    }

    /**
     * Salva um dado no banco dados sem criar uma key nova
     * @param user_token O token do usuário logado na sessão atual
     * @param path Caminho para o local onde será salvo no BD
     * @param data Dado serializado em Json
     * @return Se houve sucesso ao colocar o dado
     * @throws NoConnectionException Sem internet
     * @throws ForbiddenException Request violou as regras do BD
     * @throws InternalServerErrorException Erro do servidor
     * @throws NotFoundException Caminho não encontrado
     * @throws BadRequestException Chamada de servidor inválida (veja documentação firebase)
     * @throws NotAuthorizedException Não autorizado para o token dado
     * @throws NotImplementedErrorExcpetion Erro não implementado
     */
    public JsonElement putData(String user_token, String path, JsonElement data) throws ForbiddenException,
            InternalServerErrorException, NotFoundException, BadRequestException,
            NotAuthorizedException, NotImplementedErrorExcpetion, NoConnectionException {
        WebTarget target = root_target_.path(BD_URL).path(path + ".json").
                queryParam("auth", user_token);
        Response response;

        try{
            response = target.request(MediaType.APPLICATION_JSON_TYPE).put(Entity.entity(data.toString(),
                    MediaType.APPLICATION_JSON_TYPE));
        }
        catch (ProcessingException e){
            throw new NoConnectionException();
        }

        if(response.getStatusInfo().getStatusCode() == Response.Status.OK.getStatusCode()){
            return json_parser_.parse(response.readEntity(String.class));
        }
        else{
            parseBDError(response);
        }

        return null;
    }

    /**
     * Persiste um dado no BD gerando uma key nova
     * @param user_token O token do usuário logado na sessão atual
     * @param path Caminho onde o dado será salvo
     * @param data JSON que representa o dado
     * @return A key gerada no post
     * @throws NoConnectionException Sem internet
     * @throws ForbiddenException Request violou as regras do BD
     * @throws InternalServerErrorException Erro do servidor
     * @throws NotFoundException Caminho não encontrado
     * @throws BadRequestException Chamada de servidor inválida (veja documentação firebase)
     * @throws NotAuthorizedException Não autorizado para o token dado
     * @throws NotImplementedErrorExcpetion Erro não implementado
     */
    public String postData(String user_token, String path, JsonElement data) throws ForbiddenException,
            InternalServerErrorException, NotFoundException, BadRequestException,
            NotAuthorizedException, NotImplementedErrorExcpetion, NoConnectionException {
        WebTarget target = root_target_.path(BD_URL).path(path + ".json").
                queryParam("auth", user_token);
        Response response;

        try{
            response = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(gson_.toJson(data),
                    MediaType.APPLICATION_JSON_TYPE));
        }
        catch (ProcessingException e){
            throw new NoConnectionException();
        }

        String key = null;
        if(response.getStatusInfo().getStatusCode() == Response.Status.OK.getStatusCode()){
            key = gson_.fromJson(response.readEntity(String.class), PostResponse.class).getKey();
        }
        else{
            parseBDError(response);
        }

        return key;
    }

    /**
     * Atualiza um dado no banco dados
     * @param user_token O token do usuário logado na sessão atual
     * @param path Caminho para o local onde será salvo no BD
     * @param data Dado serializado em Json
     * @return Se houve sucesso ao colocar o dado
     * @throws NoConnectionException Sem internet
     * @throws ForbiddenException Request violou as regras do BD
     * @throws InternalServerErrorException Erro do servidor
     * @throws NotFoundException Caminho não encontrado
     * @throws BadRequestException Chamada de servidor inválida (veja documentação firebase)
     * @throws NotAuthorizedException Não autorizado para o token dado
     * @throws NotImplementedErrorExcpetion Erro não implementado
     */
    public JsonElement patchData(String user_token, String path, JsonElement data) throws ForbiddenException,
            InternalServerErrorException, NotFoundException, BadRequestException,
            NotAuthorizedException, NotImplementedErrorExcpetion, NoConnectionException {
        WebTarget target = root_target_.path(BD_URL).path(path + ".json").
                queryParam("auth", user_token).queryParam("x-http-method-override", "PATCH");
        Response response;

        try{
            response = target.request(MediaType.APPLICATION_JSON_TYPE).post(
                    Entity.entity(gson_.toJson(data), MediaType.APPLICATION_JSON_TYPE));
        }
        catch (ProcessingException e){
            throw new NoConnectionException();
        }

        if(response.getStatusInfo().getStatusCode() == Response.Status.OK.getStatusCode()){
            return json_parser_.parse(response.readEntity(String.class));
        }
        else{
            parseBDError(response);
        }

        return null;
    }

    /**
     * Remove um dado do banco dados
     * @param user_token O token do usuário logado na sessão atual
     * @param path Caminho para o local onde será salvo no BD
     * @throws NoConnectionException Sem internet
     * @throws ForbiddenException Request violou as regras do BD
     * @throws InternalServerErrorException Erro do servidor
     * @throws NotFoundException Caminho não encontrado
     * @throws BadRequestException Chamada de servidor inválida (veja documentação firebase)
     * @throws NotAuthorizedException Não autorizado para o token dado
     * @throws NotImplementedErrorExcpetion Erro não implementado
     */
    public void deleteData(String user_token, String path) throws ForbiddenException,
            InternalServerErrorException, NotFoundException, BadRequestException,
            NotAuthorizedException, NotImplementedErrorExcpetion, NoConnectionException {
        WebTarget target = root_target_.path(BD_URL).path(path + ".json").
                queryParam("auth", user_token).queryParam("x-http-method-override", "DELETE");
        Response response;

        try{
            response = target.request(MediaType.APPLICATION_JSON_TYPE).post(
                    Entity.entity("", MediaType.APPLICATION_JSON_TYPE));
        }
        catch (ProcessingException e){
            throw new NoConnectionException();
        }

        if(response.getStatusInfo().getStatusCode() != Response.Status.OK.getStatusCode()){
            parseBDError(response);
        }
    }

    /**
     * Inicia um stream de dados com o firebase no caminho dado
     * @param user_token Token do usuário atual
     * @param path Caminho no banco de dados
     * @param progress Função que será chamada na ocorrência de uma mudança no BD
     * @param fail Função chamada na ocorrência de um erro
     * @param time Tempo de duração do stream em milissegundos
     */
    public void startStream(String user_token, String path, ProgressCallback<StreamingEvent> progress,
                              FailCall fail, long time){

        Stream stream;
        AsyncHttpClient client = new AsyncHttpClient(
                new AsyncHttpClientConfig.Builder().setRequestTimeout((int)time).build()
        );

        BaseFirebaseRestDatabaseFactory factory = new BaseFirebaseRestDatabaseFactory(
                client,
                gson_
        );

        FirebaseRestDatabase restonfire_database_ = factory.create(BD_URL, user_token);

        Promise<Void, FirebaseRuntimeException, StreamingEvent> promise;
        FirebaseRestEventStream event_stream;

        event_stream = restonfire_database_.getEventStream(path);
        promise = event_stream.startListening();

        promise.progress(progress);

        stream = new Stream(path, progress, client, event_stream);
        fail.setStream(stream);
        promise.fail(fail);

        promise.done(e -> System.out.println("done"));
    }

    /**
     * Fecha o stream dado
     * @param stream A instância do stream a ser parado
     */
    private void stopStream(Stream stream){
        Thread stop = new Thread(() -> {
            stream.getStream().stopListening();
            stream.getClient().close();
        });
        stop.setDaemon(true);
        stop.start();
    }

    /**
     * Lê um erro de autenticação e o classifica
     * @param error Resposta de erro
     * @param res A string da resposta
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
    private void parseAuthError(ErrorResponse error, String res) throws EmailNotFoundException, InvalidEmailException,
            InvalidPasswordException, UserDisabledException, NotImplementedErrorExcpetion, MissingPasswordException,
            TooManyAttempsException, EmailExistsException, OpNotAllowedException {
        if(error.getError().getMessage() == null){
            throw new NotImplementedErrorExcpetion(res);
        }
        switch (error.getError().getMessage()){
            case EMAIL_NOT_FOUND:
                throw new EmailNotFoundException();

            case INVALID_EMAL:
                throw new InvalidEmailException();

            case INVALID_PASSWORD:
                throw new InvalidPasswordException();

            case USER_DISABLED:
                throw new UserDisabledException();

            case MISSING_PASSWORD:
                throw new MissingPasswordException();

            case TOO_MANY_ATTEMPTS_TRY_LATER:
                throw new TooManyAttempsException();

            case EMAIL_EXISTS:
                throw new EmailExistsException();

            case OPERATION_NOT_ALLOWED:
                throw new OpNotAllowedException();

            default:
                throw new NotImplementedErrorExcpetion(res);
        }
    }

    /**
     * Lê um erro de conexão com o BD e o classifica
     * @param response A resposta de erro
     * @throws ForbiddenException Request violou as regras do BD
     * @throws InternalServerErrorException Erro do servidor
     * @throws NotFoundException Caminho não encontrado
     * @throws BadRequestException Chamada de servidor inválida (veja documentação firebase)
     * @throws NotAuthorizedException Não autorizado para o token dado
     * @throws NotImplementedErrorExcpetion Erro não implementado
     */
    private void parseBDError(Response response) throws ForbiddenException,
            InternalServerErrorException, NotFoundException, BadRequestException,
            NotAuthorizedException, NotImplementedErrorExcpetion {
        if(response.getStatusInfo().getStatusCode() == Response.Status.FORBIDDEN.getStatusCode()){
            throw new ForbiddenException(response.readEntity(String.class));
        }
        else if(response.getStatusInfo().getStatusCode() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()){
            throw new InternalServerErrorException(response.readEntity(String.class));
        }
        else if(response.getStatusInfo().getStatusCode() == Response.Status.NOT_FOUND.getStatusCode()){
            throw new NotFoundException(response.readEntity(String.class));
        }
        else if(response.getStatusInfo().getStatusCode() == Response.Status.BAD_REQUEST.getStatusCode()){
            throw new BadRequestException(response.readEntity(String.class));
        }
        else if(response.getStatusInfo().getStatusCode() == Response.Status.UNAUTHORIZED.getStatusCode()){
            throw new NotAuthorizedException(response.readEntity(String.class));
        }
        else{
            throw new NotImplementedErrorExcpetion(response.readEntity(String.class));
        }
    }

    /**
     * Representa um stream com o firebase
     */
    public class Stream {
        private String path_;
        private ProgressCallback<StreamingEvent> progress_;
        private AsyncHttpClient client_;
        private FirebaseRestEventStream stream_;

        Stream(String path, ProgressCallback<StreamingEvent> progress,
               AsyncHttpClient client, FirebaseRestEventStream stream){
            this.path_ = path;
            this.progress_ = progress;
            this.client_ = client;
            this.stream_ = stream;
        }

        /**
         * Para este stream
         */
        public void stopStream(){
            BancoDeDados.getInstance().stopStream(this);
        }

        AsyncHttpClient getClient() {
            return client_;
        }

        void setStream(FirebaseRestEventStream stream){
            this.stream_ = stream;
        }

        FirebaseRestEventStream getStream() {
            return stream_;
        }

        String getPath(){
            return path_;
        }

        ProgressCallback<StreamingEvent> getProgress(){
            return progress_;
        }
    }
}
