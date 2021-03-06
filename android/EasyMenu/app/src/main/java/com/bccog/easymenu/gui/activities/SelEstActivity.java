package com.bccog.easymenu.gui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bccog.easymenu.R;
import com.bccog.easymenu.controladores.ControladorEstabelecimento;
import com.bccog.easymenu.entidades.estabelecimento.Estabelecimento;
import com.bccog.easymenu.gui.adapters.EstabelecimentoAdapter;

import java.util.ArrayList;
import java.util.List;

public class SelEstActivity extends AppCompatActivity {

    private EstabelecimentoAdapter adapter;
    RecyclerView recycler_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sel_est);

        setupRecycler();
    }

    private void setupRecycler() {
        List<Estabelecimento> estabelecimentos = new ArrayList<>();
        estabelecimentos.addAll(ControladorEstabelecimento.getEstabelecimentos());

        recycler_view = findViewById(R.id.recycler_view_layout_est);
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
