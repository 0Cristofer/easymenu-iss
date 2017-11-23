package com.bccog.easymenu.gui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.bccog.easymenu.R;
import com.bccog.easymenu.controladores.ControladorEstabelecimento;
import com.bccog.easymenu.controladores.ControladorUsuario;
import com.google.firebase.auth.FirebaseAuthException;

/**
 * Controlador da interface de login por email e senha
 * @author Cristofer Oswald
 * @since 06/10/2017
 */
public class LoginActivity extends AppCompatActivity{

    private boolean is_loading_ = false;

    //Referências à interface
    private EditText email_view_;
    private EditText senha_view_;
    private View login_form_;
    private View progress_form_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle(getString(R.string.title_login));

        email_view_ = (EditText) findViewById(R.id.email);
        senha_view_ = (EditText) findViewById(R.id.password);
        login_form_ = findViewById(R.id.login_form);
        progress_form_ = findViewById(R.id.progress_form);

        //Configura os botões
        findViewById(R.id.email_sign_in_button).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                logar();
            }
        });

        findViewById(R.id.login_social).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                loginSocial();
            }
        });

    }

    /**
     * Tenta realizar um login com email e senha, se o usuário não existir
     * pergunta para criar novo. Se houver erro de senha/login mostra o erro.
     */
    private void logar() {
        final AppCompatActivity activity = this;
        String email = email_view_.getText().toString();
        String senha = senha_view_.getText().toString();
        boolean cancelar = false;
        View focusView = null;

        //Reseta os erros
        email_view_.setError(null);
        senha_view_.setError(null);

        if (TextUtils.isEmpty(email)) {
            email_view_.setError(getString(R.string.error_field_required));
            focusView = email_view_;
            cancelar = true;
        }
        else if (TextUtils.isEmpty(senha)) {
            senha_view_.setError(getString(R.string.error_field_required));
            focusView = senha_view_;
            cancelar = true;
        }

        if (cancelar) {
            //Houve um erro
            focusView.requestFocus();
        } else {
            setLoading(true);

            ControladorUsuario.logarUsuarioComEmail(email, senha, new ControladorUsuario.LogarUsuarioListener() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onFailure(FirebaseAuthException e) {
                    Log.d("exce", e.getErrorCode());
                    if(is_loading_){
                        setLoading(false);
                    }
                }

                @Override
                public void onClienteLogado() {
                    ControladorEstabelecimento.carregaEstabelecimentos(new ControladorEstabelecimento.EstabelecimentoListener() {
                        @Override
                        public void onCarregado() {
                            startActivity(new Intent(activity, SelEstActivity.class));
                            if(is_loading_){
                                setLoading(false);
                            }
                        }

                        @Override
                        public void onCancelado() {

                        }
                    });
                }

                @Override
                public void onErroCliente() {
                    if(is_loading_){
                        setLoading(false);
                    }
                }
            });
        }
    }

    /**
     * Troca a tela pela de login social
     */
    private void loginSocial(){
        Intent intent = new Intent(this, SocialLoginActivity.class);
        startActivity(intent);
    }

    /**
     * Configura a tela para mostrar a interface de carregamento
     * @param loading Se deve ser mostrado o carregamento
     */
    private void setLoading(boolean loading){
        is_loading_ = loading;
        if(loading){
            progress_form_.setVisibility(View.VISIBLE);
            login_form_.setVisibility(View.GONE);

        }
        else{
            progress_form_.setVisibility(View.GONE);
            login_form_.setVisibility(View.VISIBLE);
        }
    }

}

