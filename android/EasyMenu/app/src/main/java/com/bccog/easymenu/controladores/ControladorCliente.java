package com.bccog.easymenu.controladores;

import com.bccog.easymenu.entidades.cliente.Cliente;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Realiza a comunicação entre o modelo cliente e a visão (view)
 * @author Cristofer
 * @since 06/10/2017
 */

public class ControladorCliente {
    private static Cliente cliente_atual_ = null;

    /**
     * Configura o cliente da sessão atual carregando seus dados a partir do ID do usuário
     * Se o cliete não exsistir no BD, cria com os dados básicos
     * @param id Id do usuário logado
     * @param listener O listener que será chamado ao final da função
     */
    static void setClienteAtual(final String id, final ControladorUsuario.LogarUsuarioListener listener){
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference("/cliente/" + id + "/");

        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Verifica se o caminho para o usuário logado existe no BD
                if(!dataSnapshot.exists()){
                    //Se não existe, cria
                    Cliente cliente = new Cliente(ControladorUsuario.getAcct().getDisplayName(), null, null, null);
                    cliente.setId(id);
                    cliente_atual_ = cliente;

                    database.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            listener.onClienteLogado();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    database.setValue(cliente);
                }
                else{
                    cliente_atual_ = dataSnapshot.getValue(Cliente.class);

                    if (cliente_atual_ != null) {
                        cliente_atual_.setId(id);
                    }

                    listener.onClienteLogado();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onErroCliente();
            }
        });

    }

    /**
     * @return O cliente logado na sessão atual
     */
    public static Cliente getClienteAtual(){
        return cliente_atual_;
    }

}
