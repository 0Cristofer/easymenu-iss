package com.bccog.easymenu.gui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bccog.easymenu.R;
import com.bccog.easymenu.entidades.produto.Produto;
import com.bccog.easymenu.gui.holders.ProdutoHolder;

import java.util.List;

/**
 * Created by crisofer on 23/11/17.
 */

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoHolder> {
    private final List<Produto> produtos;

    public ProdutoAdapter(List<Produto> produtos) {
        this.produtos = produtos;
    }

    @Override
    public ProdutoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProdutoHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.it_produto, parent, false));
    }

    @Override
    public void onBindViewHolder(final ProdutoHolder holder, int position) {
        final Produto atual = produtos.get(position);
        holder.nome_produto.setText(atual.getNome());
    }

    @Override
    public int getItemCount() {
        return produtos != null ? produtos.size() : 0;
    }

}
