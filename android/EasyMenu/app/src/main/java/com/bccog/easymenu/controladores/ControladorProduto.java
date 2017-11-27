package com.bccog.easymenu.controladores;

import com.bccog.easymenu.entidades.produto.Produto;
import com.bccog.easymenu.entidades.produto.ProdutoComTamanho;
import com.bccog.easymenu.entidades.produto.ProdutoPrecoUnico;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by crisofer on 23/11/17.
 */

public class ControladorProduto {
    static private List<Produto> produtos_ = null;

    static public void carregaProdutos(final ProdutoListener listener){
        produtos_ = new ArrayList<>();

        DatabaseReference estabelecimento_reference = FirebaseDatabase.getInstance().
                getReference("/estabelecimento/"+ControladorEstabelecimento.getAtual().getId());
        DatabaseReference comtamanho_reference = estabelecimento_reference.child(ProdutoComTamanho.class.getSimpleName().toLowerCase());
        final DatabaseReference unico_reference = estabelecimento_reference.child(ProdutoPrecoUnico.class.getSimpleName().toLowerCase());
        final DatabaseReference datacomtamanho_reference = FirebaseDatabase.getInstance().
                getReference("/" + ProdutoComTamanho.class.getSimpleName().toLowerCase());
        final DatabaseReference dataunico_reference = FirebaseDatabase.getInstance().
                getReference("/" + ProdutoPrecoUnico.class.getSimpleName().toLowerCase());

        comtamanho_reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final long[] i = {dataSnapshot.getChildrenCount()};
                for(DataSnapshot d : dataSnapshot.getChildren()){
                    datacomtamanho_reference.child(d.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            ProdutoComTamanho pt = dataSnapshot.getValue(ProdutoComTamanho.class);
                            pt.setId(dataSnapshot.getKey());
                            produtos_.add(pt);
                            i[0] = i[0] - 1;
                            if(i[0] == 0) {
                                unico_reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        final long[] i = {dataSnapshot.getChildrenCount()};
                                        for (DataSnapshot d : dataSnapshot.getChildren()) {
                                            dataunico_reference.child(d.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    ProdutoPrecoUnico pu = dataSnapshot.getValue(ProdutoPrecoUnico.class);
                                                    pu.setId(dataSnapshot.getKey());
                                                    produtos_.add(pu);
                                                    i[0] = i[0] - 1;
                                                    if (i[0] == 0) {
                                                        listener.onCarregado();
                                                    }
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
                            }
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

    }

    static public List<Produto> getProdutos(){
        return produtos_;
    }

    public interface ProdutoListener{
        void onCarregado();
        void onCancelado();
    }
}
