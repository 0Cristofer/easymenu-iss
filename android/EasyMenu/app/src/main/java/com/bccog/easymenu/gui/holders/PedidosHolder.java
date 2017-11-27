package com.bccog.easymenu.gui.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bccog.easymenu.R;

/**
 * Created by crisofer on 19/11/17.
 */

public class PedidosHolder extends RecyclerView.ViewHolder {

    public TextView nome;
    public TextView quantidade;
    public TextView tipo;
    public Button cancelar;

    public PedidosHolder(View itemView) {
        super(itemView);
        nome = (TextView) itemView.findViewById(R.id.produto_pedido);
        quantidade = (TextView) itemView.findViewById(R.id.quantidade_pedido);
        tipo = (TextView) itemView.findViewById(R.id.tipo_pedido);
        cancelar = itemView.findViewById(R.id.cancelar);
    }
}
