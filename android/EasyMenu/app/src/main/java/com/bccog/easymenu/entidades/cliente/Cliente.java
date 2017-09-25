package com.bccog.easymenu.entidades.cliente;

import com.bccog.easymenu.entidades.EntidadeBasica;
import com.bccog.easymenu.entidades.embutido.Endereco;
import com.bccog.easymenu.entidades.pedido.Pedido;
import com.bccog.easymenu.entidades.produto.Tag;
import com.google.firebase.database.Exclude;

import java.util.List;

/**
 * Representa um cliente que faz pedidos
 * @author Cristofer Oswald
 * @since 24/07/17
 */
public class Cliente extends EntidadeBasica {
    private String nome_;
    private String sexo_;
    private List<Endereco> enderecos_;
    private List<Tag> tags_;
    private List<Pedido> historico_pedidos_;

    public Cliente(){

    }

    public Cliente(String nome, String sexo, List<Endereco> enderecos, List<Tag> tags, List<Pedido> pedidos){
        this.nome_ = nome;
        this.sexo_ = sexo;
        this.enderecos_ = enderecos;
        this.tags_ = tags;
        this.historico_pedidos_ = pedidos;
    }

    public Cliente(String nome, String sexo, List<Endereco> enderecos, List<Tag> tags){
        this.nome_ = nome;
        this.sexo_ = sexo;
        this.enderecos_ = enderecos;
        this.tags_ = tags;
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

    @Exclude
    public List<Pedido> getHistoricoPedidos() {
        return historico_pedidos_;
    }

    public void setHistoricoPedidos(List<Pedido> historico_pedidos) {
        this.historico_pedidos_ = historico_pedidos;
    }
}