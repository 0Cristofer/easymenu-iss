package com.bccog.easymenu.gui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.bccog.easymenu.R;
import com.bccog.easymenu.entidades.cliente.Cliente;
import com.bccog.easymenu.entidades.embutido.Endereco;
import com.bccog.easymenu.entidades.produto.Tag;
import com.bccog.easymenu.entidades.usuario.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {
    public static Usuario usuario_atual_;

    private List<String> emails;
    private List<String> senhas;

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();

        listener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("in", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("out", "onAuthStateChanged:signed_out");
                }
            }
        };

        emails = new ArrayList<>();
        senhas = new ArrayList<>();

        emails.add("user1@gmail.com");
        emails.add("user2@gmail.com");
        emails.add("user3@gmail.com");
        emails.add("user4@gmail.com");
        emails.add("user5@gmail.com");

        senhas.add("user1senha");
        senhas.add("user2senha");
        senhas.add("user3senha");
        senhas.add("user4senha");
        senhas.add("user5senha");
    }

    public void login(View view) {
        final Intent intent = new Intent(this, ProdutosActivity.class);
        int rand = ThreadLocalRandom.current().nextInt(0, 5);

        String email = emails.get(rand);
        String password = senhas.get(rand);
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("in2", "signInWithEmail:onComplete:" + task.getResult().getUser().getEmail());
                        usuario_atual_ = new Usuario(task.getResult().getUser().getUid());

                        DatabaseReference database = FirebaseDatabase.getInstance().getReference("/cliente/" + usuario_atual_.getId() + "/");
                        database.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                usuario_atual_.setCliente(dataSnapshot.getValue(Cliente.class));
                                usuario_atual_.getCliente().setId(usuario_atual_.getId());
                                startActivity(intent);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                });


    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(listener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        auth.removeAuthStateListener(listener);
    }

    private void criaCliente(final String email, final String senha, String nome, String sexo,
                             List<Endereco> enderecos, final List<Tag> tags){
        final Cliente cliente = new Cliente(nome, sexo, enderecos, tags);

        auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(
                                new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        DatabaseReference database = FirebaseDatabase.getInstance()
                                                .getReference("/cliente/" + auth.getCurrentUser().getUid() + "/");
                                        database.setValue(cliente);
                                    }
                                });
                    }
                });

    }
}

/* Exemplo de push
ProdutoComTamanho produto = new ProdutoComTamanho("TAMANHOOOOOOOOOOOOOOOOOOOO",  "dahorissimo", null,
                "path_foto", 12.2f, 13.4f, 14.5f);

 user_reference = database.getReference("/estabelecimento/" + auth.getCurrentUser().getUid() + "/produtocomtamanho");

        user_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("user: " + dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        data_reference = database.getReference("/produtocomtamanho");

        data_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("data: " + dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        data_reference = data_reference.push();
        String key = data_reference.getKey();
        data_reference.setValue(produto);
        Map<String, Object> map = new HashMap<>();
        map.put(key, true);
        user_reference.updateChildren(map);

 */
/* TESTE
 auth.signInAnonymously().addOnCompleteListener(this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        List<Endereco> enderecos = new ArrayList<>();
                        enderecos.add(new Endereco("87030110", "Rua Clementina Basseto", "148", "AP 603", "Perto morangueira"));
                        enderecos.add(new Endereco("87220260", "Av. Mario Urbinatti", "332", "AP 302", "Perto UEM"));

                        List<Tag> tags = new ArrayList<>();
                        tags.add(Tag.BACON);
                        tags.add(Tag.PIMENTA);

                        criaCliente("user1@gmail.com", "user1senha", "João", "Masculino", enderecos, tags);
                        criaCliente("user2@gmail.com", "user2senha", "Maria", "Femenino", enderecos, tags);
                        criaCliente("user3@gmail.com", "user3senha", "José", "Masculino", enderecos, tags);
                        criaCliente("user4@gmail.com", "user4senha", "Joaquina", "Femenino", enderecos, tags);
                        criaCliente("user5@gmail.com", "user5senha", "Felizberto", "Indefinido", enderecos, tags);
                    }
                });
*
* */