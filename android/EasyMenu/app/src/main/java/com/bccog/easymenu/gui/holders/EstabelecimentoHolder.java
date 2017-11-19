package com.bccog.easymenu.gui.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bccog.easymenu.R;

/**
 * Created by crisofer on 19/11/17.
 */

public class EstabelecimentoHolder extends RecyclerView.ViewHolder {

    public TextView nome;
    public TextView endereco;
    public TextView aberto;


    public EstabelecimentoHolder(View itemView) {
        super(itemView);
        nome = (TextView) itemView.findViewById(R.id.txt_nome_estabelecimento);
        endereco = (TextView) itemView.findViewById(R.id.txt_endereco);
        aberto = (TextView) itemView.findViewById(R.id.txt_aberto);
    }
}
