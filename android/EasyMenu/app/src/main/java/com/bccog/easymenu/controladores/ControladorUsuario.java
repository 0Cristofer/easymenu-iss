package com.bccog.easymenu.controladores;

import android.support.annotation.NonNull;

import com.bccog.easymenu.entidades.usuario.Usuario;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.GoogleAuthProvider;

/**
 * Realiza a comunicação entre o modelo usuário e a visão (view)
 * @author Cristofer
 * @since 06/10/2017
 */

public class ControladorUsuario {
    private static Usuario usuario_atual_ = null;
    private static GoogleSignInAccount acct_ = null;

    /**
     * Loga um usuário no firebase
     * @param email Email do usuário
     * @param senha Senha do usuário
     * @param listener A instância do listener que será chamado em seus respectivos lugares
     */
    public static void logarUsuarioComEmail(String email, String senha,
                                            final LogarUsuarioListener listener){

        logarUsuario(FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha), listener);
    }

    /**
     * Loga um usuário com as credenciais do google
     * @param acct Os dados da conta google
     * @param listener A instância do listener que será chamado em seus respectivos lugares
     */
    public static void logarUsuarioGoogle(GoogleSignInAccount acct, final LogarUsuarioListener listener){
        acct_ = acct;

        logarUsuario(FirebaseAuth.getInstance()
                .signInWithCredential(GoogleAuthProvider.getCredential(acct.getIdToken(), null)), listener);
    }

    /**
     * @return O usuário logado na sessão atual
     */
    public static Usuario getUsuarioAtual(){
        return usuario_atual_;
    }

    /**
     * @return A instância do login do google
     */
    static GoogleSignInAccount getAcct(){
        return acct_;
    }

    /**
     * Loga um usuário no firebase
     * @param task O pedido de login
     * @param listener O listener que será acoplado
     */
    private static void logarUsuario(Task<AuthResult> task, final LogarUsuarioListener listener){
        task
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        usuario_atual_ = new Usuario(authResult.getUser().getUid());
                        listener.onSuccess();
                        ControladorCliente.setClienteAtual(usuario_atual_.getId(), listener);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listener.onFailure((FirebaseAuthException)e);
                    }
                });
    }

    /**
     * Interface responsável por chamar as funçoẽs em seus respectivos listeners
     */
    public interface LogarUsuarioListener{
        /**
         * É chamado quando o login tem sucesso
         */
        void onSuccess();

        /**
         * É chamado quando há um erro no login
         * @param e A exceção que causou o erro
         */
        void onFailure(FirebaseAuthException e);

        /**
         * É chamado quando o cliente termina de ser carregado
         */
        void onClienteLogado();

        /**
         * É chamado quando há um erro ao carregar o cliente
         */
        void onErroCliente();
    }
}
