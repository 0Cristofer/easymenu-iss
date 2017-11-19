package com.bccog.easymenu.gui.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bccog.easymenu.R;
import com.bccog.easymenu.entidades.embutido.Endereco;
import com.bccog.easymenu.entidades.estabelecimento.Estabelecimento;
import com.bccog.easymenu.entidades.estabelecimento.EstabelecimentoMesa;
import com.bccog.easymenu.gui.adapters.EstabelecimentoAdapter;

import java.util.ArrayList;
import java.util.List;

public class SelEstActivity extends AppCompatActivity {

    RecyclerView recycler_view;

    private EstabelecimentoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sel_est);

        setTitle("Estabelecimentos");
        setupRecycler();
    }


    private void setupRecycler() {
        EstabelecimentoMesa m = new EstabelecimentoMesa();
        Endereco e = new Endereco();
        e.setRua("rua massa");
        e.setNumero("2323");
        m.setNome("testezão");
        m.setEndereco(e);
        List<Estabelecimento> estabelecimentos = new ArrayList<>();
        estabelecimentos.add(m);

        recycler_view = findViewById(R.id.recycler_view_layour_recycler);
        // Configurando o gerenciador de layout para ser uma lista.
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler_view.setLayoutManager(layoutManager);

        // Adiciona o adapter que irá anexar os objetos à lista.
        // Está sendo criado com lista vazia, pois será preenchida posteriormente.
        adapter = new EstabelecimentoAdapter(estabelecimentos);
        recycler_view.setAdapter(adapter);

        // Configurando um dividr entre linhas, para uma melhor visualização.
        recycler_view.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }
}
