package com.bccog.easymenu.gui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bccog.easymenu.R;
import com.bccog.easymenu.controladores.ControladorPedido;
import com.bccog.easymenu.entidades.produto.ProdutoPedido;
import com.bccog.easymenu.gui.holders.PedidosHolder;

import java.util.List;

/**
 * Created by crisofer on 19/11/17.
 */

public class PedidosAdapter extends RecyclerView.Adapter<PedidosHolder> {
    private final List<List<ProdutoPedido>> pedidos;

    public PedidosAdapter(List<List<ProdutoPedido>> pedidos) {
        this.pedidos = pedidos;
    }

    @Override
    public PedidosHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PedidosHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.it_pedidos, parent, false));
    }

    @Override
    public void onBindViewHolder(final PedidosHolder holder, final int position) {
        final List<ProdutoPedido> atual = pedidos.get(position);
        holder.nome.setText(atual.get(0).getProdutoO().getNome());
        holder.quantidade.setText(String.valueOf(atual.size()));

        switch (atual.get(0).getTamanho()){
            case PEQUENO:
                holder.tipo.setText("P");
                break;
            case MEDIO:
                holder.tipo.setText("M");
                break;
            case GRANDE:
                holder.tipo.setText("G");
                break;
            case UNICO:
                break;
        }
        holder.cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifyItemRemoved(holder.getAdapterPosition());
                notifyItemRangeChanged(holder.getAdapterPosition(), pedidos.size());
                ControladorPedido.removerPedido(atual);
            }
        });
    }



    @Override
    public int getItemCount() {
        return pedidos != null ? pedidos.size() : 0;
    }

}
