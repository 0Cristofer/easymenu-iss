package com.bccog.easymenu.gui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.bccog.easymenu.R;

/**
 * Controlador da interface de login por email e senha
 * @author Cristofer Oswald
 * @since 06/10/2017
 */
public class LoginActivity extends AppCompatActivity{

    //Referências à interface
    private EditText email_view_;
    private EditText password_view_;
    private View progress_view_;
    private View login_form_view_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email_view_ = (EditText) findViewById(R.id.email);
        password_view_ = (EditText) findViewById(R.id.password);
        login_form_view_ = findViewById(R.id.login_form);
        progress_view_ = findViewById(R.id.login_progress);

        findViewById(R.id.email_sign_in_button).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                logar();
            }
        });

    }

    /**
     * Tenta realizar um login com email e senha, se o usuário não existir
     * pergunta para criar novo. Se houver erro de senha/login mostra o erro.
     */
    private void logar() {
        String email = email_view_.getText().toString();
        String password = password_view_.getText().toString();
        boolean cancel = false;
        View focusView = null;

        //Reseta os erros
        email_view_.setError(null);
        password_view_.setError(null);

        if (TextUtils.isEmpty(password)) {
            password_view_.setError(getString(R.string.error_field_required));
            focusView = password_view_;
            cancel = true;
        }

        if (TextUtils.isEmpty(email)) {
            email_view_.setError(getString(R.string.error_field_required));
            focusView = email_view_;
            cancel = true;
        }

        if (cancel) {
            //Houve um erro
            focusView.requestFocus();
        } else {
            //Tenta logar o usuário
        }
    }

    /**
     * Mostra a tela de carregamento
     */
    /*private void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        login_form_view_.setVisibility(show ? View.GONE : View.VISIBLE);
        login_form_view_.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                login_form_view_.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });

        progress_view_.setVisibility(show ? View.VISIBLE : View.GONE);
        progress_view_.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                progress_view_.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });
    }*/

}

