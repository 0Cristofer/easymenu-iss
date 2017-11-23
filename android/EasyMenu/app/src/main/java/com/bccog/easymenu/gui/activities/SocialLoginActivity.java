package com.bccog.easymenu.gui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.bccog.easymenu.R;
import com.bccog.easymenu.controladores.ControladorEstabelecimento;
import com.bccog.easymenu.controladores.ControladorUsuario;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuthException;

public class SocialLoginActivity extends AppCompatActivity
        implements GoogleApiClient.OnConnectionFailedListener {

    private boolean is_loading_ = false;

    private View login_form_;
    private View progress_form_;

    private static final int RC_SIGN_IN = 9001; //ID único para identificar a intent
    private static final String TAG = "google_login";
    private GoogleApiClient google_api_client_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_login);

        setTitle(getString(R.string.title_social));

        //Ativa o botão voltar no action bar
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        login_form_ = findViewById(R.id.login_form_social);
        progress_form_ = findViewById(R.id.progress_form_social);

        //Configura o botão
        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logar();
            }
        });

        //Cria uma configuração do google com o login padrão, que contém os dados básicos
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        //Configura a conexão com a API para o login
        google_api_client_ = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    /**
     * Define a ação do botão voltar no topo da tela
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Função chamada quando o botão de login do google é pressionado.
     * Inicia a janela de login do google
     */
    private void logar() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(google_api_client_);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    //Fução chamada quando o intent do login do google terminar.
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Lê o resultado retornado pelo intent
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            receberResutado(result);
        }
    }

    /**
     * Lê o resultado e o passa para o controlador caso houve sucesso
     * @param result O resultado da intent anterior
     */
    private void receberResutado(GoogleSignInResult result) {
        final AppCompatActivity activity = this;
        Log.d(TAG, "resultado:" + result.getStatus().getStatusCode());
        setLoading(true);

        if (result.isSuccess()) {
            ControladorUsuario.logarUsuarioGoogle(result.getSignInAccount(),
                    new ControladorUsuario.LogarUsuarioListener() {
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
        } else {
            Log.d(TAG, "não foi possível logar");
        }
    }



    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

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
