package com.bccog.easymenu.gui.activities;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.bccog.easymenu.R;
import com.bccog.easymenu.entidades.pedido.Pedido;
import com.bccog.easymenu.entidades.produto.ProdutoComTamanho;
import com.bccog.easymenu.entidades.produto.ProdutoPedido;
import com.bccog.easymenu.entidades.produto.ProdutoPrecoUnico;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class ProdutosActivity extends AppCompatActivity {
    private int unico = -1;
    private int tamanho = -1;

    private static String estabelecimento_id = "J6bxswlindXzK4OYkdObTxSn4Do1";

    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private DatabaseReference comtamanho_reference;
    private DatabaseReference unico_reference;
    private DatabaseReference datacomtamanho_reference;
    private DatabaseReference dataunico_reference;
    private DatabaseReference datapedido_reference;
    private DatabaseReference pedido_e_reference;
    private DatabaseReference pedido_c_reference;
    private DatabaseReference cliente_reference;
    private DatabaseReference estabelecimento_reference;

    private List<ProdutoComTamanho> produtos_tamanho = new ArrayList<>();
    private List<ProdutoPrecoUnico> produtos_unico = new ArrayList<>();

    private List<ProdutoPedido> produtos_pedido = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);

        estabelecimento_reference = database.getReference("/estabelecimento/"+estabelecimento_id);
        cliente_reference = database.getReference("/cliente/" + MainActivity.usuario_atual_.getCliente().getId());

        comtamanho_reference = estabelecimento_reference.child(ProdutoComTamanho.class.getSimpleName().toLowerCase());
        unico_reference = estabelecimento_reference.child(ProdutoPrecoUnico.class.getSimpleName().toLowerCase());

        pedido_e_reference = estabelecimento_reference.child(Pedido.class.getSimpleName().toLowerCase());
        pedido_c_reference = cliente_reference.child(Pedido.class.getSimpleName().toLowerCase());

        datacomtamanho_reference = database.getReference("/" + ProdutoComTamanho.class.getSimpleName().toLowerCase());
        dataunico_reference = database.getReference("/" + ProdutoPrecoUnico.class.getSimpleName().toLowerCase());
        datapedido_reference = database.getReference("/" + Pedido.class.getSimpleName().toLowerCase());

        comtamanho_reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot d : dataSnapshot.getChildren()){
                    datacomtamanho_reference.child(d.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            produtos_tamanho.add(dataSnapshot.getValue(ProdutoComTamanho.class));
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        unico_reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot d : dataSnapshot.getChildren()){
                    dataunico_reference.child(d.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            produtos_unico.add(dataSnapshot.getValue(ProdutoPrecoUnico.class));
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        addunico(null);
        addtamanho(null);
    }

    public void addunico(View view) {
        unico = unico + 1;

        ((TextView) findViewById(R.id.produtounico))
                .setText(getResources().getString(R.string.produtcs) + unico);
        atualizarTotal();

        if(view != null){
            ProdutoPrecoUnico produto = produtos_unico.get(ThreadLocalRandom.current().nextInt(0, produtos_unico.size()));
            produtos_pedido.add(new ProdutoPedido(produto.getNome() + ", com cebola", produto, ProdutoPedido.TamanhoProduto.UNICO));
        }

    }

    public void addtamanho(View view) {
        tamanho = tamanho + 1;

        ((TextView) findViewById(R.id.produtotamanho))
                .setText(getResources().getString(R.string.produtcs) + tamanho);
        atualizarTotal();

        if(view != null){
            ProdutoComTamanho produto = produtos_tamanho.get(ThreadLocalRandom.current().nextInt(0, produtos_tamanho.size()));
            produtos_pedido.add(new ProdutoPedido(produto.getNome() + ", médio, bem passado", produto, ProdutoPedido.TamanhoProduto.MEDIO));
        }
    }

    public void order(View view) {
        Pedido pedido = new Pedido();
        /*Pedido pedido = new Pedido(MainActivity.usuario_atual_.getCliente(), DateTime.now().getMillis(),
                false, "semcupom", MainActivity.usuario_atual_.getCliente().getEnderecos().get(0),
                "traz ketchup", Status.RECEBIDO, produtos_pedido, estabelecimento_id);

        pedido.calcularPreco();*/

        DatabaseReference new_datapedido_reference = datapedido_reference.push();
        String key = new_datapedido_reference.getKey();
        new_datapedido_reference.setValue(pedido);
        Map<String, Object> map = new HashMap<>();
        map.put(key, true);
        pedido_e_reference.updateChildren(map);
        pedido_c_reference.updateChildren(map);

        limpar();
    }

    private void atualizarTotal(){
        ((TextView) findViewById(R.id.total))
                .setText(getResources().getString(R.string.total) + (unico + tamanho));
    }

    private void limpar(){
        produtos_pedido = new ArrayList<>();

        unico = -1;
        tamanho = -1;

        addunico(null);
        addtamanho(null);

        atualizarTotal();
    }
}
