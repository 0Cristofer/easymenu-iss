package com.bccog.easymenu.gui.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bccog.easymenu.R;

/**
 * Created by crisofer on 23/11/17.
 */

public class ProdutoHolder extends RecyclerView.ViewHolder {

    public TextView nome_produto;

    public ProdutoHolder(View itemView) {
        super(itemView);

        nome_produto = itemView.findViewById(R.id.txt_nome_produto);
    }
}
