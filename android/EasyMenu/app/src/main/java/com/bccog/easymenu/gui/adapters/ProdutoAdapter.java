package com.bccog.easymenu.gui.adapters;

import android.app.Dialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bccog.easymenu.R;
import com.bccog.easymenu.controladores.ControladorPedido;
import com.bccog.easymenu.entidades.produto.Produto;
import com.bccog.easymenu.entidades.produto.ProdutoComTamanho;
import com.bccog.easymenu.entidades.produto.ProdutoPedido;
import com.bccog.easymenu.entidades.produto.ProdutoPrecoUnico;
import com.bccog.easymenu.gui.holders.ProdutoHolder;

import java.util.List;

/**
 * Created by crisofer on 23/11/17.
 */

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoHolder> {
    private final List<Produto> produtos;
    private View view = null;

    public ProdutoAdapter(List<Produto> produtos) {
        this.produtos = produtos;
    }

    @Override
    public ProdutoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.it_produto, parent, false);
        return new ProdutoHolder(view);
    }

    @Override
    public void onBindViewHolder(final ProdutoHolder holder, int position) {
        final Produto atual = produtos.get(position);
        holder.nome_produto.setText(atual.getNome());

        if(atual instanceof ProdutoPrecoUnico){
            holder.adicionar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Dialog dialog = new Dialog(ProdutoAdapter.this.view.getContext());

                    dialog.setContentView(R.layout.adicionar_unico);

                    TextView produto = dialog.findViewById(R.id.produto_pop);
                    TextView preco = dialog.findViewById(R.id.preco_u);
                    final EditText quantidade = dialog.findViewById(R.id.quantidade);
                    Button ok = dialog.findViewById(R.id.ok);
                    Button x = dialog.findViewById(R.id.sair);

                    produto.setText(atual.getNome());
                    preco.setText(String.valueOf(((ProdutoPrecoUnico)atual).getPreco()));

                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(quantidade.getText().toString().equals("")){
                                Toast.makeText(dialog.getContext(), "Quantidade inválida", Toast.LENGTH_LONG).show();
                                return;
                            }
                            if(Integer.parseInt(quantidade.getText().toString()) < 0){
                                Toast.makeText(dialog.getContext(), "Quantidade inválida", Toast.LENGTH_LONG).show();
                                return;
                            }
                            ControladorPedido.adicionarProdutoPedido(atual,
                                    Integer.parseInt(quantidade.getText().toString()),
                                    ProdutoPedido.TamanhoProduto.UNICO);
                            dialog.cancel();
                        }
                    });

                    x.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.cancel();
                        }
                    });

                    dialog.show();
                }
            });
        }
        else{
            holder.adicionar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Dialog dialog = new Dialog(ProdutoAdapter.this.view.getContext());
                    final TamanhoListener l = new TamanhoListener();

                    dialog.setContentView(R.layout.adicionar_comtamanho);

                    TextView produto = dialog.findViewById(R.id.produto_pop);
                    TextView preco_p = dialog.findViewById(R.id.preco_p);
                    TextView preco_m = dialog.findViewById(R.id.preco_m);
                    TextView preco_g = dialog.findViewById(R.id.preco_g);
                    final EditText quantidade = dialog.findViewById(R.id.quantidade);
                    RadioButton btn_p = dialog.findViewById(R.id.pequeno);
                    RadioButton btn_m = dialog.findViewById(R.id.medio);
                    RadioButton btn_g = dialog.findViewById(R.id.grande);
                    Button ok = dialog.findViewById(R.id.ok);
                    Button x = dialog.findViewById(R.id.sair);

                    produto.setText(atual.getNome());
                    preco_p.setText(String.valueOf(((ProdutoComTamanho)atual).getPrecoP()));
                    preco_m.setText(String.valueOf(((ProdutoComTamanho)atual).getPrecoM()));
                    preco_g.setText(String.valueOf(((ProdutoComTamanho)atual).getPrecoG()));

                    btn_p.setOnClickListener(l);
                    btn_m.setOnClickListener(l);
                    btn_g.setOnClickListener(l);

                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(quantidade.getText().toString().equals("")){
                                Toast.makeText(dialog.getContext(), "Quantidade inválida", Toast.LENGTH_LONG).show();
                                return;
                            }
                            if(Integer.parseInt(quantidade.getText().toString()) < 1){
                                Toast.makeText(dialog.getContext(), "Quantidade inválida", Toast.LENGTH_LONG).show();
                                return;
                            }
                            ControladorPedido.adicionarProdutoPedido(atual,
                                    Integer.parseInt(quantidade.getText().toString()),
                                    l.preco_escolhido);
                            dialog.cancel();
                        }
                    });

                    x.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.cancel();
                        }
                    });

                    dialog.show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return produtos != null ? produtos.size() : 0;
    }

    class TamanhoListener implements View.OnClickListener{
        ProdutoPedido.TamanhoProduto preco_escolhido = ProdutoPedido.TamanhoProduto.PEQUENO;

        @Override
        public void onClick(View view) {
            boolean checked = ((RadioButton) view).isChecked();

            switch(view.getId()) {
                case R.id.pequeno:
                    if(checked){
                        preco_escolhido = ProdutoPedido.TamanhoProduto.PEQUENO;
                        break;
                    }
                case R.id.medio:
                    if(checked){
                        preco_escolhido = ProdutoPedido.TamanhoProduto.MEDIO;
                        break;
                    }
                case R.id.grande:
                    if(checked){
                        preco_escolhido = ProdutoPedido.TamanhoProduto.GRANDE;
                        break;
                    }
            }
        }
    }

}
