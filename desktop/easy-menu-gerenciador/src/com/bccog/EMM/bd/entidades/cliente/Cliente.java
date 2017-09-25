package com.bccog.EMM.bd.entidades.cliente;

import com.bccog.EMM.bd.EntidadeBasica;
import com.bccog.EMM.bd.entidades.embutido.Endereco;
import com.bccog.EMM.bd.entidades.pedido.Pedido;
import com.bccog.EMM.bd.entidades.produto.Tag;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Representa um cliente que faz pedidos
 * @author Cristofer Oswald
 * @since 24/07/17
 */
public class Cliente extends EntidadeBasica {
    @SerializedName("nome")
    private String nome_;
    @SerializedName("sexo")
    private String sexo_;
    @SerializedName("enderecos")
    private List<Endereco> enderecos_;
    @SerializedName("tags")
    private List<Tag> tags_;
    private transient List<Pedido> historico_pedidos_;

    public Cliente(){

    }

    public Cliente(String nome, String sexo, List<Endereco> enderecos, List<Tag> tags, List<Pedido> pedidos){
        this.nome_ = nome;
        this.sexo_ = sexo;
        this.enderecos_ = enderecos;
        this.tags_ = tags;
        this.historico_pedidos_ = pedidos;
    }

    public String getNome() {
        return nome_;
    }

    public void setNome(String nome) {
        this.nome_ = nome;
    }

    public String getSexo() {
        return sexo_;
    }

    public void setSexo(String sexo) {
        this.sexo_ = sexo;
    }

    public List<Endereco> getEnderecos() {
        return enderecos_;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos_ = enderecos;
    }

    public List<Tag> getTags() {
        return tags_;
    }

    public void setTags(List<Tag> tags) {
        this.tags_ = tags;
    }

    public List<Pedido> getHistoricoPedidos() {
        return historico_pedidos_;
    }

    public void setHistoricoPedidos(List<Pedido> historico_pedidos) {
        this.historico_pedidos_ = historico_pedidos;
    }

    @Override
    public String toString(){
        return this.nome_ + " " + this.sexo_;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Cliente) && super.equals(obj);
    }
}
