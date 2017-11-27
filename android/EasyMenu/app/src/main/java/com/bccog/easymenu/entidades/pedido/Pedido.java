package com.bccog.easymenu.entidades.pedido;

import com.bccog.easymenu.entidades.EntidadeBasica;
import com.bccog.easymenu.entidades.cliente.Cliente;
import com.bccog.easymenu.entidades.embutido.Endereco;
import com.bccog.easymenu.entidades.estabelecimento.Estabelecimento;
import com.bccog.easymenu.entidades.produto.ProdutoComTamanho;
import com.bccog.easymenu.entidades.produto.ProdutoPedido;
import com.bccog.easymenu.entidades.produto.ProdutoPrecoUnico;
import com.google.firebase.database.Exclude;

import java.util.List;

/**
 * Pedido do cliente que é feito pelo aplicativo
 * @author Cristofer Oswald
 * @since 06/10/2017
 */
public class Pedido extends EntidadeBasica {
    private String cliente_id_;
    private String estabelecimento_id_;
    private long timestamp_;
    private boolean delivery_;
    private String cupom_;
    private Endereco endereco_;
    private float valor_;
    private String comentario_;
    private Status status_;
    private List<ProdutoPedido> produtos_no_pedido_;

    private Cliente cliente_;
    private Estabelecimento estabelecimento_;

    public Pedido(){

    }

    public Pedido(Cliente cliente, long timestamp, boolean delivery, String cupom, Endereco endereco,
                  String comentario, Status status, List<ProdutoPedido> produtos_no_pedido,
                  String estabelecimento) {
        this.cliente_ = cliente;
        this.cliente_id_ = cliente.getId();
        this.timestamp_ = timestamp;
        this.delivery_ = delivery;
        this.cupom_ = cupom;
        this.endereco_ = endereco;
        this.comentario_ = comentario;
        this.status_ = status;
        this.produtos_no_pedido_ = produtos_no_pedido;
        this.estabelecimento_id_ = estabelecimento;
        this.valor_ = 0;
    }

    public String getClienteId() {
        return cliente_id_;
    }

    public void setClienteId(String cliente_id) {
        this.cliente_id_ = cliente_id;
    }

    public String getEstabelecimentoId() {
        return estabelecimento_id_;
    }

    public void setEstabelecimentoId(String estabelecimento_id) {
        this.estabelecimento_id_ = estabelecimento_id;
    }

    @Exclude
    public Cliente getCliente() {
        return cliente_;
    }

    public void setCliente(Cliente cliente) {
        this.cliente_ = cliente;
    }

    public long getTimestamp() {
        return timestamp_;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp_ = timestamp;
    }

    public boolean isDelivery() {
        return delivery_;
    }

    public void setDelivery(boolean delivery) {
        this.delivery_ = delivery;
    }

    public String getCupom() {
        return cupom_;
    }

    public void setCupom(String cupom) {
        this.cupom_ = cupom;
    }

    public Endereco getEndereco() {
        return endereco_;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco_ = endereco;
    }

    public float getValor() {
        return valor_;
    }

    public void setValor(float valor) {
        this.valor_ = valor;
    }

    public String getComentario() {
        return comentario_;
    }

    public void setComentario(String comentario) {
        this.comentario_ = comentario;
    }

    public Status getStatus() {
        return status_;
    }

    public void setStatus(Status status) {
        this.status_ = status;
    }

    public List<ProdutoPedido> getProdutosNoPedido() {
        return produtos_no_pedido_;
    }

    public void setProdutosNoPedido(List<ProdutoPedido> produtos_no_pedido) {
        this.produtos_no_pedido_ = produtos_no_pedido;
    }

    public void adicionarProdutoPedido(ProdutoPedido p){
        produtos_no_pedido_.add(p);
    }

    @Exclude
    public Estabelecimento getEstabelecimento() {
        return estabelecimento_;
    }

    public void setEstabelecimento(Estabelecimento estabelecimento) {
        this.estabelecimento_ = estabelecimento;
    }

    public void calcularPreco(){
        for(ProdutoPedido p : produtos_no_pedido_){
            switch (p.getTamanho()){
                case UNICO:
                    valor_ = valor_ + ((ProdutoPrecoUnico)p.getProdutoO()).getPreco();
                    break;

                case PEQUENO:
                    valor_ = valor_ + ((ProdutoComTamanho)p.getProdutoO()).getPrecoP();
                    break;

                case MEDIO:
                    valor_ = valor_ + ((ProdutoComTamanho)p.getProdutoO()).getPrecoM();
                    break;

                case GRANDE:
                    valor_ = valor_ + ((ProdutoComTamanho)p.getProdutoO()).getPrecoG();
                    break;
            }
        }
    }
}
