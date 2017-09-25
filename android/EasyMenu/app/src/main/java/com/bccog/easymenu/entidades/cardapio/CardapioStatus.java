package com.bccog.easymenu.entidades.cardapio;

/**
 * Representa os possíveis estados de um cardápio
 * @author Cristofer Oswald
 * @since 07/06/17
 */
public enum CardapioStatus {
    ATIVO("Ativo"), ANALISE("Análise"), DORMINDO("Dormindo"), RECUSADO("Recusado");

    private String nome_;

    CardapioStatus(String nome){
        this.nome_ = nome;
    }

    public String getNome(){
        return nome_;
    }

}

