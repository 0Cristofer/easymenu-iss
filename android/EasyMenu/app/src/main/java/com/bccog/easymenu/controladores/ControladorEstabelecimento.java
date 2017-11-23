package com.bccog.easymenu.controladores;

import com.bccog.easymenu.entidades.estabelecimento.Estabelecimento;
import com.bccog.easymenu.entidades.estabelecimento.EstabelecimentoMesa;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by crisofer on 22/11/17.
 */

public class ControladorEstabelecimento {
    static private List<Estabelecimento> estabelecimentos_ = null;
    static private Estabelecimento atual = null;

    static public void carregaEstabelecimentos(final EstabelecimentoListener listener){
        estabelecimentos_ = new ArrayList<>();
        DatabaseReference estabelecimento_reference = FirebaseDatabase.getInstance().getReference("/estabelecimento/");
        estabelecimento_reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i = 0;
                for(DataSnapshot d : dataSnapshot.getChildren()){
                    estabelecimentos_.add(d.getValue(EstabelecimentoMesa.class));
                    estabelecimentos_.get(i).setId(d.getKey());
                }
                listener.onCarregado();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onCancelado();
            }
        });

    }

    public static Estabelecimento getAtual() {
        return atual;
    }

    public static void setAtual(Estabelecimento atual) {
        ControladorEstabelecimento.atual = atual;
    }

    static public List<Estabelecimento> getEstabelecimentos(){
        return estabelecimentos_;
    }

    public interface EstabelecimentoListener{
        void onCarregado();
        void onCancelado();
    }
}
