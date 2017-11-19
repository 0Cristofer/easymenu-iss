package com.bccog.easymenu.gui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bccog.easymenu.R;
import com.bccog.easymenu.entidades.estabelecimento.Estabelecimento;
import com.bccog.easymenu.gui.holders.EstabelecimentoHolder;

import java.util.List;

/**
 * Created by crisofer on 19/11/17.
 */

public class EstabelecimentoAdapter extends RecyclerView.Adapter<EstabelecimentoHolder> {
    private final List<Estabelecimento> estabelecimentos;

    public EstabelecimentoAdapter(List<Estabelecimento> estabelecimentos) {
        this.estabelecimentos = estabelecimentos;
    }

    @Override
    public EstabelecimentoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EstabelecimentoHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.it_estabelecimento, parent, false));
    }

    @Override
    public void onBindViewHolder(EstabelecimentoHolder holder, int position) {
        Estabelecimento atual = estabelecimentos.get(position);
        holder.nome.setText(atual.getNome());
        holder.endereco.setText(atual.getEndereco().getRua() + " " + atual.getEndereco().getNumero());
        holder.aberto.setText(atual.getNome());
    }

    @Override
    public int getItemCount() {
        return estabelecimentos != null ? estabelecimentos.size() : 0;
    }

}
