package com.bccog.easymenu.gui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.bccog.easymenu.R;
import com.bccog.easymenu.controladores.ControladorPedido;
import com.bccog.easymenu.gui.adapters.PedidosAdapter;

public class PedidosActivity extends AppCompatActivity {

    private PedidosAdapter adapter;
    RecyclerView recycler_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);

        //Ativa o botão voltar no action bar
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setupRecycler();
    }

    private void setupRecycler() {

        recycler_view = findViewById(R.id.recycler_view_layout_pedidos);
        // Configurando o gerenciador de layout para ser uma lista.
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler_view.setLayoutManager(layoutManager);

        // Adiciona o adapter que irá anexar os objetos à lista.
        // Está sendo criado com lista vazia, pois será preenchida posteriormente.
        adapter = new PedidosAdapter(ControladorPedido.getPedidos());
        recycler_view.setAdapter(adapter);

        // Configurando um dividr entre linhas, para uma melhor visualização.
        recycler_view.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
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
}
