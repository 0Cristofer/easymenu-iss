package com.bccog.easymenu.gui.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bccog.easymenu.R;
import com.bccog.easymenu.controladores.ControladorPedido;
import com.bccog.easymenu.controladores.ControladorProduto;
import com.bccog.easymenu.gui.adapters.ProdutoAdapter;

public class SelProdutoActivity extends AppCompatActivity implements View.OnClickListener {

    private ProdutoAdapter adapter;
    RecyclerView recycler_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sel_produto);

        setupRecycler();

        findViewById(R.id.editar).setOnClickListener(this);
        findViewById(R.id.pedir).setOnClickListener(this);
    }

    private void setupRecycler() {

        recycler_view = findViewById(R.id.recycler_view_layout_prod);
        // Configurando o gerenciador de layout para ser uma lista.
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler_view.setLayoutManager(layoutManager);

        // Adiciona o adapter que irá anexar os objetos à lista.
        // Está sendo criado com lista vazia, pois será preenchida posteriormente.
        adapter = new ProdutoAdapter(ControladorProduto.getProdutos());
        ControladorPedido.setTxtTotal((TextView)findViewById(R.id.total_prod));
        recycler_view.setAdapter(adapter);

        // Configurando um dividr entre linhas, para uma melhor visualização.
        recycler_view.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.editar) startActivity(new Intent(this, PedidosActivity.class));
        else{
            if(ControladorPedido.getTotal() < 1){
                Toast.makeText(this, "Não há produtos para fazer um pedido", Toast.LENGTH_LONG).show();
                return;
            }
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.terminar_pedido);

            TextView valor = dialog.findViewById(R.id.valor_total);
            TextView total = dialog.findViewById(R.id.total_pedido);
            Button confirmar = dialog.findViewById(R.id.confirmar);

            valor.setText(String.valueOf(ControladorPedido.getValorTotal()));
            total.setText(String.valueOf(ControladorPedido.getTotal()));

            confirmar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int total = ControladorPedido.fecharPedido();
                    dialog.cancel();
                    Toast.makeText(view.getContext(), "Pedido com " + String.valueOf(total) + " produtos enviado!", Toast.LENGTH_LONG).show();
                }
            });

            dialog.show();
        }
    }
}
