package com.bccog.easymenu.entidades.cardapio;

/**
 * Representa os possíveis estados de um cardápio
 * @author Diogo Almeida
 * @since 06/10/2017
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

