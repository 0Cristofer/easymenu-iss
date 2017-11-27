package com.bccog.EMM.bd.entidades.pedido;

import com.bccog.EMM.bd.EntidadeBasica;
import com.bccog.EMM.bd.entidades.cliente.Cliente;
import com.bccog.EMM.bd.entidades.embutido.Endereco;
import com.bccog.EMM.bd.entidades.estabelecimento.Estabelecimento;
import com.bccog.EMM.bd.entidades.produto.ProdutoComTamanho;
import com.bccog.EMM.bd.entidades.produto.ProdutoPedido;
import com.bccog.EMM.bd.entidades.produto.ProdutoPrecoUnico;
import com.google.gson.annotations.SerializedName;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Pedido do cliente que é feito pelo aplicativo
 * @author Cristofer Oswald
 * @author Bruno Cesar
 * @since 06/06/2017
 */
public class Pedido extends EntidadeBasica {
        @SerializedName("clienteId")
        private String cliente_id_;
        @SerializedName("estabelecimentoId")
        private String estabelecimento_id_;
        @SerializedName("timestamp")
        private long timestamp_;
        @SerializedName("timestampFinal")
        private long timestamp_final_;
        @SerializedName("delivery")
        private boolean delivery_;
        @SerializedName("cupom")
        private String cupom_;
        @SerializedName("enereco")
        private Endereco endereco_;
        @SerializedName("valor")
        private float valor_;
        @SerializedName("comentario")
        private String comentario_;
        @SerializedName("status")
        private Status status_;
        @SerializedName("produtosNoPedido")
        private List<ProdutoPedido> produtos_no_pedido_;

        private transient Cliente cliente_;
        private transient Estabelecimento estabelecimento_;


        public Pedido(Cliente cliente, long timestamp, boolean delivery, String cupom, Endereco endereco,
                      float valor, String comentario, Status status, List<ProdutoPedido> produtos_no_pedido,
                      Estabelecimento estabelecimento) {
            this.cliente_ = cliente;
            this.timestamp_ = timestamp;
            this.delivery_ = delivery;
            this.cupom_ = cupom;
            this.endereco_ = endereco;
            this.valor_ = valor;
            this.comentario_ = comentario;
            this.status_ = status;
            this.produtos_no_pedido_ = produtos_no_pedido;
            this.estabelecimento_ = estabelecimento;
        }

    public long getTimestamp_final_() {
        return timestamp_final_;
    }

    public void setTimestamp_final_(long timestamp_final_) {
        this.timestamp_final_ = timestamp_final_;
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
                        valor_ = valor_ + ((ProdutoPrecoUnico)p.getProduto()).getPreco();
                        break;

                    case PEQUENO:
                        valor_ = valor_ + ((ProdutoComTamanho)p.getProduto()).getPrecoP();
                        break;

                    case MEDIO:
                        valor_ = valor_ + ((ProdutoComTamanho)p.getProduto()).getPrecoM();
                        break;

                    case GRANDE:
                        valor_ = valor_ + ((ProdutoComTamanho)p.getProduto()).getPrecoG();
                        break;
                }
            }
        }

        public DateTime getDateTime(){
            return new DateTime(timestamp_);
        }

        @Override
        public String toString() {
            String retorno = "Cliente: " + getCliente().getNome() + " comprou " + getValor() + " reais";
            for(ProdutoPedido p : produtos_no_pedido_){
                retorno = retorno + "\nComprou ";
                if(p.getTamanho() == ProdutoPedido.TamanhoProduto.UNICO){
                    ProdutoPrecoUnico pd = ((ProdutoPrecoUnico)p.getProduto());
                    retorno = retorno + pd.getNome() + " : " + pd.getPreco();
                }
                else{
                    ProdutoComTamanho pd = ((ProdutoComTamanho)p.getProduto());
                    switch (p.getTamanho()){
                        case PEQUENO:
                            retorno = retorno + pd.getNome() + " : " + pd.getPrecoP();
                            break;
                        case MEDIO:
                            retorno = retorno + pd.getNome() + " : " + pd.getPrecoM();
                            break;
                        case GRANDE:
                            retorno = retorno + pd.getNome() + " : " + pd.getPrecoG();
                            break;
                    }
                }

            }

            return retorno;
        }

    public String produtosToString(){
        String retorno= "";

        for(ProdutoPedido p : produtos_no_pedido_) {
            if (p.getTamanho() == ProdutoPedido.TamanhoProduto.UNICO) {
                ProdutoPrecoUnico pd = ((ProdutoPrecoUnico) p.getProduto());
                retorno = retorno + pd.getNome() + " : " + pd.getPreco() + "\n";
            } else {
                ProdutoComTamanho pd = ((ProdutoComTamanho) p.getProduto());
                switch (p.getTamanho()) {
                    case PEQUENO:
                        retorno = retorno + pd.getNome() + " : " + pd.getPrecoP();
                        break;
                    case MEDIO:
                        retorno = retorno + pd.getNome() + " : " + pd.getPrecoM();
                        break;
                    case GRANDE:
                        retorno = retorno + pd.getNome() + " : " + pd.getPrecoG();
                        break;
                }
                retorno = retorno + "\n";
            }
        }

        return retorno;
    }

        @Override
        public boolean equals(Object obj) {
            return (obj instanceof Pedido) && super.equals(obj);
        }
}
