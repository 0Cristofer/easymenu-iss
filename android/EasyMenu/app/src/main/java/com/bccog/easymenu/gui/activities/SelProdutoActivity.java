package com.bccog.easymenu.gui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bccog.easymenu.R;
import com.bccog.easymenu.controladores.ControladorProduto;
import com.bccog.easymenu.entidades.produto.Produto;
import com.bccog.easymenu.entidades.produto.ProdutoComTamanho;
import com.bccog.easymenu.entidades.produto.ProdutoPrecoUnico;
import com.bccog.easymenu.gui.adapters.ProdutoAdapter;

import java.util.ArrayList;
import java.util.List;

public class SelProdutoActivity extends AppCompatActivity {

    private ProdutoAdapter adapter;
    RecyclerView recycler_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sel_produto);

        setupRecycler();
    }

    private void setupRecycler() {
        List<Produto> produtos = new ArrayList<>();
        Produto p1 = new ProdutoComTamanho();
        p1.setNome("nome1111");
        produtos.add(p1);
        Produto p2 = new ProdutoPrecoUnico();
        p2.setNome("nome22222");
        produtos.add(p2);
        produtos.addAll(ControladorProduto.getProdutos());

        recycler_view = findViewById(R.id.recycler_view_layout_prod);
        // Configurando o gerenciador de layout para ser uma lista.
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler_view.setLayoutManager(layoutManager);

        // Adiciona o adapter que irá anexar os objetos à lista.
        // Está sendo criado com lista vazia, pois será preenchida posteriormente.
        adapter = new ProdutoAdapter(produtos);
        recycler_view.setAdapter(adapter);

        // Configurando um dividr entre linhas, para uma melhor visualização.
        recycler_view.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }
}
